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
    //Idicates if side can be placed horizontally
    public boolean xv,yv,zv;
    public int totalVol;
    //Reflects the sides os of the box according to it's orientation
    public int volume[];

    public Orientation vSide;
    
    //Class constructor
    public Box(Vector3d _sides, boolean _xv, boolean _yv, boolean _zv)
    {
        //Set sides
        this.sides = new Vector3d(_sides.x, _sides.y, _sides.z);
        this.volume = new int[3];
        
        //Default vertical orientation == z
        this.vSide = Orientation.z;
        this.volume[0] = this.sides.x;
        this.volume[1] = this.sides.y;
        this.volume[2] = this.sides.z;
        this.totalVol = this.sides.x * this.sides.y * this.sides.z;
        //Set vertical orientation possibilities
        this.xv = _xv;
        this.yv = _yv;
        this.zv = _zv;
    }

    //Checks if a box axis can be placed vertically
    public boolean canFlip(Orientation or)
    {
        switch(or)
        {
            case x:
            {
                if(this.xv)
                    return true;
                else
                    return false;
            }
            case y:
            {
                if(this.yv)
                    return true;
                else
                    return false;
            }
            case z:
            {
                if(this.zv)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    //Changes the volume[] values to reflects the box vertical orientation
    public void flip(Orientation or)
    {
        if (this.vSide == or)
            return;

        this.vSide = or;
        switch(or)
        {
            case  x:
            {
                this.volume[0] = this.sides.z;
                this.volume[1] = this.sides.y;
                this.volume[2] = this.sides.x;
                break;
            }
            case y:
            {
                this.volume[0] = this.sides.x;
                this.volume[1] = this.sides.z;
                this.volume[2] = this.sides.y;
                break;
            }
            case z:
            {
                this.volume[0] = this.sides.x;
                this.volume[1] = this.sides.y;
                this.volume[2] = this.sides.z;
                break;
            }
        }
    }
}
