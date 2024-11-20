import java.util.Random;

public class Fire_Knight extends Robot implements Runnable {
    private WorldOfRobot world;
    private Random random;
    private boolean running;
    private int speed;
    private int direction;  // Pour se déplacer en triangle
    private int step;  // Étape du triangle

    /**
    Kael LISSARAGUE
    */
    
    public Fire_Knight(String name, int x, int y, WorldOfRobot worldR) {
        super(name, x, y, worldR);
        this.world = worldR;
        this.random = new Random();
        this.running = true;
        this.speed = 1; // Vitesse initiale
        this.direction = 0;  // Initialisation de la direction (0=diag bas-droite, 1=haut-droite, 2=retour)
        this.step = 0;  // Étape du triangle (0=vers le bas, 1=vers la droite, 2=retour)

        // Ajouter le robot au monde et l'afficher
        worldR.addRobot(this);
        setColourBody("BLACK");  // Couleur initiale
        super.showRobot();

        // Lancer le thread pour le mouvement automatique
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * Déplacement du robot en forme de triangle
     */
    @Override
    public void move() {
        int currentX = getX();
        int currentY = getY();
        int newX = currentX;
        int newY = currentY;

        // Changer la direction pour simuler un mouvement en triangle
        switch (step) {
            case 0: // Diagonale vers le bas-droite
                newX = currentX + 1;
                newY = currentY + 1;
                break;
            case 1: // Mouvement vers la droite
                newX = currentX + 1;
                break;
            case 2: // Retour vers la gauche-haut (diagonale inverse)
                newX = currentX - 1;
                newY = currentY - 1;
                break;
        }

        // Mettre à jour l'étape pour créer un cycle triangulaire
        step = (step + 1) % 3;  // Cycle entre 0, 1, 2

        // Déplacer le robot
        setPosition(newX, newY);

        // Vérifier si une collision est détectée
        if (checkCollision()) {
            teleportRandomly();  // Si collision, téléportation
        }

        super.showRobot();
    }

    /**
     * Vérifie s'il y a une collision avec un autre robot
     */
    public boolean checkCollision() {
        for (Robot otherRobot : world.getList()) {
            if (!otherRobot.equals(this)) {
                if (this.getX() == otherRobot.getX() && this.getY() == otherRobot.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Téléporte le robot à une position aléatoire
     */
    public void teleportRandomly() {
        System.out.println("Collision détectée, le robot se téléporte !");
        int newX = random.nextInt(MAX_POSITION + 1);  // Génère une nouvelle position aléatoire
        int newY = random.nextInt(MAX_POSITION + 1);
        setPosition(newX, newY);
        System.out.println("Le robot s'est téléporté à la position : (" + newX + ", " + newY + ")");
    }

    @Override
    public void run() {
        while (running) {
            move();  // Le robot bouge automatiquement tant qu'il est en marche

            try {
                // Introduire un délai pour simuler la vitesse normale
                Thread.sleep(1000 / speed);  // Vitesse contrôlée par l'intervalle de déplacement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Arrête le mouvement du robot
     */
    public void stop() {
        running = false;
    }
}
