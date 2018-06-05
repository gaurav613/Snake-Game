package snakegame;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Entity {

	private int x,y,size;
	//essentially a 'block' of the snake- gets added when the apple gets eaten
	public Entity(int size) {
		this.size=size;
	}
	
	public int getX() {
		return x;
	}
	
	public int gety() {
		return y;
	}
	
	public void setX(int x) {
		this.x=x;
	}
	
	public void setY(int y) {
		this.y=y;
	}
	
	public void setPosition(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public void move(int dx,int dy) {
	
		x+=dx;
		y+=dy;
		
	}
	
	private Rectangle getBound() 
	{
		return new Rectangle(x,y,size,size);
	}
	//checking for collision
	public boolean isCollide(Entity o) {
		if(o==this) return false;
		return getBound().intersects(o.getBound());
	}
	//making separations for snake body attachments
	public void render(Graphics2D g2d) {
		g2d.fillRect(x + 1, y + 1, size - 2, size - 2);
	}
	
}
