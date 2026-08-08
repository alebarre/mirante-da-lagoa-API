package br.com.mirantedalagoa.service;

import br.com.mirantedalagoa.dto.FuncionarioDTO;
import br.com.mirantedalagoa.dto.FuncionarioOcorrenciaDTO;
import br.com.mirantedalagoa.dto.FuncionarioResumoFinanceiroDTO;
import br.com.mirantedalagoa.model.Funcionario;
import br.com.mirantedalagoa.model.FuncionarioOcorrencia;
import br.com.mirantedalagoa.repository.FuncionarioOcorrenciaRepository;
import br.com.mirantedalagoa.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class FuncionarioService implements CrudService<FuncionarioDTO, FuncionarioDTO> {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private FuncionarioOcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private ParametroCondominioService parametroService;

    @Override
    public List<FuncionarioDTO> listAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public FuncionarioDTO findById(UUID id) {
        return toDTO(repository.findById(id).orElseThrow(() -> new RuntimeException("Funcionario nao encontrado")));
    }

    @Override
    @Transactional
    public FuncionarioDTO create(FuncionarioDTO dto) {
        Funcionario entity = toEntity(dto);
        entity.setCreatedAt(Instant.now());
        calcularEncargos(entity);
        return toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public FuncionarioDTO update(UUID id, FuncionarioDTO dto) {
        Funcionario existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Funcionario nao encontrado"));
        updateEntity(existing, dto);
        existing.setUpdatedAt(Instant.now());
        calcularEncargos(existing);
        return toDTO(repository.save(existing));
    }

    public void calcularEncargos(Funcionario f) {
        if (f == null || f.getSalary() == null || f.getSalary().compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        if (!"CLT".equalsIgnoreCase(f.getWorkRegime())) {
            return;
        }
        java.util.Map<String, BigDecimal> percentuais = parametroService.findPercentuaisFolha();
        BigDecimal salario = f.getSalary();
        f.setInssEmployer(arredondar(calcular(salario, percentuais.get("INSS_PATRONAL_PERCENTUAL"))));
        f.setFgts(arredondar(calcular(salario, percentuais.get("FGTS_PERCENTUAL"))));
        f.setIrrf(arredondar(calcular(salario, percentuais.get("IRRF_PERCENTUAL"))));
        f.setTransportAllowance(arredondar(calcular(salario, percentuais.get("TRANSPORTE_PERCENTUAL"))));
        f.setMealAllowance(arredondar(calcular(salario, percentuais.get("ALIMENTACAO_PERCENTUAL"))));
        f.setHealthInsurance(arredondar(calcular(salario, percentuais.get("SAUDE_PERCENTUAL"))));
        f.setOtherBenefits(arredondar(calcular(salario, percentuais.get("BENEFICIOS_OUTROS_PERCENTUAL"))));
        f.setThirteenthSalaryProvision(arredondar(calcular(salario, percentuais.get("DECIMO_TERCEIRO_PERCENTUAL"))));
        f.setVacationProvision(arredondar(calcular(salario, percentuais.get("FERIAS_PERCENTUAL"))));
        f.setVacationThirdProvision(arredondar(calcular(salario, percentuais.get("FERIAS_TERCO_PERCENTUAL"))));
        f.setSeveranceFineProvision(arredondar(calcular(salario, percentuais.get("MULTA_RESCISORIA_PERCENTUAL"))));
    }

    private BigDecimal calcular(BigDecimal base, BigDecimal percentual) {
        if (base == null || percentual == null) {
            return BigDecimal.ZERO;
        }
        return base.multiply(percentual);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private FuncionarioDTO toDTO(Funcionario e) {
        List<FuncionarioOcorrenciaDTO> ocorrencias = ocorrenciaRepository.findByFuncionarioIdOrderByDataDesc(e.getId()).stream()
            .map(this::toOcorrenciaDTO)
            .toList();

        return new FuncionarioDTO(
            e.getId(), e.getFullName(), e.getCpf(), e.getRg(), e.getBirthDate(), e.getPhone(),
            e.getEmail(), e.getAddress(), e.getPosition(), e.getDepartment(), e.getHireDate(),
            e.getTerminationDate(), e.getSalary(), e.getWorkRegime(), e.getBankAccount(), e.getNotes(),
            e.getInssEmployer(), e.getFgts(), e.getIrrf(), e.getTransportAllowance(),
            e.getMealAllowance(), e.getHealthInsurance(), e.getOtherBenefits(),
            e.getThirteenthSalaryProvision(), e.getVacationProvision(), e.getVacationThirdProvision(),
            e.getSeveranceFineProvision(), ocorrencias
        );
    }

    private FuncionarioOcorrenciaDTO toOcorrenciaDTO(FuncionarioOcorrencia e) {
        return new FuncionarioOcorrenciaDTO(
            e.getId(),
            e.getFuncionario() != null ? e.getFuncionario().getId() : null,
            e.getTipo(), e.getData(), e.getDescricao(), e.getAnexo()
        );
    }

    private Funcionario toEntity(FuncionarioDTO dto) {
        Funcionario entity = new Funcionario();
        updateEntity(entity, dto);
        return entity;
    }

    private void updateEntity(Funcionario entity, FuncionarioDTO dto) {
        entity.setFullName(dto.fullName());
        entity.setCpf(dto.cpf());
        entity.setRg(dto.rg());
        entity.setBirthDate(dto.birthDate());
        entity.setPhone(dto.phone());
        entity.setEmail(dto.email());
        entity.setAddress(dto.address());
        entity.setPosition(dto.position());
        entity.setDepartment(dto.department());
        entity.setHireDate(dto.hireDate());
        entity.setTerminationDate(dto.terminationDate());
        entity.setSalary(dto.salary());
        entity.setWorkRegime(dto.workRegime());
        entity.setBankAccount(dto.bankAccount());
        entity.setNotes(dto.notes());
        entity.setInssEmployer(dto.inssEmployer());
        entity.setFgts(dto.fgts());
        entity.setIrrf(dto.irrf());
        entity.setTransportAllowance(dto.transportAllowance());
        entity.setMealAllowance(dto.mealAllowance());
        entity.setHealthInsurance(dto.healthInsurance());
        entity.setOtherBenefits(dto.otherBenefits());
        entity.setThirteenthSalaryProvision(dto.thirteenthSalaryProvision());
        entity.setVacationProvision(dto.vacationProvision());
        entity.setVacationThirdProvision(dto.vacationThirdProvision());
        entity.setSeveranceFineProvision(dto.severanceFineProvision());
    }

    public FuncionarioResumoFinanceiroDTO calcularResumoFinanceiro() {
        LocalDate hoje = LocalDate.now();
        List<Funcionario> ativos = repository.findAll().stream()
            .filter(f -> f.getTerminationDate() == null || !f.getTerminationDate().isBefore(hoje))
            .toList();

        BigDecimal totalSalarios = ativos.stream().map(f -> defaultValue(f.getSalary())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalInssEmployer = ativos.stream().map(f -> defaultValue(f.getInssEmployer())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalFgts = ativos.stream().map(f -> defaultValue(f.getFgts())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalIrrf = ativos.stream().map(f -> defaultValue(f.getIrrf())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalTransportAllowance = ativos.stream().map(f -> defaultValue(f.getTransportAllowance())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalMealAllowance = ativos.stream().map(f -> defaultValue(f.getMealAllowance())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalHealthInsurance = ativos.stream().map(f -> defaultValue(f.getHealthInsurance())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalOtherBenefits = ativos.stream().map(f -> defaultValue(f.getOtherBenefits())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalThirteenthSalaryProvision = ativos.stream().map(f -> defaultValue(f.getThirteenthSalaryProvision())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalVacationProvision = ativos.stream().map(f -> defaultValue(f.getVacationProvision())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalVacationThirdProvision = ativos.stream().map(f -> defaultValue(f.getVacationThirdProvision())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalSeveranceFineProvision = ativos.stream().map(f -> defaultValue(f.getSeveranceFineProvision())).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalEncargosBeneficios = totalInssEmployer
            .add(totalFgts).add(totalIrrf).add(totalTransportAllowance)
            .add(totalMealAllowance).add(totalHealthInsurance).add(totalOtherBenefits);

        BigDecimal totalProvisoes = totalThirteenthSalaryProvision
            .add(totalVacationProvision).add(totalVacationThirdProvision).add(totalSeveranceFineProvision);

        BigDecimal custoTotalMensal = totalSalarios.add(totalEncargosBeneficios).add(totalProvisoes);

        return new FuncionarioResumoFinanceiroDTO(
            ativos.size(),
            arredondar(totalSalarios),
            arredondar(totalInssEmployer),
            arredondar(totalFgts),
            arredondar(totalIrrf),
            arredondar(totalTransportAllowance),
            arredondar(totalMealAllowance),
            arredondar(totalHealthInsurance),
            arredondar(totalOtherBenefits),
            arredondar(totalEncargosBeneficios),
            arredondar(totalThirteenthSalaryProvision),
            arredondar(totalVacationProvision),
            arredondar(totalVacationThirdProvision),
            arredondar(totalSeveranceFineProvision),
            arredondar(totalProvisoes),
            arredondar(custoTotalMensal)
        );
    }

    private BigDecimal defaultValue(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    private BigDecimal arredondar(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
