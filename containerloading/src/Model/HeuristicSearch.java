/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class HeuristicSearch {

    private List<Box> boxesInside = new ArrayList<Box>();
    private List<Box> boxesOutside = new ArrayList<Box>();

    ;
    private Container container;
    private BTabu listaTabu;

    public HeuristicSearch(List<Box> _boxesOutside, int contLenght, int contWidth, int contHeight) {
        boxesOutside = _boxesOutside;
        boxesInside.clear();
        listaTabu.clear();
        container = new Container(new Vector3d(contLenght, contWidth, contHeight));
    }

    public void Resolve() {
        Box bestBox;
        Box tempBox;
        Vector3d lastBoxInserted = new Vector3d(0, 0, 0);
        List<Box> solucaoIntermediaria = new ArrayList<Box>();
        int valorFuncaoObjetivo = 0;
        int nroIteracoes = 0;

        //critério de parada: volume dentro do container igual ao volume do container (sol. ótima)
        //numero de iteracoes maximo.
        while (volumeTotal(boxesInside) < container.volume && nroIteracoes <= 1000000) {
            //seleciona uma caixa que ainda não está no container seguindo alguma euristica
            //(nesse caso está sendo a de pegar a melhor caixa == caixa com maior volume.
            bestBox = melhorCaixa(boxesOutside);
            //se aquela caixa não está na lista tabu e ela cabe em algum lugar dentro do container
            //a solução atual mais essa caixa é um vizinho válido da solução atual.
            if (!listaTabu.contains(bestBox)) {
                tempBox = container.fitsIn(bestBox,lastBoxInserted);
                if (tempBox != null) {
                    lastBoxInserted = new Vector3d(
                            tempBox.relativeCoordenates.x,
                            tempBox.relativeCoordenates.y + tempBox.relativeDimensions[1],
                            tempBox.relativeCoordenates.z);
                    //aqui, caso a caixa caiba no container, ela já tem as coordenadas
                    //de onde deve ficar.
                    solucaoIntermediaria.add(tempBox);
                } else {
                    //aquele "modelo" de caixa não cabe dentro do container no momento.
                    listaTabu.addBox(bestBox);
                }
            } else {
                //não faz nada,reinicia o laço para pegar outra caixa.
            }
            int fo = funcaoObjetivo(boxesInside);
            if (fo > valorFuncaoObjetivo) {
                valorFuncaoObjetivo = fo;
                boxesInside = solucaoIntermediaria;
            }

            nroIteracoes++;

        }
    }

    /**
     * Calcula o volume total de uma lista de caixas
     */
    public int volumeTotal(List<Box> _lstBoxes) {
        int volumeTotal = 0;

        for (Box box : _lstBoxes) {
            volumeTotal += box.volume;
        }
        return volumeTotal;
    }

    /**
     * Retorna a melhor (maior volume) caixa da lista recebida
     */
    public Box melhorCaixa(List<Box> _lstBoxes) {
        Box bestBox = new Box(new Vector3d(1, 1, 1), true, true, true);

        for (Box box : _lstBoxes) {
            if (box.volume > bestBox.volume) {
                bestBox = box;
            }
        }
        return bestBox;
    }

    /**
     * A funcao objetivo é dada pelo somatorio dos volumes das caixas
     * contidas no container.
     */
    public int funcaoObjetivo(List<Box> _lstBoxes) {
        int volumeTotal = 0;

        for (Box box : _lstBoxes) {
            volumeTotal += box.volume;
        }
        return volumeTotal;
    }
}
