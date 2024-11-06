import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings({"unused", "serial"})
public class Frogger extends JFrame implements KeyListener, ActionListener {
	private Frog frog;
	private Vehicle vehicle;
	private Log log;
	
	private Container content;
	private JLabel frogLabel, roadLabel, grassLabel, waterLabel, vehicleLabel, logLabel;
	private ImageIcon frogImage, roadImage, grassImage, waterImage, vehicleImage, logImage;

	private int screenWidth = GameProperties.SCREEN_WIDTH;
	private int screenHeight = GameProperties.SCREEN_HEIGHT;
	private int charStep = GameProperties.CHARACTER_STEP;
	
	// Used to prioritize which graphic elements sit on top of each other 
	// so the frog isn't behind the background
	private int count = 0;
	
	public Frogger() {
		// Setting the GUI window
		setSize(screenWidth, screenHeight);
		content = getContentPane();
		setResizable(false);
		setTitle("Frogger");
		setLayout(null);
		content.addKeyListener(this);
		content.setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Initializing Objects
		createFrog();
		createVehicle();
		createLog();
		
		// Setting Background
		setBackground();	
	}
	
	// Main
	public static void main(String[] args) {
		Frogger Frogger = new Frogger();
		Frogger.setVisible(true);	
	}

	// Keyboard Input
	public void keyPressed(KeyEvent e) {
		// Move Frog with Arrow Keys or WASD
		int x = frog.getX();
		int y = frog.getY();
		int key = e.getKeyCode();

		// Up / W
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {moveUp();}
			
		// Down / S
		else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {moveDown();}
			
		// Left / A
		else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {moveLeft();}
			
		// Right / D
		else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {moveRight();}

		// Escape Key closes the game
		else if (key == KeyEvent.VK_ESCAPE) {
			vehicle.setMoving(false);
			log.setMoving(false);
			dispose();
			System.out.println("Exiting Game... ");
		}
		
		else {}
		
		frogLabel.setLocation(frog.getX(), frog.getY());
	}

	// TODO - Finish Implementing this properly
	public void endGameSequence() {
		System.out.println("Game Over");
		log.setMoving(false);
		vehicle.setMoving(false);
	}
	
	// Retrieve Image from Image Folder
	public ImageIcon setImage(String fileName) {
		return new ImageIcon(getClass().getResource("images/" + fileName));
	}
	
	// Attach Image to Label (Background) - Temporary
	public void loadBackgroundImage(JLabel label, ImageIcon image, int width, int height, int x, int y) {
		label.setIcon(image);
		label.setSize(width, height);
		label.setLocation(x, y);
		content.add(label);
	}
	
	// Attach Image to Label
	public void loadImgOntoLabel(JLabel label, ImageIcon img, Sprite object) {
		label.setIcon(img);
		label.setSize(object.getWidth(), object.getHeight());
		label.setLocation(object.getX(), object.getY());
		content.add(label);
	}
	
	// Frog Movement
	public void moveUp() {
		int y = frog.getY();
		y -= GameProperties.CHARACTER_STEP;
		if (y <= 0) {y = 0;} // Prevented from moving off screen top
		frog.setY(y);
	}
	public void moveDown() {
		int y = frog.getY();
		y += GameProperties.CHARACTER_STEP;
		// Prevents player from moving off bottom screen
		if (y >= GameProperties.SCREEN_HEIGHT - GameProperties.TOOL_BAR - frog.getHeight()) {
			y = GameProperties.SCREEN_HEIGHT - GameProperties.TOOL_BAR - frog.getHeight();
		}
		frog.setY(y);
	}
	public void moveLeft() {
		int x = frog.getX();
		int y = frog.getY();
		
		x -= GameProperties.CHARACTER_STEP;
		
		// Frog Loops only on Grass
		if (isOnGrass(y)) {if (x + frog.getWidth() <= 0) {x = GameProperties.SCREEN_WIDTH;}} 
		else {if (x < 0) { x = 0;}}
		
		frog.setX(x);
	}
	public void moveRight() {
		int x = frog.getX();
		int y = frog.getY();
		int charStep = GameProperties.CHARACTER_STEP;
		int screenWidth = GameProperties.SCREEN_WIDTH;
		
		x += charStep;
	
		// Frog Loops only on Grass
		if (isOnGrass(y)) {if (x >= screenWidth) {x = -frog.getWidth();}} 
		else {if (x > screenWidth - charStep) {x = screenWidth - charStep;}}
		
		frog.setX(x);
	}
	
