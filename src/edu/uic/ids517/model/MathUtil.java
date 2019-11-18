package edu.uic.ids517.model;

public class MathUtil {
	public static double round(double value, double precision) {
		return Math.round(value * precision)/precision;
		}
}

