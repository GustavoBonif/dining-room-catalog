Projeto Dining-Room | Arquitetura de Microsserviços

Esta versão da aplicação Dining-Room foi desenvolvido com arquitetura de microsserviços para o meu Trabalho de Conclusão de Curso (TCC) entitulado "ANÁLISE COMPARATIVA ENTRE ARQUITETURA DE SISTEMA MONOLÍTICO E MICROSSERVIÇO: DESEMPENHO E CONSUMO DE RECURSO".

Neste repositório está o microsserviço do _Catalog_, contendo a entidade _Product_ e _Brand_, que trata das interações e mantém o os produtos e as marcas dos produtos do sistema.

Os outros microsserviços que contém a aplicação Dining-Room na arquitetura de microsserviços são:

- Eureka-Server: https://github.com/GustavoBonif/dining-room-eureka-server
- User-Registry: https://github.com/GustavoBonif/dining-room-user-registry 
- Catalog: https://github.com/GustavoBonif/dining-room-catalog
- Warehouse: https://github.com/GustavoBonif/dining-room-warehouse
- Cart: https://github.com/GustavoBonif/dining-room-cart
- Gateway: https://github.com/GustavoBonif/dining-room-gateway

A versão em arquitetura monolítica deste projeto está em:
- https://github.com/GustavoBonif/dining-room-mono 

O projet Dining-Room o é um _ecommerce_ simples, com apenas o _backend_, e tem como objetivo comparar o tempo do resposta e o consumo de CPU das instâncias EC2 na AWS de cada aplicação.

As tecnologias empregadas foram:
- Java 17;
- Spring 3.1.4;
- MySQL 8.0.29.

As ferramentas utilizadas: 
- _Postman_ para realização de consulta de API;
- _Apache JMeter_ para criação de plano de testes para _baseline test_ e _load test_

Além disso, foi utilizado o ambiente _Amazon Web Server_ (AWS) para hospedagem do sistema em plataforam nuvem.
