import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Edge implements DijkstraObject{
    public int u, v, w;
	private int x1, y1, x2, y2;
	private Color c;
	private boolean mode;
    Graphics graphics;
	Rectangle rect;
    Edge(Node u, Node v, int w)
    {
        this.u = u.u;
        this.v = v.u;
        this.w = w;

		int x1, y1;
        x1 = u.getRectangle().x + v.getRectangle().w/2;
        y1 = u.getRectangle().y + v.getRectangle().h/2;

        int x2, y2;

        x2 = v.getRectangle().x;
        y2 = v.getRectangle().y;
		
		this.mode = u.getMode();
		if(!mode)
		{
			x2 += v.getRectangle().w/2;
			y2 += v.getRectangle().h/2;
		}
		else
		{
			if(x2 >= x1)
			{
				if(y2 < y1)
				{
					if((y1 - y2) > (x2 - x1))
					{
						x2 += v.getRectangle().w/2;
						y2 += v.getRectangle().h;
					}
					else
					{
						y2 += v.getRectangle().h/2;
					}
				}
				else
				{
					if((y2 - y1) > (x2 - x1))
					{
						x2 += v.getRectangle().w/2;
					}
					else
					{
						y2 += v.getRectangle().h/2;
					}
				}

			}
			else
			{
				if(y2 < y1)
				{
					if((y2 - y1) > (x1 - x2))
					{
						x2 += v.getRectangle().w/2;
						y2 += v.getRectangle().h;
					}
					else
					{
						x2 += v.getRectangle().w;
						y2 += v.getRectangle().h/2;
					}
				}
				else
				{
					if((y2 - y1) > (x1 - x2))
					{
						x2 += v.getRectangle().w/2;
					}
					else
					{
						x2 += v.getRectangle().w;
						y2 += v.getRectangle().h/2;
					}
				}
			}
		}
		setCoords(x1, y1, x2, y2);

		c = Colour.hex2Rgb("#D9D9D9");
    }

	Edge(int v, int w)
	{
		this.v = v;
		this.w = 0;
	}

    public void render(Graphics graphics)
	{
		this.graphics = graphics;
		graphics.setColor(c);
		Graphics2D g2 = (Graphics2D) graphics;
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(x1, y1, x2, y2);
		if(mode)
		Arrow.draw(g2, new Point2D.Double((double)x1, (double)y1),  new Point2D.Double((double)x2, (double)y2), g2.getStroke(), g2.getStroke(), 25.0f);
		graphics.drawString(w + "", (x1 + x2)/2, (y1 + y2)/2-7);
	}

	public void update(Main game)
	{
		mode = game.getMode();
		if(mode)
		{
			if(game.getSelectedNode() == u)
			{
				ArrayList<Edge> e = game.getSelectedEdges();
				boolean found = false;
				for(int i = 0; i < e.size(); i++)
				{
					if(e.get(i).v == v && e.get(i).w == w)
					{
						setColor();
						found = true;
					}
				}
				if(!found)
					resetColor();
			}
			else
				resetColor();
		}
		else
		{
			if(game.getSelectedNode() == v || game.getSelectedNode() == u)
			{
				ArrayList<Edge> e = game.getSelectedEdges();
				boolean found = false;
				for(int i = 0; i < e.size(); i++)
				{
					if((e.get(i).v == u && e.get(i).w == w) || (e.get(i).v == v && e.get(i).w == w))
					{
						setColor();
						found = true;
					}
				}
				if(!found)
					resetColor();
				}
				else
					resetColor();
		}
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle();
	}
	
	public void setColor()
	{
		c = Colour.hex2Rgb("#545B7F");
	}

	public void resetColor()
	{
		c = Colour.hex2Rgb("#D9D9D9");
	}

	public void setCoords(int x1, int y1, int x2, int y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void setWeight(int w)
	{
		this.w = w;
	}

	public int getWeight()
	{
		return w;
	}
}
