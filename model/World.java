package model;

import java.util.ArrayList;

import view.View;

/**
 * The world is our model. It saves the bare minimum of information required to
 * accurately reflect the state of the game. Note how this does not know
 * anything about graphics.
 */
public class World {

	public static final int DIR_RIGHT = 3;
	public static final int DIR_LEFT = 2;
	public static final int DIR_DOWN = 1;
	public static final int DIR_UP = 0;
	/** The world's width. */
	private final int width;
	/** The world's height. */
	private final int height;
        /** position of the hunters */
        private ArrayList<Position> hunterPositions = new ArrayList<Position>();
        /** position of the walls */
        private ArrayList<Position> wallPositions = new ArrayList<Position>();
        /** position of the player*/
 	private Position playerPosition = new Position();

	/** Set of views registered to be notified of world updates. */
	private final ArrayList<View> views = new ArrayList<>();

	/**
	 * Creates a new world with the given size.t
	 */
	public World(int width, int height) {
		// Normally, we would check the arguments for proper values
		this.width = width;
		this.height = height;
	}

	///////////////////////////////////////////////////////////////////////////
	// Getters and Setters

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
    
        public ArrayList<Position> getHunterPositions(){
	    return new ArrayList<>(this.hunterPositions);
	}

        public ArrayList<Position> getWallPositions(){
	    return new ArrayList<>(this.wallPositions);
	}

	public Position getPlayerPosition() {
	    return playerPosition;
	}
        
	public void setPlayerX(int playerX) {
	        playerX = Math.max(0, playerX);
		playerX = Math.min(getWidth() - 1, playerX);
		this.playerPosition.setX(playerX);
		
		updateViews();
	}


	public void setPlayerY(int playerY) {
		playerY = Math.max(0, playerY);
		playerY = Math.min(getHeight() - 1, playerY);
		this.playerPosition.setY(playerY);

		updateViews();
	}

	///////////////////////////////////////////////////////////////////////////
	// Player Management
	
	/**
	 * Moves the player along the given direction.
	 * 
	 * @param direction where to move. 1 up, 2 down, 3, left, 4 right
	 */
	public void movePlayer(int direction) {	
		// The direction tells us exactly how much we need to move along
		// every direction
	        setPlayerX(this.playerPosition.getX() + Direction.getDeltaX(direction));
		setPlayerY(this.playerPosition.getY() + Direction.getDeltaY(direction));
	}

	///////////////////////////////////////////////////////////////////////////
	// View Management

	/**
	 * Adds the given view of the world and updates it once. Once registered through
	 * this method, the view will receive updates whenever the world changes.
	 * 
	 * @param view the view to be registered.
	 */
	public void registerView(View view) {
		views.add(view);
		view.update(this);
	}

	/**
	 * Updates all views by calling their {@link View#update(World)} methods.
	 */
	private void updateViews() {
		for (int i = 0; i < views.size(); i++) {
			views.get(i).update(this);
		}
	}

}
