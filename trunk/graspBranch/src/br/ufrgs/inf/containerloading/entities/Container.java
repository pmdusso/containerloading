/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package br.ufrgs.inf.containerloading.entities;

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
	this.x = _size.getX();
	this.y = _size.getY();
	this.z = _size.getZ();
	this.volume = (_size.getX() * _size.getY() * _size.getZ());
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

    // Checks if the bottom of a box hits something

    private boolean BottomOverlap(int x, int y, Vector3d _pos) {
	int xMax = _pos.getX() + x;
	int yMax = _pos.getY() + y;

	try {
	    if (xMax > this.x || yMax > this.y) {
		throw new Exception("bottomOverlap(int x, int y, Vector3d _pos) - Porra, saiu fora do container!");
	    }

	    for (int i = _pos.getX(); i < xMax; i++) {
		for (int j = _pos.getY(); j < yMax; j++) {
		    if (this.spMatrix[i][j][_pos.getZ()]) {
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
     * Makes a box fall to the lowest z of it's current (x,y) position.
     * PRECONDITIONS: box position must be a valid one (e.g. no space
     * overlapping and no container boundary exceeded)
     */
    public Vector3d FallBox(Box bx, Vector3d _pos) {
	Vector3d pos = _pos;
	try {
	    if (pos.getZ() < 0) {
		throw new Exception("FallBox(Box bx, Vector3d _pos) - Porra, saiu fora do container!");
	    }
	    int upperRef = pos.getZ();
	    int currentRef = upperRef;
	    int lowerRef = 0;

	    while (true) {
		if (BottomOverlap(bx.getRelativeDimensions().getX(), bx.getRelativeDimensions().getY(), pos)) {
		    lowerRef = pos.getZ();
		    currentRef = (lowerRef + upperRef) / 2;
		    pos.setZ(currentRef);
		} else {
		    upperRef = pos.getZ();
		    currentRef = (lowerRef + upperRef) / 2;
		    pos.setZ(currentRef);
		}

		if (upperRef - lowerRef == 1) {
		    pos.setZ(pos.getZ() + 1);
		    break;
		} else if (upperRef == lowerRef) {
		    pos.setZ(pos.getZ() + 1);
		    break;
		}
	    }
	} catch (Exception ex) {
	    // System.out.println("\n/*FallBox()\n *EXCEPTION:: " +
	    // ex.toString() + "\n */");
	}

	return pos;
    }

    public Vector3d getPosition(Box bx) {
	// min(x,y), max(z)
	Vector3d pos = new Vector3d(0, 0, this.z - bx.getRelativeDimensions().getZ());

	/*
	 * TODO
	 * 
	 * Create logic to find a position to the box. I suggest we use a
	 * lefter/deeper aproach (min x,y) after what we can make the box fall
	 * to the lower z (min z).
	 * 
	 * Ao invés de
	 */

	return pos;
    }

    /*
     * It basically check if the box bottom overlaps any occupied block. Since
     * all the boxes are placed in the ceiling at first, there is no need
     * PRECONDITIONS: the box position must be such as to make it touch the
     * ofceiling of the container. (pos.z + vertical side value = container.z +
     * 1)
     */
    public boolean fitsHere(Box bx, Vector3d pos) {
	try {
	    // First checks if any container boundary is exceeded
	    // Removido o "igual" do teste, deixando apenas o "maior"
	    // pois pode haver casos como container {10,10,10} e caixa
	    // {10,10,10} (caixa justa no container)
	    if ((pos.getX() + bx.getRelativeDimensions().getX()) > this.x) {
		return false;
		// throw new
		// Exception("fitsHere(Box bx, Vector3d _pos) - A caixa saiu fora do container pelo vetor X!");
	    }
	    if ((pos.getY() + bx.getRelativeDimensions().getY()) > this.y) {
		return false;
		// throw new
		// Exception("fitsHere(Box bx, Vector3d _pos) - A caixa saiu fora do container pelo vetor Y");
	    }
	    if ((pos.getZ() + bx.getRelativeDimensions().getZ()) > this.z) {
		return false;
		// throw new
		// Exception("fitsHere(Box bx, Vector3d _pos) - A caixa saiu fora do container pelo vetor Z");
	    }

	    /*
	     * if (BottomOverlap(bx.relativeDimensions[0],
	     * bx.relativeDimensions[0], pos)) { return false; }
	     */

	    // Brute force check
	    for (int i = pos.getX(); i < pos.getX() + bx.getRelativeDimensions().getX(); i++) {
		for (int j = pos.getY(); j < pos.getY() + bx.getRelativeDimensions().getY(); j++) {
		    for (int k = pos.getZ(); k < pos.getZ() + bx.getRelativeDimensions().getZ(); k++) {
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

	// Se a ultima caixa colocada "encheu" o eixo X, passa para a proxima
	// linha. OR
	// Se a distancia entre a ultima caixa colocada e a parede do container
	// nao for
	// suficiente para a proxima caixa...
	if ((lastBoxInserted.getX() == this.x) || ((this.x - lastBoxInserted.getX()) < _box.getRelativeDimensions().getX())) {

	    lastBoxInserted = new Vector3d(0, lastBoxInserted.getY() + _box.getRelativeDimensions().getY(), lastBoxInserted.getZ());
	}
	// Se a ultima caixa colocada "encheu" o eixo Y, passa para a linha de
	// cima. OR
	// Se a distancia entre a ultima caixa colocada e a parede do container
	// nao for
	// suficiente para a proxima caixa...
	if ((lastBoxInserted.getY() == this.y) || ((this.y - lastBoxInserted.getY()) < _box.getRelativeDimensions().getY())) {
	    lastBoxInserted = new Vector3d(0, // volta para o começo do
					      // container
		    0, lastBoxInserted.getZ() + _box.getRelativeDimensions().getZ());
	}
	try {

	    // Varre shuazenegeriamente procurando um lugar pra colocar a caixa.
	    for (int i = lastBoxInserted.getZ(); i < this.z; i++) {
		for (int j = lastBoxInserted.getY(); j < this.y; j++) {
		    for (int k = lastBoxInserted.getX(); k < this.x; k++) {
			// Se a caixa cabe naquela posicao, atualiza as
			// coordenadas
			// relativas ao container da caixa e retorna ela.
			if (!this.spMatrix[k][j][i]) {
			    if (fitsHere(_box, new Vector3d(k, j, i))) {
				_box.setRelativeCoordenates(new Vector3d(k, j, i));
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
	    for (int i = bx.getRelativeCoordenates().getX(); i < bx.getRelativeCoordenates().getX() + bx.getRelativeDimensions().getX(); i++) {
		for (int j = bx.getRelativeCoordenates().getY(); j < bx.getRelativeCoordenates().getY() + bx.getRelativeDimensions().getY(); j++) {
		    for (int k = bx.getRelativeCoordenates().getZ(); k < bx.getRelativeCoordenates().getZ() + bx.getRelativeDimensions().getZ(); k++) {
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

    /**
     * Remove uma caixa dentro do container utilizando as coordenadas da caixa
     * relativa a posicao que ela ficava no container.
     */
    public boolean removeBox(Box bx) {
	try {
	    for (int i = bx.getRelativeCoordenates().getX(); i < bx.getRelativeCoordenates().getX() + bx.getRelativeDimensions().getX(); i++) {
		for (int j = bx.getRelativeCoordenates().getY(); j < bx.getRelativeCoordenates().getY() + bx.getRelativeDimensions().getY(); j++) {
		    for (int k = bx.getRelativeCoordenates().getZ(); k < bx.getRelativeCoordenates().getZ() + bx.getRelativeDimensions().getZ(); k++) {
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
