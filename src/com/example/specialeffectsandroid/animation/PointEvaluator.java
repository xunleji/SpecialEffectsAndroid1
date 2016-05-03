package com.example.specialeffectsandroid.animation;

import com.nineoldandroids.animation.TypeEvaluator;

public class PointEvaluator implements TypeEvaluator<Point> {

	@Override
	public Point evaluate(float fraction, Point startPoint, Point endPoint) {
		float x = startPoint.getX() + fraction
				* (endPoint.getX() - startPoint.getX());
		float y = startPoint.getY() + fraction
				* (endPoint.getY() - startPoint.getY());
		Point point = new Point(x, y);
		return point;
	}

}
