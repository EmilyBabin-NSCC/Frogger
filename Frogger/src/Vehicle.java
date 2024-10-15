import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("unused")
public class Vehicle extends Sprite implements Runnable {
	private Boolean moving;
	private Thread thread;
	private JLabel vehicleLabel, frogLabel;
	private Frog frog;
	
	public Vehicle() {
		super();
	}
	
	public Vehicle(int x, int y, int height, int width, String image, Boolean moving) {
		super(x, y, height, width, image);
		vehicleLabel = new JLabel(new ImageIcon(getClass().getResource("images/vehicle1.png")));
		vehicleLabel.setSize(width, height);
		vehicleLabel.setLocation(x, y);
		this.moving = moving;
	}
	
	public Vehicle(int x, int y, int height, int width) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		vehicleLabel.setSize(width, height);
		vehicleLabel.setLocation(x, y);;
	}
	
	public Boolean getMoving() {
		return moving;
	}
	
	public void setMoving(Boolean moving) {
		this.moving = moving;
	}
	
	
	@Override
	public void run() {
		System.out.println("Thread Started");
		while (this.moving) {
			int x = this.x;
			x += GameProperties.CHARACTER_STEP;
			if ( x >= GameProperties.SCREEN_WIDTH) { x =- 1 * this.width;}
			
			this.setX(x);
			vehicleLabel.setLocation(this.x, this.y);
			
//			System.out.println("Detecting Collision...");
			this.detectCollision();
//		
			
//			System.out.println("v x, y:" + this.x + ", " + this.y);
//			System.out.println("v r.x, r.y:" + this.r.x + ", " + this.r.y);
//			System.out.println("v width, height:" + this.width + ", " + this.height);
//			System.out.println("v r.width, r.height:" + this.r.width + ", " + this.r.height);
			
			try { Thread.sleep(200);}
			catch (Exception e) {
				System.out.println("Caught");
			}
		}
		
		System.out.println("Thread Stopped");
		
	}
	
	public void stopThread() {
		this.moving = false;
	}
	
	// Start function to call thread
	public void startThread() {
		thread = new Thread(this, "Vehicle Thread");
		thread.start();
	}
	
	public void setVehicleLabel(JLabel temp) {vehicleLabel = temp;}
	
	public void setFrog(Frog temp) {frog = temp;}
	
	public void setFrogLabel(JLabel temp) {frogLabel = temp;}
	
	private void detectCollision() {
		if (r.intersects(frog.getRectangle())) {
			System.out.println("Collision");
			this.moving = false;
			
		}
	}
	
}
