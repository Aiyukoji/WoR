public class RobotLeft extends Robot implements Runnable {
    private int direction;
    private int speed;
    private boolean running; 

    /**
     * Anaïs GIRARD
     */
    public RobotLeft(String name, int x, int y, WorldOfRobot worldR) {
        super(name, x, y, worldR);
        this.direction = 2; // Initial direction
        this.speed = 1; // Initial speed
        worldR.addRobot(this);
        super.showRobot();
        this.running = true;
        
        setColourBody("PURPLE");  // Setting default color
        
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
        x -= 1; // Move left
        setPosition(x, y);
        
        // Check for collisions with other robots
        if (checkCollision()) {
            jump();
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
     * Makes the robot "jump" when it collides with another robot
     */
    public void jump() {
        System.out.println("Collision detected, the robot jumps!");
        setPosition(getX(), getY() + 10); // Simulate the jump by changing the Y position
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


