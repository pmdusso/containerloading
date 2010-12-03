/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author otavio_zabaleta
 */
public class Container {
    //The container is divided in 1cm side cubes.
    public boolean spMatrix[][][];
    private int x,y,z;
    public int volume;

    public Container(Vector3d _size)
    {
        this.x = _size.x;
        this.y = _size.y;
        this.z = _size.z;
        this.volume = _size.x * _size.y *_size.z;
        spMatrix = new boolean[x][y][z];
        //inicializa toda a matriz do container com 0 (container vazio)
        for (int i = 0; i < x; i++)
            for(int j = 0; j < y; j++)
                for(int k = 0; k < z; k++)
                    spMatrix[i][j][k] = false;
    }

    //Add a box
    public void Add(Box bx)
    {}

    //Remove a box
    public void Remove(Box bx)
    {}

    //Checks if the bottom of a box hits something
    private boolean BottomOverlap(int x, int y, Vector3d _pos)
    {
        int xMax = _pos.x + x;
        int yMax = _pos.y + y;

        try {
            if (xMax > this.x || yMax > this.y)
                throw new Exception("bottomOverlap(int x, int y, Vector3d _pos) - Porra, saiu fora do container!");

            for(int i = _pos.x; i < xMax; i++)
            {
                for (int j = _pos.y; j < yMax; j++)
                {
                    if (this.spMatrix[i][j][_pos.z])
                    {
                        return true;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    /* Makes a box fall to the lowest z of it's current (x,y) position.
     * PRECONDITIONS: box position must be a valid one (e.g. no space
     * overlapping and no container boundary exceeded)
     */
    public Vector3d FallBox(Box bx, Vector3d _pos)
    {
        Vector3d pos = _pos;
        try
        {
            if(pos.z < 0)
                throw new Exception("FallBox(Box bx, Vector3d _pos) - Porra, saiu fora do container!");
            int upperRef = pos.z;
            int currentRef = upperRef;
            int lowerRef = 0;

            while(true)
            {
                if(BottomOverlap(bx.relativeDimensions[0], bx.relativeDimensions[1], pos))
                {
                    lowerRef = pos.z;
                    currentRef = (lowerRef + upperRef) / 2;
                    pos.z = currentRef;
                }
                else
                {
                    upperRef = pos.z;
                    currentRef = (lowerRef + upperRef) / 2;
                    pos.z = currentRef;
                }

                if(upperRef - lowerRef == 1)
                {
                    pos.z++;
                    break;
                }
                else if(upperRef == lowerRef)
                {
                    pos.z++;
                    break;
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return pos;
    }

    public Vector3d getPosition(Box bx)
    {
        //min(x,y), max(z)
        Vector3d pos = new Vector3d(0,0,this.z - bx.relativeDimensions[2]);

        /*TODO
         *
         * Create logic to find a position to the box. I suggest we use a
         * lefter/deeper aproach (min x,y) after what we can make the box
         * fall to the lower z (min z).
         *
         * Ao invés de
        */

        return pos;
    }

    /*
     * It basically check if the box bottom overlaps any occupied block.
     * Since all the boxes are placed in the ceiling at first, there is no need
     * PRECONDITIONS: the box position must be such as to make it touch the
     * ofceiling of the container.
     * (pos.z + vertical side value = container.z + 1)
    */
    public boolean fitsHere(Box bx, Vector3d pos)
    {
        try{
            //First checks if any container boundary is exceeded
            if ((pos.x + bx.relativeDimensions[0]) >= this.x) {
                throw new Exception("fitsHere(Box bx, Vector3d _pos) - Porra, saiu fora do container!");
            }
            if ((pos.y + bx.relativeDimensions[1]) >= this.y) {
                throw new Exception("fitsHere(Box bx, Vector3d _pos) - Porra, saiu fora do container!");
            }
            if ((pos.z + bx.relativeDimensions[2]) >= this.z) {
                throw new Exception("fitsHere(Box bx, Vector3d _pos) - Porra, saiu fora do container!");
            }

            if(BottomOverlap(bx.relativeDimensions[0], bx.relativeDimensions[0], pos))
                return false;


            //Brute force check
            for(int i = pos.x; i < pos.x + bx.relativeDimensions[0]; i++)
                for(int j = pos.y; j < pos.y + bx.relativeDimensions[1]; j++)
                    for(int k = pos.z; k < pos.z + bx.relativeDimensions[2]; k++)
                        if(this.spMatrix[i][j][k])
                            return false;

        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    /**
     * Testa se uma caixa cabe dentro do container, em algum lugar.
     */
    public Vector3d fitsIn(Box _box)
    {
        //Varre shuazenegeriamente procurando um lugar pra colocar a caixa.
        for (int i= 0; i < this.x; i++) {
            for (int j= 0; j < this.y; j++) {
                for (int k= 0; k < this.z; k++) {
                    //Se a caixa cabe naquela posicao retorna o
                    //canto inferior da onde ela foi colocada.
                    if(fitsHere(_box, new Vector3d(i, j, k)))
                        return new Vector3d(i, j, k);
                    else
                    {
                        //Se nao cabe, rotaciona ela e tenta de novo.
                        _box.rotate();
                        if(fitsHere(_box, new Vector3d(i, j, k)))
                            return new Vector3d(i, j, k);
                    }
                }
            }
        }
        // o possível problema dessa metodo é atualizar a caixa que rotacionada
        //aqui dentro lá fora... (criar uma struct de vector + bool talvez..)
        return null;
    }
    /**
     * Insere uma caixa dentro do container e retorna as coordenadas
     * relativas ao container de onde a caixa parou.
     */
    public Vector3d insertBox(Box _box)
    {
        return new Vector3d(100, 100, 100);
    }
}