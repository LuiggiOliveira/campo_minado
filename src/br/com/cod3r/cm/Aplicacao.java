package br.com.cod3r.cm;

import br.com.cod3r.cm.modelo.Tabuleiro;
import br.com.cod3r.cm.visao.TabuleiroConsole;

public class Aplicacao {
    public static void main(String[] args) {

        Tabuleiro tabuleiro = new Tabuleiro(10, 10, 25);
        new TabuleiroConsole(tabuleiro);

//      Testando do modo tradicional:
//      tabuleiro.abrir(3,3);
//      tabuleiro.alternarMarcacao(4, 5);
//      tabuleiro.abrir(5, 5);
    }

}
