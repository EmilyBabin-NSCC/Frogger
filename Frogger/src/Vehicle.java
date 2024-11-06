import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("unused")
public class Vehicle extends Sprite implements Runnable {
	private Boolean moving, collisionDetected = false;
	private int speed;
	
	private Thread thread;
	private JLabel vehicleLabel, frogLabel;
	private Frog frog;
	
	private Frogger game;
	
	public Vehicle() {
		super();
	}
	
	public Vehicle(int x, int y, int height, int width, String image, Boolean moving, Frogger game) {
		super(x, y, height, width, image);
		this.moving = moving;
		this.game = game;
		vehicleLabel = new JLabel(new ImageIcon(getClass().getResource(image)));
		vehicleLabel.setSize(width, height);
		vehicleLabel.setLocation(x, y);
		
	}
	
	public Vehicle(int x, int y, int height, int width) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		vehicleLabel.setSize(width, height);
		vehicleLabel.setLocation(x, y);;
	}
	
	public Vehicle(int x, int y, int height, int width, int speed) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.speed = speed;
		vehicleLabel.setSize(width, height);
		vehicleLabel.setLocation(x, y);;
	}
	
	public Boolean getMoving() {
		return moving;
	}
	
	public void setMoving(Boolean moving) {
		this.moving = moving;
	}
	
	public void setFrogger(Frogger game) {
		this.game = game;
	}
	
	
	@Override
	public void run() {
		while (this.moving) {
			int x = this.x;
			x += 50;
			if ( x >= GameProperties.SCREEN_WIDTH) { x =- 1 * this.width;}
			
			this.setX(x);
			vehicleLabel.setLocation(this.x, this.y);
			
			this.detectCollision();

			try { Thread.sleep(100);}
			catch (Exception e) {
				System.out.println("Caught");
			}
		}
		
	}
	
	public void stopThread() {
		this.moving = false;
	}
	
	// Start function to call thread
	public void startThread() {
		thread = new Thread(this, "Vehicle Thread");
		thread.start();
		this.moving = true;
	}
	
	public void setVehicleLabel(JLabel temp) {vehicleLabel = temp;}
	
	public void setFrog(Frog temp) {frog = temp;}
	
	public void setFrogLabel(JLabel temp) {frogLabel = temp;}
	
//	public Boolean collided() {
//		return collisionDetected;
//	}
	
	private void detectCollision() {
		// If Vehicle's Rectangle intersects with Frog's Rectangle:
		if (r.intersects(frog.getRectangle())) {
			System.out.println("Collision detected!");
			this.moving = false;
			this.collisionDetected = true;
			game.endGameSequence();
			
		}
	}
	
}
