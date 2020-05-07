package com.mod.graph;

import java.util.Random;
import android.graphics.Point;
import 	java.lang.Math;

public class Calc {
	
	public static Random random = new Random();

	public static double map(double x, double in_min, double in_max, double out_min, double out_max){
		  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	public static Point rotate(float centerX, float centerY, float angle,Point p) {
		double rad = Math.toRadians(angle);
		double newX = (Math.cos(rad) * (p.x - centerX) - Math.sin(rad) * (p.y - centerY) + centerX);
		double newY = (Math.sin(rad) * (p.x - centerX) + Math.cos(rad) * (p.y - centerY) + centerY);
		return new Point((int)newX, (int) newY);
	}

}
