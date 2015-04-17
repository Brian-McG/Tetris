//Brian Mc George
//MCGBRI004

package com.mygdx.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.stream.*;

public class Leaderboard
{
	private Leader[] leadersArr = new Leader[5];

	// Adds Leader to leaderboard if the score is greater than the any score
	// currently on the leaderboard
	public Leader[] addLeader(long argScore, int argLevel)
	{
		if (argLevel > 0)
		{
			int index = checkLeader(argScore);
			if (index != -1)
			{
				// Declare variables final to be used in thread
				final long score = argScore;
				final int level = argLevel;
				final int indexFinal = index;

				// Prevent game pane freezing by running JOptionPane in own
				// thread
				java.awt.EventQueue.invokeLater(new Runnable()
				{
					@Override
					public void run() // Runs the JOptionPane
					{
						String name = (String) JOptionPane.showInputDialog(
								null, "Please enter your name", "High Score!",
								JOptionPane.PLAIN_MESSAGE, null, null, "");

						if (name != null)
						{
							// Move each leader one place back
							for (int i = 4; i > indexFinal; i--)
							{
								leadersArr[i] = leadersArr[i - 1];
							}
							leadersArr[indexFinal] = new Leader(name, score,
									level);
							try
							{
								writeJsonStream(new FileOutputStream(
										"leaderboard.json"), leadersArr);
							}
							catch (FileNotFoundException e)
							{
								e.printStackTrace();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
				});
				return leadersArr;
			}
		}
		return leadersArr;
	}

	// Returns the an array of current leaders from leaderboard.json
	public Leader[] getLeaders()
	{
		File file = new File("leaderboard.json");
		try
		{
			file.createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			leadersArr = readJsonStream(new FileInputStream("leaderboard.json"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return leadersArr;
	}

	// Checks if current score is greater than one on the leaderboard and
	// returns index that the leader should be inserted, else returns -1 if the
	// score is not greater than one on the leaderboard
	private int checkLeader(long score)
	{
		try
		{
			File file = new File("leaderboard.json");
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			leadersArr = readJsonStream(new FileInputStream("leaderboard.json"));
			int index = -1;
			for (int i = 0; i < 5; i++)
			{
				if (leadersArr[i] == null)
				{
					index = i;
					break;
				}
				else if (score > leadersArr[i].getScore())
				{
					index = i;
					break;
				}
			}
			if (index != -1)
			{
				return index;
			}
			else
			{
				return -1;
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return -1;

	}

	// Writes leaders array to JSON file
	public void writeJsonStream(OutputStream out, Leader[] leaders)
			throws IOException
	{
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
		writeLeaders(writer, leaders);
		writer.close();
	}

	// Writes leaders array to JSON file
	private void writeLeaders(JsonWriter writer, Leader[] leaders)
	{
		try
		{
			writer.beginArray();

			for (Leader leader : leaders)
			{
				if (leader != null)
				{
					writer.beginObject();
					writer.name("name").value(leader.getName());
					writer.name("score").value(leader.getScore());
					writer.name("level").value(leader.getLevel());
					writer.endObject();
				}
			}
			writer.endArray();
		}
		catch (IOException e)
		{
			System.out.println(e.getStackTrace());
		}

	}

	// Reads leaders array from JSON file returns an array of leaders
	public Leader[] readJsonStream(InputStream in)
	{
		try
		{
			JsonReader reader = new JsonReader(new InputStreamReader(in,
					"UTF-8"));
			try
			{
				return readLeader(reader).toArray(new Leader[5]);
			}
			finally
			{
				reader.close();
			}
		}
		catch (IOException e)
		{
			System.out.println(e.getStackTrace());
		}
		return null;
	}

	// Reads leaders array from JSON file, returns a List of leaders
	private List<Leader> readLeader(JsonReader reader)
	{
		List<Leader> leaders = new ArrayList<Leader>();
		try
		{
			reader.beginArray();
			while (reader.hasNext())
			{
				reader.beginObject();
				String name = null;
				long score = -1;
				int level = -1;

				while (reader.hasNext())
				{
					String typeString = reader.nextName();
					if (typeString.equals("name"))
					{
						name = reader.nextString();
					}
					else if (typeString.equals("score"))
					{
						score = reader.nextLong();
					}
					else if (typeString.equals("level"))
					{
						level = reader.nextInt();
					}

				}
				reader.endObject();
				leaders.add(new Leader(name, score, level));
			}
			reader.endArray();

		}
		catch (IOException e)
		{
			// Throws if leaderboard empty - do nothing
		}
		return leaders;
	}

}
