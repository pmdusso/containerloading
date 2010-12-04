/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Pedro
 */
public class BTabu{

    private int listLimit;
    private Queue<Box> listaTabu;

    public BTabu(int _numberOfBoxTypes)
    {
        listLimit = _numberOfBoxTypes;
        listaTabu = new ArrayBlockingQueue<Box>(_numberOfBoxTypes);
    }
    public int listTabuLimit()
    {
        return listLimit;
    }
    public int listTabuNumberOfBoxes()
    {
        return listaTabu.size();
    }
    public boolean addBox(Box _box)
    {
        //se a lista estiver cheia, remove a ultima solucao valida
        if(listaTabu.size() == listLimit)
            listaTabu.remove();
        return listaTabu.add(_box);
    }
    public boolean contains(Box _box)
    {
        if(listaTabu.contains(_box)) return true; else return false;
    }
    public boolean clear()
    {
        listaTabu.clear();
        return true;
    }
}