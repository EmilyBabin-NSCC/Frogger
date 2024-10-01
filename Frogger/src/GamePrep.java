import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GamePrep extends JFrame implements KeyListener, ActionListener {
	private Frog frog;
	private Vehicle vehicle;
	
	private Container content;
	private JLabel frogLabel, roadLabel, grassLabel, waterLabel, vehicleLabel, logLabel;
	private ImageIcon frogImage, roadImage, grassImage, waterImage, vehicleImage, logImage;
	
	// Used to prioritize which graphic elements sit on top of each other so the frog isn't behind the background
	private int count = 0;
	
	public GamePrep() {
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

		// Vehicle - X | Y | H | W 
		vehicle = new Vehicle(50, 600, 50, 50, "images/vehicle1.png", true);
		vehicle.setHeight(50);
		vehicle.setWidth(50);
		
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
	
		
		content.setComponentZOrder(vehicleLabel, count++);
		content.setComponentZOrder(logLabel, count++);
		
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 0);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 50);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 100);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 150);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 200);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 250);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadImage(label, image, 50, 50, i *50, 300);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 350);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 400);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadImage(label, image, 50, 50, i *50, 450);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadImage(label, image, 50, 50, i *50, 500);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadImage(label, image, 50, 50, i *50, 550);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadImage(label, image, 50, 50, i *50, 600);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 650);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 700);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		for (int i =0; i < GameProperties.GRID_WIDTH; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadImage(label, image, 50, 50, i *50, 750);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		
		// Load Image - Label | Image | W | H | X | Y
//		loadImage(grassLabel, grassImage, 50, 50, 0, 0);
//		loadImage(waterLabel, waterImage, 50, 50, 0, 100);
//		loadImage(frogLabel, frogImage, 50, 50, 0, 200);
		
//		loadImage(roadLabel, roadImage, 50, 50, 0, 400);
		
		
		// Setting Load order of the labels so the frog isn't behind anything
//		content.setComponentZOrder(frogLabel, count);
//		content.setComponentZOrder(grassLabel, count += 1);
//		content.setComponentZOrder(waterLabel, count += 1);
//		content.setComponentZOrder(vehicleLabel,  count += 1);
////		content.setComponentZOrder(roadLabel, count += 1);
//		content.setComponentZOrder(logLabel, count += 1);
		
		
		
		content.addKeyListener(this);
		content.setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Main
	public static void main(String[] args) {
		GamePrep Frogger = new GamePrep();
		Frogger.setVisible(true);
	}

	public void keyPressed(KeyEvent e) {
		// Move Frog with Arrow Keys or WASD
		int x = frog.getX();
		int y = frog.getY();
		
		//Up / W
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			y -= GameProperties.CHARACTER_STEP;
			// Prevents player from moving off top screen
			if (y <= 0) {y = 0;}
			
		// Down / S
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			y += GameProperties.CHARACTER_STEP;
			// Prevents player from moving off bottom screen
			if (y >= GameProperties.SCREEN_HEIGHT - GameProperties.TOOL_BAR - frog.getHeight()) {y = GameProperties.SCREEN_HEIGHT - GameProperties.TOOL_BAR - frog.getHeight();}
			
		// Left / A
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			x -= GameProperties.CHARACTER_STEP;
			// Loops player around to the right side
			if (x + frog.getWidth() <= 0) {x = GameProperties.SCREEN_WIDTH;}
			
		// Right / D
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			x += GameProperties.CHARACTER_STEP;
			// Loops player around to the left side
			if (x >= GameProperties.SCREEN_WIDTH) {x = -1 * frog.getWidth();}

		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			vehicle.setMoving(false);
			dispose();
			System.out.println("Exiting Game... ");
		}
		
		else {System.out.println("Invalid Operation");}
//		
		System.out.println("frog.x, frog.y:" + frog.getX() + ", " + frog.getY());
		System.out.println("frog.r.x, frogr.y:" + frog.getRectangle().x + ", " + frog.getRectangle().y);
		System.out.println("frog width, height:" + frog.getWidth() + ", " + frog.getHeight());
		System.out.println("frog r.width, r.height:" + frog.getRectangle().width + ", " + frog.getRectangle().height);
		
		frog.setX(x);
		frog.setY(y);
		
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
	
}
