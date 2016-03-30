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
        this.position = this.position.move(this.currentDirection);
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
        UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

        private final int x;

        private final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        private boolean isDirectionPossible(Direction direction) {
            return (this.getX() == 0 || direction.getX() == 0) &&
                   (this.getY() == 0 || direction.getY() == 0);
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

        public Point move(Direction direction) {
            return new Point(this.x + direction.getX() * SPEED,
                             this.y + direction.getY() * SPEED);
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
