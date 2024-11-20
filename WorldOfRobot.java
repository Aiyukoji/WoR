import java.util.ArrayList;
public class WorldOfRobot {
    // List that stores all robots in the world
    private ArrayList<Robot> worldList;
    //This attribute uses the CanvasRobot class to draw the robot on the canvas
    private CanvasRobot canvasRobot;
    //This attributes defines the x position of the robot
    private int x;
    //This attributes defines the y position of the robot
    private int y;
    //Define an instance variable with the number of robots in the world
    private int numberOfRobots;
  

    /**
     * Constructor for objects of class WorldOfRobot
     */
    public WorldOfRobot() {
        this.worldList = new ArrayList<Robot>();
    }
    
    /**
     * Returns the list of robots in the world
     */
    public ArrayList<Robot> getRobots() {
        return this.worldList;
    }

     /**
     * Get the number of robot objects in the list
     * @return size
     */
    public int getNumberOfRobots(){
        numberOfRobots = worldList.size();
        return numberOfRobots;
    }
    
    /**
     * Checks if a given position is already occupied by another robot
     */
    public boolean canItMove(int x, int y) {
        for (Robot r : this.worldList) {
            if (r.getX() == x && r.getY() == y) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Adds a robot to the list of robots in the world
     */
    public void addRobot(Robot r) {
        this.worldList.add(r);
    }
    
    public ArrayList<Robot> getList() {
        return worldList;  // Renvoie la liste des robots
    }

    /**
     * Moves all robots in the world
     */
    public void moveAll() {
        while(true) {
            for (Robot rob : worldList)
            rob.move();
                    try {
                Thread.sleep(1000); // Delay for one second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
