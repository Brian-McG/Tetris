//Brian Mc George
//MCGBRI004

package com.mygdx.game;

//Imports
import java.util.Random;

import com.mygdx.game.Tetris.gameState;

public class TetrisLogic
{
	private Tetris gameObject;
	private Random r = new Random();

	public TetrisLogic(Tetris gameObj)
	{
		gameObject = gameObj;
	}

	// Moves active blocks one unit down
	void autoMoveBlocks()
	{
		if (gameObject.twoPlayer)
		{
			// Decide which player is below the other, the lowest player (in the
			// grid) moves first, this is done so that a block never stops
			// moving because of another block below it
			int whoMoveFirst = 0;
			int minValue = 22;
			for (int i = 0; i < 4; i++)
			{
				if (gameObject.cursorLocP1[i][1] < minValue)
				{
					whoMoveFirst = 0;
					minValue = gameObject.cursorLocP1[i][1];
				}
				if (gameObject.cursorLocP2[i][1] < minValue)
				{
					whoMoveFirst = 1;
					minValue = gameObject.cursorLocP2[i][1];
				}
			}
			// Player 1 below player 2
			if (whoMoveFirst == 0)
			{
				moveDown(0);
				moveDown(1);
			}
			// Player 2 below player 1
			else
			{
				moveDown(1);
				moveDown(0);

			}
		}
		else
		{
			moveDown(0);
		}
	}

	// Move players block down
	boolean moveDown(int playerType)
	{
		// Player 1
		if (playerType == 0)
		{
			// Check if player 2 blocks player 1
			boolean otherPlayerBlock = false;
			if (gameObject.twoPlayer)
			{
				otherPlayerBlock = isOtherPlayerInWay(0);
			}
			if (canMoveDown(gameObject.cursorLocP1) && !otherPlayerBlock)
			{
				// Move block down
				for (int i = 0; i < 4; i++)
				{
					gameObject.cursorLocP1[i][1]--;
				}
				return true;

			}
			else if (!otherPlayerBlock)
			{
				// Make new block because this one cannot move
				setNewP1Cursor();
				return false;
			}
			else
			{
				return false;
			}
		}
		// Player 2
		else
		{
			// Check if player 2 blocks Player 1
			boolean otherPlayerBlock = isOtherPlayerInWay(1);
			if (canMoveDown(gameObject.cursorLocP2) && !otherPlayerBlock)
			{
				for (int i = 0; i < 4; i++)
				{
					gameObject.cursorLocP2[i][1]--;
				}
				return true;

			}
			else if (!otherPlayerBlock)
			{
				// Make new block because this one cannot move
				setNewP2Cursor();
				return false;
			}
			else
			{
				return false;
			}
		}
	}

	// Keep moving block down until it cannot move
	void instantMoveDown(int playerType)
	{
		while (moveDown(playerType))
		{
		}
	}

	// Checks if the other player is blocking argument player from moving one
	// unit down
	private boolean isOtherPlayerInWay(int playerType)
	{
		boolean blocked = false;
		if (playerType == 0)
		{
			for (int i = 0; i < 4; i++)
			{
				for (int z = 0; z < 4; z++)
				{
					if (gameObject.cursorLocP1[i][1] - 1 == gameObject.cursorLocP2[z][1])
					{
						if (gameObject.cursorLocP1[i][0] == gameObject.cursorLocP2[z][0])
						{
							blocked = true;
						}
					}
				}
			}
		}
		else
		{
			for (int i = 0; i < 4; i++)
			{
				for (int z = 0; z < 4; z++)
				{
					if (gameObject.cursorLocP2[i][1] - 1 == gameObject.cursorLocP1[z][1])
					{
						if (gameObject.cursorLocP2[i][0] == gameObject.cursorLocP1[z][0])
						{
							blocked = true;
						}
					}
				}
			}
		}
		return blocked;
	}

