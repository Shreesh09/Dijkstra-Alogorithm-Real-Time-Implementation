import java.awt.Color;
import java.awt.Graphics;

public class GUI implements DijkstraObject
{
	private Rectangle rect = new Rectangle();
	private Color color;
	private GUIbutton[] buttons = null;

	public GUI(Color color, GUIbutton[] buttons, Rectangle rect)
	{	
		this.rect.x = rect.x;
		this.rect.y = rect.y;
		this.rect.w = rect.w;
		this.rect.h = rect.h;

		this.color = color;
		this.buttons = buttons;

		if(color == null)
		{
			rect.w = 0;
			rect.h = 0;
		}
	}

	public void render(Graphics graphics)
	{
		//System.out.println(buttons.length);
		if(color != null)
		{
            graphics.setColor(color);
		    graphics.fillRect(rect.x, rect.y, rect.w , rect.h);
		}
	
		if(buttons != null)
			for(int i = 0; i < buttons.length; i++)
                if(buttons[i] != null)
                    buttons[i].render(graphics);
	}

	public void update(Main game)
    {
		if(color != null)
		{
			rect.h = game.getScreenHeight();
		}

    	if(buttons != null)
    	{
    		for(int i = 0; i < buttons.length; i++)
    			buttons[i].update(game);
    	}
    }

    public Rectangle getRectangle()
    {
        return new Rectangle();
    }

    public boolean handleMouseClick(Rectangle mouseRectangle)
    {
    	boolean hasStopped = false;
    	if(rect.w ==0 || rect.h == 0 || mouseRectangle.intersects(rect))
    	{
    		for(int i = 0; i < buttons.length; i++)
    		{
    			boolean result = buttons[i].handleMouseClick(mouseRectangle);
    			if(hasStopped == false)
    				hasStopped = result;
    		}
    	}

    	return hasStopped;
    }
} 