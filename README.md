# Redes de Computadores: Aplicação e Transporte

Projetos da atividade acadêmica:

## WordFindr - Trabalho GA

- Integrantes:

  - Daniel Panno Santos
  - Gabriel de Freitas Adolfo
  - Mathias Daniel Hahner

- Requisitos:

  - 2 jogadores

- Servidor:

  - Sorteia um jogadorX para definir a palavra
  - Requisita ao jogadorX uma palavra para ser adivinhada pelo jogadorY

- Game loop:
  - Servidor chama o jogadorY para informar uma letra
  - Cliente envia um palpite para servidor
  - Servidor retorna informações sobre o palpite
  - Após dois erros o jogadorY pode solicitar uma dica para o servidor
  - Após três erros o jogadorX é o vencedor
  - Se o jogadorY descobrir a palavra ele é o vencedor
