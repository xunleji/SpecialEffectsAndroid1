package com.example.specialeffectsandroid.lockpass;

public class PointInfo {
	
	private int id;
	private int nextId;
	private boolean selected;
	private int defaultX;
	private int defaultY;
	private int seletedX;
	private int seletedY;
	private int selectedBitmapRadius;
	private int selectedBitmapDiameter;

	public PointInfo(int id, int defaultX, int defaultY, int seletedX, int seletedY,int selectedBitmapRadius,int selectedBitmapDiameter) {
		this.id = id;
		this.nextId = id;
		this.defaultX = defaultX;
		this.defaultY = defaultY;
		this.seletedX = seletedX;
		this.seletedY = seletedY;
		this.selectedBitmapDiameter = selectedBitmapDiameter;
		this.selectedBitmapRadius = selectedBitmapRadius;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isNotSelected() {
		return !isSelected();
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public int getDefaultX() {
		return defaultX;
	}

	public int getDefaultY() {
		return defaultY;
	}

	public int getSeletedX() {
		return seletedX;
	}

	public int getSeletedY() {
		return seletedY;
	}

	public int getCenterX() {
		return seletedX + selectedBitmapRadius;
	}

	public int getCenterY() {
		return seletedY + selectedBitmapRadius;
	}

	public boolean hasNextId() {
		return nextId != id;
	}

	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

	public boolean isInMyPlace(int x, int y) {
		boolean inX = x > seletedX && x < (seletedX + selectedBitmapDiameter);
		boolean inY = y > seletedY && y < (seletedY + selectedBitmapDiameter);

		if (inX && inY) {
			return true;
		} else {
			return false;
		}
	}
}
