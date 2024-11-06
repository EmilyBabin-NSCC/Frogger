public class Frog extends Sprite {
	private Frogger game;
	
	public Frog() {
		super();
	}
	
	public Frog(int x, int y, int height, int width, String image, Frogger game) {
		super(x, y, height, width, image);
	}
	
	public Frog(int x, int y, int height, int width, String image) {
		super(x, y, height, width, image);
	}
	
	public Frog(int x, int y, int height, int width) {
		super(x, y, height, width);
	}
	
	public void logFrogCollision(Log log) {
		if (y >= 100 && y <= 350) {		
			if (r.intersects(log.getRectangle())) {
//				collision = true;
				this.setX(this.getX() + log.getSpeed());
				
			} else {
				log.setMoving(false);
				game.endGameSequence();
			}
			
		}
	}
	
	public void setGame(Frogger game) {this.game = game;}

	public boolean isCollidingWith(Log log) {
		return 
				this.getX() < log.getX() + log.getWidth() &&
				this.getX() + this.getWidth() > log.getX() &&
				this.getY() == log.getY();
	}
	

}
