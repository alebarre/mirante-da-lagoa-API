package br.com.mirantedalagoa.config;

import br.com.mirantedalagoa.model.Role;
import br.com.mirantedalagoa.model.User;
import br.com.mirantedalagoa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@mirantedalagoa.com.br").isEmpty()) {
            User admin = User.builder()
                .createdAt(Instant.now())
                .email("admin@mirantedalagoa.com.br")
                .fullName("Administrador Mirante da Lagoa")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .active(true)
                .build();
            userRepository.save(admin);
            System.out.println("Usuario admin criado: admin@mirantedalagoa.com.br / admin123");
        }
    }
}