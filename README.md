# trabalho-dsd-threads
Trabalho para a matéria de DSD envolvendo threads

Equipe: Eduardo Schork, Luíza Nurnberg e Maria Cecilia Holler

### Requisitos Funcionais

| ID                                            | Descrição                                                                                                         |
|------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|
| RF01                              | Cada veículo se move uma posição por vez, respeitando o sentido de fluxo da pista.                                |
| RF02                                                   | O veículo pode trocar de pista em vias de pista dupla, movendo-se para a posição em diagonal à frente, se livre. |
| RF03                                    | Ao se deparar com um cruzamento, o veículo escolhe aleatoriamente uma das vias de saída antes de ingressar.       |
| RF04                    | O veículo verifica se todas as posições no cruzamento por onde vai passar estão totalmente livres.               |
| RF05                                    | O veículo não deve bloquear o cruzamento para outros veículos.                                                    |
| RF06        | Novos veículos são inseridos nos pontos de entrada da malha.                                                      |
| RF07                | O veículo é encerrado ao atingir um ponto de saída.                                                               |
| RF08                              | Os veículos possuem diferentes velocidades de movimentação.                                                       |
| RF09                              | Opções para limitar a quantidade de veículos na simulação.                                                        |
| RF10                               | Ao iniciar a simulação, são inseridos veículos, respeitando a quantidade especificada. Ao encerrar, os veículos são imediatamente encerrados.                                          |

### Requisitos Não Funcionais

| ID | Categoria        | Descrição                                                                                       |
|--------|------------------|-------------------------------------------------------------------------------------------------|
| RNF01 | Segurança        | Garantir que os veículos não colidam entre si na malha.                                         |
| RNF02 | Eficiência       | O sistema deve ser eficiente, mantendo a simulação em tempo real sempre que possível.           |
| RNF03 | Escalabilidade   | Capacidade de lidar com um grande número de veículos e uma malha de tamanho variável.           |
| RNF04 | Robustez         | Lidar com situações inesperadas, como falhas de veículos ou falhas na malha.                    |
                  

### Regras de Negócio

| Regras de Negócio                   | Descrição                                                                                       |
|-------------------------------------|-------------------------------------------------------------------------------------------------|
| RN01               | Os veículos devem se mover continuamente pela malha, respeitando as regras de tráfego.          |
| RN02   | A escolha das vias de saída em cruzamentos deve ser aleatória para cada veículo.                |
| RN03       | Garantir que as posições por onde um veículo passará em um cruzamento estejam totalmente livres. |
| RN04       | Os veículos devem respeitar o sentido de fluxo das pistas ao se movimentarem.                    |

