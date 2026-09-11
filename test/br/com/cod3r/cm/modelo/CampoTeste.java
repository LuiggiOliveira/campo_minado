package br.com.cod3r.cm.modelo;

import br.com.cod3r.cm.excecao.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CampoTeste {

    private Campo campo;

//     Para cada teste ele vai chama primeiro essa função, de modo que sempre instancie de forma "limpa" sem que um teste
//     possa influenciar no outro, devido a alguma alteração nesse mesmo objeto criado. Assim, o mesmo objeto é testado
//     de forma unitária
    @BeforeEach
    void iniciarCampo() {
        campo = new Campo(3, 3);
    }

//    Eu poderia testar cada direção e sentido, criando um teste que verifica se é distância 1 em cima, embaixo,
//    direita ou esquerda... Isso se eu quisesse ser mais rigoroso, mas aqui não é tão necessário, os 4 cenários já bastam
    @Test
    void testeVizinhoRealDistancia1 () {
        Campo vizinho = new Campo(3, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeVizinhoRealDistancia2 () {
        Campo vizinho = new Campo(2, 4);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    void testeNaoVizinhoEmCruz () {
        Campo vizinho = new Campo(1, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertFalse(resultado); // atenção: se espera que seja Falso, por isso, assertFalse e não assertTrue
    }

    @Test
    void testeNaoVizinhoNaDiagonal () {
        Campo vizinho = new Campo(1, 5);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertFalse(resultado);
    }

    @Test
    void testarValorPadraoAtributoMarcado() {
        assertFalse(campo.isMarcado());
    }

    @Test
    void testarAlternarMarcacao() {
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    void testarDuasVezesAlternarMarcacao() {
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testarAbrirNaoMinadoNaoMarcado() {
        assertTrue(campo.abrir());
    }

    @Test
    void testarAbrirNaoMinadoMarcado() {
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    void testarAbrirCampoAberto() {
        campo.abrir();
        assertFalse(campo.abrir());
    }

    @Test
    void testarAbrirMinadoMarcado() {
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void testarAbrirMinadoNaoMarcado() {
        campo.minar();
        assertThrows(ExplosaoException.class, () -> campo.abrir());
    }

    @Test
        void testarAbrirComVizinhos1() {

        Campo campo22 = new Campo(2, 2);
        Campo campo11= new Campo(1, 1);

        campo22.adicionarVizinho(campo11);
        campo.adicionarVizinho(campo22);

        campo.abrir();
        assertTrue(campo22.isAberto() && campo11.isAberto());
    }

    @Test
    void testarAbrirComVizinhos2() {

        Campo campo11 = new Campo(1, 1);
        Campo campo12= new Campo(1, 2);
        campo12.minar();

        Campo campo22 = new Campo(2, 2);
        campo22.adicionarVizinho(campo11);
        campo22.adicionarVizinho(campo12);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isFechado());
    }

    @Test
    void testarContagemMinasNaVizinhanca(){
        Campo c32 = new Campo(3, 2);
        Campo c22 = new Campo(2, 2);
        Campo c43 = new Campo(4, 3);

        c22.minar();
        c43.minar();

        campo.adicionarVizinho(c32);
        campo.adicionarVizinho(c22);
        campo.adicionarVizinho(c43);

        assertEquals(2, campo.minasNaVizinhanca());
    }

    @Test
    void testarToStringMarcado(){
        campo.alternarMarcacao();
        assertEquals("X", campo.toString());
    }

    @Test
    void testarMostrarAbertoMinado(){
        campo.abrir();
        campo.minar();
        assertEquals("*", campo.toString());
    }

    @Test
    void testarMostrarMinaVizinhanca(){
        Campo c32 = new Campo(3, 2);
        Campo c22 = new Campo(2, 2);
        Campo c43 = new Campo(4, 3);

        c32.minar();
        c22.minar();
        c43.minar();

        campo.adicionarVizinho(c32);
        campo.adicionarVizinho(c22);
        campo.adicionarVizinho(c43);
        campo.abrir();

        String esperado = "\u001B[31m\u001B[1m3\u001B[0m"; // String com um 3 (que estaria em vermelho no terminal)
        assertEquals(esperado, campo.toString());
    }

    @Test
    void testarMostrarAberto(){
        campo.abrir();
        assertEquals(" ", campo.toString());
    }

    @Test
    void testarMostrarNaoDescoberto(){
        assertEquals("?", campo.toString());
    }
}
