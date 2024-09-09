	package kiu.tetris;

	import com.badlogic.gdx.Gdx;
	import com.badlogic.gdx.Input;
	import com.badlogic.gdx.graphics.Camera;
	import com.badlogic.gdx.graphics.Color;
	import com.badlogic.gdx.graphics.GL20;
	import com.badlogic.gdx.graphics.OrthographicCamera;
	import com.badlogic.gdx.graphics.g2d.BitmapFont;
	import com.badlogic.gdx.graphics.g2d.SpriteBatch;
	import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
	import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
	import com.badlogic.gdx.scenes.scene2d.InputEvent;
	import com.badlogic.gdx.scenes.scene2d.InputListener;
	import com.badlogic.gdx.scenes.scene2d.Stage;
	import com.badlogic.gdx.utils.Timer;
	import com.badlogic.gdx.utils.viewport.ScreenViewport;

	import tetris.Controller;
	import tetris.Graphics;
	import tetris.TetrisModel;
	import tetris.View;

	public class TetrisStage extends Stage implements Graphics {

		static Color[] COLORS = {
				Color.DARK_GRAY, Color.RED, Color.GREEN,
				Color.BLUE, Color.CYAN, Color.YELLOW,
				Color.MAGENTA, Color.MAROON};

		private ShapeRenderer shape;
		private SpriteBatch batch;
		private BitmapFont font;
		private View view;
		private TetrisModel model;
		private Controller controller;

		Timer.Task timerTask;

		public TetrisStage() {
			OrthographicCamera camera = new OrthographicCamera();
			camera.setToOrtho(true);
			setViewport(new ScreenViewport(camera));
			shape = new ShapeRenderer();
			font = new BitmapFont(); // Initialize BitmapFont
			Gdx.graphics.setWindowedMode(400, 700);
		}
		private void updateTimer() {
			System.out.println("alal lalalalalalal");
			if (timerTask != null) {
				timerTask.cancel();
			}
			assignNewTimer();
		}

		private void assignNewTimer() {
			timerTask = Timer.schedule(new Timer.Task() {
				@Override
				public void run() {
					if(model.speedUpdate) {
						System.out.println(model.state.position);
						updateTimer();
						model.speedUpdate = false;
					}
					controller.slideDown();
				}
			}, model.state.speed / 2, model.state.speed / 2);
		}
		public int[][] clear(int[][] matrix){
			return new int[matrix.length][matrix[0].length];
		}

		public void init() {
			model = new TetrisModel();
			view = new View(this);
			controller = new Controller(model, view);
			model.addListener(controller);

			assignNewTimer();

			Gdx.input.setInputProcessor(this);

			addListener(new InputListener() {
				@Override
				public boolean keyDown(InputEvent event, int keycode) {
					if(model.state.figure.length == 0) {
						System.out.println("tatata");
						timerTask.cancel();
						model.clear();
						System.out.println(	model.state.position);
						assignNewTimer();
					}
					switch (keycode) {
						case Input.Keys.LEFT:
							controller.moveLeft();
							break;
						case Input.Keys.RIGHT:
							controller.moveRight();
							break;
						case Input.Keys.DOWN:
							controller.drop();
							break;
						case Input.Keys.UP:
							controller.rotate();
							break;
					}
					return true;
				}
			});
			batch = new SpriteBatch();
			font = new BitmapFont();
			font.setColor(Color.RED);
		}
		@Override
		public void dispose() {
			batch.dispose();
			font.dispose();
		}

		@Override
		public void draw() {
			batch.begin();
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			font.draw(batch, "Score: " + model.state.score, 50, 40);
			batch.end();
			view.draw(model);
			if(model.state.figure.length == 0) {
				batch.begin();
				Gdx.gl.glClearColor(1, 1, 1, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				font.draw(batch, "GAME OVER MY BOY: YOUR SCORE\n Press any button to start over" + model.state.score, 50, 200);
				batch.end();


			}
		}

		@Override
		public void drawBoxAt(int x, int y, int size, int colorIndex) {
			Camera camera = getViewport().getCamera();
			camera.update();

			shape.setProjectionMatrix(camera.combined);

			shape.begin(ShapeType.Filled);
			shape.setColor(COLORS[colorIndex]);
			shape.rect(x, y, size, size);
			shape.end();
		}

	}
