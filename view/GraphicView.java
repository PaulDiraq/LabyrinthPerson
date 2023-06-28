package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import java.awt.Image;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import model.World;
import model.Position;
import model.GameState;

/**
 * A graphical view of the world.
 */
public class GraphicView extends JPanel implements View {
	
	/** The view's width. */
	private final int WIDTH;
	/** The view's height. */
	private final int HEIGHT;
    /** */
	private Dimension fieldDimension;
    /** position of the player */
	private Position playerPositionStored; // Store the player's position
	
	public GraphicView(int width, int height, Dimension fieldDimension) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fieldDimension = fieldDimension;
		this.bg = new Rectangle(WIDTH, HEIGHT);
	}

	private void setFontSize(Graphics g,int size){
		Font myfont =  g.getFont();
		g.setFont( new Font(myfont.getName(),myfont.getStyle(),size));
	};
	/** The background rectangle. */
	private final Rectangle bg;
	/** The rectangle we're moving. */
    private final Rectangle player = new Rectangle(1, 1);
    private final Rectangle goal = new Rectangle(1,1);
	/** The State of the Game. */
	private  GameState gameState=GameState.GAME;
        private  int numberOfHunters ;

        /** List of all Hunters. */
	private final ArrayList<Rectangle> hunters = new ArrayList<>();
	/** List of all Walls. */
	private final ArrayList<Rectangle> walls = new ArrayList<>();
	
	/**
	 * Creates a new instance.
	 */
	@Override
	public void paint(Graphics g) {
	    // Paint background
	    g.setColor(Color.BLACK);
	    g.fillRect(bg.x, bg.y, bg.width, bg.height);
	    // paint text
	    if (this.gameState == GameState.WIN){
	        
		g.setColor(Color.WHITE );
		this.setFontSize(g,40);
		g.drawString("You won",bg.width/2-20,bg.height/2-20);
		this.setFontSize(g,18);
		g.drawString("Want to try again?", bg.width/2-20,bg.height/2+30);
		g.drawString("Number of Hunters:", bg.width/2-170,bg.height/2+50);		
		g.drawString(String.valueOf(this.numberOfHunters),bg.width/2,bg.height/2+50 );	
		g.drawString("Press <Enter> to continue",bg.width/2,bg.height/2+70 );
	    } else if(this.gameState == GameState.LOST)  {
		g.setColor(Color.WHITE );
		this.setFontSize(g,40);
		g.drawString("You Lost",bg.width/2-20,bg.height/2-20);
		this.setFontSize(g,18);
		g.drawString("Want to try again?", bg.width/2-20,bg.height/2+30);
		g.drawString("Number of Hunters:", bg.width/2-170,bg.height/2+50);		
		g.drawString(String.valueOf(this.numberOfHunters),bg.width/2,bg.height/2+50 );	
		g.drawString("Press <Enter> to continue",bg.width/2,bg.height/2+70 );
	    }else {
		//Paint goal
		g.setColor(Color.YELLOW);
		g.fillRect(goal.x,goal.y,goal.width,goal.height);
		// Paint player
		// Source: https://www.iconsdb.com/white-icons/happy-icon.html
		Image playerImage = loadImage("view"+File.separator+"face.png", fieldDimension.width, fieldDimension.height); // Load the image for the player
		int playerX = playerPositionStored.getX() * fieldDimension.width;
		int playerY = playerPositionStored.getY() * fieldDimension.height;
		g.drawImage(playerImage, playerX, playerY, null);
		
		// Paint Hunters
		g.setColor(Color.RED);
		for (Rectangle hunter : hunters) {
		    g.fillRect(hunter.x, hunter.y, hunter.width, hunter.height);
		}
		    // Paint Walls
		g.setColor(Color.BLUE);
		for (Rectangle wall : walls) {
		    g.fillRect(wall.x, wall.y, wall.width, wall.height);
		}
	    }	    
	}
    
	@Override
	public void update(World world) {
	    Position goalPosition = world.getGoalPosition();
	    goal.setLocation((int) goalPosition.getX() * fieldDimension.width,
			     (int) goalPosition.getY() * fieldDimension.height);
	    this.gameState=world.getGameState();
	    goal.setSize(fieldDimension);
	    this.numberOfHunters = world.getNumberOfHunters();
	    // Update players size and location
	    player.setSize(fieldDimension);

	    Position playerPosition = world.getPlayerPosition();
	    playerPositionStored = playerPosition;
	    int playerX = playerPosition.getX();
	    int playerY = playerPosition.getY();		
	    player.setLocation((int)
			       (playerX * fieldDimension.width),
			       (int) (playerY * fieldDimension.height));

	    // Update size and location of all Hunters
	    ArrayList<Position> hunterPositions = world.getHunterPositions();
	    hunters.clear();
	    for (Position hunterPosition : hunterPositions) {
		Rectangle hunter = new Rectangle(1, 1);
		hunter.setSize(fieldDimension);

		int hunterX = hunterPosition.getX();
		int hunterY = hunterPosition.getY();
		hunter.setLocation(hunterX * fieldDimension.width, hunterY * fieldDimension.height);

		hunters.add(hunter);
	    }

	    // Paint all Walls
	    ArrayList<Position> wallPositions = world.getWallPositions();
	    walls.clear();
	    for (Position wallPosition : wallPositions) {
		Rectangle wall = new Rectangle(1, 1);
		wall.setSize(fieldDimension);

		int wallX = wallPosition.getX();
		int wallY = wallPosition.getY();
		wall.setLocation(wallX * fieldDimension.width, wallY * fieldDimension.height);

		walls.add(wall);

	    }
	    repaint();
	}
	/**
		 * Load an image from the given file path.
		 * @param imagePath the path to the image file
		 * @return the loaded Image object
		 */
    private Image loadImage(String imagePath, int width, int height) {
	Image image = null;
	try {
	    image = ImageIO.read(new File(imagePath));
	    image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return image;
    }
}
