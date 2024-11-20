import java.lang.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Random;

    public abstract class Robot {
    public int x;
    public int y;
    private String name;
    public static final int MIN_POSITION = 0;
    public static final int MAX_POSITION = 11;
    private CanvasRobot canvasRobot; 
    private Colour colour;
    private Canvas canvas;
    private String colourBody;
    private WorldOfRobot worldR;

    /**
     * Constructor for objects of class Robot
     */
    public Robot(String name, int x, int y, WorldOfRobot worldR) {
        this.worldR = worldR;
        if (worldR.getNumberOfRobots() < 122)
            setPosition(x,y);
        this.canvasRobot = new CanvasRobot(colourBody);
    }
    
    /**
     * Set a new name for the robot
     * @param name, robot name
     */
    public void setName(String name){
        this.name = name.trim();
    }
    
    /**
     * Return the robot's name
     * @return name
     */
    public String getName(){
        return name;
    }
    
        /**
     * Sets the position of the robot
     */
    public void setPosition(int nx, int ny) {
        boolean search = false;
        while (!search){
            if(this.canItMove(nx,ny) && this.canItDisplay(nx,ny)){
                this.x = nx;
                this.y = ny;
                search = true;
            }
            else {
                nx = (int) (Math.random()*MAX_POSITION);
                ny = (int) (Math.random()*MAX_POSITION);
            }
        }
    }    


    /**
     * Get the robot's x position
     */
    public int getX() {
        return x;
    }

    /**
     * Get the robot's y position
     */
    public int getY() {
        return y;
    }

    /**
     * Set a colour for the robot's body
     * @param colour
     */
    public void setColourBody(String colour){
        this.colourBody = colour;
        canvasRobot.setColourBody(colour);
    }
    
    /**
     * Abstract method to be implemented by subclasses to define robot movement
     */
    public abstract void move();

    /**
     * Tells if the position the robot tries to move in is already occupied
     * @param x, horizontal robot position
     * @param y, vertical robot position
     * @return true or false
     */
    public boolean canItMove(int x, int y){
        return worldR.canItMove(x, y);
    }

    /**
     * Tells if the position the robot tries to move in is included in the canvas
     * @param x, horizontal robot position
     * @param y, vertical robot position
     * return true/false, the robot's position has a value between [0;10]
     */
    public boolean canItDisplay(int x, int y){
        if ((x >= MIN_POSITION) && (x <= MAX_POSITION) && (y >= MIN_POSITION) && (y <= MAX_POSITION)){
             return true;                   
            }
        else return false;    
    }
    
    /**
     * Draw the robot using the canvas
     */
    public void showRobot(){
       this.canvasRobot.drawRobot(x, y);
    }
    
    /**
     * Get the world where the robot exists
     */
    public WorldOfRobot getWorldR() {
        return worldR;
    }
}

   

