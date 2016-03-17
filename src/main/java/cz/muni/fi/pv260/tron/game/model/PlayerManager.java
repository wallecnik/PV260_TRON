package cz.muni.fi.pv260.tron.game.model;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 422718
 */
public class PlayerManager {

    private Dimension playgroundSize;

    private final Map<Player, Controls> controls = new HashMap<>();

    public void add(Player player, Controls controls) {
        if (controls instanceof MouseControls) {
            ((MouseControls) controls).setDirection(player.getCurrentDirection());
        }
        this.controls.put(player, controls);
    }

    public Set<Player> getPlayers() {
        return this.controls.keySet();
    }

    public void setPlaygroundSize(Dimension playgroundSize) {
        this.playgroundSize = playgroundSize;
    }

    public void sendInputEvent(InputEvent event) {
        for (Map.Entry<Player, Controls> controls : this.controls.entrySet()) {
            final Player.Direction direction = controls.getValue().direction(event);
            if (direction != null) {
                controls.getKey().changeDirection(direction);
            }
        }
    }

    public void movePlayers() {
        for (Player player : this.getPlayers()) {
            player.makeStep();
            this.returnPlayersOutOfBounds(player);
        }
    }

    private void returnPlayersOutOfBounds(Player player) {
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

    public static abstract class Controls {
        public abstract Player.Direction direction(InputEvent event);
    }

    public static class KeyControls extends Controls {

        private final int up;

        private final int down;

        private final int left;

        private final int right;

        public KeyControls(int up, int down, int left, int right) {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }

        public Player.Direction direction(InputEvent event) {
            if (!(event instanceof KeyEvent)) {
                return null;
            }
            final int keyCode = ((KeyEvent) event).getKeyCode();
            if (keyCode == up) {
                return Player.Direction.UP;
            } else if (keyCode == down) {
                return Player.Direction.DOWN;
            } else if (keyCode == left) {
                return Player.Direction.LEFT;
            } else if (keyCode == right) {
                return Player.Direction.RIGHT;
            }
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof KeyControls)) return false;

            KeyControls controls = (KeyControls) o;

            if (up != controls.up) return false;
            if (down != controls.down) return false;
            if (left != controls.left) return false;
            return right == controls.right;

        }

        @Override
        public int hashCode() {
            int result = up;
            result = 31 * result + down;
            result = 31 * result + left;
            result = 31 * result + right;
            return result;
        }
    }

    public static class MouseControls extends Controls {

        private Player.Direction currentDirection;

        private void setDirection(Player.Direction direction) {
            this.currentDirection = direction;
        }

        public Player.Direction direction(InputEvent event) {
            if (!(event instanceof MouseEvent)) {
                return null;
            }
            final MouseEvent mouseEvent = (MouseEvent) event;
            if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                this.currentDirection = currentDirection.toLeft();
                return this.currentDirection;
            }
            if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                this.currentDirection = currentDirection.toRight();
                return this.currentDirection;
            }
            return null;
        }
    }

}
