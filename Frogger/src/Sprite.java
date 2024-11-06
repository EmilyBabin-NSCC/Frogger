import java.awt.Rectangle;

public class Sprite {
	// Attributes
	protected int x, y; // Top Left Positioning
	protected int height, width; // Sprite Dimensions
	protected String image; // Image File
	protected Rectangle r; // Rectangle for Collision Detection
	
	public Sprite() {
		super();
		r = new Rectangle(0,0,0,0);
	}
	
	public Sprite(int x, int y, int height, int width, String image) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.image = image;
		r = new Rectangle(x, y, height, width);
	}
	
	public Sprite(int x, int y, int height, int width) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		r = new Rectangle(x, y, height, width);
	}
	
	// Getters / Setters
	// X
	public int getX() {return x;}
	public void setX(int x) {
		this.x = x;
		this.r.x = this.x;
	}
		
	// Y
	public int getY() {return y;}
	
	public void setY(int y) {
		this.y = y;
		this.r.y = this.y;
	}

	// Height
	public int getHeight() {return height;}
	public void setHeight(int height) {
		this.height = height;
		this.r.height = height;
	}
	
	// Width
	public int getWidth() {return width;}
	public void setWidth(int width) {
		this.width = width;
		this.r.width = width;
	}
	
	//Image
	public String getImage() {return image;}
	public void setImage(String image) {this.image = image;}
	
	// Rectangle
	public Rectangle getRectangle() {return this.r;}
	
	
}
