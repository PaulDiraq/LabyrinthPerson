package view;
import java.util.ArrayList;

import model.World;
import model.Position;
/**
 * A view that prints the current state of the world to the console upon every
 * update.
 */
public class ConsoleView implements View {

	@Override
	public void update(World world) {
		// The player's position
	    ArrayList<Position> wallPositions = world.getWallPositions();
	    ArrayList<Position> hunterPositions = world.getHunterPositions();
		for (int row = 0; row < world.getHeight(); row++) {
			for (int col = 0; col < world.getWidth(); col++) {
				// If the player is here, print #, otherwise print .
			    Position currentPos = new Position(col,row);
			    if  (wallPositions.contains(currentPos )){
				System.out.print("W");
			    }  else if(hunterPositions.contains(currentPos)){
				System.out.print("V");
			    } else if (world.getPlayerPosition().equals(currentPos)) {
				System.out.print("#");
			    } else if (world.getStartPosition().equals(currentPos)) {
				System.out.print("S");
			    } else if (world.getGoalPosition().equals(currentPos)) {
				System.out.print("Z");
			    }else {
				System.out.print(".");
			    }
			}

			// A newline after every row
			System.out.println();
		}

		// A newline between every update
		System.out.println();
	}

}
