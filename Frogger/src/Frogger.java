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

	// Used to prioritize which graphic elements sit on top of each other so the frog isn't behind the background
	private int count = 0;
	
	public Frogger() {
		// Setting the GUI window
		setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
		content = getContentPane();
		setResizable(false);
		setTitle("Frogger");
		setLayout(null);
		
		// Frog - X | Y | H | W 
		frog = new Frog(50 * 7, 650, GameProperties.CHARACTER_STEP, GameProperties.CHARACTER_STEP);
		frog.setHeight(GameProperties.CHARACTER_STEP);
		frog.setWidth(GameProperties.CHARACTER_STEP);

		// Vehicle - X | Y | H | W | Game
		vehicle = new Vehicle(50, 600, 50, 50, "", true, this);
		vehicle.setHeight(50);
		vehicle.setWidth(50);
		
		// Log -
		log = new Log(100, 300, 50, 50, "", true, this);
		log.setHeight(50);
		log.setWidth(50);
		
		// Labels
		grassLabel = new JLabel();
		waterLabel = new JLabel();
		frogLabel = new JLabel();
		vehicleLabel = new JLabel();
		roadLabel = new JLabel();
		logLabel = new JLabel();
		
		// Retrieving each image
		grassImage = setImage("grass1.png");
		waterImage = setImage("water1.png");
		frogImage = setImage("frog1.png");
		vehicleImage = setImage("vehicle1.png");
		roadImage = setImage("road1.png");
		logImage = setImage("log1.png");
		
		// Load Image - Label | Image | H | W | X | Y
		loadImage(frogLabel, frogImage, frog.getHeight(), frog.getWidth(), frog.getX(), frog.getY());
		content.setComponentZOrder(frogLabel, count++);
		
		loadImage(vehicleLabel, vehicleImage, 50, 50, 50, 600);
		loadImage(logLabel, logImage, 50, 50, 100, 300);
		
		vehicle.setVehicleLabel(vehicleLabel);
		vehicle.setFrog(frog);
		vehicle.setFrogLabel(frogLabel);
		vehicle.startThread();
		
		log.setLogLabel(logLabel);
		log.setFrog(frog);
		log.setFrogLabel(frogLabel);
		log.startThread();
		
		content.setComponentZOrder(vehicleLabel, count++);
		content.setComponentZOrder(logLabel, count++);
		
		setBackground();
		
		content.addKeyListener(this);
		content.setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}
	
	// Main
	public static void main(String[] args) {
		Frogger Frogger = new Frogger();
		Frogger.setVisible(true);
		
	}

	public void keyPressed(KeyEvent e) {
		// Move Frog with Arrow Keys or WASD
		int x = frog.getX();
		int y = frog.getY();

		// Up / W
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {moveUp();}
			
		// Down / S
		else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {moveDown();}
			
		// Left / A
		else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {moveLeft();}
			
		// Right / D
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {moveRight();}

		// Escape Key closes the game
		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			vehicle.setMoving(false);
			dispose();
			System.out.println("Exiting Game... ");
		}
		
		else {System.out.println("Invalid Operation");}
		
		frogLabel.setLocation(frog.getX(), frog.getY());
		
	}

	// Method to retrieve an image from the images folder
	public ImageIcon setImage(String fileName) {
		return new ImageIcon(getClass().getResource("images/" + fileName));
	}
	
	// Method to attach the images to labels to display
	public void loadImage(JLabel label, ImageIcon image, int width, int height, int x, int y) {
		label.setIcon(image);
		label.setSize(width, height);
		label.setLocation(x, y);
		content.add(label);
	}
	
	// Unused Events
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setBackground() {
		// This is a temporary setup
		// Grass
		// Y: 0 to 50
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 0);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 50 to 100
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 50);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Water
		// Y: 100 to 150
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 100);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 150 to 200
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 150);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 200 to 250
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 200);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 250 to 300
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 250);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 300 to 350
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 300);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Grass
		// Y: 350 to 400
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 350);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 400 to 450
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 400);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Road
		// Y: 450 to 500
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadImage(label, image, 50, 50, i *50, 450);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 500 to 550
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadImage(label, image, 50, 50, i *50, 500);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 550 to 600
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadImage(label, image, 50, 50, i *50, 550);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 600 to 650
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadImage(label, image, 50, 50, i *50, 600);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Grass
		// Y: 650 to 700
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 650);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 700 to 750
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 700);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 750 to 800
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 750);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
	}
	
	public void endGameSequence() {
		System.out.println("Game Over");
	}
	
	public void moveUp() {
		int y = frog.getY();
		y -= GameProperties.CHARACTER_STEP;
		if (y <= 0) {y = 0;} // Prevented from moving off screen top
		frog.setY(y);
	}
	
	public void moveRight() {
		int x = frog.getX();
		x += GameProperties.CHARACTER_STEP;
		if (x >= GameProperties.SCREEN_WIDTH) {x = -1 * frog.getWidth();} // Loops player around to the left side
		frog.setX(x);
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
		x -= GameProperties.CHARACTER_STEP;
		if (x + frog.getWidth() <= 0) {x = GameProperties.SCREEN_WIDTH;} // Loops player around to the right side
		frog.setX(x);
	}
	
	public void detectCollision() {
		while (vehicle.getMoving()) {
			if (vehicle.collided() == true) {
				endGameSequence();
				break;
			}
		}
	}
		
}
