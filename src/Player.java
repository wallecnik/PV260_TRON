import java.util.ArrayList;

/**
 * Created by Wallecnik on 16.03.16.
 */
public class Player {

    final int centrex;
    final int centrey;
    final int currentDirection;
    final ArrayList<Integer> pathx1 = new ArrayList<>();
    final ArrayList<Integer> pathy1 = new ArrayList<>();

    public Player(int centrex, int centrey, int currentDirection) {
        this.centrex = centrex;
        this.centrey = centrey;
        this.currentDirection = currentDirection;
    }
}
