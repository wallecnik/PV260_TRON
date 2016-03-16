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
    private final List<Point> placesVisited = new ArrayList<>();

    public Player(Point origin, Direction currentDirection) {
        this.position = origin;
        this.currentDirection = currentDirection;

        this.placesVisited.add(this.position);
    }

    public void move() {
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

    public void changeDirection(Direction wantedDirection) {
        if (this.currentDirection.isPossible(wantedDirection)) {
            this.currentDirection = wantedDirection;
        }
    }

    public List<Point> getPlacesVisited() {
        return Collections.unmodifiableList(placesVisited);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        private boolean isPossible(Direction direction) {
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

        public Point toUp(int speed) {
            return new Point(this.x, this.y + speed);
        }
        public Point toDown(int speed) {
            return new Point(this.x, this.y - speed);
        }
        public Point toLeft(int speed) {
            return new Point(this.x - speed, this.y);
        }
        public Point toRight(int speed) {
            return new Point(this.x + speed, this.y);
        }
    }
}
