import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Wallecnik on 16.03.16.
 */
public class Player {

    private final static int SPEED = 5;

    private Point position;
    private Direction currentDirection;
    private Color color;
    private Controls controls;
    private final List<Point> placesVisited = new ArrayList<>();

    public Player(Point origin,
                  Direction currentDirection,
                  Color color,
                  Controls controls) {
        this.position = origin;
        this.currentDirection = currentDirection;
        this.color = color;
        this.controls = controls;
        this.placesVisited.add(this.position);
    }

    public void makeStep() {
        switch(currentDirection){
            case DOWN:
                this.position = this.position.toDown(SPEED);
                break;
            case RIGHT:
                this.position = this.position.toRight(SPEED);
                break;
            case UP:
                this.position = this.position.toUp(SPEED);
                break;
            case LEFT:
                this.position = this.position.toLeft(SPEED);
                break;
        }

        this.placesVisited.add(this.position);
    }

    public void sendKeyEvent(int keyCode) {
        final Direction direction = this.controls.direction(keyCode);
        if (direction != null) {
            this.changeDirection(direction);
        }
    }

    public void changeDirection(Direction desiredDirection) {
        if (this.currentDirection.isDirectionPossible(desiredDirection)) {
            this.currentDirection = desiredDirection;
        }
    }

    public boolean hasSelfCollision() {
        for (int i = 0; i < this.placesVisited.size() - 1; i++) {
            if (this.position.equals(placesVisited.get(i))) {
                return true;
            }
        }
        return false;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public List<Point> getPlacesVisited() {
        return Collections.unmodifiableList(placesVisited);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        private boolean isDirectionPossible(Direction direction) {
            switch (this) {
                case UP:
                    return direction != DOWN;
                case DOWN:
                    return direction != UP;
                case LEFT:
                    return direction != RIGHT;
                case RIGHT:
                    return direction != LEFT;
            }
            return false;
        }
    }

    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Point toUp(int speed) {
            return new Point(this.x, this.y - speed);
        }
        public Point toDown(int speed) {
            return new Point(this.x, this.y + speed);
        }
        public Point toLeft(int speed) {
            return new Point(this.x - speed, this.y);
        }
        public Point toRight(int speed) {
            return new Point(this.x + speed, this.y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;

            Point point = (Point) o;

            if (getX() != point.getX()) return false;
            return getY() == point.getY();
        }

        @Override
        public int hashCode() {
            int result = getX();
            result = 31 * result + getY();
            return result;
        }
    }

    public static class Controls {

        private final int up;
        private final int down;
        private final int left;
        private final int right;

        public Controls(int up, int down, int left, int right) {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
        }

        public Direction direction(int keyCode) {
            if (keyCode == up) {
                return Direction.UP;
            } else if (keyCode == down) {
                return Direction.DOWN;
            } else if (keyCode == left) {
                return Direction.LEFT;
            } else if (keyCode == right) {
                return Direction.RIGHT;
            }
            return null;
        }

    }

}
