package cz.muni.fi.pv260.tron.game;

import cz.muni.fi.pv260.tron.game.model.Player;

import java.awt.*;

public class TronGameView {

    private TronGameCore gameCore;

    private Dimension bounds;

    public TronGameView(Dimension bounds, TronGameCore gameCore) {
        this.bounds = bounds;
        this.gameCore = gameCore;
    }

    public void drawGameField(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, bounds.width, bounds.height);
        for (Player player : gameCore.getPlayers()) {
            for (int x = 0; x < player.getPlacesVisited().size(); x++) {
                g.setColor(player.getColor());
                g.fillRect(player.getPlacesVisited().get(x).getX(),
                           player.getPlacesVisited().get(x).getY(), 10, 10);
            }
        }
    }

    public void draw(Graphics2D graphicsContext) {
        this.drawGameField(graphicsContext);
    }
}
