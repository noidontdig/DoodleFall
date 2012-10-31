// Alexandra Qin

package DoodleFall;

import java.awt.*;


public class GamePlayer 
{
	// initialize player speed
	private int fall_y_speed = GC.PLAYER_START_FALL_SPEED;
	private int move_x_speed = GC.PLAYER_MOVE_X_SPEED;
	
	private int x_pos_left;
	private int x_pos_right;
	private int y_pos_up;
	private int y_pos_down;
	
	private boolean falling;
	private boolean fallSet;
	private boolean moving_left;
	private boolean moving_right;
	
	private boolean bonus_intersect;
	
	private Image objectPic;
	
	private Component parent;
	
	public GamePlayer (int x, int y, Component parent)
	{
		//initialize player positions
		x_pos_left = x;
		x_pos_right = x + GC.PLAYER_IMAGE_WIDTH;
		y_pos_up = y;
		y_pos_down = y + GC.PLAYER_IMAGE_HEIGHT;
		
		this.parent = parent;
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
	
	public void setFallSpeed(int speed)
	{
		this.fall_y_speed = speed;
	}
	
	public int getFallSpeed()
	{
		return fall_y_speed;
	}
	
	public void hasSetFall(boolean value)
	{
		this.fallSet = value;
	}
	
	public boolean getFallSet()
	{
		return fallSet;
	}
	
	public void setFalling(boolean value)
	{
		this.falling = value;
	}
	
	public boolean getIsFalling()
	{
		return falling;
	}
	
	// returns true if player is out of bounds down
	public boolean isOutDown()
	{
		return (y_pos_up > GC.APPLET_HEIGHT);
	}
	
	// returns true if player is out of bounds left
	public boolean isOutLeft()
	{
		return (x_pos_right < 0);
	}
	
	// returns true if player is out of bounds right
	public boolean isOutRight()
	{
		return (x_pos_left > GC.APPLET_WIDTH);
	}
	
	// resets player positions
	public void resetPlayer(int x, int y)
	{
		x_pos_left = x;
		x_pos_right = x + GC.PLAYER_IMAGE_WIDTH;
		y_pos_up = y;
		y_pos_down = y + GC.PLAYER_IMAGE_HEIGHT;

	}
	
	public void playerMoveLeft(boolean value)
	{
		moving_left = value;
	}

	public void playerMoveRight(boolean value)
	{
		moving_right = value;
	}
	
	// determines player's behavior is player is moving
	public void playerMove()
	{
		if(moving_left)
		{
			x_pos_left -= move_x_speed;
			x_pos_right -= move_x_speed;
		}
		else if(moving_right)
		{
			x_pos_left += move_x_speed;
			x_pos_right += move_x_speed;

		}
		if(falling)
		{
			y_pos_up += fall_y_speed;
			y_pos_down += fall_y_speed;
		}
	}
	
	// returns true if player intersects enemy
	public boolean intersects(GameEnemy enemy)
	{
		return ((x_pos_right > enemy.getXPosLeft() && (x_pos_left < enemy.getXPosRight())
				&& (y_pos_down > enemy.getYPosUp() && y_pos_up < enemy.getYPosDown())));
	}
	
	// returns true if player intersects bonus
	public boolean intersects(GameBonus bonus)
	{
		return ((x_pos_right >= bonus.getXPosLeft() && (x_pos_left <= bonus.getXPosRight())
				&& (y_pos_down >= bonus.getYPosUp() && y_pos_up <= bonus.getYPosDown())));
	}
	
	// sets to true if player has intersected bonus
	public void intersectsBonus(boolean value)
	{
		bonus_intersect = value; 
	}
	
	// returns true if player has intersected bonus
	public boolean hasIntersectedBonus()
	{
		return bonus_intersect;
	}

	
	public void paintPlayer(Graphics g)
	{
		g.drawImage(objectPic, x_pos_left, y_pos_up, parent);
	}
}