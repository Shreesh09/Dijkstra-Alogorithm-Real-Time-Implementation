
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
// Java program for sin() method
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import java.lang.Runnable;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import javax.swing.JOptionPane;



public class Main extends JFrame implements Runnable
{
	private Canvas canvas = new Canvas();

    private KeyBoardListener keyListener = new KeyBoardListener(this);
    private Rectangle mouseRectangle;
	private MouseEventListener mouseListener = new MouseEventListener(this);

    private DijkstraTable table;

	private DijkstraObject[] objects;
    private Graph G;

    private GUIbutton[] buttons;
    private GUI gui;

    private int selectedButton = -1;
    private int selectedNode1 = -1;
    private int selectedNode2 = -1;
    private ArrayList<Edge> selectedEdges = new ArrayList<Edge>();
    private Dimension screenSize;
    private boolean DU = false;
    private RenderingHints rh;
	
	public Main()
	{
        rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//Makes our program shut down when exit out.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Set the position and size of our frame.
        setBounds(0,0,1000,800);
        
        //Put our frame in the centre of the screen.
        setLocationRelativeTo(null);
        
        //Adds canvas.B
        add(canvas);
       
        //Make our frame visible.
        setVisible(true); 
       
        //Create our object for Bufferstrategy.
        canvas.createBufferStrategy(3);    
        
        buttons = new GUIbutton[4];

        Rectangle rect = new Rectangle(27,20, 35, 35);
        buttons[0] = new StartButton(this, 0, Colour.hex2Rgb("#D9D9D9"), rect);

        rect = new Rectangle(16,1 * (50 + 30) + 20, 55, 50);
        buttons[1] = new AddNodeButton(this, 1, Colour.hex2Rgb("#D9D9D9"), rect);

        rect = new Rectangle(16,2 * (50 + 30) + 20, 55, 50);
        buttons[2] = new AddEdgeButton(this, 2, Colour.hex2Rgb("#D9D9D9"), rect);

        rect = new Rectangle(getWidth() - 50, 50, 100, 50);
        buttons[3] = new ModeSelect(this, 3, Colour.hex2Rgb("#D9D9D9"), rect);
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        rect = new Rectangle(0, 0, 89, (int)screenSize.getHeight());
		gui = new GUI(Colour.hex2Rgb("#191932"), buttons, rect);

        //Add Listeners.
        canvas.addKeyListener(keyListener);
        canvas.addFocusListener(keyListener);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);
        
