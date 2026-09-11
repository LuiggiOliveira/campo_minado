package br.com.cod3r.cm.modelo;

import br.com.cod3r.cm.excecao.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TabuleiroTeste {

    private Tabuleiro tabuleiro;

    @BeforeEach
    void iniciarTabuleiro() {
        tabuleiro = new Tabuleiro(6, 6, 6);
    }

//    OBS: Eu iria realmente escrever testes para cada funcionalidade, mas agora que sempre eu instanciar o tabuleiro
//    automaticamente ele inicializa o jogo... Eu não sei como que vou testar cada funcionalidade sendo que
//    ela deveria simular o jogador escrevendo nos inputs... Por exemplo, como que eu vou testar unicamente o sortearMina()
//    se para usar ele, ao instanciar um tabuleiro ele já chama esse método junto com outros? Até tem como aparentemente
//    simular inputs do usuário no console, mas aí já fica um pouco mais complicado...
//    OBS2: quando não tinha a parte da view, dava sim para testar as funcionalidades de modo unitário, mas agora não sei...
//    OBS3: por conta de que esse projetinho é um campo minado de console, bem simples, é possível testar tudo já jogando o jogo
//    acho que já basta... (não, não é preguiça de escrever testes!)

}
