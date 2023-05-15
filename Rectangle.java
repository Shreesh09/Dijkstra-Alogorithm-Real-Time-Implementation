public class Rectangle
{
	public int x,y,w,h;

	Rectangle(int x,int y,int w,int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	Rectangle()
	{
		this(0,0,0,0);
	}

	public boolean intersects(Rectangle otherRectangle)
	{
		if(x > otherRectangle.x + otherRectangle.w || otherRectangle.x > x + w)
			return false;
			
		if(y > otherRectangle.y + otherRectangle.h || otherRectangle.y > y + h)
			return false;

		return true;
	}
}