	// Generate completely new block for player 2
	void makeCompletlyNewCursorP2()
	{
		gameObject.cursorTypeP2 = setToRandomBlock(gameObject.cursorLocP2,
				gameObject.startX_P2);
		gameObject.nextCursorP2Type = setToRandomBlock(gameObject.nextCursorP2,
				gameObject.startX_P2);
	}

	// Set current block to next block and generate new next block for player 1
	void setNewP1Cursor()
	{
		gameObject.cursorLocP1 = copycursorLoc(gameObject.nextCursorP1);
		gameObject.cursorTypeP1 = gameObject.nextCursorP1Type;
		gameObject.nextCursorP1Type = setToRandomBlock(gameObject.nextCursorP1,
				gameObject.startX_P1);
	}

	// Set current block to next block and generate new next block for player 2
	void setNewP2Cursor()
	{
		gameObject.cursorLocP2 = copycursorLoc(gameObject.nextCursorP2);
		gameObject.cursorTypeP2 = gameObject.nextCursorP2Type;
		gameObject.nextCursorP2Type = setToRandomBlock(gameObject.nextCursorP2,
				gameObject.startX_P2);
	}

	// Copy cursor location of argument array and return new array containing
	// argument array values. This is done to prevent reference passing.
	private int[][] copycursorLoc(int[][] inputArr)
	{
		int[][] newArr = new int[4][2];
		for (int i = 0; i < 4; i++)
		{
			newArr[i][0] = inputArr[i][0];
			newArr[i][1] = inputArr[i][1];
		}
		return newArr;
	}

	// returns argument array set to random block at set starting position with
	// argument start X location (for each player)
	int setToRandomBlock(int[][] inputArr, int startX)
	{
		int cursorType = -1;
		int randomInt = r.nextInt(7);

		if (randomInt == 0) // Z Piece
		{
			cursorType = 0;
			inputArr[0][0] = startX;
			inputArr[0][1] = gameObject.startY;
			inputArr[1][0] = startX + 1;
			inputArr[1][1] = gameObject.startY;
			inputArr[2][0] = startX;
			inputArr[2][1] = gameObject.startY + 1;
			inputArr[3][0] = startX - 1;
			inputArr[3][1] = gameObject.startY + 1;

		}
		else if (randomInt == 1) // O Piece - Square
		{
			cursorType = 1;
			inputArr[0][0] = startX;
			inputArr[0][1] = gameObject.startY;
			inputArr[1][0] = startX + 1;
			inputArr[1][1] = gameObject.startY;
			inputArr[2][0] = startX;
			inputArr[2][1] = gameObject.startY + 1;
			inputArr[3][0] = startX + 1;
			inputArr[3][1] = gameObject.startY + 1;
		}
		else if (randomInt == 2) // S shape
		{
			cursorType = 2;
			inputArr[0][0] = startX;
			inputArr[0][1] = gameObject.startY;
			inputArr[1][0] = startX - 1;
			inputArr[1][1] = gameObject.startY;
			inputArr[2][0] = startX;
			inputArr[2][1] = gameObject.startY + 1;
			inputArr[3][0] = startX + 1;
			inputArr[3][1] = gameObject.startY + 1;
		}
		else if (randomInt == 3) // T Piece
		{
			cursorType = 3;
			inputArr[0][0] = startX;
			inputArr[0][1] = gameObject.startY + 1;
			inputArr[1][0] = startX - 1;
			inputArr[1][1] = gameObject.startY + 1;
			inputArr[2][0] = startX;
			inputArr[2][1] = gameObject.startY;
			inputArr[3][0] = startX + 1;
			inputArr[3][1] = gameObject.startY + 1;
		}
		else if (randomInt == 4) // J Shape
		{
			cursorType = 4;
			inputArr[0][0] = startX;
			inputArr[0][1] = gameObject.startY + 1;
			inputArr[1][0] = startX - 1;
			inputArr[1][1] = gameObject.startY + 1;
			inputArr[2][0] = startX + 1;
			inputArr[2][1] = gameObject.startY + 1;
			inputArr[3][0] = startX + 1;
			inputArr[3][1] = gameObject.startY;
		}
		else if (randomInt == 5) // L Piece
		{
			cursorType = 5;
			inputArr[0][0] = startX;
			inputArr[0][1] = gameObject.startY + 1;
			inputArr[1][0] = startX - 1;
			inputArr[1][1] = gameObject.startY + 1;
			inputArr[2][0] = startX + 1;
			inputArr[2][1] = gameObject.startY + 1;
			inputArr[3][0] = startX - 1;
			inputArr[3][1] = gameObject.startY;
		}
		else if (randomInt == 6) // I Piece
		{
			cursorType = 6;
			inputArr[0][0] = startX;
			inputArr[0][1] = gameObject.startY;
			inputArr[1][0] = startX + 1;
			inputArr[1][1] = gameObject.startY;
			inputArr[2][0] = startX + 2;
			inputArr[2][1] = gameObject.startY;
			inputArr[3][0] = startX - 1;
			inputArr[3][1] = gameObject.startY;
		}
		return cursorType;
	}

