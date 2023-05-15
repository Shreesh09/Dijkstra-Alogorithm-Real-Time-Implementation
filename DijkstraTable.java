import java.util.ArrayList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DijkstraTable implements DijkstraObject{
    private ArrayList<ArrayList<Integer>> table;
    private Rectangle rect;
    private Color color;
    private Graph g;
    private boolean executed;
    private ArrayList<Integer> selectedNodes;

    DijkstraTable(Color color, Rectangle rect)
    {
        this.rect = rect;
        this.color = color;;
        executed = false;
        table = new ArrayList<ArrayList<Integer>>();
        selectedNodes = new ArrayList<Integer>();
    }

    public void render(Graphics graphics)
    {
        
        graphics.setColor(color);
        graphics.fillRect(rect.x, rect.y, rect.w, rect.h);
        graphics.setColor(Colour.hex2Rgb("#FFF8F8"));
        if(executed)
        {
            int n = g.getV();
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(100, rect.y + 50, 129 + 80 * n, rect.y + 50);
            g2.drawLine(150, rect.y + 5, 150, rect.y + 10 + (50 * table.size()));
            for(int i = 0; i <= n; i++)
                graphics.drawString(i + "", 170 + (i * 35), rect.y + 45);
            
            for(int i = 0; i < table.size(); i++)
            {
                ArrayList<Integer> t = table.get(i);
                if(i > 0)
                    graphics.drawString(selectedNodes.get(i-1) + "", 125, rect.y + 80 + (i * 30));
                for(int j = 0; j < t.size(); j ++)
                {
                    String w = "";
                    if(t.get(j) == Integer.MAX_VALUE)
                        w = "∞";
                    else 
                        w = t.get(j) + "";
                    graphics.drawString(w + "", 170 + (j * 35), rect.y + 80 + (i * 30));
                }
            }
        }
    }

    public void initializeTable(Graph g)
    {
        this.g = g;
        table.add(new ArrayList<Integer>());
        for(int i = 0; i <= g.getV(); i++)
            table.get(0).add(g.getNode(i).w);
    }

    public void update(Main m)
    {
        rect.h = m.getHeight();
        rect.w = m.getWidth();
    }

    public void reset()
    {
        table = new ArrayList<ArrayList<Integer>>();
        executed = false;
        selectedNodes.clear();
    }

    public void setSelectedNode(int v)
    {
        selectedNodes.add(v);
    }

    public void addRow(Graph g)
    {
        this.g = g;
        int l = table.size();
        table.add(new ArrayList<Integer>());
        for(int i = 0; i <= g.getV(); i++)
            table.get(l).add(g.getNode(i).w);
    }

    public Rectangle getRectangle()
    {
        return rect;
    }

    public void setState(boolean state)
    {
        executed = state;
    }
}