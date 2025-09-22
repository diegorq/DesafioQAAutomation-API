#language: pt
Funcionalidade: Biblioteca DemoQA

  @api
  Cenário: Aluguel de Livros com Novo Usuário

    Dado que eu cadastre um novo usuário
    E gere um token de acesso
    E esteja autorizado
    Quando eu listar os livros disponíveis
    E realizar o aluguel de 2 livros
    Então os livros alugados são listados nos dados do meu usuário

