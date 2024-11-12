import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings({"serial"})
public class Frogger extends JFrame implements KeyListener, ActionListener {
	// Objects
	private Frog frog;
	private Vehicle[] vehicles;
	private Log[] logs;
	
	private int[][] vehiclesArray;
	private int[][] logsArray;
	
	// Graphics
	private Container content;
	private JLabel frogLabel, scoreLabel;
	private ImageIcon frogImage, vehicleImage, logImage;

	// Shortcuts
	private int screenWidth = GameProperties.SCREEN_WIDTH;
	private int screenHeight = GameProperties.SCREEN_HEIGHT;
	private int charStep = GameProperties.CHARACTER_STEP;
	private int gridWidth = GameProperties.GRID_WIDTH;
	
	private int frogStartX = charStep * 7;
	private int frogStartY = 650;

	// Coordinates
	private int[] roadLane = {450, 500, 550, 600};
	private int[] waterLane = {100, 150, 200, 250, 300};
	private int winGrass = 50;
	
	// Used to prioritize which graphic elements sit on top of each other 
	// so the frog isn't behind the background
	private int count = 0;
	
	private Boolean gameOver = false;
	private Boolean gameWin = false;
	private int score = 0;
	
	public Frogger() {
		// Setting the GUI window
		setSize(screenWidth, screenHeight);
		content = getContentPane();
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Frogger");
		setLayout(null);
		content.addKeyListener(this);
		content.setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		vehiclesArray = new int[][] { 	// Cars on Road
			{roadLane[0], 1, 10},  		// Lane 1 | #ofVehicles | Speed
			{roadLane[1], 1, 30},  		// Lane 2 | #ofVehicles | Speed
			{roadLane[2], 1, -10}, 		// Lane 3 | #ofVehicles | Speed
			{roadLane[3], 1, -15}  		// Lane 4 | #ofVehicles | Speed
		};
		
		logsArray = new int[][] {   // Logs in Water
			{waterLane[0], 5, 30},  // Lane 1 | #of Logs | Speed
			{waterLane[1], 5, -25},	// Lane 2 | #of Logs | Speed
			{waterLane[2], 5, 15},	// Lane 3 | #of Logs | Speed
			{waterLane[3], 5, -30},	// Lane 4 | #of Logs | Speed
			{waterLane[4], 5, 20}   // Lane 5 | #of Logs | Speed
		};
		
		scoreLabel = new JLabel();
		scoreLabel.setText("Score: " + score);
		scoreLabel.setFont(new Font("Calibri", Font.BOLD, 20));
		scoreLabel.setForeground(Color.white);
		scoreLabel.setBounds(550, 0, 500, 50);
		content.add(scoreLabel);
		content.setComponentZOrder(scoreLabel, count++);
		
		// Initializing Objects
		createFrog();
		createVehicles();
		createLogs();
		
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
			if (JOptionPane.showConfirmDialog(
					null, "Are you sure you want to exit? \n Score will not be recorded.", 
					"", JOptionPane.YES_NO_OPTION) 
				== JOptionPane.YES_OPTION) {
				gameClose();
			}
		}
		
		else {}
		
