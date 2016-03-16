import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Core {
	private boolean isRunning;
	protected ScreenManager screenManager;
	
	public void stop(){
		isRunning = false;
	}
	
	public void run(){
		try{
			init();
			runGameLoop();
		}finally{
			screenManager.restoreScreen();
		}
	}
	
	public void init(){
		screenManager = new ScreenManager();
		screenManager.setFullscreen();
		this.configureWindow( screenManager.getFullScreenWindow() );
	}
	
	private void configureWindow(final Window w){
		w.setFont(new Font("Arial",Font.PLAIN,20));
		w.setBackground(Color.WHITE);
		w.setForeground(Color.RED);
		w.setCursor(w.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),"null")); 
	}
	
	private int tickTime(){
		return 20;
	}
	
	public void runGameLoop(){
		isRunning = true;
		
		while (isRunning){
			Graphics2D g = screenManager.getGraphics();
			draw(g);
			g.dispose();
			screenManager.update();
			
			try{
				Thread.sleep( tickTime() );
			} catch(Exception ex){
				
			}
		}
	}
	public abstract void draw(Graphics2D g);
}
