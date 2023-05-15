import java.awt.Color;
import java.awt.Graphics;

public class ModeSelect extends GUIbutton{
    
    private int buttonID;
    private Color color, tempColor;
    private boolean isOn;
    private Main m;
    private Rectangle rect;
    private Rectangle smolrect;
    private String str;

    ModeSelect(Main m, int buttonID, Color color, Rectangle rect)
    {
        super(color, rect);
        this.rect = rect;
        this.m = m;
        this.buttonID = buttonID;
        this.color = color;
        tempColor = color;
        isOn = false;
        smolrect = new Rectangle();
        smolrect.x = rect.x + 2;
        smolrect.y = rect.y + 2;
        smolrect.w  = rect.w/2;
        smolrect.h = rect.h - 4;
        str = "undirected";
    }

    @Override
    public void render(Graphics graphics)
    {
        graphics.setColor(color);
        graphics.fillRoundRect(rect.x, rect.y, rect.w, rect.h, 50, 50);
        graphics.setColor(Colour.hex2Rgb("#191932"));
        graphics.fillRoundRect(smolrect.x, smolrect.y, smolrect.w, smolrect.h, 50, 50);
        graphics.setColor(Colour.hex2Rgb("#D9D9D9"));
        graphics.drawString(str, rect.x, rect.y -15);
    }

    @Override
	public void update(Main m)
	{
        rect.x = m.getWidth() - 200;
        smolrect.x = rect.x + 2;
        if(isOn)
        {
            smolrect.x = rect.x + rect.w/2;
            str = "Directed";
        }
        else
        {
            smolrect.x = rect.x + 2;
            str = "Undirected";
        }
    }

    public Rectangle getRectangle()
    {
        return rect;
    }

    public void activate()
    {
        if(!isOn)
		{
			color = Colour.hex2Rgb("#545B7F");
			isOn = true;
		}
        else
		{
			color = tempColor;
            isOn = false;
        }
    }

    public boolean getState()
    {
        return isOn;
    }
}
