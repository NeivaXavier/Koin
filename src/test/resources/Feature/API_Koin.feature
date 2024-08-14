#language:pt


@regressao
Funcionalidade: Requisição Criar, atualizar e consultar

  Cenário: Criar um novo post e verificar a resposta
    Dado que eu tenha um payload válido para criar um novo post
    Quando eu enviar uma requisição POST para o endpoint
    Então o status code da resposta deve ser 201
    E a resposta deve conter os dados corretos
    
    
   

  Cenario: Atualizar um post e verificar a resposta
    Dado que eu tenha um payload válido para atualizar um post
    Quando eu enviar uma requisição PUT para o endpoint
    Então o status code da resposta deve ser 200
    E a resposta deve conter os dados atualizados corretamente
    

  Cenario: Listar todos os posts e verificar a resposta
    Quando eu enviar uma requisição GET para listar todos os posts
    Então o status code da resposta da consulta deve ser 200
    E a resposta deve conter a lista de posts
    
