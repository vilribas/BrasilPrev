@tag
Feature: Cadastrar Usuarios na base de dados

  @tag1
  Scenario Outline: Usuario chama API para cadastrar novo Usuario na base de dados
    Given que o Usuario insere "<Nome>", "<Cpf>", "<Logradouro>", <Numero>, "<Complemento>", "<Bairro>", "<Cidade>", "<Estado>", "<ddd>" e "<Telefone>" na Api
    Then a API efetua o cadastro do Usuario com sucesso
    
    Examples: 
      | Nome          | Cpf         | Logradouro     | Numero  | Complemento | Bairro              | Cidade    | Estado   | ddd | Telefone  |
      | Lucas Neto    | 68038986016 | Rua Clélia     | 569     | Ap. 21      | Lapa                | São Paulo | SP       |  11 | 972510748 |
    
  @tag2
  Scenario Outline: Usuario chama API para cadastrar Usuario com CPF duplicado na base de dados
    Given que o Usuario insere CPF "<Cpf>" duplicado
    Then a API nao efetua o cadastro do Usuario com CPF "<Cpf>" duplicado
    
    Examples: 
      | Cpf         |
      | 68038986016 |
      
  @tag3
  Scenario Outline: Usuario chama API para cadastrar Usuario com Telefone duplicado na base de dados
    Given que o Usuario insere DDD "<ddd>" e Telefone "<Telefone>" duplicados
    Then a API nao efetua o cadastro do Usuario com DDD "<ddd>" e Telefone "<Telefone>" duplicados
    
    Examples: 
      | ddd | Telefone  |
      |  11 | 972510748 |