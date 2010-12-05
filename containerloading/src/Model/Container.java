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
    private int x, y, z;
    private int volume;

    public int getX() {
        return this.x;
    }

    public int getVolume() {
        return volume;
    }
    private static final Integer X = 0;

    public int getY() {
        return this.y;
    }
    private static final Integer Y = 1;

    public int getZ() {
        return this.z;
    }
    private static final Integer Z = 2;

    public Container(Vector3d _size) {
        this.x = _size.x;
        this.y = _size.y;
        this.z = _size.z;
        this.volume = (_size.x * _size.y * _size.z);
        spMatrix = new boolean[x][y][z];
        //inicializa toda a matriz do container com 0 (container vazio)
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                for (int k = 0; k < this.z; k++) {
                    spMatrix[i][j][k] = false;
                }
            }
        }
    }
    //Checks if the bottom of a box hits something

    private boolean BottomOverlap(int x, int y, Vector3d _pos) {
        int xMax = _pos.x + x;
        int yMax = _pos.y + y;

        try {
            if (xMax > this.x || yMax > this.y) {
                throw new Exception("bottomOverlap(int x, int y, Vector3d _pos) - Porra, saiu fora do container!");
            }

            for (int i = _pos.x; i < xMax; i++) {
                for (int j = _pos.y; j < yMax; j++) {
                    if (this.spMatrix[i][j][_pos.z]) {
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    /* Makes a box fall to the lowest z of it's current (x,y) position.
     * PRECONDITIONS: box position must be a valid one (e.g. no space
     * overlapping and no container boundary exceeded)
     */
    public Vector3d FallBox(Box bx, Vector3d _pos) {
        Vector3d pos = _pos;
        try {
            if (pos.z < 0) {
                throw new Exception("FallBox(Box bx, Vector3d _pos) - Porra, saiu fora do container!");
            }
            int upperRef = pos.z;
            int currentRef = upperRef;
            int lowerRef = 0;

            while (true) {
                if (BottomOverlap(bx.relativeDimensions.x, bx.relativeDimensions.y, pos)) {
                    lowerRef = pos.z;
                    currentRef = (lowerRef + upperRef) / 2;
                    pos.z = currentRef;
                } else {
                    upperRef = pos.z;
                    currentRef = (lowerRef + upperRef) / 2;
                    pos.z = currentRef;
                }

                if (upperRef - lowerRef == 1) {
                    pos.z++;
                    break;
                } else if (upperRef == lowerRef) {
                    pos.z++;
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return pos;
    }

    public Vector3d getPosition(Box bx) {
        //min(x,y), max(z)
        Vector3d pos = new Vector3d(0, 0, this.z - bx.relativeDimensions.z);

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
    public boolean fitsHere(Box bx, Vector3d pos) {
        try {
            //First checks if any container boundary is exceeded
            //Removido o "igual" do teste, deixando apenas o "maior"
            //pois pode haver casos como container {10,10,10} e caixa {10,10,10} (caixa justa no container)
            if ((pos.x + bx.relativeDimensions.x) > this.x) {
                return false;
                //throw new Exception("fitsHere(Box bx, Vector3d _pos) - A caixa saiu fora do container pelo vetor X!");
            }
            if ((pos.y + bx.relativeDimensions.y) > this.y) {
                return false;
                //throw new Exception("fitsHere(Box bx, Vector3d _pos) - A caixa saiu fora do container pelo vetor Y");
            }
            if ((pos.z + bx.relativeDimensions.z) > this.z) {
                return false;
                //throw new Exception("fitsHere(Box bx, Vector3d _pos) - A caixa saiu fora do container pelo vetor Z");
            }

            /*if (BottomOverlap(bx.relativeDimensions[0], bx.relativeDimensions[0], pos)) {
            return false;
            }*/


            //Brute force check
            for (int i = pos.x; i < pos.x + bx.relativeDimensions.x; i++) {
                for (int j = pos.y; j < pos.y + bx.relativeDimensions.y; j++) {
                    for (int k = pos.z; k < pos.z + bx.relativeDimensions.z; k++) {
                        if (this.spMatrix[i][j][k]) {
                            return false;
                        }
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    /**
     * Testa se uma caixa cabe dentro do container, em algum lugar.
     * Inicia a procura pelo canto inferior esquerdo e vai progradindo
     * primeiro em x (largura), depois em y (comprimento) e então em z (altura).
     */
    public Box fitsIn(Box _box, Vector3d lastBoxInserted) {

        //Se a ultima caixa colocada "encheu" o eixo Y, passa para a proxima linha.
        if (lastBoxInserted.x == this.x) {

            lastBoxInserted = new Vector3d(
                    0,
                    lastBoxInserted.y + _box.relativeDimensions.y,
                    lastBoxInserted.z);
        }
        //Se a ultima caixa colocada "encheu" o eixo X, passa para a linha de cima.
        if (lastBoxInserted.y == this.y) {
            lastBoxInserted = new Vector3d(
                    0, //volta para o começo do container
                    0,
                    lastBoxInserted.z + _box.relativeDimensions.z);
        }
        try {

            //Varre shuazenegeriamente procurando um lugar pra colocar a caixa.
            for (int i = lastBoxInserted.z; i < this.z; i++) {
                for (int j = lastBoxInserted.y; j < this.y; j++) {
                    for (int k = lastBoxInserted.x; k < this.x; k++) {
                        //Se a caixa cabe naquela posicao, atualiza as coordenadas
                        //relativas ao container da caixa e retorna ela.
                        if (fitsHere(_box, new Vector3d(k, j, i))) {
                            _box.relativeCoordenates = new Vector3d(k, j, i);
                            return _box;
                        } else {
                            //Se nao cabe, rotaciona ela e tenta de novo.
                            _box.rotate();
                            if (fitsHere(_box, new Vector3d(k, j, i))) {
                                _box.relativeCoordenates = new Vector3d(k, j, i);
                                return _box;
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Insere uma caixa dentro do container utilizando as coordenadas da caixa
     * relativa a posicao que ela deve ficar no container.
     */
    public boolean insertBox(Box bx) {
        try {
            for (int i = bx.relativeCoordenates.x; i < bx.relativeCoordenates.x + bx.relativeDimensions.x; i++) {
                for (int j = bx.relativeCoordenates.y; j < bx.relativeCoordenates.y + bx.relativeDimensions.y; j++) {
                    for (int k = bx.relativeCoordenates.z; k < bx.relativeCoordenates.z + bx.relativeDimensions.z; k++) {
                        if (!this.spMatrix[i][j][k]) {
                            this.spMatrix[i][j][k] = true;
                        } else {
                            System.out.println("Posicao: (" + i + "," + j + "," + k + ") previamente ocupada.");
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    /**
     * Remove uma caixa dentro do container utilizando as coordenadas da caixa
     * relativa a posicao que ela ficava no container.
     */
    public boolean removeBox(Box bx) {
        try {
            for (int i = bx.relativeCoordenates.x; i < bx.relativeCoordenates.x + bx.relativeDimensions.x; i++) {
                for (int j = bx.relativeCoordenates.y; j < bx.relativeCoordenates.y + bx.relativeDimensions.y; j++) {
                    for (int k = bx.relativeCoordenates.z; k < bx.relativeCoordenates.z + bx.relativeDimensions.z; k++) {
                        if (this.spMatrix[i][j][k]) {
                            this.spMatrix[i][j][k] = false;
                        } else {
                            System.out.println("Posicao: (" + i + "," + j + "," + k + ") previamente ocupada.");
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
