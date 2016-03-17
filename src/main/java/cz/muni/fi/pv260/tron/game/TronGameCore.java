package cz.muni.fi.pv260.tron.game;

import cz.muni.fi.pv260.tron.game.model.Player;
import cz.muni.fi.pv260.tron.sdk.Core;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TronGameCore extends Core {

    private List<Player> players = new ArrayList<>();

    private Dimension playgroundSize;

    public void init() {
        this.createPlayers();
        this.playgroundSize = new Dimension();
    }

    public void initWithPlaygroundSize(Dimension playgroundSize) {
        this.init();
        this.playgroundSize = playgroundSize;
    }

    private void createPlayers() {
        this.players.add(new Player(new Player.Point(40, 40),
                                    Player.Direction.RIGHT,
                                    Color.red,
                                    new Player.Controls(KeyEvent.VK_UP,
                                                        KeyEvent.VK_DOWN,
                                                        KeyEvent.VK_LEFT,
                                                        KeyEvent.VK_RIGHT)));
        this.players.add(new Player(new Player.Point(600, 440),
                                    Player.Direction.LEFT,
                                    Color.green,
                                    new Player.Controls(KeyEvent.VK_W,
                                                        KeyEvent.VK_S,
                                                        KeyEvent.VK_A,
                                                        KeyEvent.VK_D)));

        this.players.add(new Player(new Player.Point(600, 120),
                                    Player.Direction.LEFT,
                                    Color.yellow,
                                    new Player.Controls(KeyEvent.VK_U,
                                                        KeyEvent.VK_J,
                                                        KeyEvent.VK_H,
                                                        KeyEvent.VK_K)));
    }

    public Dimension getPlaygroundSize() {
        return this.playgroundSize;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void performGameTick() {
        movePlayers();
        returnPlayersWithinBounds();
        if (areThereCollisions()) {
            this.delegate.gameDidFinish();
        }
    }

    private void movePlayers() {
        for (Player player : this.players) {
            player.makeStep();
        }
    }

    private void returnPlayersWithinBounds() {
        for (Player player : this.players) {
            Player.Point position = player.getPosition();
            if (position.getX() <= 0) {
                player.setPosition(new Player.Point(playgroundSize.width,
                                                    position.getY()));
            }
            if (position.getX() > playgroundSize.width) {
                player.setPosition(new Player.Point(0,
                                                    position.getY()));
            }
            if (position.getY() <= 0) {
                player.setPosition(new Player.Point(position.getX(),
                                                    playgroundSize.height));

            }
            if (position.getY() > playgroundSize.height) {
                player.setPosition(new Player.Point(position.getX(),
                                                    0));
            }
        }
    }

    private boolean areThereCollisions() {
        for (Player p : players) {
            if (p.hasSelfCollision()) {
                return true;
            }
        }

        for (Player p1 : players) {
            for (Player p2 : players) {
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

    public void keyPressed(KeyEvent e) {
        for (Player player : this.players) {
            player.sendKeyEvent(e.getKeyCode());
        }
    }
}
