package model;

import java.util.ArrayList;

import org.omg.PortableInterceptor.NON_EXISTENT;

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
        /** position of the starting tile*/
        private Position startPosition = new Position();
        /** position of the goal tile*/
        private Position goalPosition = new Position();
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
    public Position getStartPosition(){
	    return this.startPosition;
	}
    public Position getGoalPosition(){
	    return this.goalPosition;
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
	
    /**
       parse a state from an array of Strings.
       length of the array has to be height of the world
       length of every string has to be the width of the world.
     */
    public void fromStringArray(ArrayList<String>state ){
	    if (state.size() != this.height)
	    	throw new IllegalArgumentException("mismatch between world dimensions and parsing dimensions: expected height: "+this.height +" got: " +state.size());
	    for (int iy=0; iy<state.size(); ++iy){
		String row = state.get(iy);
		if ( row.length()!= this.width)
		    throw new IllegalArgumentException("mismatch between world dimensions and parsing dimensions: expected width: "+this.width + " got: "+ row.length());
		for(int ix=0; ix < row.length(); ++ix){
		    char c = row.charAt(ix);
		    if(c == 'W'){
			this.wallPositions.add(new Position(ix,iy ));
		    } else if (c == '#'){
			this.playerPosition= new Position(ix,iy);
		    } else if (c == 'V'){
			this.hunterPositions.add(new Position(ix,iy));
		    } else if (c == 'S'){
			this.startPosition= new Position(ix,iy);
		    } else if (c == 'Z'){
			this.goalPosition= new Position(ix,iy);
		    };
			}
	    }
	} 

	/**
	 * Checks if there is a Wall at given Position
	 * @param posX 
	 * @param posY
	 * @return true if wall in the way and false if not
	 */
	public boolean isWall(int posX, int posY) {
		ArrayList<Position> wallPositions = getWallPositions();
		for (Position wall : wallPositions) {
			int wallX = wall.getX();
			int wallY = wall.getY();
			if (posX == wallX && posY == wallY) {
				return true;
			}
		}
		return false;
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
		// every direction. Every move also makes the hunters move
		if (!isWall(this.playerPosition.getX() + Direction.getDeltaX(direction), this.playerPosition.getY() + Direction.getDeltaY(direction))) {
	    	setPlayerX(this.playerPosition.getX() + Direction.getDeltaX(direction));
			setPlayerY(this.playerPosition.getY() + Direction.getDeltaY(direction));
			huntPlayer();
		}
	}

	///////////////////////////////////////////////////////////////////////////
	// Hunter Management

	/**
 	* Moves the hunters towards the player's position based on the specified difficulty level.
 	*
 	* @param difficulty The difficulty level indicating the behavior of the hunters.
 	*/
	public void huntPlayer() {
		// Every Hunter gets moved along the coordinate with
		// the biggest distance to the player unless there is a wall in their way
		int playerX = this.playerPosition.getX();
    	int playerY = this.playerPosition.getY();
    	ArrayList<Position> hunterPositions = getHunterPositions();

		for (Position hunterPosition : hunterPositions) {
			int hunterX = hunterPosition.getX();
			int hunterY = hunterPosition.getY();

			int difX = Math.abs(hunterX - playerX);
			int difY = Math.abs(hunterY - playerY);

			if (difX > difY) {
				if (playerX > hunterX) {
					// Check if moving right is possible and there is no wall
					if (!isWall(hunterX + 1, hunterY)) {
						hunterX++;
					}
					// If moving right is not possible, try moving in a different direction
					else if (!isWall(hunterX, hunterY + 1) && !isWall(hunterX, hunterY - 1)) {
						// Move up if there is no wall
						if (!isWall(hunterX, hunterY + 1)) {
							hunterY++;
						}
						// Move down if there is no wall
						else if (!isWall(hunterX, hunterY - 1)) {
							hunterY--;
						}
					}
				} else if (playerX < hunterX) {
					// Check if moving left is possible and there is no wall
					if (!isWall(hunterX - 1, hunterY)) {
						hunterX--;
					}
					// If moving left is not possible, try moving in a different direction
					else if (!isWall(hunterX, hunterY + 1) && !isWall(hunterX, hunterY - 1)) {
						// Move up if there is no wall
						if (!isWall(hunterX, hunterY + 1)) {
							hunterY++;
						}
						// Move down if there is no wall
						else if (!isWall(hunterX, hunterY - 1)) {
							hunterY--;
						}
					}
				}
			} else if (difX < difY) {
				if (playerY > hunterY) {
					// Check if moving down is possible and there is no wall
					if (!isWall(hunterX, hunterY + 1)) {
						hunterY++;
					}
					// If moving down is not possible, try moving in a different direction
					else if (!isWall(hunterX + 1, hunterY) && !isWall(hunterX - 1, hunterY)) {
						// Move right if there is no wall
						if (!isWall(hunterX + 1, hunterY)) {
							hunterX++;
						}
						// Move left if there is no wall
						else if (!isWall(hunterX - 1, hunterY)) {
							hunterX--;
						}
					}
				} else if (playerY < hunterY) {
					// Check if moving up is possible and there is no wall
					if (!isWall(hunterX, hunterY - 1)) {
						hunterY--;
					}
					// If moving up is not possible, try moving in a different direction
					else if (!isWall(hunterX + 1, hunterY) && !isWall(hunterX - 1, hunterY)) {
						// Move right if there is no wall
						if (!isWall(hunterX + 1, hunterY)) {
							hunterX++;
						}
						// Move left if there is no wall
						else if (!isWall(hunterX - 1, hunterY)) {
							hunterX--;
						}
					}
				}
			}

			hunterPosition.setX(hunterX);
			hunterPosition.setY(hunterY);

			if (playerX == hunterX && playerY == hunterY) {
				System.exit(0);
			}
		}
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