        rect = new Rectangle(89, (int)(getHeight()*.60), getWidth(), getHeight());
        table = new DijkstraTable(Colour.hex2Rgb("#272740"), rect);
        objects = new DijkstraObject[3];
        G = new Graph(0);
		objects[0] = G;
        objects[1] = gui;
        objects[2] = table;
	}

 	public void update() 
    {
    	for(int i = 0; i < objects.length; i++)
    		objects[i].update(this);
    }

    public KeyBoardListener getKeyListener()
    {
    	return keyListener;
    }


	public void render()
    {
    	BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        bufferStrategy = canvas.getBufferStrategy();
    	 
         Graphics graphics = bufferStrategy.getDrawGraphics();
         Graphics2D g2 = (Graphics2D)graphics;
         g2.setRenderingHints(rh);
         Font currentFont = graphics.getFont();
         Font font = currentFont.deriveFont(20.0F);
         graphics.setFont(font);
 
	     super.paint(graphics);
	     
         //Repainting the bakground.
	     graphics.setColor(Colour.hex2Rgb("#21213B"));
	     graphics.fillRect(0,0,getWidth(),getHeight());
         
         for(int i = 0; i < objects.length; i++)
    		objects[i].render(graphics);

         graphics.dispose();
         bufferStrategy.show();
    }

    public void leftClick(int x, int y)
    {
        mouseRectangle = new Rectangle(x, y, 1, 1);
        if(G.getV() == 0)
        {
            buttons[3].handleMouseClick(mouseRectangle);
            ModeSelect t = (ModeSelect)buttons[3];
            DU = t.getState();
        }
        if(selectedButton == -1)
        {
            gui.handleMouseClick(mouseRectangle);
            if(selectedButton == 0)
            {
                dijkstra(G, 0);
                //selectedButton = -1;
            }
        }
        else if(selectedButton == 0)
        {
            selectedButton = -1;
            reset();
        }
        else if(selectedButton == 1 && selectedNode1 == -1)
        {
    	    G.setV(G.getV() + 1);

            if(x > 89 && y < (int)(getHeight()*.60))
            {
                G.addNode(G.getV(), x, y);
                //G.getNode(G.getV()).setWeight(Integer.parseInt(createDialogBox()));
                selectedButton = -1;
            }
        }
        else if(selectedButton == 2)
        {
            int t = G.handleMouseClick(mouseRectangle);
            if(selectedNode1 == -1)
                selectedNode1 = t;
            else if(selectedNode2 == -1)
                selectedNode2 = t;
            else
            {
                int w = Integer.parseInt(createDialogBox());
                G.addEdge(selectedNode1, selectedNode2, w);
                if(!DU)
                    G.addEdge(selectedNode2, selectedNode1, w);
                selectedNode1 = selectedNode2 = selectedButton = -1;
            }
        }
    }

    public void doubleClick(int x, int y)
    {
        mouseRectangle = new Rectangle(x, y, 1, 1);
        int t = G.handleMouseClick(mouseRectangle);
        if(t != -1)
        {
            selectedNode1 = t;
            //G.getNode(t).setWeight(Integer.parseInt(createDialogBox()));
            selectedNode1 = -1;
        }
    }

    public void rightClick(int x, int y)
    {

    }

    public void Esc()
    {
        selectedButton = -1;
        selectedNode1 = -1;
        selectedNode2 = -1;
    }

    public void reset()
    {
        Esc();
        G.reset();
        table.reset();
    }

    public void dijkstra(Graph graph, int src)
	{
        int V = graph.getV() + 1;
		int[] distance = new int[V];
		for (int i = 0; i < V; i++)
        {
			distance[i] = Integer.MAX_VALUE;
            G.getNode(i).setWeight(Integer.MAX_VALUE);
        }
		distance[src] = 0;
        G.getNode(src).setWeight(0);
        table.initializeTable(G);
        table.setState(true);

		PriorityQueue<Edge> pq = new PriorityQueue<>(
			(v1, v2) -> v1.getWeight() - v2.getWeight());
		pq.add(new Edge(src, 0));

		while (pq.size() > 0) {
			Edge current = pq.poll();
            selectedNode1 = current.v;
            selectedEdges.clear();
			for (Edge n : graph.getNode(current.v).getEdges()) {
				if (distance[current.v]
						+ n.getWeight()
					< distance[n.v]) {
                    selectedEdges.add(n);
					distance[n.v]
						= n.getWeight()
						+ distance[current.v];
                    G.getNode(n.v).setWeight(distance[n.v]);
					pq.add(new Edge( n.v, distance[n.v]));
				}
                try
                {
                    TimeUnit.SECONDS.sleep(2); 
                }
                catch(InterruptedException ex)
                {
                    ex.printStackTrace();
                }
			}
            if(pq.size() > 0)
            {
                table.addRow(G);
                table.setSelectedNode(pq.peek().v);
            }
		}
		// If you want to calculate distance from source to
		// a particular target, you can return
		// distance[target]
        selectedNode1 = -1;
        selectedEdges.clear();
	}

    public ArrayList<Edge> getSelectedEdges()
    {
        return selectedEdges;
    }

    public int getSelectedNode()
    {
        return selectedNode1;
    }

    public void setSelectedNode(int i)
    {
        selectedNode1 = i;
    }

    public int getSelectedButton()
    {
        return selectedButton;
    }

    public void setSelectedButton(int buttonID)
    {
        selectedButton = buttonID;
    }

    public Graph getGraph()
    {
        return G;
    }
    
    public int getScreenHeight()
    {
        return (int)screenSize.getHeight();
    }

    public int getScreenWidth()
    {
        return (int)screenSize.getWidth();
    }
    
   private String createDialogBox()
   {  
        JFrame f;
        f=new JFrame();   
        String name=JOptionPane.showInputDialog(f,"Enter Weight");     
        return name;       
    }
    
    public int[] getSelectedNodes(){
        int n[] = new int[2];
        n[0] = selectedNode1;
        n[1] = selectedNode2;
        return n;
    }
    
	public void run()
    {
        long lastTime =System.nanoTime(); //long=2^63;
        double nanoSecondConversion =1000000000.0/60.0; //60 frames per second
        double changeInseconds=0.0;  
        while(true)
        {
        	long now=System.nanoTime();
        	changeInseconds += (now-lastTime)/nanoSecondConversion;
        	while(changeInseconds >= 1)
        	{
             update();
             changeInseconds=0;
              
        	}
        	render();
        	lastTime=now;
        }
    }

    public boolean getMode()
    {
        return DU;
    }

	public static void main(String[] args) 
	{	
		Main Main = new Main();
		Thread gameThread = new Thread(Main);
		gameThread.start();
	}
}