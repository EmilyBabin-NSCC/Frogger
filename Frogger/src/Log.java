import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Log extends Sprite implements Runnable {
	// Attributes
	private Boolean moving;
	private JLabel logLabel, frogLabel;
	private Frog frog;
	private Frogger game;
	private int speed;
	
	private Thread thread;
	
	// Default Constructor
	public Log() {
		super();
	}
	
	// Secondary Constructor
	public Log(int x, int y, int h, int w, String img, Boolean moving, Frogger game, int speed) {
		super(x, y, h, w, img);
		this.moving = moving;
		this.game = game;
		this.speed = speed;
		logLabel = new JLabel(new ImageIcon(getClass().getResource(img)));
		logLabel.setSize(w, h);
		logLabel.setLocation(w, h);
	}
	
	// Getters / Setters
	// Moving
	public Boolean getMoving() {return moving;}
	public void setMoving(Boolean temp) {moving = temp;}
	
	// Speed
	public int getSpeed() {return speed;}
	public void setSpeed(int temp) {speed = temp;}
	
	// LogLabel
	public JLabel getLogLabel() {return logLabel;}
	public void setLogLabel(JLabel temp) {logLabel = temp;}
	
	// Frog
	public Frog getFrog() {return frog;}
	public void setFrog(Frog temp) {frog = temp;}
	
	// Frog Label
	public JLabel getFrogLabel() {return frogLabel;}
	public void setFrogLabel(JLabel temp) {frogLabel = temp;}
	
	// Frogger
	public Frogger getFrogger() {return game;}
	public void setFrogger(Frogger frogger) {this.game = frogger;}

	// Thread
	@Override
	public void run() {
		while (this.moving) {
			int x = this.x;
			x += 25;
			if (x >= GameProperties.SCREEN_WIDTH) {x =-1 * this.width;}
			
			this.setX(x);
			logLabel.setLocation(this.x, this.y);

			game.detectLogCollision();
			
			try { 
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("Caught");
			}
			
		}
		
	}
	
	// Start Thread
	public void startThread() {
		this.moving = true;
		thread = new Thread(this, "Log Thread");
		thread.start();
	}
	
	// Stop Thread
	public void stopThread() {this.moving = false;}
}
