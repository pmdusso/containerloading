/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author otavio_zabaleta
 */
public class Box {

    //Box dimensions
    public Vector3d sides;
    //se a caixa está dentro do container
    public boolean isInside;
    //se a caixa está rotacionada
    public boolean isRotated;
    //Idicates if side can be placed horizontally
    public boolean xv, yv, zv;
    //The box volume
    private int volume;
    //The type of the box
    private int boxType;
    //Reflects the sides os of the box according to it's orientation
    public Vector3d relativeDimensions;
    public Vector3d relativeCoordenates;
    public Orientation vSide;

    //Class constructor
    public Box(Vector3d _sides, boolean _xv, boolean _yv, boolean _zv,int _boxType) {
        //Set sides
        this.sides = new Vector3d(_sides.x, _sides.y, _sides.z);
        //sets the box type.
        this.boxType = _boxType;
        //Default vertical orientation == z
        this.vSide = Orientation.z;
        //quando criada, a caixa esta com os sides originais
        this.isRotated = false;
        //quando criada, a caixa esta fora do container
        this.isInside = false;
        //dimensoes relativas ao container
        this.relativeDimensions = new Vector3d(this.sides.x, this.sides.y, this.sides.z);

        //coordenadas da caixa dentro do container, quando inserida.
        this.relativeCoordenates = new Vector3d(0, 0, 0);

        //volume da caixa
        this.volume = (this.sides.x * this.sides.y * this.sides.z);
        //Set vertical orientation possibilities
        this.xv = _xv;
        this.yv = _yv;
        this.zv = _zv;
    }

    //Checks if a box axis can be placed vertically
    public boolean canFlip(Orientation or) {
        switch (or) {
            case x: {
                if (this.xv) {
                    return true;
                } else {
                    return false;
                }
            }
            case y: {
                if (this.yv) {
                    return true;
                } else {
                    return false;
                }
            }
            case z: {
                if (this.zv) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    //Changes the relativeDimensions[] values to reflects the box vertical orientation
    public void flip(Orientation or) {
        if (this.vSide == or) {
            return;
        }

        this.vSide = or;
        switch (or) {
            case x: {
                this.relativeDimensions.x = this.sides.z;
                this.relativeDimensions.y = this.sides.y;
                this.relativeDimensions.z = this.sides.x;
                break;
            }
            case y: {
                this.relativeDimensions.x = this.sides.x;
                this.relativeDimensions.y = this.sides.z;
                this.relativeDimensions.z = this.sides.y;
                break;
            }
            case z: {
                this.relativeDimensions.x = this.sides.x;
                this.relativeDimensions.y = this.sides.y;
                this.relativeDimensions.z = this.sides.z;
                break;
            }
        }
    }

    /*
     * Rotates a box 90° around container z axis
     */
    public void rotate() {
        int aux = this.relativeDimensions.x;
        this.relativeDimensions.x = this.relativeDimensions.y;
        this.relativeDimensions.y = aux;
        this.isRotated = !this.isRotated;
    }

    public int getBoxType() {
        return boxType;
    }

    public int getVolume() {
        return this.volume;
    }
}
