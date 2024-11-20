import java.util.ArrayList;

public class CollisionBot extends Robot implements Runnable {
    private WorldOfRobot world;
    private boolean collisionDetected;
    private boolean running;
    private int speed;
    private int direction;  // 0 = right, 1 = down, 2 = left, 3 = up

    /**
     * Dorian GIRARD
     */
    
    // Constructor
    public CollisionBot(String name, int x, int y, WorldOfRobot world) {
        super(name, x, y, world);
        this.world = world;
        this.collisionDetected = false;
        this.direction = 0;  // Start by moving to the right
        this.speed = 1; // Initial speed
        this.running = true;
        
        world.addRobot(this);  // Adding this robot to the world
        setColourBody("BLUE");  // Setting default color
        super.showRobot();
        
        // Lancer le thread pour que le robot bouge automatiquement
        Thread t = new Thread(this);
        t.start();  // Démarre l'exécution du robot dans un thread séparé
    }

    // Move method that checks for collisions and boundary limits
    @Override
    public void move() {
        int currentX = getX();
        int currentY = getY();
        int newX = currentX;
        int newY = currentY;

        // Determine the next position based on current direction
        switch (direction) {
            case 0: newX = currentX + 1; break;  // Moving right
            case 1: newY = currentY + 1; break;  // Moving down
            case 2: newX = currentX - 1; break;  // Moving left
            case 3: newY = currentY - 1; break;  // Moving up
        }

        // Check for collision with another robot or world boundaries
        if (detectCollision() || !canItDisplay(newX, newY)) {
            // Change direction on collision or boundary hit
            changeDirection();
        } else {
            // Move to the new position if valid
            if (canItMove(newX, newY)) {
                setPosition(newX, newY);
                showRobot();
            }
        }
    }

    // Method to detect if another robot is nearby
    private boolean detectCollision() {
        ArrayList<Robot> robots = world.getRobots();
        for (Robot r : robots) {
            if (r != this && Math.abs(r.getX() - getX()) <= 1 && Math.abs(r.getY() - getY()) <= 1) {
                collisionDetected = true;
                return true;
            }
        }
        collisionDetected = false;
        return false;
    }

    // Change direction by 90 degrees (right turn)
    private void changeDirection() {
        direction = (direction + 1) % 4;  // Right turn (90 degrees)
    }

    // Getters and setters for collision status if needed
    public boolean isCollisionDetected() {
        return collisionDetected;
    }

    public void setCollisionDetected(boolean collisionDetected) {
        this.collisionDetected = collisionDetected;
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


