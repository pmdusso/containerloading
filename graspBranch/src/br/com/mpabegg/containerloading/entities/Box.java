/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package br.com.mpabegg.containerloading.entities;

/**
 * 
 * @author otavio_zabaleta
 */
public class Box implements Comparable<Box> {

    private Vector3d dimensions;
    private Boolean isInside;
    private Boolean isRotated;
    private Boolean xv;
    private Boolean yv;
    private Boolean zv;
    private Integer volume;
    private Integer boxType;
    private Vector3d relativeDimensions;
    private Vector3d relativeCoordenates;
    private Orientation vSide;
    private Integer orientation = 1;

    public Box(Vector3d sides, Boolean xv, Boolean yv, Boolean zv, Integer boxType) {
	this.setDimensions(new Vector3d(sides.getX(), sides.getY(), sides.getZ()));
	this.setBoxType(boxType);
	this.setvSide(Orientation.z);
	this.setIsRotated(false);
	this.setIsInside(false);
	this.setRelativeDimensions(new Vector3d(this.getDimensions().getX(), this.getDimensions().getY(), this.getDimensions().getZ()));
	this.setRelativeCoordenates(new Vector3d(0, 0, 0));
	this.setVolume((this.getDimensions().getX() * this.getDimensions().getY() * this.getDimensions().getZ()));
	this.setXv(xv);
	this.setYv(yv);
	this.setZv(zv);
    }

    public Boolean canFlip(Orientation orientation) {
	switch (orientation) {
	    case x: {
		return this.getXv();
	    }
	    case y: {
		return this.getYv();
	    }
	    case z: {
		return this.getZv();
	    }
	    default:
		return false;
	}
    }

    public void flip(Orientation or) {
	if (this.getvSide() == or) {
	    return;
	}

	this.setvSide(or);
	switch (or) {
	    case x: {
		this.getRelativeDimensions().setX(this.getDimensions().getZ());
		this.getRelativeDimensions().setY(this.getDimensions().getY());
		this.getRelativeDimensions().setZ(this.getDimensions().getX());
		break;
	    }
	    case y: {
		this.getRelativeDimensions().setX(this.getDimensions().getX());
		this.getRelativeDimensions().setY(this.getDimensions().getZ());
		this.getRelativeDimensions().setZ(this.getDimensions().getY());
		break;
	    }
	    case z: {
		this.getRelativeDimensions().setX(this.getDimensions().getX());
		this.getRelativeDimensions().setY(this.getDimensions().getY());
		this.getRelativeDimensions().setZ(this.getDimensions().getZ());
		break;
	    }
	}
    }

    public void rotate() {
	int aux = 0;
	switch (this.getOrientation()) {
	    case 1:
		if (this.getZv()) {
		    aux = this.getRelativeDimensions().getY();
		    this.getRelativeDimensions().setY(this.getRelativeDimensions().getX());
		    this.getRelativeDimensions().setX(aux);
		    this.setvSide(Orientation.z);
		}
		setOrientation(getOrientation() << 1);
		break;
	    case 2:
		if (this.getYv()) {
		    aux = this.getRelativeDimensions().getZ();
		    this.getRelativeDimensions().setZ(this.getRelativeDimensions().getX());
		    this.getRelativeDimensions().setX(aux);
		    this.setvSide(Orientation.y);
		}
		setOrientation(getOrientation() << 1);
		break;
	    case 4:
		if (this.getYv()) {
		    aux = this.getRelativeDimensions().getX();
		    this.getRelativeDimensions().setX(this.getRelativeDimensions().getY());
		    this.getRelativeDimensions().setY(aux);
		    this.setvSide(Orientation.y);
		}
		setOrientation(getOrientation() << 1);
		break;
	    case 8:
		if (this.getXv()) {
		    aux = this.getRelativeDimensions().getZ();
		    this.getRelativeDimensions().setZ(this.getRelativeDimensions().getX());
		    this.getRelativeDimensions().setX(aux);
		    this.setvSide(Orientation.x);
		}
		setOrientation(getOrientation() << 1);
		break;
	    case 16:
		if (this.getXv()) {
		    aux = this.getRelativeDimensions().getY();
		    this.getRelativeDimensions().setY(this.getRelativeDimensions().getX());
		    this.getRelativeDimensions().setX(aux);
		    this.setvSide(Orientation.x);
		}
		setOrientation(getOrientation() << 1);
		break;
	    case 32:
		if (this.getZv()) {
		    aux = this.getRelativeDimensions().getX();
		    this.getRelativeDimensions().setX(this.getRelativeDimensions().getZ());
		    this.getRelativeDimensions().setZ(aux);
		    this.setvSide(Orientation.z);
		}
		setOrientation(getOrientation() << 1);
		break;
	}
    }

    public Integer getBoxType() {
	return boxType;
    }

    public Integer getVolume() {
	return this.volume;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Box) {
	    Box box = (Box) obj;
	    return box.getBoxType().equals(this.getBoxType());
	}
	return false;
    }

    public void setRelativeDimensions(Vector3d relativeDimensions) {
	this.relativeDimensions = relativeDimensions;
    }

    public Vector3d getRelativeDimensions() {
	return relativeDimensions;
    }

    public void setRelativeCoordenates(Vector3d relativeCoordenates) {
	this.relativeCoordenates = relativeCoordenates;
    }

    public Vector3d getRelativeCoordenates() {
	return relativeCoordenates;
    }

    public void setvSide(Orientation vSide) {
	this.vSide = vSide;
    }

    public Orientation getvSide() {
	return vSide;
    }

    public void setDimensions(Vector3d dimensions) {
	this.dimensions = dimensions;
    }

    public Vector3d getDimensions() {
	return dimensions;
    }

    public void setIsInside(Boolean isInside) {
	this.isInside = isInside;
    }

    public Boolean getIsInside() {
	return isInside;
    }

    public void setIsRotated(Boolean isRotated) {
	this.isRotated = isRotated;
    }

    public Boolean getIsRotated() {
	return isRotated;
    }

    public void setXv(Boolean xv) {
	this.xv = xv;
    }

    public Boolean getXv() {
	return xv;
    }

    public void setYv(Boolean yv) {
	this.yv = yv;
    }

    public Boolean getYv() {
	return yv;
    }

    public void setZv(Boolean zv) {
	this.zv = zv;
    }

    public Boolean getZv() {
	return zv;
    }

    private void setOrientation(Integer orientation) {
	this.orientation = orientation;
    }

    private Integer getOrientation() {
	return orientation;
    }

    private void setVolume(Integer volume) {
	this.volume = volume;
    }

    private void setBoxType(Integer boxType) {
	this.boxType = boxType;
    }

    @Override
    public int compareTo(Box o) {
	return getVolume().compareTo(o.getBoxType());
    }
}
