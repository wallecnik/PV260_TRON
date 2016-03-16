import java.awt.*;
import java.awt.image.BufferedImage;

public class TronGameController implements CoreInterface {
	private TronGameCore gameCore;
	private TronGameView gameView;
	private ScreenManager screenManager;

	public static void main(String[] args) {
		TronGameController game = new TronGameController();
		game.run();
    }	
	
	public void run(){
		this.screenManager = new ScreenManager();
		this.makeWindowAndShow();
		
		this.gameCore = new TronGameCore( );
		this.gameCore.delegate = this;
        this.gameCore.initWithPlaygroundSize( new Dimension(screenManager.getWidth(), screenManager.getHeight()) );
        this.gameView = new TronGameView( gameCore );
        
    	Window w = this.applicationWindow();
        w.addKeyListener(gameCore);
        w.addMouseListener(gameCore);
        w.addMouseMotionListener(gameCore);
    
        this.gameCore.run();
	}
	
	private void makeWindowAndShow(){
		screenManager.setFullscreen();
		this.configureWindow( this.applicationWindow() );
	}
	
	private void configureWindow(final Window w){
		w.setFont(new Font("Arial",Font.PLAIN,20));
		w.setBackground(Color.WHITE);
		w.setForeground(Color.RED);
		w.setCursor(w.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),"null")); 
	}
	
	private Window applicationWindow(){
		return screenManager.getFullScreenWindow();
	}
	
	public void gameDidTick(){
		this.gameView.draw(this.screenManager.getGraphics());
		screenManager.update();
	}

	public void gameDidFinish(){
		screenManager.restoreScreen();
		System.exit(1);
	}
}


