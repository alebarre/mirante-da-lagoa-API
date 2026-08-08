package br.com.mirantedalagoa.dto;

import java.math.BigDecimal;

public record FuncionarioResumoFinanceiroDTO(
    int totalFuncionarios,
    BigDecimal totalSalarios,
    BigDecimal totalInssEmployer,
    BigDecimal totalFgts,
    BigDecimal totalIrrf,
    BigDecimal totalTransportAllowance,
    BigDecimal totalMealAllowance,
    BigDecimal totalHealthInsurance,
    BigDecimal totalOtherBenefits,
    BigDecimal totalEncargosBeneficios,
    BigDecimal totalThirteenthSalaryProvision,
    BigDecimal totalVacationProvision,
    BigDecimal totalVacationThirdProvision,
    BigDecimal totalSeveranceFineProvision,
    BigDecimal totalProvisoes,
    BigDecimal custoTotalMensal
) {}
