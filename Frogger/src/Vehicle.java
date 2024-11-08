import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Vehicle extends Sprite implements Runnable {
	// Attributes
	private Boolean moving;
	private Frogger frogger;
	private int speed;
	private JLabel vehicleLabel, frogLabel;
	private Frog frog;
	
	private Thread thread;
	
	// Default Constructor
	public Vehicle() {
		super();
	}
	
	// Secondary Constructor
	public Vehicle(int x, int y, int height, int width, String image, Boolean moving, Frogger game, int speed) {
		super(x, y, height, width, image);
		this.moving = moving;
		this.frogger = game;
		this.speed = speed;
		vehicleLabel = new JLabel(new ImageIcon(getClass().getResource(image)));
		vehicleLabel.setSize(width, height);
		vehicleLabel.setLocation(x, y);
	}

	// Getters / Setters
	// Moving
	public Boolean getMoving() {return moving;}
	public void setMoving(Boolean temp) {moving = temp;}
	
	// Frogger
	public Frogger getFrogger() {return frogger;}
	public void setFrogger(Frogger game) {this.frogger = game;}
	
	// Speed
	public int getSpeed() {return speed;}
	public void setSpeed(int temp) {speed = temp;}
	
	// VehicleLabel
	public JLabel getVehicleLabel() {return vehicleLabel;}
	public void setVehicleLabel(JLabel temp) {vehicleLabel = temp;}
	
	// Frog
	public Frog getFrog() {return frog;}
	public void setFrog(Frog temp) {frog = temp;}
		
	// Frog Label
	public JLabel getFrogLabel() {return frogLabel;}
	public void setFrogLabel(JLabel temp) {frogLabel = temp;}
	
	// Thread
	@Override
	public void run() {
		while (this.moving) {
			int x = this.x;
			x += speed;

			// Loops the vehicle properly depending on if its moving left or right
			if (speed > 0 && x >= GameProperties.SCREEN_WIDTH) {x =- 1 * this.width;}
			else if (speed < 0 && x < 0 - this.width) {x = GameProperties.SCREEN_WIDTH + this.width;}
			
			this.setX(x);
			vehicleLabel.setLocation(this.x, this.y);
			
			this.detectCollision();

			try { Thread.sleep(100);}
			catch (Exception e) {
				System.out.println("Caught");
			}
		}
		
	}
	
	  public Thread getThread() { return thread; }
	
	// Start Thread
	public void startThread() {
		thread = new Thread(this, "Vehicle Thread");
		thread.start();
		this.moving = true;
	}
	
	// Stop Thread
	public void stopThread() {this.moving = false;}

	// Check if Frog Collides with Vehicle
	private void detectCollision() {
		// If Vehicle's Rectangle intersects with Frog's Rectangle:
		if (r.intersects(frog.getRectangle())) {
			this.moving = false;
			frogger.endGameSequence();		
		}
	}
}
