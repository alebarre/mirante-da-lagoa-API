-- Seed completo para testes do condomínio Mirante da Lagoa - Saquarema/RJ
-- Pode ser rodado diretamente no PostgreSQL

-- Funcionários
INSERT INTO funcionarios (id, full_name, cpf, rg, birth_date, phone, email, address, position, department, hire_date, termination_date, salary, work_regime, bank_account, notes, created_at, updated_at)
VALUES
  (gen_random_uuid(), 'João Carlos da Silva', '123.456.789-00', '12.345.678-9', '1985-03-15', '(21) 99999-1111', 'joao.silva@mirantedalagoa.com.br', 'Rua das Palmeiras, 45 - Saquarema/RJ', 'Porteiro', 'Portaria', '2022-01-10', null, 2500.00, 'CLT', 'Banco do Brasil - Ag 1234 / CC 56789-0', 'Responsável pelo turno da manhã na portaria principal.', now(), null),
  (gen_random_uuid(), 'Maria Aparecida Souza', '987.654.321-00', '98.765.432-1', '1990-07-22', '(21) 98888-2222', 'maria.souza@mirantedalagoa.com.br', 'Av. Beira Mar, 120 - Saquarema/RJ', 'Auxiliar de Limpeza', 'Limpeza', '2021-05-18', null, 1900.00, 'CLT', 'Itaú - Ag 4321 / CC 98765-4', 'Atua na limpeza das áreas comuns e piscina.', now(), null);

-- Compromissos
INSERT INTO compromissos (id, title, description, scheduled_at, location, responsible, status, created_at, updated_at)
VALUES
  (gen_random_uuid(), 'Reunião do Conselho', 'Reunião mensal com síndico e subsíndicos.', '2026-08-10 19:00:00', 'Salão de festas', 'Síndico', 'AGENDADO', now(), null),
  (gen_random_uuid(), 'Vistoria hidráulica', 'Vistoria preventiva na caixa d''água e bombas.', '2026-08-12 09:00:00', 'Casa de máquinas', 'Encanador', 'AGENDADO', now(), null);

-- Obrigações Trabalhistas
INSERT INTO obrigacoes_trabalhistas (id, name, description, periodicity, due_date, completed_at, responsible, status, notes, created_at, updated_at)
VALUES
  (gen_random_uuid(), 'Folha de pagamento', 'Pagamento de salários dos funcionários.', 'MENSAL', '2026-08-05', null, 'Administradora', 'PENDENTE', 'Aguardar fechamento do mês.', now(), null),
  (gen_random_uuid(), 'FGTS', 'Depósito do FGTS dos funcionários.', 'MENSAL', '2026-08-07', null, 'Administradora', 'PENDENTE', 'Vencimento até dia 7.', now(), null),
  (gen_random_uuid(), 'IRRF e INSS', 'Recolhimento de impostos trabalhistas.', 'MENSAL', '2026-08-20', null, 'Contador', 'PENDENTE', 'Guias DARF e GPS.', now(), null);

-- Moradores
INSERT INTO moradores (id, full_name, cpf, rg, birth_date, phone, email, block, apartment, parking_spot, pets, owner, move_in_date, move_out_date, emergency_contact, notes, created_at, updated_at)
VALUES
  (gen_random_uuid(), 'Roberto Almeida', '111.222.333-44', '11.222.333-4', '1978-11-05', '(21) 97777-3333', 'roberto.almeida@email.com', 'A', '101', 'A-01', null, true, '2020-02-15', null, '(21) 96666-4444', 'Proprietário e morador.', now(), null),
  (gen_random_uuid(), 'Fernanda Lima', '222.333.444-55', '22.333.444-5', '1992-04-28', '(21) 95555-6666', 'fernanda.lima@email.com', 'A', '102', 'A-02', '1 cachorro', false, '2021-08-10', null, '(21) 94444-7777', 'Inquilina.', now(), null);

-- Eventos
INSERT INTO eventos (id, title, description, start_at, end_at, location, organizer, status, restricted_to_residents, max_participants, notes, created_at, updated_at)
VALUES
  (gen_random_uuid(), 'Festa Junina 2026', 'Festa junina para moradores e familiares.', '2026-06-15 18:00:00', '2026-06-15 23:00:00', 'Salão de festas', 'Comissão de Eventos', 'CONFIRMADO', true, 120, 'Traje à carater é opcional.', now(), null),
  (gen_random_uuid(), 'Assembleia Geral', 'Assembleia ordinária de prestação de contas.', '2026-09-20 10:00:00', '2026-09-20 12:00:00', 'Salão de festas', 'Síndico', 'AGENDADO', true, null, 'Todos os proprietários convocados.', now(), null);

-- Parâmetros de encargos e provisões trabalhistas (CLT vigente)
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
  (gen_random_uuid(), 'FOLHA_PAGAMENTO', 'MULTA_RESCISORIA_PERCENTUAL', 'Provisão de multa rescisória (FGTS + 40%)', 0.0320, null, now());
