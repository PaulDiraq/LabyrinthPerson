package model;

import java.util.Random;
import java.util.ArrayList;

class LabyrinthCreator{
    private static int maxLevelNumber = 0;
    
    public void createLevel(World world, int i){
	if (i<0){
	    i= (new Random()).nextInt(maxLevelNumber+1);
	}
	i = i% (maxLevelNumber+1);
	System.out.println(i);
	this.createLevel0(world);
    };


    private void createLevel0(World world){
	System.out.println("creating Level 0");
	
	ArrayList<String> level = new ArrayList<>();
	level.add("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
	level.add("W......................................W");
	level.add("WWW.WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW.WWW");
	level.add("W.W.WWWW...........Z............WWWW.W.W");
	level.add("W.W.......WWWWWWWW....WWWWWWWW.......W.W");
	level.add("W...WWWWWWW........WW........WWWWWWW...W");
	level.add("W.......W.....WWW..WW..WWW.....W.......W");
	level.add("WWWWWWW.W..WW...W......W.......W.WWWWWWW");
	level.add("W.......W.....W.W..WW..W.W.....W.......W");
	level.add("W.W.WWWWW.WWWWW....WW....WWWW..WWWWW.W.W");
	level.add("W.............W..WWWWWWW.W...........W.W");
	level.add("W....W.....W........S.......W...W......W");
	level.add("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	level.add("........................................");
	world.fromStringArray(level);
	ArrayList<Position> hunterPositions = new ArrayList<Position>();
	hunterPositions.add(new Position(1,1));
	hunterPositions.add(new Position(38,1));
	hunterPositions.add(new Position(6,6));
	hunterPositions.add(new Position(1,1));
        world.setHunterPositions(new ArrayList(hunterPositions.subList(0,world.getNumberOfHunters())));
    }







}
