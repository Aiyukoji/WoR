public class RobotFuite extends Robot implements Runnable {
    private WorldOfRobot world;
    private int direction;
    private int speed;
    private boolean running; 

    /**
     * Lola RENAULT
     */
    public RobotFuite(String name, int x, int y, WorldOfRobot world) {
        super(name, 1, 1, world);
        this.world = world; 
        this.direction = 3; // Initial direction
        this.speed = 1; // Initial speed
        this.running = true;
        
        setColourBody("Yellow");  // Setting default color
        
        world.addRobot(this);
        super.showRobot();
        
        // Lancer le thread pour que le robot bouge automatiquement
        Thread t = new Thread(this);
        t.start();  // Démarre l'exécution du robot dans un thread séparé
    }

    /**
     * Moves the robot based on the direction and checks for collisions
     */
    @Override
    public void move() {
        int x = getX();
        int y = getY();
        y += 1; // Move bas
        setPosition(x, y);
        
        // Check for collisions with other robots
        if (checkCollision()) {
            fuite();
        }
        super.showRobot();
    }

    /**
     * Checks if this robot collides with another robot in the world
     */
    public boolean checkCollision() {
        for (Robot otherRobot : getWorldR().getRobots()) {
            if (!otherRobot.equals(this)) {
                if (this.getX() == otherRobot.getX() && this.getY() == otherRobot.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Makes the robot "fuite" when it collides with another robot
     */
    public void fuite() {
        System.out.println("Collision detectée, le robot fuit!");
        // Reverse the direction
        if (direction == 0){ // If the direction is up
            y -= 1;
        }
        else {
            y += 1;
        }
        
        // Double the speed
        speed *= 2;
        
    }
    
    public void run() {
        while (running) {
            move();  // Le robot bouge automatiquement tant qu'il est en marche
            
            try {
                // Introduce a delay between moves to simulate normal speed
                Thread.sleep(1000 / speed);  // Speed determines how fast it moves (1 second divided by speed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }
}