		frogLabel.setLocation(frog.getX(), frog.getY());
	}

	// End Game
	public void gameStop() {
		// End All Log Threads
		 if (logs != null) {
	        for (Log log : logs) {
	        	if (log != null) {log.stopThread();}
        	}
		 }
  
		 // End All Vehicle Threads
		 for (Vehicle vehicle : vehicles) {
            if (vehicle != null) {vehicle.stopThread();}
		 }
	}
	
	public void gameOver() {
		System.out.println("Game Over");
		score -= 50;
		updateScore();
		gameOver = true;
		gameWin = false;
		gameStop();
		promptRestart();
	}
	
	public void gameWin() {
		if (frog.getY() == winGrass && gameOver == false) {
			gameOver = true;
			gameWin = true;
			System.out.println("Game Win!");
			score += 50;
			updateScore();
			gameStop();
			promptRestart();
		}
		
	}
	
	public void promptRestart() {
		String gameState;
		if (!gameWin) {gameState = "Game Over";}
		else {gameState = "You Win!";}
		
		if (JOptionPane.showConfirmDialog(
				null, "Do you want to play again?", 
				gameState, JOptionPane.YES_NO_OPTION) 
			== JOptionPane.YES_OPTION) {
		    System.out.println("Restarting...");
		    gameRestart();
		} else {
			System.out.println("Exiting...");
			String name = JOptionPane.showInputDialog("Enter your name");
			System.out.println(name + ": " + score);
			
			saveData(name, score);
			
			
			gameClose();
			
		}
	}
	
	public void saveData(String name, int score) {
		Connection conn = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			
			String dbURL = "jdbc:sqlite:frogger.db";
			conn = DriverManager.getConnection(dbURL);
			
			if (conn != null) {
				
				DatabaseMetaData db = (DatabaseMetaData) conn.getMetaData();
				System.out.println("\nConnected to Database");
				System.out.println("Driver Name: " + db.getDriverName());
				System.out.println("Driver Version: " + db.getDriverVersion());
				System.out.println("Product Name: " + db.getDatabaseProductName());
				System.out.println("Product Version: " + db.getDatabaseProductVersion());
				System.out.println("\n");
				
				// Create Table
				String sqlCreateTable = "CREATE TABLE IF NOT EXISTS PLAYERS " +
	                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
	                        " NAME TEXT NOT NULL, " +
	                        " SCORE INT NOT NULL)";
				try (PreparedStatement createTable = conn.prepareStatement(sqlCreateTable)) {
					createTable.executeUpdate();
				}
				
				// Insert Data
				String sqlInsert = "INSERT INTO PLAYERS (NAME, SCORE) VALUES (?, ?)";
				try (PreparedStatement insert = conn.prepareStatement(sqlInsert)) {
					  insert.setString(1, name);
					  insert.setInt(2, score);
					  insert.executeUpdate();
					
					  System.out.println("Data Saved");
				}
				
				// Select Data
				String sqlSelect = "SELECT * FROM PLAYERS";
				try (PreparedStatement select = conn.prepareStatement(sqlSelect)) {
					ResultSet rs = select.executeQuery();
                	DisplayRecords(rs);
                	rs.close();
				}
				
			}
			
			conn.close();
			
		} catch (Exception e) {e.printStackTrace();}	
	}

	public static void DisplayRecords(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int score = rs.getInt("score");
            
            System.out.println("-----------------------");
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Score: " + score);
            System.out.println("-----------------------\n");
        }
    }
	
	public void gameRestart() {
		gameOver = false;
		
		frog.setLocation(frogStartX, frogStartY);
		frogLabel.setLocation(frog.getX(), frog.getY());
		
		// End All Log Threads
		 if (logs != null) {
	        for (Log log : logs) {
	        	if (log != null) {log.startThread();}
        	}
		 }
  
		 // End All Vehicle Threads
		 for (Vehicle vehicle : vehicles) {
            if (vehicle != null) {vehicle.startThread();}
		 }
	    
	    System.out.println("Game Loaded");
	    
	}
	
	public void gameClose() {
		gameStop();
		dispose();
		System.out.println("Exiting Game... ");
	}
	
	public void updateScore() {
		scoreLabel.setText("Score: " + score);
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
		y -= charStep;
		if (y <= winGrass) {y = winGrass;} // Prevented from moving off screen top
		frog.setY(y);
	}
	public void moveDown() {
		int y = frog.getY();
		y += charStep;
		// Prevents player from moving off bottom screen
		if (y >= screenHeight - GameProperties.TOOL_BAR - frog.getHeight()) {
			y = screenHeight - GameProperties.TOOL_BAR - frog.getHeight();
		}
		frog.setY(y);
	}
	public void moveLeft() {
		int x = frog.getX();
		int y = frog.getY();
		
		x -= charStep;
		
		// Frog Loops only on Grass
		if (isOnGrass(y)) {if (x + frog.getWidth() <= 0) {x = screenWidth;}} 
		else {if (x < 0) { x = 0;}}
		
		frog.setX(x);
	}
	public void moveRight() {
		int x = frog.getX();
		int y = frog.getY();
		
		x += charStep;
	
		// Frog Loops only on Grass
		if (isOnGrass(y)) {if (x >= screenWidth) {x = -frog.getWidth();}} 
		else {if (x > screenWidth - charStep) {x = screenWidth - charStep;}}
		
		frog.setX(x);
	}
	
	public void detectLogCollision() {
		boolean onLog = false;

	    for (Log log : logs) {
	        if (log != null) {
	            if (frog.isCollidingWith(log)) {
	                frog.setX(log.getX());
	                frogLabel.setLocation(log.getX(), log.getY());
	                onLog = true; 
	                break;
	            }
	        }
	    }

	    // If the frog is in water and not on any log, end the game
	    if (isInWater(frog.getY()) && !onLog && gameOver != true) {
	    	gameOver = true;
	        gameOver();
	    }
		
	}

	// Checks if Frog is in Water
	private Boolean isInWater(int y) {
		return y >= 100 && y < 350;
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
		frog.setLocation(frogStartX, frogStartY);
		
		// H W
		frog.setHeight(charStep);
		frog.setWidth(charStep);
		
		// Image
		frog.setImage("frog1.png");
		frogImage = setImage(frog.getImage());		
		loadImgOntoLabel(frogLabel, frogImage, frog);
		
		content.setComponentZOrder(frogLabel, count++);
	}
	
	// Creates Vehicle Objects
	private void createVehicles() {
		// Counts the total number of vehicles
		int totalVehicles = 0;
		for (int i = 0; i < vehiclesArray.length; i++) {
			totalVehicles += vehiclesArray[i][1]; 
		}
		
		// Creates an array of vehicles = # of total vehicles
		vehicles = new Vehicle[totalVehicles];
		int vehicleIndex = 0; 
		
		// For each Lane
		for (int i = 0; i < vehiclesArray.length; i++) {
			int xPos = 0;
			// Used to equally separate the vehicles
			int increment = screenWidth / vehiclesArray[i][1];
			
			// For each car in lane
			for (int j = 0; j < vehiclesArray[i][1]; j++) {
				Vehicle vehicle = new Vehicle();
				JLabel vehLabel = new JLabel();
				
				// X | Y
				vehicle.setX(xPos);
				vehicle.setY(vehiclesArray[i][0]);
				
				// H | W
				vehicle.setHeight(50);	
				vehicle.setWidth(50);
				
				// Image
				vehicle.setImage("vehicle1.png");
				vehicleImage = setImage(vehicle.getImage());
				loadImgOntoLabel(vehLabel, vehicleImage, vehicle);
				
				// Speed
				vehicle.setSpeed(vehiclesArray[i][2]);
				
				// Moving | Frogger
				vehicle.setMoving(true);
				vehicle.setFrogger(this);
				
				// VehLabel | Frog | FrogLabel
				vehicle.setVehicleLabel(vehLabel);
				vehicle.setFrog(frog);
				vehicle.setFrogLabel(frogLabel);
		
				// Start Thread
				vehicle.startThread();
				
				// Adding Vehicles to Array | Incrementing X Position
				vehicles[vehicleIndex++] = vehicle;
				xPos += increment;
				
				content.setComponentZOrder(vehLabel, count++);
			}
		}
	}

 	private void createLogs() {
 		int totalLogs = 0;
 		for (int i = 0; i < logsArray.length; i++) {
			totalLogs += logsArray[i][1]; 
		}
		
		// Creates an array of logs = # of total logs
		logs = new Log[totalLogs];
		int logIndex = 0;
		
		// For each Lane
		for (int i = 0; i < logsArray.length; i++) {
			int xPos = 0;
			// Used to equally separate the logs
			int increment = screenWidth / logsArray[i][1];
			
			// For each log in lane
			for (int j = 0; j < logsArray[i][1]; j++) {
				Log log = new Log();
				JLabel logLabel = new JLabel();
			
				// X | Y
				log.setX(xPos);
				log.setY(logsArray[i][0]);
				
				// H | W
				log.setHeight(50);	
				log.setWidth(50);
				
				// Image
				log.setImage("log1.png");
				logImage = setImage(log.getImage());
				loadImgOntoLabel(logLabel, logImage, log);
				
				// Speed
				log.setSpeed(logsArray[i][2]);
				
				// Moving | Frogger
				log.setMoving(true);
				log.setFrogger(this);
				
				// logLabel | Frog | FrogLabel
				log.setLogLabel(logLabel);
				log.setFrog(frog);
				log.setFrogLabel(frogLabel);
		
				// Start Thread
				log.startThread();
				
				// Adding Logs to Array | Incrementing X Position
				logs[logIndex++] = log;
				xPos += increment;
				
				content.setComponentZOrder(logLabel, count++);
			}
		}
		
 	}
 	
	// Sets Background - Temporary
	public void setBackground() {		
		// This is a temporary setup
		// Grass
		// Y: 0 to 50
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("frame1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 0);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 50 to 100
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 50);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Water
		// Y: 100 to 150
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 100);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 150 to 200
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 150);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 200 to 250
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 200);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 250 to 300
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 250);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 300 to 350
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("water1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 300);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Grass
		// Y: 350 to 400
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 350);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 400 to 450
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 400);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Road
		// Y: 450 to 500
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 450);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 500 to 550
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 500);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 550 to 600
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 550);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 600 to 650
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("road1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 600);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Grass
		// Y: 650 to 700
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 650);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 700 to 750
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("grass1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 700);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
		
		// Y: 750 to 800
		for (int i =0; i < gridWidth; i++) {
			JLabel label = new JLabel();
			ImageIcon image = setImage("frame1.png");
			loadBackgroundImage(label, image, 50, 50, i *50, 750);
			content.add(label);
			content.setComponentZOrder(label, count++);
		}
	}
	
	// Unused Events
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}