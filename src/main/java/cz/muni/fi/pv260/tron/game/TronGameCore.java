package cz.muni.fi.pv260.tron.game;

import cz.muni.fi.pv260.tron.game.model.Player;
import cz.muni.fi.pv260.tron.game.model.PlayerManager;
import cz.muni.fi.pv260.tron.sdk.Core;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TronGameCore extends Core {

    private PlayerManager playerManager = new PlayerManager();

    public void init() {
        this.createPlayers();
    }

    public void initWithPlaygroundSize(Dimension playgroundSize) {
        this.init();
        this.playerManager.setPlaygroundSize(playgroundSize);
    }

    private void createPlayers() {
        this.playerManager.add(new Player(new Player.Point(40, 40),
                                          Player.Direction.RIGHT,
                                          Color.red),
                               new PlayerManager.KeyControls(KeyEvent.VK_UP,
                                                             KeyEvent.VK_DOWN,
                                                             KeyEvent.VK_LEFT,
                                                             KeyEvent.VK_RIGHT));
        this.playerManager.add(new Player(new Player.Point(600, 440),
                                          Player.Direction.LEFT,
                                          Color.green),
                               new PlayerManager.KeyControls(KeyEvent.VK_W,
                                                             KeyEvent.VK_S,
                                                             KeyEvent.VK_A,
                                                             KeyEvent.VK_D));

        this.playerManager.add(new Player(new Player.Point(600, 120),
                                          Player.Direction.LEFT,
                                          Color.yellow),
                               new PlayerManager.MouseControls());
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(this.playerManager.getPlayers());
    }

    public void performGameTick() {
        playerManager.movePlayers();
        if (areThereCollisions()) {
            this.delegate.gameDidFinish();
        }
    }

    private boolean areThereCollisions() {
        for (Player p : this.playerManager.getPlayers()) {
            if (p.hasSelfCollision()) {
                return true;
            }
        }

        for (Player p1 : this.playerManager.getPlayers()) {
            for (Player p2 : this.playerManager.getPlayers()) {
                if (doPlayersCollide(p1, p2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Please erase this comment after reading. 
     * 
     * The issue with the last implementation was that: 
     * hasAnySame(p1.getPosition(), p2.getPlacesVisited()) was NOT equal hasAnySame(p2..(), p1..())
     * while it obviously should be commutative.
     * 
     * It worked as 'does the first player's head touch the path of the second one?', 
     * thus, if you switch p1 and p2, you will get false (head of p2 does not touch path of p1)
     * 
     * Even though it did not cause problems (since you check this for both players, and 
     * for one of them you actually will get true)
     * 
     * Btw I've renamed the methods a little, but please feel free to change it again! if needed.
     * Please also feel free to refactor the code which I've added :) 
     */
    private boolean doPlayersCollide(Player p1, Player p2) {
        if (p1 == p2)
            return false;

        List<Player.Point> firstPlayerPath = p1.getPlacesVisited();
        List<Player.Point> secondPlayerPath = p2.getPlacesVisited();

        for (Player.Point pointOne : firstPlayerPath) {
            for (Player.Point pointTwo : secondPlayerPath) {
                if (pointOne.equals(pointTwo)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        playerManager.sendInputEvent(e);
    }

    public void mousePressed(MouseEvent e) {
        playerManager.sendInputEvent(e);
    }
}
