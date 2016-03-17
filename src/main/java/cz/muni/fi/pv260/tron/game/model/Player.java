package cz.muni.fi.pv260.tron.game.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 422718
 */
public class Player {

    private final static int SPEED = 5;
    private final List<Point> placesVisited = new ArrayList<>();
    private Point position;
    private Direction currentDirection;
    private Color color;

    public Player(Point origin,
            Direction currentDirection,
            Color color) {
        this.position = origin;
        this.currentDirection = currentDirection;
        this.color = color;
        this.placesVisited.add(this.position);
    }

    public void makeStep() {
        switch (currentDirection) {
            case DOWN :
                this.position = this.position.toDown(SPEED);
                break;
            case RIGHT :
                this.position = this.position.toRight(SPEED);
                break;
            case UP :
                this.position = this.position.toUp(SPEED);
                break;
            case LEFT :
                this.position = this.position.toLeft(SPEED);
                break;
        }

        this.placesVisited.add(this.position);
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

    public Direction getCurrentDirection() {
        return currentDirection;
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
                case UP :
                    return direction != DOWN;
                case DOWN :
                    return direction != UP;
                case LEFT :
                    return direction != RIGHT;
                case RIGHT :
                    return direction != LEFT;
            }
            return false;
        }

        public Direction toRight() {
            switch (this) {
                case UP :
                    return RIGHT;
                case DOWN :
                    return LEFT;
                case LEFT :
                    return UP;
                case RIGHT :
                    return DOWN;
            }
            return null;
        }

        public Direction toLeft() {
            switch (this) {
                case UP :
                    return LEFT;
                case DOWN :
                    return RIGHT;
                case LEFT :
                    return DOWN;
                case RIGHT :
                    return UP;
            }
            return null;
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
            if (this == o)
                return true;
            if (!(o instanceof Point))
                return false;

            Point point = (Point) o;

            if (getX() != point.getX())
                return false;
            return getY() == point.getY();
        }

        @Override
        public int hashCode() {
            int result = getX();
            result = 31 * result + getY();
            return result;
        }
    }

}
