package com.github.jmatcj.ld40.gui;

import javafx.scene.paint.Color;

public class ProgressBars {
	
	private int setX;
	private int setY;
	private Color color;
	
	ProgressBars(Color color, int x, int y) {
		setX = x;
		setY = y;
	}
	
	public int getX() {
		return setX;
	}
	
	public int getY() {
		return setY;
	}
	
	public Color getColor() {
		return color;
	}
}
