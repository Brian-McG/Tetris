//Brian Mc George
//MCGBRI004

package com.mygdx.game;

//Stores a leader in the leaderboard.
public class Leader
{
	private String name;
	private long score;
	private int level;

	public Leader(String n, long scr, int lvl)
	{
		name = n;
		score = scr;
		level = lvl;
	}

	public String getName()
	{
		return name;
	}

	public long getScore()
	{
		return score;
	}

	public int getLevel()
	{
		return level;
	}
}
