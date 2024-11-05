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
		Boolean collision = false;
		
		if (y >= 100 && y <= 350) {
			
			if (!collision) {
				game.endGameSequence();
			}
			
		}
	}
	

}
