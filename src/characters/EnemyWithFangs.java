/**
 * @author Anatolii Andrusenko, Vladislav Marchenko, Andrii Sulimenko
 *
 * @version 1.0
 *
 * class of enemy with fangs. This type of enemy tries to get to player as close as he can
 */
package characters;

import main.GamePanel;
import objects.Coin;

import java.awt.*;


public class EnemyWithFangs extends Character{

    //DAMAGE
    public static int constDamage;

    //STAND FRAMES
    int standFrames = 0;
    int framesToCount = -gp.FPS;

    //DISTANCE TO PLAYER
    double playerDistance;
    //DELTAS
    double deltaX, deltaY;
    //IS ENEMY MOVING
    public boolean moving;
    //IS ENEMY AGGRESSIVE
    public boolean agressive;
    //TEMP MOVING
    public boolean tempMoving;
    public int tempMovingFrames = 0;
    //RECOVER
    public boolean recover;
    //DAMAGE FRAMES
    public int tempDamageFrames = 0;
    //MOVING TIME
    int movingTime;
    //STANDING TIME
    int standTime;
    //DISTANCE OF AGRO
    int distanceAgro;
    //REQUIRED RELOADING FRAMES
    public int requiredReloadingFrames = 60;

    /**
     * constructor
     * @param gp game panel
     * @param screenX x pos on screen
     * @param screenY y pos on screen
     */
    public EnemyWithFangs(GamePanel gp, double screenX, double screenY) {
        super(gp);

        this.screenX = screenX;
        this.screenY = screenY;

        setDefaultParameters();
        setEnemyImages();
    }

    /**
     * method which sets default parameters
     */
    public void setDefaultParameters() {
        speed = 2;

        // COLLISION SQUARE OF THE PLAYER
        areaOfCollision = new Rectangle(8, 15, 32, 33);
        defaultCollisionAreaX = 8;
        defaultCollisionAreaY = 15;

        maxHP = 2 * gp.difficulty;
        HP = maxHP;

        damage = gp.difficulty;
        constDamage = damage;

        name = "enemyWithFangs";
    }

    /**
     * method which sets default images of enemy
     */
    public void setEnemyImages() {
        up1 = setImage("alien_claws/alien_claws_up_1");
        up2 = setImage("alien_claws/alien_claws_up_2");
        up3 = setImage("alien_claws/alien_claws_up_3");
        down1 = setImage("alien_claws/alien_claws_down_1");
        down2 = setImage("alien_claws/alien_claws_down_2");
        down3 = setImage("alien_claws/alien_claws_down_3");
        left1 = setImage("alien_claws/alien_claws_left_1");
        left2 = setImage("alien_claws/alien_claws_left_2");
        left3 = setImage("alien_claws/alien_claws_left_3");
        right1 = setImage("alien_claws/alien_claws_right_1");
        right2 = setImage("alien_claws/alien_claws_right_2");
        right3 = setImage("alien_claws/alien_claws_right_3");
    }

