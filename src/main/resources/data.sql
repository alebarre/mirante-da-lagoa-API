-- Seed de funcionários para o condomínio Mirante da Lagoa - Saquarema/RJ
INSERT INTO funcionarios (id, full_name, cpf, rg, birth_date, phone, email, address, position, department, hire_date, termination_date, salary, work_regime, bank_account, notes, created_at, updated_at)
VALUES
  (gen_random_uuid(), 'João Carlos da Silva', '123.456.789-00', '12.345.678-9', '1985-03-15', '(21) 99999-1111', 'joao.silva@mirantedalagoa.com.br', 'Rua das Palmeiras, 45 - Saquarema/RJ', 'Porteiro', 'Portaria', '2022-01-10', null, 2500.00, 'CLT', 'Banco do Brasil - Ag 1234 / CC 56789-0', 'Responsável pelo turno da manhã na portaria principal.', now(), null),
  (gen_random_uuid(), 'Maria Aparecida Souza', '987.654.321-00', '98.765.432-1', '1990-07-22', '(21) 98888-2222', 'maria.souza@mirantedalagoa.com.br', 'Av. Beira Mar, 120 - Saquarema/RJ', 'Auxiliar de Limpeza', 'Limpeza', '2021-05-18', null, 1900.00, 'CLT', 'Itaú - Ag 4321 / CC 98765-4', 'Atua na limpeza das áreas comuns e piscina.', now(), null);
