-- Seed de funcionários para o condomínio Mirante da Lagoa - Saquarema/RJ
INSERT INTO funcionarios (id, full_name, cpf, rg, birth_date, phone, email, address, position, department, hire_date, termination_date, salary, work_regime, bank_account, notes, created_at, updated_at)
VALUES
  (gen_random_uuid(), 'João Carlos da Silva', '123.456.789-00', '12.345.678-9', '1985-03-15', '(21) 99999-1111', 'joao.silva@mirantedalagoa.com.br', 'Rua das Palmeiras, 45 - Saquarema/RJ', 'Porteiro', 'Portaria', '2022-01-10', null, 2500.00, 'CLT', 'Banco do Brasil - Ag 1234 / CC 56789-0', 'Responsável pelo turno da manhã na portaria principal.', now(), null),
  (gen_random_uuid(), 'Maria Aparecida Souza', '987.654.321-00', '98.765.432-1', '1990-07-22', '(21) 98888-2222', 'maria.souza@mirantedalagoa.com.br', 'Av. Beira Mar, 120 - Saquarema/RJ', 'Auxiliar de Limpeza', 'Limpeza', '2021-05-18', null, 1900.00, 'CLT', 'Itaú - Ag 4321 / CC 98765-4', 'Atua na limpeza das áreas comuns e piscina.', now(), null);

-- Seed de parâmetros de encargos e provisões trabalhistas (CLT vigente)
INSERT INTO parametros_condominio (id, categoria, chave, descricao, valor_numerico, valor_texto, atualizado_em)
VALUES
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'INSS_PATRONAL_PERCENTUAL', 'INSS patronal (aproximado para pequenas empresas / Simples diferenciado)', 0.2000, null, now()),
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'FGTS_PERCENTUAL', 'FGTS mensal sobre a remuneração (8%)', 0.0800, null, now()),
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'IRRF_PERCENTUAL', 'IRRF retido na fonte (teto médio simplificado)', 0.0750, null, now()),
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'TRANSPORTE_PERCENTUAL', 'Vale-transporte (6%, limitado legalmente)', 0.0600, null, now()),
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'ALIMENTACAO_PERCENTUAL', 'Vale-alimentação/refeição estimado', 0.0800, null, now()),
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'SAUDE_PERCENTUAL', 'Plano de saúde estimado sobre salário', 0.0500, null, now()),
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'BENEFICIOS_OUTROS_PERCENTUAL', 'Outros benefícios/eventuais', 0.0200, null, now()),
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'DECIMO_TERCEIRO_PERCENTUAL', 'Provisão de 13º salário (1/12 por mês)', 0.0833, null, now()),
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'FERIAS_PERCENTUAL', 'Provisão de férias (1/12 por mês)', 0.0833, null, now()),
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'FERIAS_TERCO_PERCENTUAL', 'Provisão de 1/3 de férias', 0.0278, null, now()),
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'MULTA_RESCISORIA_PERCENTUAL', 'Provisão de multa rescisória (FGTS + 40%)', 0.0320, null, now())
ON CONFLICT (chave) DO NOTHING;
