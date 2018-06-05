package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Gpanel extends JPanel implements Runnable, KeyListener {

	public static final int WIDTH = 800;
	public static final int HEIGHT=800;
	//rendering
	private Graphics2D g2d;
	private BufferedImage img;
	//Game
	private Thread thread;
	private boolean running;
	private long targetTime;
	//Game Stuff
	private Entity head,apple;
	private int SIZE=20;
	ArrayList<Entity> snake;
	private int score;
	private int level;
	private boolean gameover=false;
	
	//snake movement
	private int dx, dy;
	
	//key pressing
	private boolean up,down,left,right,start;
	public Gpanel() 
	{
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
	}
	
	//controls the speed of the snake
	private void setFPS(int fps) 
	{
		targetTime=1000/fps;
	}
	public void addNotify() {
		super.addNotify();
		thread = new Thread(this);
		thread.start();
	}
	
	//controlling the movement of the snake(with the help of arrow keys)
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		
		if(k==KeyEvent.VK_UP) up=true;
		if(k==KeyEvent.VK_DOWN) down=true;
		if(k==KeyEvent.VK_LEFT) left=true;
		if(k==KeyEvent.VK_RIGHT) right=true;
		if(k==KeyEvent.VK_ENTER) start=true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		
		if(k==KeyEvent.VK_UP) up=false;
		if(k==KeyEvent.VK_DOWN) down=false;
		if(k==KeyEvent.VK_LEFT) left=false;
		if(k==KeyEvent.VK_RIGHT) right=false;
		if(k==KeyEvent.VK_ENTER) start=false;

	}

	public void keyTyped(KeyEvent arg0) {

	}

	
	public void run() {
		if(running) return;
		init();
		long startTime;
		long elapsed;
		long wait;
		//configuring timing 
		while(running) {
			startTime=System.nanoTime();
			update();
			requestRender();
			elapsed=System.nanoTime()-startTime;
			wait= targetTime - elapsed/1000000;
			
			if(wait>0) {
				try {
					Thread.sleep(wait);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		}
	
	private void init() {
		img = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
		g2d=img.createGraphics();
		running = true;
		setUpLevel();
		gameover= false;
		setFPS(10);
	}
//setting up the snake- getting the coordinates of it's head and body.
	private void setUpLevel() {
		snake = new ArrayList<Entity>();
		head = new Entity(SIZE);
		head.setPosition(WIDTH/2, HEIGHT/2);
		snake.add(head);
		
		for(int i =0;i<3;i++) {
			Entity e = new Entity(SIZE);
			e.setPosition(head.getX()+(i*SIZE), head.gety());
			snake.add(e);
		}
		apple = new Entity(SIZE);
		score=0;
	}
	
	public void setApple() {
		int x = (int)(Math.random()*(WIDTH-SIZE));
		int y = (int)(Math.random()*(HEIGHT-SIZE));
		apple.setPosition(x, y);
	}
	//requesting graphics render
	private void requestRender() {
		// TODO Auto-generated method stub
		render(g2d);
		Graphics g = getGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
	}
	//movement of snake
	private void update() {
		
		if(gameover) {
			if(start) {
				
				setUpLevel();
			}
		}
		if(up && dy ==0)
		{
			dy=-SIZE;
			dx=0;
		}
		if(down && dy ==0)
		{
			dy=SIZE;
			dx=0;
		}
		if(left && dx ==0)
		{
			dy=0;
			dx=-SIZE;
		}
		if(right && dx ==0 && dy!=0)
		{
			dy=0;
			dx=SIZE;
		}
		
		if(dx!=0||dy!=0)
		{
			for(int i = snake.size()-1;i>0;i--)
			{
				snake.get(i).setPosition(snake.get(i-1).getX(), snake.get(i-1).gety());
			}
		
			head.move(dx, dy);
		}
		//checking if snake collides with itself
		for(Entity e :snake)
		{
			if(e.isCollide(head))
			{
				gameover=true;
				break;
			}
		}
		
		//checking if snake eats apple
		if(apple.isCollide(head))
		{
			score++;
			setApple();
			
			Entity e = new Entity(SIZE);
			e.setPosition(-100,-100);
			snake.add(e);
			
		}
		
	
			if(head.getX()<0) head.setX(WIDTH);
			if(head.gety()<0) head.setY(HEIGHT);
			if(head.getX()>WIDTH) head.setX(0);
			if(head.gety()>HEIGHT) head.setY(0);
		}
	
	//setting up the size and color of the snake
	public void render(Graphics2D g2d) 
	{
		g2d.clearRect(0, 0, WIDTH, HEIGHT);
		g2d.setColor(Color.GREEN);
		
		for(Entity e: snake)
		{
			e.render(g2d);
		}
		g2d.setColor(Color.RED);
		apple.render(g2d);
		if(gameover==false) {
			g2d.drawString("Gameover", 150, 200);
		}
		
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial",Font.PLAIN,30)); ;
		g2d.drawString("Score: " +score, 670, 770);
		if(dx==0&&dy==0)
		{
			g2d.setFont(new Font("ComicSansMS",Font.PLAIN,50));
			g2d.drawString("Ready?", 350,400);
			g2d.drawString("Press any arrow key to start", 100,450);
		}


	}}
