
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyListener;
// Java program for sin() method

public class Node implements DijkstraObject
{
	public int u, w;
	Graphics graphics;
	public Rectangle rect = new Rectangle();
	private ArrayList<Edge> adjList = new ArrayList<Edge>();
	boolean isRed;
	Color color, tempColor;
	Font font;
	boolean mode = false;
	private int frameHeight;
	Node(int u)
	{
		this.u = u;
		rect.w = 60;
		rect.h = 60;
		rect.x = 0;
		rect.y = 0;
		isRed = false;
		color = Colour.hex2Rgb("#D9D9D9");
		tempColor = color;
		w = -1;
		frameHeight = 800;
	}
	
	public void render(Graphics graphics)
	{
		this.graphics = graphics;
		graphics.setColor(color);
		graphics.fillOval(rect.x, rect.y, rect.w, rect.h);
		graphics.setColor(Color.black);
		graphics.drawString(u + "", rect.x + rect.w/2 - 10, rect.y + rect.h/2);
		if(w != -1)
		{
			graphics.setColor(Colour.hex2Rgb("#D9D9D9"));
			String weight;
			if(w == Integer.MAX_VALUE)
				weight = "∞";
			else
				weight = w + "";
			if(rect.y <= (frameHeight * .6)/2)
				graphics.drawString(weight+"", rect.x + rect.w/2 - 10, rect.y - 10);
			else
				graphics.drawString(weight+"", rect.x + rect.w/2 - 10, rect.y + rect.h + 20);
		}
	}

	public void update(Main m)
	{
		mode = m.getMode();
		frameHeight = m.getHeight();
		if(u == m.getSelectedNodes()[0] || u == m.getSelectedNodes()[1])
		{
			if(!isRed)
			{
				color = Colour.hex2Rgb("#545B7F");;
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

	public boolean getMode()
	{
		return mode;
	}
	
	public Rectangle getRectangle()
	{
		return rect;
	} 

	public boolean handleMouseClick(Rectangle mouseRectangle)
	{
		if(mouseRectangle.intersects(rect))
		{

			return true;
		}

		return false;
	}

	public void addEdge(Node v, int w)
	{
		Edge e1 = new Edge(this, v, w);
		adjList.add(e1);
	}

	public void removeEdge(int v, int w)
	{
		for(int i = 0; i < adjList.size(); i++)
		{
			if(adjList.get(i).v == v && adjList.get(i).w == w)
			{
				adjList.remove(i);
				break;
			}
		}
	}

	public void setWeight(int w)
	{
		this.w = w;
	}

	public void setPosition(int x, int y)
	{
		rect.x = x;
		rect.y = y;
	}

	public ArrayList<Edge> getEdges()
	{
		return adjList;
	}
}