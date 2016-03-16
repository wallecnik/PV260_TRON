import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TronGameCore extends Core{

    private List<Player> players = new ArrayList<>();
    private Dimension playgroundSize;

    public void init () {
    	this.createPlayers();
    	this.playgroundSize = new Dimension( );
    }
    
    public void initWithPlaygroundSize( Dimension playgroundSize ){
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
    }
    
    public Dimension getPlaygroundSize(){
    	return this.playgroundSize;
    }
    
    public List<Player> getPlayers(){
    	return this.players;
    }

    public void performGameTick(){
    	movePlayers();
        placeThemBack();
        if (hasCollisions()) {
            this.delegate.gameDidFinish();
        }
	}

    private void movePlayers() {
        for (Player player : this.players) {
            player.move();
        }
    }

    private void placeThemBack() {
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

    private boolean hasCollisions() {
        for (Player p1 : players) {
            for (Player p2 : players) {
                if (p1 == p2) {
                    if (p1.hasSelfCollision()) {
                        return true;
                    }
                } else {
                    if (hasAnySame(p1.getPosition(), p2.getPlacesVisited())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hasAnySame(Player.Point current, List<Player.Point> allVisited) {
        for (Player.Point visited : allVisited) {
            if (current.equals(visited)) {
                return true;
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
