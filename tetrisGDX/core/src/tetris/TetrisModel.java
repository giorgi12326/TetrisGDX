package tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class TetrisModel implements GameEventsListener {

	public static final int DEFAULT_HEIGHT = 20;
	public static final int DEFAULT_WIDTH = 10;
	public static final int DEFAULT_COLORS_NUMBER = 7;
	public Boolean speedUpdate = false;

	int maxColors;
	public TetrisState state = new TetrisState();
	final List<ModelListener> listeners = new ArrayList<>();

	public void addListener(ModelListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ModelListener listener) {
		listeners.remove(listener);
	}

	public TetrisModel() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_COLORS_NUMBER);
	}

	public TetrisModel(int width, int height, int maxColors) {
		this.state.width = width;
		this.state.height = height;
		this.maxColors = maxColors;
		state.field = new int[height][width];
		initFigure();
	}

	private void initFigure() {
		state.figure = FigureFactory.createNextFigure();
		state.position = new Pair(state.width / 2 - 2, 0);
	}

	public Pair size() {
		return new Pair(state.width, state.height);
	}

	@Override
	public void slideDown() {
		var newPosition = new Pair(state.position.x(), state.position.y() + 1);
		if (isNewFiguresPositionValid(newPosition)) {
			state.position = newPosition;
			notifyListeners();
		} else {
			pasteFigure();
			initFigure();
			notifyListeners();
			if (!isNewFiguresPositionValid(state.position)) {
				gameOver();
			}
		}
	}

	private void notifyListeners() {
		listeners.forEach(listener -> listener.onChange(this));
	}

	private void gameOver() {
		state.field = new int[0][0];
		state.figure = new int[0][0];
		state.speed = 2;
		state.score = 0;
	}


	@Override
	public void moveLeft() {
		var newPosition = new Pair(state.position.x() - 1, state.position.y());
		if (isNewFiguresPositionValid(newPosition)) {
			state.position = newPosition;
			notifyListeners();
		}
	}

	@Override
	public void moveRight() {
		var newPosition = new Pair(state.position.x() + 1, state.position.y());
		if (isNewFiguresPositionValid(newPosition)) {
			state.position = newPosition;
			notifyListeners();
		}
	}

	@Override
	public void rotate() {
		int[][] rotatedFigure = new int[4][4];
		int[][] oldFigure = state.figure;

		for (int c = 0; c < state.figure.length; c++) {
			for (int r = 0; r < state.figure[0].length; r++) {
				rotatedFigure[r][3 - c] = state.figure[c][r];
			}
		}
		state.figure = rotatedFigure;
		if (isNewFiguresPositionValid(state.position)) {
			notifyListeners();
			return;
		}
		else state.figure = oldFigure;
	}

	@Override
	public void drop(){
		while (true) {
			Pair oldPosition = state.position;
			slideDown();
			if(oldPosition == state.position || state.position.y() == 0) {
				break;
			}
		}
	}

	public boolean isNewFiguresPositionValid(Pair newPosition) {

		boolean[] result = new boolean[1];
		result[0] = true;

		walkThroughAllFigureCells(newPosition, (absPos, relPos) -> {
			if (result[0]) {
				result[0] = checkAbsPos(absPos);
			}
		});

		return result[0];
	}

	private void walkThroughAllFigureCells(Pair newPosition, BiConsumer<Pair, Pair> payload) {
		for (int row = 0; row < state.figure.length; row++) {
			for (int col = 0; col < state.figure[row].length; col++) {
				if (state.figure[row][col] == 0)
					continue;
				int absCol = newPosition.x() + col;
				int absRow = newPosition.y() + row;
				payload.accept(new Pair(absCol, absRow), new Pair(col, row));
			}
		}
	}

	private boolean checkAbsPos(Pair absPos) {
		var absCol = absPos.x();
		var absRow = absPos.y();
		if (isColumnPositionOutOfBoundaries(absCol))
			return false;
		if (isRowPositionOutOfBoundaries(absRow))
			return false;
		if (state.field[absRow][absCol] != 0)
			return false;
		return true;
	}

	private boolean isRowPositionOutOfBoundaries(int absRow) {
		return absRow < 0 || absRow >= state.height;
	}

	private boolean isColumnPositionOutOfBoundaries(int absCol) {
		return absCol < 0 || absCol >= state.width;
	}

	public void pasteFigure() {
		walkThroughAllFigureCells(state.position, (absPos, relPos) -> {
			state.field[absPos.y()][absPos.x()] = state.figure[relPos.y()][relPos.x()];
		});
		checkRowsThatNeedCheckIfFilled();
	}
	private void checkRowsThatNeedCheckIfFilled() {
		for (int col = state.position.y(); col <= state.position.y()+3 && col < state.height; col++) {
			boolean ifRowShouldBeDeleted = true;
			for (int row = 0; row < state.field[0].length; row++) {
				if(state.field[col][row] == 0){
					ifRowShouldBeDeleted = false;
					break;
				}
			}
			if(ifRowShouldBeDeleted){
				state.score += state.width;
				checkIfScoreIncresesLevel(state.score);
				collapseRowsOn(col);
			}
		}
	}

	private void checkIfScoreIncresesLevel(int score) {
		if(state.speed != (float)(state.score/20)+1) {
			System.out.println(state.score +  " " + state.speed	);
			state.speed /= 2;
			speedUpdate = true;
		}
	}

	private void collapseRowsOn(int colHeight) {
		for (int col = colHeight; col > 0; col--) {
			for (int row = 0; row < state.field[0].length; row++) {
				state.field[col][row] = state.field[col-1][row];
			}
		}
		Arrays.fill(state.field[0], 0);
	}
	public void clear(){
		state.field = new int[20][10];
		state.figure = FigureFactory.createNextFigure();
		state.position = new Pair(state.width / 2 - 2, 0);
	}

}
