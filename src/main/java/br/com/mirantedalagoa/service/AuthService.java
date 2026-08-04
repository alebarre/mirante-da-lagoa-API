package br.com.mirantedalagoa.service;

import br.com.mirantedalagoa.dto.*;
import br.com.mirantedalagoa.model.PasswordResetCode;
import br.com.mirantedalagoa.model.Role;
import br.com.mirantedalagoa.model.User;
import br.com.mirantedalagoa.repository.PasswordResetCodeRepository;
import br.com.mirantedalagoa.repository.UserRepository;
import br.com.mirantedalagoa.security.JwtUtils;
import br.com.mirantedalagoa.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetCodeRepository passwordResetCodeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails.getId());

        return new AuthResponse(
            accessToken,
            refreshToken,
            "Bearer",
            900L,
            userDetails.getEmail(),
            userDetails.getFullName(),
            Role.valueOf(userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", ""))
        );
    }

    @Transactional
    public UserDTO register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("E-mail já cadastrado");
        }
        User user = User.builder()
            .fullName(request.fullName())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role(request.role())
            .active(true)
            .build();
        User saved = userRepository.save(user);
        return toUserDTO(saved);
    }

    public AuthResponse refresh(RefreshTokenRequest request) {
        if (!jwtUtils.validateRefreshToken(request.refreshToken())) {
            throw new BadCredentialsException("Refresh token inválido ou expirado");
        }
        String userId = jwtUtils.getUserIdFromToken(request.refreshToken());
        User user = userRepository.findById(UUID.fromString(userId))
            .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));
        String accessToken = jwtUtils.generateAccessTokenFromEmail(user.getEmail(), user.getFullName(), user.getId());
        String refreshToken = jwtUtils.generateRefreshToken(user.getId());
        return new AuthResponse(accessToken, refreshToken, "Bearer", 900L, user.getEmail(), user.getFullName(), user.getRole());
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.email());
        if (optionalUser.isEmpty()) {
            logger.info("Solicitação de recuperação para e-mail não cadastrado: {}", request.email());
            return;
        }

        User user = optionalUser.get();
        String code = String.format("%05d", new Random().nextInt(100000));
        PasswordResetCode resetCode = PasswordResetCode.builder()
            .email(user.getEmail())
            .code(code)
            .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
            .used(false)
            .build();
        passwordResetCodeRepository.save(resetCode);

        try {
            emailService.sendPasswordResetCode(user.getEmail(), code);
        } catch (Exception e) {
            logger.error("Falha ao enviar e-mail de recuperação para {}: {}", user.getEmail(), e.getMessage());
            throw new RuntimeException("Não foi possível enviar o e-mail de recuperação. Verifique a configuração SMTP.");
        }
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetCode code = passwordResetCodeRepository
            .findTopByEmailAndCodeAndUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(
                request.email(), request.code(), Instant.now())
            .orElseThrow(() -> new RuntimeException("Código inválido ou expirado"));

        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        code.setUsed(true);
        passwordResetCodeRepository.save(code);
    }

    private UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getFullName(), user.getRole(), user.isActive());
    }
}
