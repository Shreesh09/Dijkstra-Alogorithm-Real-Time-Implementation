import java.awt.Color;
import java.awt.Graphics;

public abstract class GUIbutton implements DijkstraObject
{
	protected Graphics graphics;
	protected Color color;
	protected Rectangle rect;

	public GUIbutton(Color color, Rectangle rect)
	{
		this.color = color;
		this.rect = rect;
	}

	public void render(Graphics graphics)
	{}

	public void update(Main game){}

	public boolean handleMouseClick(Rectangle mouseRectangle)
	{
		if(mouseRectangle.intersects(rect))
		{
			activate();
			return true;
		}

		return false;
	}

	public abstract void activate();
}