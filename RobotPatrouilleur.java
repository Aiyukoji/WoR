public class RobotPatrouilleur extends Robot implements Runnable {
    private WorldOfRobot world;  // Instance de WorldOfRobot
    private int direction;        // Direction actuelle du robot
    private int squareSize;       // Taille du carré actuel
    private int canvasSize;       // Taille du canvas en termes de cases (12x12)
    private boolean running;      // État de fonctionnement du robot
    private boolean shrinkMode;   // Indique si on est en train de rétrécir les carrés

    /**
     * Clara MOREL
     */
    public RobotPatrouilleur(String name, WorldOfRobot world) {
        super(name, 0, 0, world);  // Position initiale en haut à gauche (0, 0)
        this.world = world;         // Initialiser l'instance de WorldOfRobot
        this.direction = 0;
        this.canvasSize = 12;      // Taille de la grille en cases
        this.squareSize = canvasSize - 1;  // Taille initiale : bord complet (de 0 à 11)
        this.running = true;        // Robot en marche dès l'initialisation
        this.shrinkMode = false;// Commence par suivre les bords
        
        setColourBody("Red");  // Setting default color

        world.addRobot(this);
        showRobot();

        // Lancer le thread pour que le robot bouge automatiquement
        Thread t = new Thread(this);
        t.start();  // Démarre l'exécution du robot dans un thread séparé
    }

    @Override
    public void move() {
        while (running) {
            // Phase 1 : Suivre les bords du canvas
            suivreLesBords();
            
            // Retourner à 0, 0 après avoir suivi les bords
            resetPosition();
            showRobot();
            attendre();  // Petite pause pour visualiser

            // Phase 2 : Démarrer la coquille d'escargot à partir de (0, 0)
            resetPosition();  // Assurer que la coquille commence toujours à (0, 0)
            showRobot();
            shrinkMode = true;
            int steps = canvasSize - 1; // Commence par un carré de la taille du canvas

            boolean robotAttrape = false; // Flag pour vérifier si un robot a été attrapé
            while (shrinkMode && steps > 0) {
                for (int i = 0; i < 2; i++) {  // Chaque côté du carré
                    for (int s = 0; s < steps; s++) {
                        int nextX = getX();
                        int nextY = getY();
                        switch (direction) {
                            case 0: nextX++; break;  // Droite
                            case 1: nextY++; break;  // Bas
                            case 2: nextX--; break;  // Gauche
                            case 3: nextY--; break;  // Haut
                        }

                        // Vérifier si la prochaine position est occupée
                        if (!world.canItMove(nextX, nextY)) {
                            System.out.println("Robot attrapé...oh non il a fuit");
                            robotAttrape = true; // Un robot a été attrapé
                        } else {
                            avancer();  // Avancer dans la direction actuelle
                        }

                        showRobot();  // Afficher le robot à chaque étape
                        attendre();  // Pause pour ralentir le mouvement
                    }
                    tourner();  // Tourner à gauche après chaque côté
                }
                steps -= 2;  // Réduire la taille du carré
            }

            // Vérifier si aucun robot n'a été attrapé
            if (!robotAttrape) {
                System.out.println("Aucun robot attrapé...je reprends ma garde");
            }

            // Retourner à 0,0 après avoir terminé la coquille d'escargot
            resetPosition();
            showRobot();
            attendre();  // Pause avant de recommencer
        }
    }

    private void suivreLesBords() {
        int x = 0, y = 0;
        setPosition(x, y);  // Commencer en haut à gauche (0, 0)

        // Parcourir les bords du canvas

        // Haut (de 0 à canvasSize - 1 sur l'axe des x)
        while (x < canvasSize - 1) {
            setPosition(++x, y);
            showRobot();
            attendre();
        }

        // Droite (de 0 à canvasSize - 1 sur l'axe des y)
        while (y < canvasSize - 1) {
            setPosition(x, ++y);
            showRobot();
            attendre();
        }

        // Bas (de canvasSize - 1 à 0 sur l'axe des x)
        while (x > 0) {
            setPosition(--x, y);
            showRobot();
            attendre();
        }

        // Gauche (de canvasSize - 1 à 0 sur l'axe des y)
        while (y > 0) {
            setPosition(x, --y);
            showRobot();
            attendre();
        }
    }

    /**
     * Attendre pour ralentir le robot
     */
    private void attendre() {
        try {
            Thread.sleep(500);  // Pause de 500 millisecondes pour ralentir le mouvement
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void avancer() {
        int x = getX();
        int y = getY();
        switch (direction) {
            case 0: setPosition(x + 1, y); break;  // Droite
            case 1: setPosition(x, y + 1); break;  // Bas
            case 2: setPosition(x - 1, y); break;  // Gauche
            case 3: setPosition(x, y - 1); break;  // Haut
        }
    }

    private void tourner() {
        direction = (direction + 1) % 4;
    }

    private void resetPosition() {
        setPosition(0, 0);  // Retour en (0, 0)
    }

    @Override
    public void run() {
        while (running) {
            move();  // Le robot bouge automatiquement tant qu'il est en marche
        }
    }

    public void stop() {
        running = false;
    }
}

