import java.awt.Color;
import java.awt.Graphics;

public class AddEdgeButton extends GUIbutton{
    
    private int buttonID;
    private Color color, tempColor;
    private boolean isRed;
    private Main m;
    private Rectangle rect;

    AddEdgeButton(Main m, int buttonID, Color color, Rectangle rect)
    {
        super(color, rect);
        this.rect = rect;
        this.m = m;
        this.buttonID = buttonID;
        this.color = color;
        tempColor = color;
        isRed = false;
    }

    @Override
    public void render(Graphics graphics)
    {
        graphics.setColor(color);
        graphics.fillRect(rect.x, rect.y, rect.w, rect.h);
        graphics.setColor(Color.black);
        graphics.drawString("+E" , rect.x + 5, rect.y + rect.h/2);
    }

    @Override
	public void update(Main m)
	{
		if(buttonID == m.getSelectedButton())
		{
			if(!isRed)
			{
				color = Colour.hex2Rgb("#545B7F");
				isRed = true;
			}
		}
		else
		{
			if(isRed)
			{
				color = tempColor;
                isRed = false;
			}
		}
    }

    public Rectangle getRectangle()
    {
        return rect;
    }

    public void activate()
    {
        m.setSelectedButton(buttonID);
    }
}
