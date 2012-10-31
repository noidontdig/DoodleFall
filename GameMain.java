// Alexandra Qin

package DoodleFall;

import java.applet.*;
import java.awt.*;

public class GameMain extends Applet implements Runnable
{
	// set applet speed
	private final int speed = 26;
	
	private boolean key_left;
	private boolean key_right;
	
	private Image objectPic;
	private Image lifePic;
	private Image background;
	private Image enemyPic;
	private Image bonusPic;
	
	private Thread th;
	
	private GamePlayer player;
	private GameEnemy[] enemy;
	private GameBonus bonus;
	
	private int count;
	private int points;
	private int lives;


	public void init()
	{
		player = new GamePlayer (GC.PLAYER_START_X, GC.PLAYER_START_Y, this);
		
		lives = 3;
		player.setFalling(true);
		
		enemy = new GameEnemy[GC.ENEMY_NUM];
		for (int i = 0; i < enemy.length; i++) 
			enemy[i] = new GameEnemy();
		
		bonus = new GameBonus();
			
		key_left = false;
		key_right = false;
		
		background = getImage(getCodeBase(), "GameImages/doodle3.gif");
		objectPic = getImage(getCodeBase(), "GameImages/doodle1.gif");
		lifePic = getImage(getCodeBase(), "GameImages/coeur.gif");
		enemyPic = getImage(getCodeBase(), "GameImages/doodle2.gif");
		bonusPic = getImage(getCodeBase(), "GameImages/bonuss.gif");
		
		player.setImages(objectPic);
		for (int i = 0; i < enemy.length; i++) 
			enemy[i].setImages(enemyPic);
		bonus.setImages(bonusPic);
		
		setSize(GC.APPLET_WIDTH, GC.APPLET_HEIGHT);
	}
	
	public void start ()
	{
		th = new Thread (this);
		th.start ();
	}
	
