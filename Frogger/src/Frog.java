import java.awt.Rectangle;

public class Frog extends Sprite {
	// Attributes
	private Frogger frogger;
	
	// Default Constructor
	public Frog() {
		super();
	}
	
	// Secondary Constructor
	public Frog(int x, int y, int height, int width, String image, Frogger frogger) {
		super(x, y, height, width, image);
		this.frogger = frogger;
	}
	
	// Getters / Setters
	// Frogger
	public Frogger getFrogger() {return frogger;}
	public void setGame(Frogger frogger) {this.frogger = frogger;}

	// Checks if Frog Collides with Log
	public Boolean isCollidingWith(Log log) {	
		Rectangle frogRect = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	    Rectangle logRect = new Rectangle(log.getX(), log.getY(), log.getWidth(), log.getHeight());
	    
	    return frogRect.intersects(logRect);
	}
}