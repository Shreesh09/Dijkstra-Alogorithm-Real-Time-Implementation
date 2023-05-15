import java.util.ArrayList;
import java.awt.Graphics;

public class Graph implements DijkstraObject{
    public int V;
    Graphics graphics;
    ArrayList<Node> nodeList;
    Graph(int V)
    {
        this.V = V;
        nodeList = new ArrayList<Node>(V);
        for(int i = 0; i <= V; i++)
        {
            addNode(i, 16 * 8, 150);   
        }

        for(int i = 0; i < V; i++)
        {
            addEdge(i, i + 1, 0);
        }
    }

    public void render(Graphics graphics)
	{
        this.graphics = graphics;
		for(int i = 0; i < nodeList.size(); i++)
        {
            Node n = nodeList.get(i);
            n.render(graphics);
            ArrayList<Edge> a = n.getEdges();
            for(int j = 0; j < a.size(); j++)
            {
                a.get(j).render(graphics);
            }
        }
	}

	public void update(Main game)
	{
        for(int i = 0; i < nodeList.size(); i++)
        {
            Node n = nodeList.get(i);
            n.update(game);
            ArrayList<Edge> a = n.getEdges();
            for(int j = 0; j < a.size(); j++)
            {
                a.get(j).update(game);
            }
        }	
    }
	
	public Rectangle getRectangle()
	{
        return new Rectangle();
	} 

    
    public void addNode(int u, int w)
    {
        Node n = new Node(u);
        nodeList.add(n);
        n.setPosition(nodeList.size()*100, 150);
    }

    public int handleMouseClick(Rectangle mouseRectangle)
    {
        for(int i = 0; i < nodeList.size(); i++)
        {
            boolean intersect = nodeList.get(i).handleMouseClick(mouseRectangle);
            if(intersect == true)
                return i;
        }
    	return -1;
    }

    public void addNode(int u, int x, int y)
    {
        Node n = new Node(u);
        nodeList.add(n);
        n.setPosition(x, y);
    }

    public void addNode(int u)
    {
        Node n = new Node(u);
        nodeList.add(n);
        n.setPosition((nodeList.size()-1)*125, 150);
    }

    public void addEdge(int u, int v, int w)
    {
        nodeList.get(u).addEdge(nodeList.get(v), w);
    }

    public void setV(int V)
    {
        this.V =V;
    }

    public int getV()
    {
        return V;
    }

    public void reset()
    {
        nodeList.clear();
        addNode(0, 16 * 8, 150);
        V = 0;
    }

    public Node getNode(int i)
    {
        if(i > V)
            return null;

        return nodeList.get(i);
    }

}
