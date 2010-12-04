package Model;

import java.util.List;

/**
 *
 * @author Matheus Abegg
 */
public class GRASP {

    /*
     * para i = 0 ; i < nIterações ; i++
     * 1 cria uma colução gulosa
     * 2 faz uma busca local com essa solução
     * retorna melhor solução
     *
     *
     * 1.
     *   enquanto solução não é completa (
     *      gera lista de candidatos (todas as caixas fora do container)
     *      ordena por maior volume antes
     *      seleciona uma aleatoria das a% primeiras caixas da lista;
     *      s += caixa selecionada;
     *
     *  retorna S;
     *
     * 
     */
    private List<Box> allBoxes;
    private List<Box> boxesIn;
    private List<Box> boxesOut;

    private void makeGreedySolution(Float alpha) {
    }
}