    /**
     * method which updates enemy
     */
    @Override
    public void update() {

        playerDistance = Math.sqrt(Math.pow(gp.player.screenX - screenX, 2)
                + Math.pow(gp.player.screenY - gp.player.areaOfCollision.height/2.0 - (screenY - areaOfCollision.height/2.0), 2));

        if (gp.roomManager.currentRoom.name.equals("dungeon")) {
            distanceAgro = 4;
        } else distanceAgro = 10;

        if(playerDistance < gp.squareSize * distanceAgro) {
            if(!agressive) {
                framesToCount = 0;
            }
            agressive = true;
            moving = true;
        } else {
            if(agressive) {
                framesToCount = 0;
                moving = false;
                tempMovingFrames = 0;
                tempMoving = false;
            }
            agressive = false;
        }

        //NOT AGRESSIVE
        if(!agressive) {

            if(framesToCount < 0)
                framesToCount++;

            if (framesToCount >= 0) {

                if(!moving && framesToCount == 0) {
                    switch ((int) (Math.random() * 4 + 1)) {
                        case 1 -> direction = "up";
                        case 2 -> direction = "down";
                        case 3 -> direction = "left";
                        case 4 -> direction = "right";
                    }
                    moving = true;
                    movingTime = (int)(Math.random() * 4 + 2);
                    standTime = (int)(Math.random() * 2 + 6);
                }

                framesToCount++;

                if(framesToCount == gp.FPS * movingTime) {
                    moving = false;
                    imageNum = 0;
                }
                if(framesToCount == gp.FPS * standTime) {
                    framesToCount = 0;
                }
            }
        }
        //AGRESSIVE
        else {
            //Movement Math
            deltaX = gp.player.screenX - screenX;
            deltaY = gp.player.screenY - gp.player.areaOfCollision.height/2.0 - (screenY - areaOfCollision.height/2.0);

            if(deltaX == 0 && deltaY < 0) {
                deltaX = 1;
            } else if(deltaX == 0 && deltaY > 0) {
                deltaX = 1;
            } else if(deltaX < 0 && deltaY == 0) {
                deltaY = 1;
            } else if(deltaX > 0 && deltaY == 0) {
                deltaY = 1;
            }

            if(!tempMoving) {
                if(deltaX > 0 && deltaY < 0) {
                    if(Math.abs(deltaY) > deltaX) direction = "up";
                    else direction = "right";

                    if (collisionOnX) {
                        direction = "up";
//                        screenX--;
                        tempMoving = true;
                    }
                    if(collisionOnY) {
                        direction = "right";
//                        screenY++;
                        tempMoving = true;
                    }
                }
                else if(deltaX < 0 && deltaY < 0) {
                    if(deltaY < deltaX) direction = "up";
                    else direction = "left";

                    if (collisionOnX) {
                        direction = "up";
//                        screenX++;
                        tempMoving = true;
                    }
                    if(collisionOnY) {
                        direction = "left";
//                        screenY++;
                        tempMoving = true;
                    }
                }
                else if(deltaX < 0 && deltaY > 0) {
                    if(deltaY > Math.abs(deltaX)) direction = "down";
                    else direction = "left";

                    if (collisionOnX) {
                        direction = "down";
//                        screenX++;
                        tempMoving = true;
                    }
                    if(collisionOnY) {
                        direction = "left";
//                        screenY--;
                        tempMoving = true;
                    }
                }
                else if(deltaX > 0 && deltaY > 0) {
                    if(deltaY > deltaX) direction = "down";
                    else direction = "right";

                    if (collisionOnX) {
                        direction = "down";
//                        screenX--;
                        tempMoving = true;
                    }
                    if(collisionOnY) {
                        direction = "right";
//                        screenY--;
                        tempMoving = true;
                    }
                }

                if (Math.abs(deltaY) - Math.abs(deltaX) <= 5) tempMoving = true;
            }
            if(tempMoving) {
                tempMovingFrames++;
                if(tempMovingFrames == 0.4 * gp.FPS) {
                    tempMoving = false;
                    tempMovingFrames = 0;
                }
            }
        }

        collisionOnX = false;
        collisionOnY = false;

        // CHECK COLLISION OF THE MAP
        gp.colViewer.checkMapCollision(this);
        //  CHECK COLLISION OF OBJECTS
        gp.colViewer.checkObjectCollision(this, false);
        // CHECK COLLISION WITH CHARACTERS
        gp.colViewer.checkCharacterCollision(this, false);

        //HORIZONTAL MOVEMENT
        if (!collisionOnX && moving) {
            if (direction.equals("left")) {
                screenX -= speed;
            }
            if (direction.equals("right")) {
                screenX += speed;
            }
        }
        //VERTICAL MOVEMENT
        if(!collisionOnY && moving) {
            if (direction.equals("up")) {
                screenY -= speed;
            }
            if (direction.equals("down")) {
                screenY += speed;
            }
            updateImage();
        }

        //ENEMY STANDS STILL
        else {
            standFrames++;
            if(standFrames >= gp.FPS/3) {
                imageNum = 0;
            }
        }

        //ENEMY RECOVERS
        if(recover) {
            tempDamageFrames++;
            if(tempDamageFrames == 30) {
                speed = 3;
                recover = false;
                tempDamageFrames = 0;
            }
        }
    }

    /**
     * method for enemy to receive damage
     * @param damage count of damage
     */
    @Override
    public void receiveDamage(int damage) {

        recover = true;
        speed = 1;

        if(HP > 0) {
            HP -= damage;
        }

        if (HP <= 0){
            isDead = true;
            if(!gp.roomManager.currentRoom.name.equals("finalMap")) {
                gp.roomManager.currentRoom.addGameObject(new Coin(gp), screenX, screenY - gp.squareSize);
            }
        }
    }

}
