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
    private Container container;
    private BTabu listaTabu;

    public Container getContainer() {
        return container;
    }

    public HeuristicSearch(List<Box> _boxesOutside, Container _ctn, int _numberOfBoxTypes) {
        boxesOutside = _boxesOutside;
        boxesInside.clear();
        listaTabu = new BTabu(_numberOfBoxTypes,_boxesOutside);
        container = _ctn;
    }

    public int Resolve() {
        Box bestBox;
        Box tempBox;
        Vector3d lastBoxInserted = new Vector3d(0, 0, 0);
        List<Box> solucaoIntermediaria = new ArrayList<Box>();
        int valorFuncaoObjetivo = 0;
        int nroIteracoes = 0;

        try {

            //critério de parada: volume dentro do container igual ao volume do container (sol. ótima)
            //numero de iteracoes maximo.
            while (volumeTotal(boxesInside) < container.volume && nroIteracoes <= 1000000) {
                //seleciona uma caixa que ainda não está no container seguindo alguma euristica
                //(nesse caso está sendo a de pegar a melhor caixa == caixa com maior volume.
                bestBox = melhorCaixa(boxesOutside);
                //se aquela caixa não está na lista tabu e ela cabe em algum lugar dentro do container
                //a solução atual mais essa caixa é um vizinho válido da solução atual.
                if (!listaTabu.contains(bestBox)) {
                    tempBox = container.fitsIn(bestBox, lastBoxInserted);
                    if (tempBox != null) {
                        //"otimizada": salva as coordenadas da ultima caixa inserida. Começa a busca
                        //por um lugar para as proximas caixas apartir dessa posicao.
                        lastBoxInserted = new Vector3d(
                                tempBox.relativeCoordenates.x + tempBox.relativeDimensions.x,
                                tempBox.relativeCoordenates.y,
                                tempBox.relativeCoordenates.z);
                        //aqui, caso a caixa caiba no container, ela já tem as coordenadas
                        //de onde deve ficar.
                        boxesOutside.remove(bestBox);
                        solucaoIntermediaria.add(tempBox);
                        container.insertBox(tempBox);
                    } else {
                        //aquele "modelo" de caixa não cabe dentro do container no momento.
                        tempBox = listaTabu.addBox(bestBox);

                        //devolve para a lista de caixas a serem adicionadas aquelas que
                        //nao cabem mais na lista tabu.
                        if (tempBox != null) {
                            boxesOutside.add(tempBox);
                        }

//                        if (solucaoIntermediaria.size() > 0) {
//                            solucaoIntermediaria.remove(solucaoIntermediaria.size() - 1);
//                        }
                    }

                    int fo = funcaoObjetivo(solucaoIntermediaria);
                    if (fo > valorFuncaoObjetivo) {
                        valorFuncaoObjetivo = fo;
                        boxesInside = solucaoIntermediaria;
                    }
                } else {
                    //não faz nada,reinicia o laço para pegar outra caixa.
                    boxesOutside.remove(bestBox);
                }

                nroIteracoes++;

            }

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return valorFuncaoObjetivo;
    }

    /**
     * Calcula o volume total de uma lista de caixas
     */
    public int volumeTotal(List<Box> _lstBoxes) {
        int volumeTotal = 0;

        for (Box box : _lstBoxes) {
            volumeTotal += box.getVolume();
        }
        return volumeTotal;
    }

    /**
     * Retorna a melhor (maior volume) caixa da lista recebida
     */
    public Box melhorCaixa(List<Box> _lstBoxes) {
        Box bestBox = new Box(new Vector3d(1, 1, 1), true, true, true,0);

        for (Box box : _lstBoxes) {
            if (box.getVolume() > bestBox.getVolume()) {
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
            volumeTotal += box.getVolume();
        }
        return volumeTotal;
    }

    public int getNumeroDeCaixas(Vector3d vec) {
        int volume = vec.x * vec.y * vec.z;
        int quantidade = 0;

        for (Box box : boxesInside) {
            if (box.getVolume() == volume) {
                quantidade++;
            }
        }
        return quantidade;
    }
}
