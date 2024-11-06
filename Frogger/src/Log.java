import javax.swing.JLabel;
import javax.swing.ImageIcon;

@SuppressWarnings({"unused"})
public class Log extends Sprite implements Runnable {
	private Boolean moving, collision = false;
	private Thread thread;
	private JLabel logLabel, frogLabel;
	private Frog frog;
	private Frogger game;
	private int speed;
	
	public Log() {
		super();
	}
	
	public Log(int x, int y, int h, int w, String img, Boolean moving, Frogger game) {
		super(x, y, h, w, img);
		this.moving = moving;
		this.game = game;
		logLabel = new JLabel(new ImageIcon(getClass().getResource(img)));
		logLabel.setSize(w, h);
		logLabel.setLocation(w, h);
	}
	
	public Log(int x, int y, int h, int w, String img, Boolean moving, Frogger game, int speed) {
		super(x, y, h, w, img);
		this.moving = moving;
		this.game = game;
		this.speed = speed;
		logLabel = new JLabel(new ImageIcon(getClass().getResource(img)));
		logLabel.setSize(w, h);
		logLabel.setLocation(w, h);
	}
	
	// X
	public int getX() {return x;}
	public void setX(int temp) {x = temp;}
	
	// Y
	public int getY() {return y;}
	public void setY(int temp) {y = temp;}
	
	// Height
	public int getHeight() {return height;}
	public void setHeight(int temp) {height = temp;}
	
	// Width
	public int getWidth() {return width;}
	public void setWidth(int temp) {width = temp;}
	
	// Moving
	public Boolean getMoving() {return moving;}
	public void setMoving(Boolean temp) {moving = temp;}
	
	// Speed
	public int getSpeed() {return speed;}
	public void setSpeed(int temp) {speed = temp;}

	@Override
	public void run() {
		while (this.moving) {
			int x = this.x;
			x += 25;
			if (x >= GameProperties.SCREEN_WIDTH) {x =-1 * this.width;}
			
			this.setX(x);
			logLabel.setLocation(this.x, this.y);
			
			this.detectCollision();
			game.detectCollision();
			
			
			try { 
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("Caught");
			}
			
		}
		
	}
	
	public void stopThread() {
		this.moving = false;
	}

	private void detectCollision() {
		if (r.intersects(frog.getRectangle())) {
			this.collision = true;
		}
		
	}

	public void setLogLabel(JLabel temp) {
		logLabel = temp;
		
	}

	public void setFrog(Frog temp) {
		frog = temp;
		
	}

	public void startThread() {
		this.moving = true;
		thread = new Thread(this, "Log Thread");
		thread.start();
		
	}

	public void setFrogLabel(JLabel temp) {
		frogLabel = temp;
		
	}

	public void setFrogger(Frogger frogger) {
		this.game = frogger;
		
	}
	
}
