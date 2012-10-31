// Alexandra Qin

package DoodleFall;

import java.awt.*;


public class GameEnemy 
{
	// intitalize enemy move speed
	private int move_speed = GC.ENEMY_MOVE_SPEED;
	
	private int x_pos_left;
	private int x_pos_right;
	private int y_pos_up;
	private int y_pos_down;
	
	private Image objectPic;
	
	private boolean moving;
	private boolean moving_left;
	private boolean moving_right;
	private boolean moving_up;
	private boolean moving_down;
	private int moveNum;
	
	private static Component parent;
	
	public GameEnemy()
	{
		this(parent);
	}
	
	public GameEnemy(Component parent)
	{
		// set random position for enemy
		x_pos_left = (int)(Math.random() * (GC.APPLET_WIDTH - 30));
		x_pos_right = x_pos_left + GC.ENEMY_WIDTH;
		y_pos_up = (int)(80 + Math.random() * (GC.APPLET_HEIGHT - 80));
		y_pos_down = y_pos_up + GC.ENEMY_HEIGHT;
	}
	
	public void setImages (Image objectPic)
	{
		this.objectPic = objectPic;
	}
	
	public int getXPosLeft()
	{
		return x_pos_left;
	}

	public int getXPosRight()
	{
		return x_pos_right;
	}

	public int getYPosUp()
	{
		return y_pos_up;
	}
	
	public int getYPosDown()
	{
		return y_pos_down;
	}
	
	public void resetEnemy()
	{
		// resets random position for enemy
		x_pos_left = (int)(Math.random() * (GC.APPLET_WIDTH - 30));
		x_pos_right = x_pos_left + GC.ENEMY_WIDTH;
		y_pos_up = (int)(80 + Math.random() * (GC.APPLET_HEIGHT - 80));
		y_pos_down = y_pos_up + GC.ENEMY_HEIGHT;
	}
	
	public void resetMovingEnemy()
	{
		// resets random position for moving enemy
		x_pos_left = (int)(Math.random() * (GC.APPLET_WIDTH - 30));
		x_pos_right = x_pos_left + GC.ENEMY_WIDTH;
		y_pos_up = (int)(80 + Math.random() * (GC.APPLET_HEIGHT - 80));
		y_pos_down = y_pos_up + GC.ENEMY_HEIGHT;
		
		moving = true;
		moving_left = false;
		moving_right = false;
		moving_up = false;
		moving_down = false;
		// randomly determines the direction of enemy movement
		moveNum = (int)(Math.random() * 4);
		if (moveNum == 0)
			moving_left = true;
		if (moveNum == 1)
			moving_right = true;
		if (moveNum == 2)
			moving_up = true;
		if (moveNum == 3)
			moving_down = true;
	}
	
	public boolean isMoving()
	{
		return moving;
	}
	
	public void setMoveSpeed(int speed)
	{
		this.move_speed = speed;
	}
	
	public void enemyMoveLeft(boolean value)
	{
		moving_left = value;
	}

	public void enemyMoveRight(boolean value)
	{
		moving_right = value;
	}
	
	public void enemyMoveUp(boolean value)
	{
		moving_up = value;
	}
	
	public void enemyMoveDown(boolean value)
	{
		moving_down = value;
	}
	
	// determines enemy's behavior if moving
	public void enemyMove()
	{
		if(moving_left)
		{
			x_pos_left -= move_speed;
			x_pos_right -= move_speed;
		}
		else if(moving_right)
		{
			x_pos_left += move_speed;
			x_pos_right += move_speed;
		}
		else if(moving_down)
		{
			y_pos_up += move_speed;
			y_pos_down += move_speed;
		}
		else if(moving_up)
		{
			y_pos_up -= move_speed;
			y_pos_down -= move_speed;
		}
	}
	
	// if enemy out of bounds down
	public boolean isOutDown()
	{
		return (y_pos_up > GC.APPLET_HEIGHT);
	}
	
	// if enemy out of bounds up
	public boolean isOutUp()
	{
		return (y_pos_down < 0);
	}
	
	// if enemy out of bounds left
	public boolean isOutLeft()
	{
		return (x_pos_right < 0);
	}
	
	// if enemy out of bounds right
	public boolean isOutRight()
	{
		return (x_pos_left > GC.APPLET_WIDTH);
	}
	
	// resets moving enemy if out of bounds
	public void resetOutEnemy(int x, int y)
	{
		x_pos_left = x;
		x_pos_right = x + GC.ENEMY_WIDTH;
		y_pos_up = y;
		y_pos_down = y + GC.ENEMY_HEIGHT;
	}
	
	public void paintEnemy(Graphics g)
	{
		g.drawImage(objectPic, x_pos_left, y_pos_up, parent);
	}
	
}	
