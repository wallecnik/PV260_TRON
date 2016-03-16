import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TronGameCore extends Core implements KeyListener, MouseListener,
                                      MouseMotionListener {

    private List<Player> players = new ArrayList<>();

    public static void main(String[] args) {
        new TronGameCore().run();
    }

    public void init() {
        super.init();

        this.createPlayers();

        Window w = screenManager.getFullScreenWindow();
        w.addKeyListener(this);
        w.addMouseListener(this);
        w.addMouseMotionListener(this);
    }

    private void createPlayers() {
        this.players.add(new Player(new Player.Point(40, 40),
                                    Player.Direction.RIGHT,
                                    Color.green,
                                    new Player.Controls(KeyEvent.VK_UP,
                                                        KeyEvent.VK_DOWN,
                                                        KeyEvent.VK_LEFT,
                                                        KeyEvent.VK_RIGHT)));
        this.players.add(new Player(new Player.Point(600, 440),
                                    Player.Direction.LEFT,
                                    Color.red,
                                    new Player.Controls(KeyEvent.VK_W,
                                                        KeyEvent.VK_S,
                                                        KeyEvent.VK_A,
                                                        KeyEvent.VK_D)));
    }

    public void draw(Graphics2D g) {
        movePlayers();
        placeThemBack();
        if (hasCollisions()) {
            System.exit(1);
        }
        render(g);
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
                player.setPosition(new Player.Point(screenManager.getWidth(),
                                                    position.getY()));
            }
            if (position.getX() > screenManager.getWidth()) {
                player.setPosition(new Player.Point(0,
                                                    position.getY()));
            }
            if (position.getY() <= 0) {
                player.setPosition(new Player.Point(position.getX(),
                                                    screenManager.getHeight()));

            }
            if (position.getY() > screenManager.getHeight()) {
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

    private void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenManager.getWidth(), screenManager.getHeight());
        for (Player player : this.players) {
            for (int x = 0; x < player.getPlacesVisited().size(); x++) {
                g.setColor(player.getColor());
                g.fillRect(player.getPlacesVisited().get(x).getX(),
                           player.getPlacesVisited().get(x).getY(), 10, 10);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        for (Player player : this.players) {
            player.sendKeyEvent(e.getKeyCode());
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent arg0) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
}