	// Log & Frog Collision Detection
	public void detectLogCollision() {
		// If Frog is Colliding with Log
		if (frog.isCollidingWith(log)) {
			// Set Frog's X = Log's X
			frog.setX(log.getX());
			frogLabel.setLocation(frog.getX(), frog.getY());
		} 
		
		// End Game if Frog isn't colliding with log
		else if (isInWater(frog.getY())) {
			endGameSequence();
		}
	}

	// Checks if Frog is in Water
	private Boolean isInWater(int y) {
		return y > 100 && y < 350;
	}
	
	// Checks if Frog is on Grass
	private Boolean isOnGrass(int y) {
		// Top Grass
		if (y >= 0 && y < 100) {return true;} 
		
		// Middle Grass
		else if (y >= 350 && y < 450) {return true;}
		
		// Bottom Grass
		else if (y >= 650) {return true;}
		else return false;  
	}
	
	// Creates Frog Object
	private void createFrog() {
		frog = new Frog();
		frogLabel = new JLabel();
		
		// X Y
		frog.setX(charStep * 7);
		frog.setY(650);
		
		// H W
		frog.setHeight(charStep);
		frog.setWidth(charStep);
		
		// Image
		frog.setImage("frog1.png");
		frogImage = setImage(frog.getImage());		
		loadImgOntoLabel(frogLabel, frogImage, frog);
		
		content.setComponentZOrder(frogLabel, count++);
	}
	
	// Creates Vehicle Object
	private void createVehicle() {
		vehicle = new Vehicle();
		vehicleLabel = new JLabel();
		
		// X Y
		vehicle.setX(50);
		vehicle.setY(600);
		
		// H W
		vehicle.setHeight(50);
		vehicle.setWidth(50);
		
		// Image
		vehicle.setImage("vehicle1.png");
		vehicleImage = setImage(vehicle.getImage());
		loadImgOntoLabel(vehicleLabel, vehicleImage, vehicle);
		
		// Moving | Game
		vehicle.setMoving(true);
		vehicle.setFrogger(this);
		
		// Label | Frog
		vehicle.setVehicleLabel(vehicleLabel);
		vehicle.setFrog(frog);
		vehicle.setFrogLabel(frogLabel);
		
		vehicle.startThread();
		
		content.setComponentZOrder(vehicleLabel, count++);
		
	}

	// Creates Log Object
	private void createLog() {
		log = new Log();
		logLabel = new JLabel();
		
		// X Y
		log.setX(100);
		log.setY(300);
		
		// H W
		log.setHeight(50);
		log.setWidth(50);
		
		// Image
		log.setImage("log1.png");
		logImage = setImage(log.getImage());
		loadImgOntoLabel(logLabel, logImage, log);
		
		// Moving | Game
		log.setMoving(true);
		log.setFrogger(this);
		
		// Speed
		log.setSpeed(25);
		
		// Label | Frog
		log.setLogLabel(logLabel);
		log.setFrog(frog);
		log.setFrogLabel(frogLabel);
		
		log.startThread();
		
		content.setComponentZOrder(logLabel, count++);
	}

	// Sets Background - Temporary
	// Temporary Method to Setting Background
	public void setBackground() {
		grassLabel = new JLabel();
		waterLabel = new JLabel();
		roadLabel = new JLabel();
		grassImage = setImage("grass1.png");
		waterImage = setImage("water1.png");
		roadImage = setImage("road1.png");
		
		// This is a temporary setup
		// Grass
		// Y: 0 to 50
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 0);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 50 to 100
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 50);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Water
		// Y: 100 to 150
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 100);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 150 to 200
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 150);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 200 to 250
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 200);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 250 to 300
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 250);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 300 to 350
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 300);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Grass
		// Y: 350 to 400
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 350);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 400 to 450
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 400);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Road
		// Y: 450 to 500
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 450);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 500 to 550
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 500);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 550 to 600
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 550);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 600 to 650
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 600);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Grass
		// Y: 650 to 700
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 650);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 700 to 750
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 700);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 750 to 800
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 750);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
	}
	
	// Unused Events
	
	// Unused Events
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	
}
