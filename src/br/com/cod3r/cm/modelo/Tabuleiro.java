package br.com.cod3r.cm.modelo;

import br.com.cod3r.cm.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarOsVizinhos();
        sortearMinas();
    }

    public void abrir(int linha, int coluna) {
        try {
            campos.parallelStream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(Campo::abrir);
        } catch (ExplosaoException e) {
            campos.forEach(Campo::setAberto);
            throw e; // precisa-se repassar essa exceção para que o jogo realmente finalize com a msg: "Você perdeu!"
        }
    }

    public void alternarMarcacao(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(Campo::alternarMarcacao);
    }

    private void gerarCampos() {
        for (int linha = 1; linha < linhas + 1; linha++) {
            for (int coluna = 1; coluna < colunas + 1; coluna++) {
                campos.add(new Campo(linha, coluna));
            }
        }
    }

    private void associarOsVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas;
        Predicate<Campo> minado = Campo::isMinado;

        do {
            int aleatorio = (int) (Math.random() * campos.size()); //50 * 0.9 = 45 | 50 * 0.99 = 49 | 50 * 0.97 = 48
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while (minasArmadas < minas);
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar() {
        campos.stream().forEach(Campo::reiniciar);
        sortearMinas();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int c = 1; c < colunas+1; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append("  ");
        }

        sb.append("\n");
        
        int i = 0;
        for (int linha = 1; linha < linhas+1; linha++) {
            for (int coluna = 1; coluna < colunas+1; coluna++) {
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append("  ");
                i++;
            }
            sb.append(linha);
            sb.append("\n");
        }


        return sb.toString();
    }

}
