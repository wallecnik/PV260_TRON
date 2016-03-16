import java.awt.Color;
import java.awt.Graphics2D;

public class TronGameView{
	private TronGameCore gameCore;

	public TronGameView( TronGameCore gameCore ){
		this.gameCore = gameCore;
	}
	
	public void drawGameField(Graphics2D g){
		g.setColor(Color.BLACK);
        g.fillRect(0, 0, gameCore.getPlaygroundSize().width, gameCore.getPlaygroundSize().height);
        for (Player player : gameCore.getPlayers() ) {
            for (int x = 0; x < player.getPlacesVisited().size(); x++) {
                g.setColor(player.getColor());
                g.fillRect(player.getPlacesVisited().get(x).getX(),
                           player.getPlacesVisited().get(x).getY(), 10, 10);
            }
        }
	}

	public void draw( Graphics2D drawingContext ){
		this.drawGameField(drawingContext);
		drawingContext.dispose();
	}
}
