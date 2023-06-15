package model;

public  class Position{
    int posX =0;
    int posY =0;
    /**
       default constructor, constructs a Position(0,0);
     */
    public Position(){};
    /**
       constructor
       @param[x] x-position
       @param[y] y-position
     */
    
    public Position(int x,int y){
	this.posX=x;
	this.posY=y;
    }

    /**
       getter for x
       @return the x-position
     */
    public int getX(){
	return this.posX;
    }

    /**
       setter for x
       @param the x-position
     */
    public void setX(int x){
	this.posX=x;
    }
    
    /**
       getter for y
       @return the y-position
    */
    public int getY(){
	return this.posY;
    }

    /**
       setter for y
       @param the y-position
     */
    public void setY(int y){
	this.posY=y;
    }

    
    /**
       @return a stringrepresentation of the position.
     */
    public String toString(){
	return "Position( "+this.posX+", "+Integer.toString(this.posY) +" )";
    }
}
