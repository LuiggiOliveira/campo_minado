package br.com.cod3r.cm.modelo;

import br.com.cod3r.cm.excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    // autorrelacionamento
    private final List<Campo> vizinhos = new ArrayList<>();

    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    boolean adicionarVizinho (Campo candidatoVizinho) {
        boolean linhaDiferente = linha != candidatoVizinho.linha;
        boolean colunaDiferente = coluna != candidatoVizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - candidatoVizinho.linha);
        int deltaColuna = Math.abs(coluna - candidatoVizinho.coluna);
        int deltaGeral = deltaLinha + deltaColuna;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(candidatoVizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(candidatoVizinho);
            return true;
        } else {
            return false;
        }
    }

    void alternarMarcacao() {
        if(!aberto) {
            marcado = !marcado;
        }
    }

    boolean abrir() {

        if(!aberto && !marcado) {
            aberto = true;

            if(minado) {
                throw new ExplosaoException();
            }

            if (vizinhancaSegura()) {
                vizinhos.forEach(Campo::abrir); // recursão
            }
            return true;
        } else {
            return false;
        }
    }

    boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public boolean isMarcado() {
        return marcado;
    }

    public boolean isAberto() {
        return aberto;
    }

    void setAberto() {
        this.aberto = true;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public boolean isMinado() {
        return minado;
    }

    void minar() {
        minado = true;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    long minasNaVizinhanca() {
        return vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;
    }

    public String toString() {
        if(marcado) {
            return "X";
        } else if (aberto && minado) {
            return "*";
        } else if (aberto && minasNaVizinhanca() > 0) {
            return switch ((int) minasNaVizinhanca()) {
                case 1 -> "\u001B[34m" + "\u001B[1m" + minasNaVizinhanca() + "\u001B[0m"; // 1 azul
                case 2 -> "\u001B[32m" + "\u001B[1m" + minasNaVizinhanca() + "\u001B[0m"; // 2 verde
                case 3 -> "\u001B[31m" + "\u001B[1m" + minasNaVizinhanca() + "\u001B[0m"; // 3 vermelho
                case 4 -> "\u001B[33m" + "\u001B[1m" + minasNaVizinhanca() + "\u001B[0m"; // 4 amarelo
                default -> Long.toString(minasNaVizinhanca()); //acabaram as cores... Mas teria do 5 até o 8, o que é bem mais difícil
            };
        } else if (aberto) {
            return " ";
        } else {
            return "?";
        }
    }
}
