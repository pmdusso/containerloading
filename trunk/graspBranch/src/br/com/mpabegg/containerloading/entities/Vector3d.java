package br.com.mpabegg.containerloading.entities;

public class Vector3d {

    private int x;
    private int y;
    private int z;

    public Vector3d(int x, int y, int z) {
	this.setX(x);
	this.setY(y);
	this.setZ(z);
    }

    public void setX(int x) {
	this.x = x;
    }

    public int getX() {
	return x;
    }

    public void setY(int y) {
	this.y = y;
    }

    public int getY() {
	return y;
    }

    public void setZ(int z) {
	this.z = z;
    }

    public int getZ() {
	return z;
    }
}