	public void run ()
	{
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

		while (true)
		{
			player.playerMove();
			
			for (int i = 0; i < enemy.length; i++)
				if (enemy[i].isMoving())
					enemy[i].enemyMove();

			// if player has over 100 points, speed up
			if (count > GC.FALL_COUNT)
				player.setFallSpeed(2);
			// if player has over 400 points, speed up
			if (count > (GC.FALL_COUNT) * 4)
				player.setFallSpeed(3);
			
			// if player finishes one level
			if (player.isOutDown()) 
			{
				player.resetPlayer(player.getXPosLeft(), - GC.PLAYER_IMAGE_HEIGHT);
				player.intersectsBonus(false);
				bonus.resetBonus();
				// if player has under 150 points, reset static enemies
				if (count < GC.MOVE_COUNT)
				{
					for (int i = 0; i < enemy.length; i++)
						enemy[i].resetEnemy();
				}
				// if player has between 150 and 300 points, reset 2 moving enemies
				else if ((count >= GC.MOVE_COUNT) && (count < (2 * GC.MOVE_COUNT)))
				{
					for (int i = 0; i < (enemy.length - 2); i++)
						enemy[i].resetEnemy();
					for (int i = (enemy.length - 2); i < enemy.length; i++)
						enemy[i].resetMovingEnemy();
				}
				// if player has between 150 and 300 points, reset 4 moving enemies
				else if (count >= (2 * GC.MOVE_COUNT))
				{
					for (int i = 0; i < (enemy.length - 4); i++)
						enemy[i].resetEnemy();
					for (int i = (enemy.length - 4); i < enemy.length; i++)
						enemy[i].resetMovingEnemy();
				}
			}
			
			// if player is out of bounds on the left, reset player on the right
			if (player.isOutLeft())
			{
				player.resetPlayer(GC.APPLET_WIDTH, player.getYPosUp());
			}
			// if player is out of bounds on the right, reset player on the left
			if (player.isOutRight())
			{
				player.resetPlayer(- GC.PLAYER_IMAGE_WIDTH, player.getYPosUp());
			}
			
			// if moving enemy is out of bounds, reset enemy within the bounds of applet
			for (int i = 0; i < enemy.length; i ++)
			{
				if (enemy[i].isOutLeft())
					enemy[i].resetOutEnemy(GC.APPLET_WIDTH, enemy[i].getYPosUp());
				if (enemy[i].isOutRight())
					enemy[i].resetOutEnemy(- GC.ENEMY_WIDTH, enemy[i].getYPosUp());
				if (enemy[i].isOutDown())
					enemy[i].resetOutEnemy(enemy[i].getXPosLeft(), - GC.ENEMY_HEIGHT);
				if (enemy[i].isOutUp())
					enemy[i].resetOutEnemy(enemy[i].getXPosLeft(), GC.APPLET_HEIGHT);
			}	
			
			// if player intersects with the bonus
			if (player.intersects(bonus))
			{
				player.intersectsBonus(true);
				count += 10;
			}
			
			// if player intersects with an enemy
			for (int i = 0; i < enemy.length; i ++)
				if (player.intersects(enemy[i]))
				{
					lives --; // lose a life
					player.setFalling(false); // stop falling
					break;	
				} 
			
			// if player is not falling, but still has lives, start falling again
			// and reset enemies and bonus
			if ((player.getIsFalling() == false) && (lives >= 0))
			{
				player.setFalling(true);
				player.resetPlayer(GC.PLAYER_START_X, GC.PLAYER_START_Y);
				player.intersectsBonus(false);
				for (int i = 0; i < enemy.length; i++)
					enemy[i].resetEnemy();
				bonus.resetBonus();
			}
			
			// if player is not falling and has no lives, game over
			if ((player.getIsFalling() == false) && (lives < 0))
				break;
			
			// if player is falling add points
			if (player.getFallSpeed() != 0)
				count ++;
			points = (int) (count / 10);

			repaint();

			try
			{
				Thread.sleep (speed);
			}
			catch (InterruptedException ex)
			{
				
			}
			
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
		repaint();
	}
	
	// event handling for keyboard input
	public boolean keyDown (Event e, int key)
	{
		if(key == Event.LEFT)
		{
			key_left = true;
			player.playerMoveLeft(true);
		}
		else if (key == Event.RIGHT)
		{
			key_right = true;
			player.playerMoveRight(true);
		}
		else if (key == 32)
		{
		      player.setFallSpeed(1);
		} 
		
		return true;
	}
	
	public boolean keyUp(Event e, int key)
	{
		if(key == Event.LEFT)
		{
			key_left = false;

			player.playerMoveLeft(false);
		}
		else if(key == Event.RIGHT)
		{
			key_right = false;

			player.playerMoveRight(false);
		}
		return true;
	}
	
	public void stop()
	{
		th.stop();
	}

	// Stop Thread
	public void destroy()
	{
		th.stop();
	}
	
	public void paint (Graphics g)
	{
		// if game is still going
		if ((player.getIsFalling()) || ((player.getIsFalling() == false) && (lives >= 0)))
		{
			g.drawImage(background, 0, 0, this);
			player.paintPlayer(g);
			g.drawString(String.valueOf(points), GC.POINTS_X, GC.POINTS_Y);
			g.drawString(String.valueOf(lives), GC.LIFE_X, (2 * GC.LIFE_Y));
			g.drawImage(lifePic, (GC.LIFE_X + GC.LIFE_IMAGE_WIDTH), GC.LIFE_Y, this);
			for (int i = 0; i < enemy.length; i++)
				enemy[i].paintEnemy(g);
			if (player.hasIntersectedBonus() == false)
				bonus.paintBonus(g);
		}
		// if game over
		else  if ((player.getIsFalling() == false) && (lives < 0))
		{
			g.drawImage(background, 0, 0, this);
			g.drawString("GAME OVER", GC.GAME_OVER_X, GC.GAME_OVER_Y);
			g.drawString("Your score is:", GC.GAME_OVER_X, GC.GAME_OVER_Y + 40);
			g.drawString(String.valueOf(points), GC.GAME_OVER_X, 
					GC.GAME_OVER_Y + 60);
		}
	}
	
}