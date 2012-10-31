// Alexandra Qin

package DoodleFall;

import java.awt.*;

public class GameBonus
{
	private int x_pos_left;
	private int x_pos_right;
	private int y_pos_up;
	private int y_pos_down;
	
	private Image objectPic;
	
	private static Component parent;
	
	public GameBonus()
	{
		this(parent);
	}
	
	public GameBonus(Component parent)
	{
		// set random position for bonus
		x_pos_left = (int)(Math.random() * (GC.APPLET_WIDTH - 30));
		x_pos_right = x_pos_left + GC.BONUS_WIDTH;
		y_pos_up = (int)(70 + Math.random() * (GC.APPLET_HEIGHT - 80));
		y_pos_down = y_pos_up + GC.BONUS_HEIGHT;
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
	
	public void resetBonus()
	{
		// reset random position for bonus
		x_pos_left = (int)(Math.random() * (GC.APPLET_WIDTH - 30));
		x_pos_right = x_pos_left + 10;
		y_pos_up = (int)(70 + Math.random() * (GC.APPLET_HEIGHT - 80));
		y_pos_down = y_pos_up + 10;

	}
	
	public void paintBonus(Graphics g)
	{
		g.drawImage(objectPic, x_pos_left, y_pos_up, parent);
	}
	
}	
