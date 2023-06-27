package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.World;
import model.Position;
/**
 * A graphical view of the world.
 */
public class GraphicView extends JPanel implements View {
	
	/** The view's width. */
	private final int WIDTH;
	/** The view's height. */
	private final int HEIGHT;
	
	private Dimension fieldDimension;
	
	public GraphicView(int width, int height, Dimension fieldDimension) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fieldDimension = fieldDimension;
		this.bg = new Rectangle(WIDTH, HEIGHT);
	}
	
	/** The background rectangle. */
	private final Rectangle bg;
	/** The rectangle we're moving. */
	private final Rectangle player = new Rectangle(1, 1);
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
		// Paint player
		g.setColor(Color.GREEN);
		g.fillRect(player.x, player.y, player.width, player.height);
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

	@Override
	public void update(World world) {
		
		// Update players size and location
		player.setSize(fieldDimension);

		Position playerPosition = world.getPlayerPosition();
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
}
