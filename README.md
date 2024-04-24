# trabalho-dsd-threads
Trabalho para a matéria de DSD envolvendo threads

Equipe: Eduardo Schork, Luíza Nurnberg e Maria Cecilia Holler

### Requisitos Funcionais

| ID                                            | Descrição                                                                                                         |
|------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|
| RF01 | Cada veículo se move uma posição por vez, respeitando o sentido de fluxo da pista. |
| RF02 | O veículo se movimenta uma posição por vez, respeitando o fluxo da pista. |
| RF03 | O veículo só pode se movimentar se a posição estiver livre. |
| RF04 | Ao se deparar com um cruzamento, o veículo escolhe aleatoriamente uma das vias de saída antes de ingressar. |
| RF05 | O veículo verifica se todas as posições no cruzamento por onde vai passar estão totalmente livres. |
| RF06 | O veículo não deve bloquear o cruzamento para outros veículos. |
| RF07 | Novos veículos são inseridos nos pontos de entrada da malha. |
| RF08 | O veículo é encerrado ao atingir um ponto de saída. |
| RF09 | Os veículos possuem diferentes velocidades de movimentação. |
| RF10 | O sistema deve permitir informar a quantidade máxima de veículos que estarão simultaneamente na malha. |
| RF11 | As vias serão sempre horizontais ou verticais. |
| RF12 | As vias possuem mão dupla. |
| RF13 | Nas bordas só haverá vias perpendiculares. |
| RF14 | É necessário uma visualização em interface gráfica |
| RF15 | O sistema deve suportar três mecanismos de exclusão mútua (semáforos, monitores e troca de mensagens) |


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
| RN05       | O usuário poderá escolher se deseja semáforo, monitores ou troca de mensagens como exclusão mútua                    |

