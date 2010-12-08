package Model;

/**
 * 
 * @author otavio_zabaleta
 */
public class Container {
    // The container is divided in 1cm side cubes.

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
	// inicializa toda a matriz do container com 0 (container vazio)
	for (int i = 0; i < this.x; i++) {
	    for (int j = 0; j < this.y; j++) {
		for (int k = 0; k < this.z; k++) {
		    spMatrix[i][j][k] = false;
		}
	    }
	}
    }

    // Verifica se o fundo de uma caixa bate em algo
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
	    // System.out.println("<BottomOverlap>" + ex.toString());
	}

	return false;
    }    

    /*
     * Verifica se uma caixa pode ser colocada em uma posição específica do container
     */
    public boolean fitsHere(Box bx, Vector3d pos) {
	try {
	    if ((pos.x + bx.relativeDimensions.x) > this.x) {
		return false;
	    }
	    if ((pos.y + bx.relativeDimensions.y) > this.y) {
		return false;		
	    }
	    if ((pos.z + bx.relativeDimensions.z) > this.z) {
		return false;
	    }

	    int x = pos.x;
            int y = pos.y;
            int z = pos.z;
            int xl = bx.relativeDimensions.x;
            int yl = bx.relativeDimensions.y;
            int zl = bx.relativeDimensions.z;

            if (this.spMatrix[x][y][z])
                return false;
            else if (this.spMatrix[x+xl][y][z])
                return false;
            else if (this.spMatrix[x][y+yl][z])
                return false;
            else if (this.spMatrix[x+xl][y+yl][z])
                return false;
            else if (this.spMatrix[x][y][z+zl])
                return false;
            else if (this.spMatrix[x+xl][y][z+zl])
                return false;
            else if (this.spMatrix[x][y+yl][z+zl])
                return false;
            else if (this.spMatrix[x+xl][y+yl][z+zl])
                return false;

	    // Brute force check
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
	    // System.out.println("\n/*fitsHere()\n *EXCEPTION:: " +
	    // ex.toString() + "\n */");
	}
	return true;
    }

    /**
     * Testa se uma caixa cabe dentro do container, em algum lugar. Inicia a
     * procura pelo canto inferior esquerdo e vai progradindo primeiro em x
     * (largura), depois em y (comprimento) e então em z (altura).
     */
    public Box fitsIn(Box _box, Vector3d lastBoxInserted) {

	try {

	    // Varre shuazenegeriamente procurando um lugar pra colocar a caixa.
	    for (int i = 0; i < this.x - _box.relativeDimensions.x; i++) {
		for (int j = 0; j < this.y - _box.relativeDimensions.y; j++) {
		    for (int k = 0; k < this.z - _box.relativeDimensions.z; k++) {
			// Se a caixa cabe naquela posicao, atualiza as
			// coordenadas
			// relativas ao container da caixa e retorna ela.
                        if(!this.spMatrix[i][j][k]) {
                            if (fitsHere(_box, new Vector3d(i, j, k))) {
                                _box.relativeCoordenates = new Vector3d(i, j, k);
                                return _box;
                            }
                        }
		    }
		}
	    }

	} catch (Exception e) {
	    // System.out.println("\n/*fitsIn()\n *EXCEPTION:: " + e.toString()
	    // + "\n */");
	}
	return null;
    }

    public Box fitsIn(Box _box) {
	return fitsIn(_box, new Vector3d(0, 0, 0));
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
			    // System.out.println("Posicao: (" + i + "," + j +
			    // "," + k + ") previamente ocupada.");
			    return false;
			}
		    }
		}
	    }
	} catch (Exception e) {
	    // System.out.println("\n/*InsertBox()\n *EXCEPTION:: " +
	    // e.toString() + "\n */");
	    return false;
	}
	return true;
    }

    public boolean insertBox2(Box bx) {
	try {
	    for (int i = bx.relativeCoordenates.x; i < bx.relativeCoordenates.x + bx.relativeDimensions.x; i++) {
		for (int j = bx.relativeCoordenates.y; j < bx.relativeCoordenates.y + bx.relativeDimensions.y; j++) {
		    for (int k = bx.relativeCoordenates.z; k < bx.relativeCoordenates.z + bx.relativeDimensions.z; k++) {
			if (this.spMatrix[i][j][k]) {
			    System.out.println("Posicao: (" + i + "," + j + "," + k + ") previamente ocupada.");
			    return false;
			}
		    }
		}
	    }

            for (int i = bx.relativeCoordenates.x; i < bx.relativeCoordenates.x + bx.relativeDimensions.x; i++) {
		for (int j = bx.relativeCoordenates.y; j < bx.relativeCoordenates.y + bx.relativeDimensions.y; j++) {
		    for (int k = bx.relativeCoordenates.z; k < bx.relativeCoordenates.z + bx.relativeDimensions.z; k++) {
			this.spMatrix[i][j][k] = true;
		    }
		}
	    }
	} catch (Exception e) {
	    System.out.println("\n/*InsertBox()\n *EXCEPTION:: " + e.toString() + "\n */");
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
			    // System.out.println("Posicao: (" + i + "," + j +
			    // "," + k + ") previamente ocupada.");
			    return false;
			}
		    }
		}
	    }
	} catch (Exception e) {
	    // System.out.println("\n/*removeBox()\n *EXCEPTION:: " +
	    // e.toString() + "\n */");
	    return false;
	}
	return true;
    }

    public void clear() {
	spMatrix = new boolean[x][y][z];
    }
}
