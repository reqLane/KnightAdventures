package visualEffects;

import main.GamePanel;

import java.awt.*;

public class VisualManager {

    GamePanel gp;
    LightArea caveLightArea;
    LightArea dungeonLightArea;

    public VisualManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {
        caveLightArea = new LightArea(gp);
        dungeonLightArea = new LightArea(gp);
        caveLightArea.addLightCircle(4 * gp.squareSize + 24, 4 * gp.squareSize, 190);
        caveLightArea.addLightCircle(4 * gp.squareSize + 24, 12 * gp.squareSize + 40, 190);
        caveLightArea.addLightCircle(20 * gp.squareSize + 24, 4 * gp.squareSize, 190);
        caveLightArea.addLightCircle(20 * gp.squareSize + 24, 12 * gp.squareSize + 40, 190);
        caveLightArea.addLightCircle(12 * gp.squareSize + 24, 12 * gp.squareSize + 40, 190);
        caveLightArea.addLightCircle(12 * gp.squareSize + 24, 4 * gp.squareSize, 190);

    }

    public void draw(Graphics2D g2d) {

        if(gp.roomManager.currentRoom.name.equals("cave")) {
            caveLightArea.draw(g2d);
        }
    }
}