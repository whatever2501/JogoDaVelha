package com.jogodavelha.Jogo.Model;

public class JogoDaVelha {

    private char[][] tabuleiro;
    private char jogadorAtual;
    private boolean gameOver;
    private char ganhador;
    


    public JogoDaVelha() {
        tabuleiro = new char[3][3];
        jogadorAtual = ConstantesJogadores.JOGADOR;
        gameOver = false;
        ganhador = ConstantesJogadores.VAZIO;
        limparTabuleiro();
    }
    
    public char getJogadorAtual() {
        return jogadorAtual;
    }

    public char[][] getTabuleiro() {
        return tabuleiro;
    }
    public boolean isGameOver() {
        return gameOver;
    }

    public void limparTabuleiro() {
        for (int linha = 0; linha < 3; linha++) {
            for (int col = 0; col < 3; col++) {
                tabuleiro[linha][col] = ConstantesJogadores.VAZIO;
            }
        }
    }

    public boolean jogar(int linha, int col) {

        if (!gameOver && tabuleiro[linha][col] == ConstantesJogadores.VAZIO && jogadorAtual == ConstantesJogadores.JOGADOR) {
            tabuleiro[linha][col] = jogadorAtual;
            verficarGanhador();
            mudarJogador();
            jogadaIA();
            return true;
        }if(!gameOver && jogadorAtual == ConstantesJogadores.IA ){
            tabuleiro[linha][col] = jogadorAtual;
            verficarGanhador();
            mudarJogador();
            return true;
        }if(gameOver){
            return true;
        }
        return false;

    }

    private void mudarJogador() {
        jogadorAtual = (jogadorAtual == ConstantesJogadores.JOGADOR) ? ConstantesJogadores.IA : ConstantesJogadores.JOGADOR;
    }
    public String getGanhador() {
        return String.valueOf(ganhador);
    }

    private void verficarGanhador() {
        //Olha as linhas para ver se tem vencedor
        for (int linha = 0; linha < 3; linha++) {
            if (tabuleiro[linha][0] != ConstantesJogadores.VAZIO && tabuleiro[linha][0] == tabuleiro[linha][1] && tabuleiro[linha][0] == tabuleiro[linha][2]) {
                ganhador = tabuleiro[linha][0];
                gameOver = true;
                return;
            }
        }
        //Olha as colunas para ver se tem vencedor
        for (int col = 0; col < 3; col++) {
            if (tabuleiro[0][col] != ConstantesJogadores.VAZIO && tabuleiro[0][col] == tabuleiro[1][col] && tabuleiro[0][col] == tabuleiro[2][col]) {
                ganhador = tabuleiro[0][col];
                gameOver = true;
                return;
            }
        }
        //Olhas as diagonais para ver se tem vencedor
        if (tabuleiro[0][0] != ConstantesJogadores.VAZIO && tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[0][0] == tabuleiro[2][2]) {
            ganhador = tabuleiro[0][0];
            gameOver = true;
            return;
        }
        if (tabuleiro[0][2] != ConstantesJogadores.VAZIO && tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[0][2] == tabuleiro[2][0]) {
            ganhador = tabuleiro[0][2];
            gameOver = true;
            return;
        }
        //Verifica se deu empate
        boolean semJogadas = true;
        for (int linha = 0; linha < 3; linha++) {
            for (int col = 0; col < 3; col++) {
                if (tabuleiro[linha][col] == ConstantesJogadores.VAZIO) {
                    semJogadas = false;
                    break;
                }
            }
            if (!semJogadas) {
                break;
            }
        }
        if (semJogadas) {
            ganhador = 'e';
            gameOver = true;
            
        }
    }

    public void jogadaIA() {

        int melhorPontuação = Integer.MIN_VALUE;
        int melhorLinha = -1;
        int melhorColuna = -1;

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (tabuleiro[i][j] == ConstantesJogadores.VAZIO) {
                tabuleiro[i][j] = ConstantesJogadores.IA;

                int score = minimax(tabuleiro, 0, false);

                tabuleiro[i][j] = ConstantesJogadores.VAZIO;

                if (score > melhorPontuação) {
                    melhorPontuação = score;
                    melhorLinha = i;
                    melhorColuna = j;
                }
                
            }
        }
    }
    
    jogar(melhorLinha, melhorColuna);
    

       
    }

    public static int avaliar(char[][] tabuleiro) {
        int pontuação = 0;
        for (int i = 0; i < 3; i++) {
            // Avalia as linhas
            if (tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2]) {
                if (tabuleiro[i][0] == ConstantesJogadores.IA) {
                    pontuação += 10;
                } else if (tabuleiro[i][0] == ConstantesJogadores.JOGADOR) {
                    pontuação -= 10;
                }
            }
            // Avalia as colunas
            if (tabuleiro[0][i] == tabuleiro[1][i] && tabuleiro[1][i] == tabuleiro[2][i]) {
                if (tabuleiro[0][i] == ConstantesJogadores.IA) {
                    pontuação += 10;
                } else if (tabuleiro[0][i] == ConstantesJogadores.JOGADOR) {
                    pontuação -= 10;
                }
            }
        }
        // Avalia as diagonais
        if (tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2]) {
            if (tabuleiro[0][0] == ConstantesJogadores.IA) {
                pontuação += 10;
            } else if (tabuleiro[0][0] == ConstantesJogadores.JOGADOR) {
                pontuação -= 10;
            }
        }
        if (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0]) {
            if (tabuleiro[0][2] == ConstantesJogadores.IA) {
                pontuação += 10;
            } else if (tabuleiro[0][2] == ConstantesJogadores.JOGADOR) {
                pontuação -= 10;
            }
        }
        return pontuação;
    }
    public static boolean temJogadas(char[][] tabuleiro) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ConstantesJogadores.VAZIO) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int minimax(char[][] tabuleiro, int depth, boolean isMaximizing) {
        int pontuação = avaliar(tabuleiro);
        
        if (pontuação == 10) {
            return pontuação - depth;
        }
        if (pontuação == -10) {
            return pontuação + depth;
        }
        if (!temJogadas(tabuleiro)) {
            return 0;
        }
        if (isMaximizing) {
            int melhorPontuação = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tabuleiro[i][j] == ConstantesJogadores.VAZIO) {
                        tabuleiro[i][j] = ConstantesJogadores.IA;
                        pontuação = minimax(tabuleiro, depth + 1, false);
                        tabuleiro[i][j] = ConstantesJogadores.VAZIO;
                        melhorPontuação = Math.max(melhorPontuação, pontuação);
                    }
                }
            }
            return melhorPontuação;
        } else {
            int melhorPontuação = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tabuleiro[i][j] == ConstantesJogadores.VAZIO) {
                        tabuleiro[i][j] = ConstantesJogadores.JOGADOR;
                        pontuação = minimax(tabuleiro, depth + 1, true);
                        tabuleiro[i][j] = ConstantesJogadores.VAZIO;
                        melhorPontuação = Math.min(melhorPontuação, pontuação);
                    }
                }
            }
            return melhorPontuação;
        }
    }



    
}


