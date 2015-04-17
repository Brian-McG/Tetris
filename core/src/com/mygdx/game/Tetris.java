//Brian Mc George
//MCGBRI004

package com.mygdx.game;

//Imports
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tetris extends ApplicationAdapter
{
	// Class Variables
	private TetrisLogic logic = new TetrisLogic(this);
	private Leaderboard leaderboard = new Leaderboard();

	// Sound
	Sound p1BlockFlip;
	Sound p2BlockFlip;
	Sound rowClear;

	// Sprite Related
	SpriteBatch batch;

	// Textures
	private Texture block;
	private Texture cursor;

	// Fonts
	private BitmapFont font;
	private BitmapFont font2;
	private BitmapFont font3;
	private BitmapFont gameFont;
	private BitmapFont bottomLine;
	private BitmapFont header;
	private BitmapFont gameEndScores;
	// Block Type
	int cursorTypeP1;
	int nextCursorP1Type;
	int cursorTypeP2;
	int nextCursorP2Type;

	// Block Location
	// Player 1
	int[][] nextCursorP1 = new int[4][2];
	int[][] cursorLocP1 = new int[4][2];

	// Player 2
	int[][] nextCursorP2 = new int[4][2];
	int[][] cursorLocP2 = new int[4][2];

	// Default block start positions
	int startX_P1 = 6;
	int startX_P2 = 2;
	int startY = 20;

	// Block Size
	float blockW = 20;
	float blockH = 20;

	// Grid Size
	int gridW = 10;
	int gridH = 20;

	// Grid
	boolean[][] grid = new boolean[gridW][gridH];

	// Score Board
	int fullRows = 0;
	long score = 0;
	int level = 0;
	private boolean leaderboardUpdate = false;
	Leader[] leaders = leaderboard.getLeaders();

	// Block Drop rate
	float updateRate = 1f;

	// One movement per button press restrictors
	boolean manyKeyPressedP1 = false;
	boolean manyKeyPressedP2 = false;
	boolean manyGameResetPressed = false;
	boolean spacePushed = false;
	boolean manyPausePressed = false;

	// Game State
	enum gameState
	{
		Menu, Active, Paused, Over, nextLevel
	};

	gameState state = gameState.Active;

	// Two Player Controller
	boolean twoPlayer = false;

	// Timing Variables
	private float timeElapsed = 0;
	private float waitGameOver = 0;

	@Override
	public void create() // Instantiate Variables
	{
		// Window
		resize(700, 500);
		

		// Fonts
		Texture fontTexture = new Texture(
				Gdx.files.internal("CustomFont_0.png"));
		header = new BitmapFont(Gdx.files.internal("CustomFont.fnt"),
				new TextureRegion(fontTexture), false);
		font = new BitmapFont(Gdx.files.internal("CustomFont.fnt"),
				new TextureRegion(fontTexture), false);
		font.setScale(0.5f, 0.5f);

		font2 = new BitmapFont(Gdx.files.internal("CustomFont.fnt"),
				new TextureRegion(fontTexture), false);
		font2.setScale(0.8f, 0.5f);

		font3 = new BitmapFont();

		gameFont = new BitmapFont(Gdx.files.internal("CustomFont.fnt"),
				new TextureRegion(fontTexture), false);

		bottomLine = new BitmapFont(Gdx.files.internal("CustomFont.fnt"),
				new TextureRegion(fontTexture), false);
		bottomLine.setScale(0.8f, 0.5f);
		gameEndScores = new BitmapFont(Gdx.files.internal("CustomFont.fnt"),
				new TextureRegion(fontTexture), false);
		gameEndScores.setScale(0.55f, 0.55f);

		// Sounds
		p1BlockFlip = Gdx.audio.newSound(Gdx.files
				.internal("p1BlockRotate.wav"));
		p2BlockFlip = Gdx.audio.newSound(Gdx.files
				.internal("p2BlockRotate.wav"));
		rowClear = Gdx.audio.newSound(Gdx.files.internal("rowClear.wav"));

		// Block Position and Type
		cursorTypeP1 = logic.setToRandomBlock(cursorLocP1, startX_P1);
		nextCursorP1Type = logic.setToRandomBlock(nextCursorP1, startX_P1);

		// Grid Instantiation
		for (int y = 0; y < gridH; y++)
		{
			// go over each column left to right
			for (int x = 0; x < gridW; x++)
			{
				grid[x][y] = false;
			}
		}

		// Sprite Related
		batch = new SpriteBatch();

		// Textures
		cursor = new Texture("cursor.png");
		block = new Texture("block.png");

	}

	@Override
	public void dispose()
	{
		batch.dispose();
		cursor.dispose();
		block.dispose();
	}

	// Operate Controls for Player 1
	private void player1Controls()
	{
		// Check if key pressed
		boolean upKeyPressed = Gdx.input.isKeyPressed(Input.Keys.UP);
		boolean downKeyPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN);
		boolean leftKeyPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		boolean rightKeyPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		boolean spaceKeyPressed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
		boolean shiftKeyPressed = Gdx.input
				.isKeyPressed(Input.Keys.SHIFT_RIGHT);

		// Player One Controls
		// Up
		if (upKeyPressed && !manyKeyPressedP1)
		{
			if (cursorTypeP1 != 1) // Check if
			{
				int[][] rotatedArray = TetrisLogic
						.rotate90DegreesClockwise(cursorLocP1);
				if (logic.canRotate(rotatedArray, 0))
				{
					cursorLocP1 = rotatedArray;
					p1BlockFlip.play(0.5f);
				}
			}
			else
			{
				p1BlockFlip.play(0.5f);
			}

		}
		// Down
		else if (downKeyPressed && !manyKeyPressedP1)
		{
			logic.moveDown(0);
			p1BlockFlip.play(0.25f);
		}
		// Left
		else if (leftKeyPressed && !manyKeyPressedP1)
		{
			if (logic.canMoveHorizontally(cursorLocP1, -1, 0))
			{
				for (int i = 0; i < 4; i++)
				{
					cursorLocP1[i][0]--;
				}
				manyKeyPressedP1 = true;
				p1BlockFlip.play(0.25f);
			}

		}
		// Right
		else if (rightKeyPressed && !manyKeyPressedP1)
		{
			if (logic.canMoveHorizontally(cursorLocP1, 1, 0))
			{
				for (int i = 0; i < 4; i++)
				{
					cursorLocP1[i][0]++;
				}
				manyKeyPressedP1 = true;
				p1BlockFlip.play(0.25f);
			}
		}
		// Space
		else if (spaceKeyPressed && !manyKeyPressedP1 && !manyGameResetPressed)
		{
			logic.instantMoveDown(0);
			manyKeyPressedP1 = true;
		}
		// Shift
		else if (shiftKeyPressed && !manyKeyPressedP1)
		{
			logic.instantMoveDown(0);
			manyKeyPressedP1 = true;
		}

		// Reset multiKeyPressed
		manyKeyPressedP1 = upKeyPressed | downKeyPressed | leftKeyPressed
				| spaceKeyPressed | rightKeyPressed | shiftKeyPressed;

	}

	// Operate Player 2 Controls
	private void player2Controls()
	{
		if (twoPlayer)
		{
			boolean wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
			boolean sPressed = Gdx.input.isKeyPressed(Input.Keys.S);
			boolean aPressed = Gdx.input.isKeyPressed(Input.Keys.A);
			boolean dPressed = Gdx.input.isKeyPressed(Input.Keys.D);
			boolean leftShiftPressed = Gdx.input
					.isKeyPressed(Input.Keys.SHIFT_LEFT);

			// Player Two Controls
			// Up
			if (wPressed && !manyKeyPressedP2)
			{
				if (cursorTypeP2 != 1)
				{
					int[][] rotatedArray = TetrisLogic
							.rotate90DegreesClockwise(cursorLocP2);
					if (logic.canRotate(rotatedArray, 1))
					{
						cursorLocP2 = rotatedArray;
						p2BlockFlip.play(0.5f);
					}

				}
				else
				{
					p2BlockFlip.play(0.5f);
				}
			}
			// Down
			else if (sPressed && !manyKeyPressedP2)
			{
				logic.moveDown(1);
				p2BlockFlip.play(0.25f);
			}
			// Left
			else if (aPressed && !manyKeyPressedP2)
			{
				if (logic.canMoveHorizontally(cursorLocP2, -1, 1))
				{
					for (int i = 0; i < 4; i++)
					{
						cursorLocP2[i][0]--;
					}
					manyKeyPressedP2 = true;
					p2BlockFlip.play(0.25f);
				}

			}
			// Right
			else if (dPressed && !manyKeyPressedP2)
			{
				if (logic.canMoveHorizontally(cursorLocP2, 1, 1))
				{
					for (int i = 0; i < 4; i++)
					{
						cursorLocP2[i][0]++;
					}
					manyKeyPressedP2 = true;
					p2BlockFlip.play(0.25f);
				}
			}
			// Left Shift
			else if (leftShiftPressed && !manyKeyPressedP2)
			{
				logic.instantMoveDown(1);
				manyKeyPressedP2 = true;
			}

			// Reset many keys pressed
			manyKeyPressedP2 = wPressed | sPressed | aPressed | dPressed
					| leftShiftPressed;
		}
	}

	// Activate Two players
	private void twoPlayerActivation()
	{
		boolean enterKeyPressed = Gdx.input.isKeyPressed(Input.Keys.ENTER);

		if (enterKeyPressed && !twoPlayer)
		{
			twoPlayer = true;
			logic.makeCompletlyNewCursorP2();
		}
	}

	// Operate Pause Controls
	private void operatePauses()
	{
		boolean pause = Gdx.input.isKeyPressed(Input.Keys.ESCAPE);
		if (pause && !manyPausePressed)
		{
			if (state == gameState.Active)
			{
				pause();
			}
			else if (state == gameState.Paused)
			{
				resume();
			}

		}
		manyPausePressed = pause;
	}

	// Operate game over controls
	private void operateGameOver()
	{
		spacePushed = Gdx.input.isKeyPressed(Input.Keys.SPACE);
		if (state == gameState.Over)
		{
			waitGameOver += Gdx.graphics.getDeltaTime();
			if (spacePushed && !manyGameResetPressed && waitGameOver > 1)
			{
				resetGame();
			}
		}
		manyGameResetPressed = spacePushed;
	}

	private void resetGame()
	{
		// Reset Grid
		for (int x = 0; x < gridW; x++)
		{
			for (int y = 0; y < gridH; y++)
			{
				grid[x][y] = false;
			}
		}

		// Allocate new blocks
		logic.setNewP1Cursor();
		logic.setNewP2Cursor();
		// Player 2 automatically drops out when game ends
		twoPlayer = false;
		// Set game active
		state = gameState.Active;
		waitGameOver = 0;
		leaderboardUpdate = false;
		//Reset Update Rate
		updateRate=1f;
		// Reset Scoring
		score = 0;
		fullRows = 0;
		level = 0;
		// Get new Leaders
		leaders = leaderboard.getLeaders();
	}

	public void update() // Update positions and game state
	{
		operatePauses();
		if (state == gameState.Active)
		{
			timeElapsed = timeElapsed + Gdx.graphics.getDeltaTime();
			if (timeElapsed > updateRate)
			{
				// Auto Move Blocks
				logic.autoMoveBlocks();
				// Reset Time Elapsed
				timeElapsed = 0;
			}
			twoPlayerActivation();
			player1Controls();
			player2Controls();
		}
		operateGameOver();
	}

	@Override
	public void render() // Render game
	{
		update();

		// set background colour and clear screen
		Gdx.gl.glClearColor(0.24f, 0.24f, 0.28f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// do all drawing between batch.begin and end
		batch.begin();

		// sample grid drawing
		// go over each row bottom to top

		if (state == gameState.Active)
		{
			// Heading
			header.draw(batch, "Tetris", Gdx.graphics.getWidth() / 2.4f,
					Gdx.graphics.getHeight() - 20);

			for (int y = 0; y < gridH; y++)
			{
				for (int x = 0; x < gridW; x++)
				{
					// Draw Guide lines
					if (x != gridW - 1)
					{
						font3.draw(batch, ".", (x + 2) * blockW, (y + 1)
								* blockH + blockH * 2);
					}

					// Draw Existing blocks
					if (grid[x][y] == true)
					{
						font.draw(batch, "[ ]", (x + 1) * blockW, (y + 1)
								* blockH + blockH * 2);
					}
				}

			}

			// Draw Left Side of grid
			for (int y = 0; y < gridH + 1; y++)
			{
				font.draw(batch, "<!", 0, (y + 1) * blockH + blockH);
			}
			// Draw Right Side of grid
			for (int y = 0; y < gridH + 1; y++)
			{
				font.draw(batch, "!>", (gridW + 1) * blockW, (y + 1) * blockH
						+ blockH);
			}
			// Draw Bottom if grid
			for (int x = 0; x < gridW; x++)
			{
				font2.draw(batch, "\\/", (x + 1) * blockW, blockH);
			}
			// Draw landing zone of grid
			for (int x = 0; x < gridW - 1; x++)
			{
				font2.draw(batch, "><", (x + 1) * blockW, blockH * 2);
			}

			// Player 1 Block
			for (int y = 0; y < 4; y++)
			{
				// Current Block
				if (cursorLocP1[y][1] < gridH)
				{
					// Current Block P1
					font.draw(batch, "[ ]", (cursorLocP1[y][0] + 1) * blockW,
							(cursorLocP1[y][1] + 1) * blockH + blockH * 2);
				}
				// Next Block P1
				font.draw(batch, "[ ]",
						(nextCursorP1[y][0] + 1) * blockW + 150,
						(nextCursorP1[y][1] + 1) * blockH - 200);
			}

			font.draw(batch, "Player 1 Next Block:", (2 + 1) * blockW + 200,
					(20 + 1) * blockH - 160);

			// Player 2 Block
			if (twoPlayer)
			{
				for (int y = 0; y < 4; y++)
				{
					// Current Block P2
					if (cursorLocP2[y][1] < gridH)
					{
						font.draw(batch, "[ ]", (cursorLocP2[y][0] + 1)
								* blockW, (cursorLocP2[y][1] + 1) * blockH
								+ blockH * 2);
					}
					// Next Block P2
					font.draw(batch, "[ ]", (nextCursorP2[y][0] + 1) * blockW
							+ 250, (nextCursorP2[y][1] + 1) * blockH - 270);
				}

				font.draw(batch, "Player 2 Next Block:",
						(2 + 1) * blockW + 200, (20 + 1) * blockH - 230);
			}
			else
			{
				font.draw(batch, "Press Enter To Activate Player 2", (2 + 1)
						* blockW + 200, (20 + 1) * blockH - 230);
			}

			// Scoring
			font.draw(batch, "Full Lines: " + fullRows, (2 + 1) * blockW + 200,
					(20 + 1) * blockH - 10);
			font.draw(batch, "Level: " + level, (2 + 1) * blockW + 201,
					(20 + 1) * blockH - 30);
			font.draw(batch, "Score: " + score, (2 + 1) * blockW + 200,
					(20 + 1) * blockH - 50);

			// Leaderboard
			font.draw(batch, "Leaderboard", (2 + 1) * blockW + 350, (20 + 1)
					* blockH - 10);
			font.draw(batch, "Name - Level - Score", (2 + 1) * blockW + 350,
					(20 + 1) * blockH - 30);
			for (int i = 0; i < leaders.length; i++)
			{
				if (leaders[i] != null)
				{
					font.draw(batch, "" + (i + 1) + ". " + leaders[i].getName()
							+ " - " + leaders[i].getLevel() + " - "
							+ leaders[i].getScore(), (2 + 1) * blockW + 350,
							(20 + 1) * blockH - 30 - (20 * (i + 1)));
				}
			}

		}
		else if (state == gameState.Paused)
		{

			gameFont.draw(batch, "Game Paused",
					Gdx.graphics.getWidth() / 2f - 150,
					Gdx.graphics.getHeight() / 2f + 100);
			gameFont.draw(batch, "Press Escape to un-pause",
					Gdx.graphics.getWidth() / 2f - 250,
					Gdx.graphics.getHeight() / 2f + 50);

		}
		else if (state == gameState.Over)
		{
			gameFont.draw(batch, "Game Over",
					Gdx.graphics.getWidth() / 2f - 110,
					Gdx.graphics.getHeight() / 2f + 130);
			gameFont.draw(batch, "Press Space to restart",
					Gdx.graphics.getWidth() / 2f - 210,
					Gdx.graphics.getHeight() / 2f + 80);

			if (!leaderboardUpdate)
			{
				leaders = leaderboard.addLeader(score, level);
				leaderboardUpdate = true;
			}

			// End Game scoring
			gameEndScores.draw(batch, "Full Lines: " + fullRows,
					Gdx.graphics.getWidth() / 2f - 210,
					Gdx.graphics.getHeight() / 2f);
			gameEndScores.draw(batch, "Level: " + level,
					Gdx.graphics.getWidth() / 2f - 210,
					Gdx.graphics.getHeight() / 2f - 20);
			gameEndScores.draw(batch, "Score: " + score,
					Gdx.graphics.getWidth() / 2f - 210,
					Gdx.graphics.getHeight() / 2f - 40);

			// End Game Leaderboard
			gameEndScores.draw(batch, "Leaderboard", (2 + 1) * blockW + 290,
					(20 + 1) * blockH - 170);
			gameEndScores.draw(batch, "Name - Level - Score", (2 + 1) * blockW
					+ 290, (20 + 1) * blockH - 190);
			for (int i = 0; i < leaders.length; i++)
			{
				if (leaders[i] != null)
				{
					gameEndScores.draw(batch,
							"" + (i + 1) + ". " + leaders[i].getName() + " - "
									+ leaders[i].getLevel() + " - "
									+ leaders[i].getScore(), (2 + 1) * blockW
									+ 300, (20 + 1) * blockH - 190
									- (20 * (i + 1)));
				}
			}

		}

		batch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		Gdx.graphics.setDisplayMode(width, height, false);
	}

	@Override
	public void pause()
	{
		if (state == gameState.Active)
		{
			state = gameState.Paused;
		}

	}

	@Override
	public void resume()
	{
		if (state == gameState.Paused)
		{
			state = gameState.Active;
		}
	}
}
