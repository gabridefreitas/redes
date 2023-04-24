# Redes de Computadores: Aplicação e Transporte

Projetos da atividade acadêmica:

## WordFindr - Trabalho GA

- Integrantes:
  - Daniel Panno Santos
  - Gabriel de Freitas Adolfo
  - Mathias Daniel Hahner

- Requisitos:
  - 2-4 jogadores

- Servidor:
  - Sorteia um jogadorX para definir a palavra
  - Sorteia a ordem dos demais jogadores
  - Requisita ao jogadorX uma palavra para ser adivinhada pelos outros jogadores

- Game loop:
  - Servidor chama próximo jogador da lista para informar uma letra
  - Cliente envia um palpite para servidor
  - Servidor retorna informações sobre o palpite
  - Após dois erros o jogador pode solicitar uma dica para o servidor, durante sua rodada
  - Após três erros o jogador perde e o jogadorX marca um ponto
  - Se o jogador descobrir a palavra ele marca um ponto
