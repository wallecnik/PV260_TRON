import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Window;
import java.awt.image.BufferedImage;

public class WindowController {
	private ScreenManager screenManager;
	
	public WindowController(){
		this.screenManager = new ScreenManager();
	}
	
	protected void finalize() throws Throwable {
		screenManager.restoreScreen();
	}
	
	public void makeWindowAndShow(){
		screenManager.setFullscreen();
		this.configureWindow( this.applicationWindow() );
	}
	
	private void configureWindow(final Window w){
		w.setFont(new Font("Arial",Font.PLAIN,20));
		w.setBackground(Color.WHITE);
		w.setForeground(Color.RED);
		w.setCursor(w.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),"null")); 
	}
	
	public Window applicationWindow(){
		return screenManager.getFullScreenWindow();
	}
	
	public Dimension windowBounds(){
		return new Dimension(screenManager.getWidth(), screenManager.getHeight());
	}
	
	public Graphics2D graphicsContext(){
		return this.screenManager.getGraphics();
	}
	
	public void updateGraphicsContext(){
		screenManager.update();
	}
}
