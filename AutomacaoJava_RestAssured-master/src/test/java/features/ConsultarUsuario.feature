@tag
Feature: Consultar Usuarios na base de dados

  @tag1
  Scenario Outline: Usuario chama API para consultar Usuario passando DDD e Telefone
    Given que o Usuario insere DDD "<ddd>" e Telefone "<Telefone>" na busca
    Then a API retorna o usuario cadastrado na base de dados com DDD "<ddd>" e Telefone "<Telefone>"
    
    Examples: 
      | ddd | Telefone  |
      |  11 | 972510748 |