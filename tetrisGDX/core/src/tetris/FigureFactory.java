package tetris;

import java.util.Random;

public class FigureFactory {

	private static final Random RANDOM = new Random();

	public static int[][] createNextFigure() {
		int randomFigureIndex = RANDOM.nextInt(7);
		switch (randomFigureIndex) {
			case 0: return O();
			case 1: return I();
			case 2: return S();
			case 3: return Z();
			case 4: return L();
			case 5: return J();
			case 6: return T();
			default: return new int[0][0];
		}
	}

	static int[][] O() {
		return new int[][] {
			{0, 1, 1, 0},
			{0, 1, 1, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0},
		};
	}
	static int[][] I() {
		return new int[][] {
				{0, 0, 2, 0},
				{0, 0, 2, 0},
				{0, 0, 2, 0},
				{0, 0, 2, 0},
		};
	}
	static int[][] S() {
		return new int[][] {
				{0, 0, 3, 3},
				{0, 3, 3, 0},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
		};
	}
	static int[][] Z() {
		return new int[][] {
				{0, 4, 4, 0},
				{0, 0, 4, 4},
				{0, 0, 0, 0},
				{0, 0, 0, 0},
		};
	}
	static int[][] L() {
		return new int[][] {
				{0, 5, 0, 0},
				{0, 5, 0, 0},
				{0, 5, 5, 0},
				{0, 0, 0, 0},
		};
	}
	static int[][] J() {
		return new int[][] {
				{0, 0, 6, 0},
				{0, 0, 6, 0},
				{0, 6, 6, 0},
				{0, 0, 0, 0},
		};
	}

	static int[][] T() {
		return new int[][] {
			{0, 7, 7, 7},
			{0, 0, 7, 0},
			{0, 0, 0, 0},
			{0, 0, 0, 0},
		};
	}
	static int[][] END(){
		return new int[][]{
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		};
	}

	

	
}
