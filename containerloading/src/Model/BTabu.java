/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class BTabu {

    private int listLimit;
    private Queue<Box> listaTabu;

    public BTabu(List<Box> _typeBoxes) {
        listLimit = CalculaTamanhoListaTabu(_typeBoxes);
        listaTabu = new ArrayBlockingQueue<Box>(listLimit);
    }

    private Integer CalculaTamanhoListaTabu(List<Box> _typeBoxes) {
        int tabuSize = 0;
        for (Box box : _typeBoxes) {
            if (box.xv) {
                tabuSize += 2;
            }
            if (box.yv) {
                tabuSize += 2;
            }
            if (box.zv) {
                tabuSize += 2;
            }
        }
        return tabuSize-1;
    }
    /**
     * Adiciona uma caixa na lista Tabu.
     * A lista tem um limite máximo de caixas.
     * Caso ao adicionar uma caixa a lista esteja cheia, retorna a caixa que
     * está a mais tempo na lista. Caso ao adicionar uma caixa o limite não
     * seja atingido, não retorna nenhuma caixa.
     */
    public Box addBox(Box _box) {
        //se a lista estiver cheia, remove a ultima solucao valida
        if (listaTabu.size() == listLimit) {
            listaTabu.add(_box);
            return listaTabu.remove();
        } else {
            listaTabu.add(_box);
        }
        return null;
    }

    static public boolean areEqual(long aThis, long aThat) {
        /*
         * Implementation Note
         * Note that byte, short, and int are handled by this method, through
         * implicit conversion.
         */
        //System.out.println("long");
        return aThis == aThat;
    }

    public boolean contains(Box outBox) {
        for (Box inBox : listaTabu) {
            if (areEqual(inBox.relativeDimensions.x, outBox.relativeDimensions.x)
                    && areEqual(inBox.relativeDimensions.y, outBox.relativeDimensions.y)
                    && areEqual(inBox.relativeDimensions.z, outBox.relativeDimensions.z)) {
                return true;
            }
        }
        return false;
    }

    public boolean clear() {
        listaTabu.clear();
        return true;
    }

    public int listTabuLimit() {
        return listLimit;
    }

    public int listTabuNumberOfBoxes() {
        return listaTabu.size();
    }
}
