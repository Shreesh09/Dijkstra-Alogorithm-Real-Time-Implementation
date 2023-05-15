import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseEventListener implements MouseListener, MouseMotionListener
{
	public boolean[] keys = new boolean[120];
	private MouseEvent event;
	private Main game;
    
    public MouseEventListener(Main game)
    {
    	this.game = game;
    }

    public void mouseClicked(MouseEvent event){}
    public void mouseDragged(MouseEvent event){}
    public void mouseEntered(MouseEvent event){}
    public void mouseExited(MouseEvent event){}
    public void mouseMoved(MouseEvent event){}

    public void mousePressed(MouseEvent event)
    {
        if (event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1) {
            game.doubleClick(event.getX(), event.getY());
        }
        
    	if(event.getButton() == MouseEvent.BUTTON1)
    	{
    		game.leftClick(event.getX(), event.getY());
    	}

        if(event.getButton() == MouseEvent.BUTTON3)
    	{
    		game.rightClick(event.getX(), event.getY());
    	}

    }

    public void mouseReleased(MouseEvent event)
    {
    	int keyCode = event.getButton();

    	if(keyCode < keys.length)
    		keys[keyCode] = false;

    	this.event = event;
    }

    public boolean leftClick()
    {
    	return(keys[MouseEvent.BUTTON1]);
    }

    public MouseEvent getEvent()
    {
    	return event;
    }
}