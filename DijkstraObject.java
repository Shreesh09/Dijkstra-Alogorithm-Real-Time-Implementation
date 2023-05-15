import java.awt.Graphics;

public interface DijkstraObject
{
	//Call every time physically possible.
	public void render(Graphics graphics);

	//Call at 60 fps rate.
	public void update(Main game);
	public Rectangle getRectangle();
}