import java.util.Random;
import java.util.List;

public class SkyHopper extends Robot implements Runnable 
{    
    // Direction de la tortue
    private int direction;
    
    // Vitesse de la tortue
    private int speed;
    
    //variable pour générer des mouvements aléatoires après la collision
    private Random randomGen;
    
    private boolean running;

    /**
     * Lona PORTOIS-MENU
     */
    public SkyHopper(String name,int x, int y, WorldOfRobot worldR)
    {
        // Initialisation des donnes de la classe worldR
        super(name, x, y, worldR);
        // Initialisation de la direction et la vitesse du robot
        speed = 1;
        this.running = true;
        // Ajout du robot initialise dans la classe World of robot
        worldR.addRobot(this);
        // Visualisation du robot
        showRobot();
        // Faire bouger le robot "tout seul" à des intervalles réguliers
        // initialiser le générateur aléatoire
        randomGen = new Random();
        
        setColourBody("MAGENTA");  // Setting default color
        
        // Lancer le thread pour que le robot bouge automatiquement
        Thread t = new Thread(this);
        t.start();  // Démarre l'exécution du robot dans un thread séparé
    }

    //public void turn()
    //{
        //changer la direction du robot 
        //if (direction == 4) direction = 0;
    //}
        /**
     * Méthode pour détecter une collision avec un autre robot
     */
    public boolean detectCollision()
    {
         // Obtenir la liste des robots dans le monde et vérifier les collisions
        List<Robot> robots = getWorldR().getRobots(); // Assurez-vous que getWorld() et getRobots() existent
        for (Robot otherRobot : robots) {
            // Si ce n'est pas le même robot et qu'il est très proche, il y a collision
            if (!otherRobot.equals(this) 
                && Math.abs(this.getX() - otherRobot.getX()) <= 1 
                && Math.abs(this.getY() - otherRobot.getY()) <= 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Déplacer le robot vers le haut de 50 unités en cas de collision.
     */
    public void avoidCollision()
    {
        // Si une collision est détectée, déplacer le robot vers le haut de 50 unités
        if (detectCollision()) {
            int x = getX();
            int y = getY();
            setPosition(x, y - 50); // Déplacement vers le haut
            showRobot(); // Montrer la nouvelle position du robot
            randomDirection(); // Changer la direction de manière aléatoire après le déplacement
        }
    }

    /**
     * Changer la direction de manière aléatoire après une collision
     */
    public void randomDirection()
    {
        // Générer une nouvelle direction aléatoire entre 0 et 3
        direction = randomGen.nextInt(4);
    }
    
    public void move() 
    {
        //position initiale du robot
        int x = getX();
        int y = getY();
        randomDirection(); 
        // Si une collision est détectée, éviter la collision
        avoidCollision();
        // quand le robot a une vitesse non nulle, il peut bouger
        for (int s = speed; s > 0; s--){
            switch(direction) // quand le robot change de direction
            {
                case 0: // direction est à droite : peut se déplacer
                    x = x + 1;
                    this.setPosition(x, y);
                    break;
                    
                case 1: // Direction vers le haut : peut se déplacer
                    y = y - 1;
                    this.setPosition(x, y);
                    break;
                    
                case 2: // direction vers la gauche : peut se déplacer
                    x = x - 1;
                    this.setPosition(x, y);
                    break;
            
                case 3: // direction versle bas : peut se déplacer
                    y = y + 1;
                    this.setPosition(x, y);
                    break;
            }
            showRobot(); // montrer le robot à l'endroit où il arrive
        }
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


