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
    private List<Box> boxesOutside = new ArrayList<Box>();;
    private Container container;
    private BTabu listaTabu;

    public HeuristicSearch(List<Box> _boxesOutside, int contLenght, int contWidth,int contHeight)
    {
        boxesOutside = _boxesOutside;
        boxesInside.clear();
        listaTabu.clear();
        container = new Container(new Vector3d(contLenght, contWidth, contHeight));
    }

    public void Resolve()
    {
        Box tempBox;
        List<Box> solucaoIntermediaria = new ArrayList<Box>();
        int valorFuncaoObjetivo = 0;
        int nroIteracoes = 0;

        while (volumeTotal(boxesInside) <= container.volume && nroIteracoes <= 1000000)
        {
            tempBox = melhorCaixa(boxesOutside);
            if(!listaTabu.contains(tempBox))
            {
                if (container.fitsIn(tempBox)!= null) {
                    tempBox.relativeCoordenates = container.insertBox(tempBox);
                    solucaoIntermediaria.add(tempBox);
                } else {
                    listaTabu.addBox(tempBox);
                }
            }
            else
            {
                //não faz nada,reinicia o laço para pegar outra caixa.
            }
            int fo = funcaoObjetivo(boxesInside);
            if (fo > valorFuncaoObjetivo) {
                valorFuncaoObjetivo = fo;
                boxesInside = solucaoIntermediaria;
            }

        }
    }

    /**
    * Calcula o volume total de uma lista de caixas
    */
    public int volumeTotal(List<Box> _lstBoxes)
    {
        int volumeTotal = 0;

        for (Box box : _lstBoxes) {
            volumeTotal += box.volume;
        }
        return volumeTotal;
    }
    /**
    * Retorna a melhor (maior volume) caixa da lista recebida
    */
    public Box melhorCaixa(List<Box> _lstBoxes)
    {
        Box bestBox = new Box(new Vector3d(1, 1, 1), true, true, true);

        for (Box box : _lstBoxes) {
            if(box.volume > bestBox.volume)
                bestBox = box;
        }
        return bestBox;
    }
    /**
     * A funcao objetivo é dada pelo somatorio dos volumes das caixas
     * contidas no container.
     */
    public int funcaoObjetivo(List<Box> _lstBoxes)
    {
        int volumeTotal = 0;

        for (Box box : _lstBoxes) {
            volumeTotal += box.volume;
        }
        return volumeTotal;
    }

}