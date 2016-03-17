package cz.muni.fi.pv260.tron.game;

import cz.muni.fi.pv260.tron.game.model.Player;
import cz.muni.fi.pv260.tron.game.model.PlayerPlayground;
import cz.muni.fi.pv260.tron.sdk.Core;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Set;

public class TronGameCore extends Core {

    private PlayerPlayground playerPlayground = new PlayerPlayground();

    public void init() {
        this.createPlayers();
    }

    public void initWithPlaygroundSize(Dimension playgroundSize) {
        this.init();
        this.playerPlayground.setPlaygroundSize(playgroundSize);
    }

    private void createPlayers() {
        this.playerPlayground.add(new Player(new Player.Point(40, 40),
                                          Player.Direction.RIGHT,
                                          Color.red),
                               new PlayerPlayground.KeyControls(KeyEvent.VK_UP,
                                                             KeyEvent.VK_DOWN,
                                                             KeyEvent.VK_LEFT,
                                                             KeyEvent.VK_RIGHT));
        this.playerPlayground.add(new Player(new Player.Point(600, 440),
                                          Player.Direction.LEFT,
                                          Color.green),
                               new PlayerPlayground.KeyControls(KeyEvent.VK_W,
                                                             KeyEvent.VK_S,
                                                             KeyEvent.VK_A,
                                                             KeyEvent.VK_D));

        this.playerPlayground.add(new Player(new Player.Point(600, 120),
                                          Player.Direction.LEFT,
                                          Color.yellow),
                               new PlayerPlayground.MouseControls());
    }

    public Set<Player> getPlayers() {
        return this.playerPlayground.getPlayers();
    }

    public void performGameTick() {
        playerPlayground.movePlayers();
        if (playerPlayground.doPlayersCollide()) {
            this.delegate.gameDidFinish();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        playerPlayground.sendInputEvent(e);
    }

    public void mousePressed(MouseEvent e) {
        playerPlayground.sendInputEvent(e);
    }
}