	// Removes any full rows
	private void removeFullRows()
	{
		int rowsRemovedAtOnce = 0;
		// Check grid for full rows
		for (int y = 0; y < gameObject.gridH; y++)
		{
			boolean rowFull = true;
			for (int x = 0; x < gameObject.gridW; x++)
			{
				if (gameObject.grid[x][y] == false)
				{
					rowFull = false;
					break;
				}
			}
			if (rowFull)
			{
				// Set full row to empty
				for (int x = 0; x < gameObject.gridW; x++)
				{
					gameObject.grid[x][y] = false;
				}
				// Move all blocks above this one one unit down
				for (int i = y; i < gameObject.gridH - 1; i++)
				{
					for (int z = 0; z < gameObject.gridW; z++)
					{
						gameObject.grid[z][i] = gameObject.grid[z][i + 1];
					}
				}
				y--; // recheck this same row (as above rows shifted down)
				gameObject.fullRows++;
				rowsRemovedAtOnce++;
				OperateLeveling();
				gameObject.rowClear.play(0.8f);
			}
		}
		OperateScoring(rowsRemovedAtOnce);
	}

	private void OperateLeveling()
	{
		if (5 * gameObject.level <= gameObject.fullRows)
		{
			gameObject.level++;
			// Update block descent rate
			if (gameObject.updateRate > 0.05)
			{
				gameObject.updateRate -= 0.05;
			}
		}
	}

	private void OperateScoring(int rowsRemovedAtOnce)
	{
		if (rowsRemovedAtOnce > 0)
		{
			//10 base points * 2^rowsRemoved * gameLevel
			//rowsRemoved bonus only applies if >1 row removed at once
			if(rowsRemovedAtOnce>1)
			{
				gameObject.score += Math.round(10 * Math.pow(2.0,
						0.0 + rowsRemovedAtOnce)) * gameObject.level;
			}
			else {
				gameObject.score += Math.round(10 *gameObject.level);
			}
			
		}
	}

	//Returns true if argument block can move one unit down
	boolean canMoveDown(int[][] argCursor)
	{

		boolean canMove = true;
		//Check if block goes outside grid
		for (int i = 0; i < 4; i++)
		{
			if (argCursor[i][1] - 1 < 0)
			{
				canMove = false;
			}
		}
		if (canMove)
		{
//Check if there is a block below argument block
			for (int i = 0; i < 4; i++)
			{
				if (argCursor[i][1] <= 20)
				{

					if (gameObject.grid[argCursor[i][0]][argCursor[i][1] - 1] == true)
					{
						canMove = false;
					}
				}
			}
		}
		if (!canMove)
		{
			for (int i = 0; i < 4; i++)
			{
				//Check if game over
				if (argCursor[i][1] >= 20)
				{
					gameObject.state = gameState.Over;
					System.out.println("GAME OVER");

				}
				//Update grid with new block
				else
				{
					gameObject.grid[argCursor[i][0]][argCursor[i][1]] = true;

				}

			}
			removeFullRows(); //Remove any possible full rows
		}
		return canMove;
	}

