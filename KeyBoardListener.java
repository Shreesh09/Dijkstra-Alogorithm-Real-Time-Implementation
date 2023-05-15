
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

public class KeyBoardListener implements KeyListener, FocusListener
{
	private Main m;
	public boolean[] keys = new boolean[120];

	public KeyBoardListener(Main m)
    {
    	this.m = m;
    }

	public void keyPressed(KeyEvent event)
	{
		int keyCode = event.getKeyCode();
		if(keyCode < keys.length)
			keys[keyCode] = true;

		if(keys[KeyEvent.VK_ESCAPE])
		{
			m.Esc();
		}

		if(keys[KeyEvent.VK_CONTROL] && keys[KeyEvent.VK_R])
		{
			m.reset();
		}
	}

	public void keyReleased(KeyEvent event)
	{
		int keyCode = event.getKeyCode();
		
		if(keyCode < keys.length)
			keys[keyCode] = false;
	}

	public void focusLost(FocusEvent event)
	{
		for(int i = 0; i < keys.length; i++)
			keys[i] = false;
	}

	public void keyTyped(KeyEvent event)
	{
	}

	public void focusGained(FocusEvent event)
	{
	}

	public boolean up()
	{
		return keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
	}

	public boolean down()
	{
		return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
	}

	public boolean left()
	{
		return keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
	}

	public boolean right()
	{
		return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
	}

	public int digit()
	{
		for(int i = '0'; i <= '9'; i++)
		{
			if(keys[i])
				return i;
		}
	
		return -1;
	}

	public boolean Esc()
	{
		return keys[KeyEvent.VK_ESCAPE];
	}

}