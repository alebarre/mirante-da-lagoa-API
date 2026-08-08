package br.com.mirantedalagoa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FuncionarioDTO(
    UUID id,
    String fullName,
    String cpf,
    String rg,
    LocalDate birthDate,
    String phone,
    String email,
    String address,
    String position,
    String department,
    LocalDate hireDate,
    LocalDate terminationDate,
    BigDecimal salary,
    String workRegime,
    String bankAccount,
    String notes,

    // Encargos e benefícios mensais
    BigDecimal inssEmployer,
    BigDecimal fgts,
    BigDecimal irrf,
    BigDecimal transportAllowance,
    BigDecimal mealAllowance,
    BigDecimal healthInsurance,
    BigDecimal otherBenefits,

    // Provisões trabalhistas mensais
    BigDecimal thirteenthSalaryProvision,
    BigDecimal vacationProvision,
    BigDecimal vacationThirdProvision,
    BigDecimal severanceFineProvision,

    List<FuncionarioOcorrenciaDTO> ocorrencias
) {}