	//Returns true if arguement player block can move a certain distance
	boolean canMoveHorizontally(int[][] argCursor, int movement, int playerType)
	{
		boolean canMoveHor = true;
		for (int i = 0; i < 4; i++)
		{
			//Check if block will stay in confines of grid
			if (argCursor[i][0] + movement < 0)
			{
				canMoveHor = false;
			}
			else if (argCursor[i][0] + movement > gameObject.gridW - 1)
			{
				canMoveHor = false;
			}
			if (argCursor[i][1] > gameObject.gridH - 1)
			{
				canMoveHor = false;
			}
		}
		if (canMoveHor)
		{
			for (int i = 0; i < 4; i++)
			{
				//Check if blocks in grid is blocking movement
				if (gameObject.grid[argCursor[i][0] + movement][argCursor[i][1]] == true)
				{
					canMoveHor = false;
				}
				if (gameObject.twoPlayer)
				{
					//Check if other player is blocking movement
					if (playerType == 0)
					{
						for (int z = 0; z < 4; z++)
						{
							if (argCursor[i][0] + movement == gameObject.cursorLocP2[z][0])
							{
								if (argCursor[i][1] == gameObject.cursorLocP2[z][1])
								{
									canMoveHor = false;
								}
							}
						}

					}
					else
					{
						for (int z = 0; z < 4; z++)
						{
							if (argCursor[i][0] + movement == gameObject.cursorLocP1[z][0])
							{
								if (argCursor[i][1] == gameObject.cursorLocP1[z][1])
								{
									canMoveHor = false;
								}
							}
						}
					}
				}

			}
		}
		return canMoveHor;
	}

	boolean canRotate(int rotatedArray[][], int playerType)
	{
		//Check if block stays within grid

		boolean canRotate = true;
		for (int i = 0; i < 4; i++)
		{
			if (rotatedArray[i][0] < 0)
			{
				canRotate = false;
			}
			else if (rotatedArray[i][0] > gameObject.gridW - 1)
			{
				canRotate = false;
			}
			if (rotatedArray[i][1] < 0)
			{
				canRotate = false;
			}
			else if (rotatedArray[i][1] > gameObject.gridH - 1)
			{
				canRotate = false;
			}
		}
		if (canRotate)
		{
			
			for (int i = 0; i < 4; i++)
			{
				//Check if block will intersect another block in grid
				if (gameObject.grid[rotatedArray[i][0]][rotatedArray[i][1]] == true)
				{
					canRotate = false;
				}
				if (gameObject.twoPlayer)
				{
					//Check if block will instersect with other player
					// Player 1
					if (playerType == 0)
					{
						for (int z = 0; z < 4; z++)
						{
							if (rotatedArray[i][1] == gameObject.cursorLocP2[z][1])
							{
								if (rotatedArray[i][0] == gameObject.cursorLocP2[z][0])
								{
									canRotate = false;
									break;
								}
							}
						}
					}
					// Player 2
					else
					{
						for (int z = 0; z < 4; z++)
						{
							if (rotatedArray[i][1] == gameObject.cursorLocP1[z][1])
							{
								if (rotatedArray[i][0] == gameObject.cursorLocP1[z][0])
								{
									canRotate = false;
									break;
								}
							}
						}
					}
				}
			}

		}
		return canRotate;
	}

	//Rotates argument array values 90 degrees clockwise
	public static int[][] rotate90DegreesClockwise(int[][] inputArr)
	{
		int[][] rotatedValues = new int[4][2];
		rotatedValues[0][0] = inputArr[0][0];
		rotatedValues[0][1] = inputArr[0][1];

		for (int i = 1; i < 4; i++)
		{
			rotatedValues[i][0] = inputArr[i][1]
					+ (rotatedValues[0][0] - rotatedValues[0][1]);
			rotatedValues[i][1] = (rotatedValues[0][0] + rotatedValues[0][1])
					- inputArr[i][0];
		}

		return rotatedValues;
	}
}
