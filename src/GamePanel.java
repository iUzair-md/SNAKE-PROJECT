import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	static int width = 25;
	static int height = 25;
	static int dimensions = 25;
	ArrayList<Rectangle> body;
	static int moves = 0;
	static char direction = 'r';
	static int delay = 95;
	Timer timer;
	static int appleX;
	static int appleY;
	Random random;
	static boolean running = false;
	static int score = 0;
	
	GamePanel(){
		this.setPreferredSize(new Dimension(width*dimensions,height*dimensions));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		this.setBackground(Color.black);
		body = new ArrayList<>();
		timer = new Timer(delay,this);
		random= new Random();
		startGame();
	}
	
	
	private void startGame() {
		Rectangle item = new Rectangle(dimensions,dimensions);
		item.setLocation(width/2*dimensions,height/2*dimensions);
		body.add(item);
		item = new Rectangle(dimensions,dimensions);
		item.setLocation((width/2-1)*dimensions,height/2*dimensions);
		body.add(item);
		item = new Rectangle(dimensions,dimensions);
		item.setLocation((width/2-2)*dimensions,height/2*dimensions);
		body.add(item);
		running=true;
		timer.start();
		newApple();

	}
	
		public void grow() {
			
			Rectangle first = body.get(0);
			Rectangle item = new Rectangle(dimensions,dimensions);
			switch(direction) {
			case 'u':
				item.setLocation(first.x,first.y-dimensions);
				break;
			case 'd':
				item.setLocation(first.x,first.y+dimensions);
				break;
			case 'r':
				item.setLocation(first.x+dimensions,first.y);
				break;
			case 'l':
				item.setLocation(first.x-dimensions,first.y);
				break;
			}
			body.add(0,item);
			
			
		}
	public void move() {
		
		Rectangle first = body.get(0);
		Rectangle item = new Rectangle(dimensions,dimensions);
		switch(direction) {
		case 'u':
			item.setLocation(first.x,first.y-dimensions);
			break;
		case 'd':
			item.setLocation(first.x,first.y+dimensions);
			break;
		case 'r':
			item.setLocation(first.x+dimensions,first.y);
			break;
		case 'l':
			item.setLocation(first.x-dimensions,first.y);
			break;
		}
			body.add(0,item);
			body.remove(body.size()-1);
		}
	
		public void checkCollision() {
			//checking if 1st box is colliding with the body 
			int firstX = body.get(0).x;
			int firstY = body.get(0).y;
			for(int i=1; i<body.size();i++) {
				if(firstX==body.get(i).x && firstY==body.get(i).y) {
					running=false; 
				}
			}
			//checking if head touches left border
			if(firstX<0) {
				running=false;
			}
			//checking if head touches right border
			if(firstX>(width-1)*dimensions) {
				running=false;
			}
			//checking if head touches roof
			if(firstY<0) {
				running = false;
			}
			//checking if head touches floor
			if(firstY>(height-1)*dimensions) {
				running=false;
			}
			if(!running) {
				timer.stop();
			}
			for(int i=1;i<body.size();i++) {
				if(body.get(i).x==appleX && body.get(i).y==appleY) {
					newApple();
				}
			}
		}
		


	public void newApple() {
		appleX=random.nextInt((int)(width))*dimensions;
		appleY=random.nextInt((int)(height))*dimensions;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
		move();
		checkCollision();
		applesEaten();
		}
		repaint();
	}
	public void applesEaten() {
		int firstX = body.get(0).x;
		int firstY = body.get(0).y;
		if(firstX==appleX && firstY==appleY) {
			score++;
			newApple();
			grow();
		}
	}
	public void gameOver(Graphics g) {
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,75));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Game Over", (width*dimensions-metrics.stringWidth("Game Over"))/2,height*dimensions/2);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);

		
	}
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if(running) { 
		//for(i=0;i<width;i++) {
		//	g2d.setColor(Color.DARK_GRAY);
		//	g2d.drawLine(i*dimensions,0,i*dimensions,height*dimensions);
		//	g2d.drawLine(0, i*dimensions,width*dimensions,i*dimensions);
		//}
		for(Rectangle r:body) {
			g2d.setColor(Color.DARK_GRAY);
			g2d.fill(r);
		}
		g2d.setColor(Color.red);
		g2d.fillOval(appleX, appleY, dimensions, dimensions);
		g2d.setColor(Color.red);
		g2d.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g2d.drawString("Score:"+score, (width*dimensions-metrics.stringWidth("Score: "+score))/2, g.getFont().getSize());
		}
		else
			gameOver(g2d);
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP: 
				if(direction!='d') {
					direction='u';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction!='u') {
					direction='d';
				}
				break;
			case KeyEvent.VK_LEFT:
				if(direction!='r') {
					direction='l';
				}
					break;
					case KeyEvent.VK_RIGHT:
						if(direction!='l') {
							direction='r';
						}
					break;
						}
				}
			
				}
	public ArrayList<Rectangle> getBody() {
		return body;
	}


	public void setBody(ArrayList<Rectangle> body) {
		this.body = body;
	}
	
}
  