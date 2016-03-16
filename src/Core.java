import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Core {

	private static final DisplayMode modes[] = 
		{
		new DisplayMode(1680,1050,32,0),
		new DisplayMode(800,600,32,0),
		new DisplayMode(800,600,24,0),
		new DisplayMode(800,600,16,0),
		new DisplayMode(640,480,32,0),
		new DisplayMode(640,480,24,0),
		new DisplayMode(640,480,16,0),
		};
	private boolean isRunning;
	protected ScreenManager screenManager;
	
	public void stop(){
		isRunning = false;
	}
	
	public void run(){
		try{
			init();
			gameLoop();
		}finally{
			screenManager.restoreScreen();
		}
	}
	
	public void init(){
		screenManager = new ScreenManager();
		DisplayMode dm = screenManager.findFirstCompatibaleMode(modes);
		screenManager.setFullScreen(dm);
		Window w = screenManager.getFullScreenWindow();
		this.configureWindow(w);
		isRunning = true;
	}
	
	private void configureWindow(final Window w){
		w.setFont(new Font("Arial",Font.PLAIN,20));
		w.setBackground(Color.WHITE);
		w.setForeground(Color.RED);
		w.setCursor(w.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),"null")); 
	}
	
	public void gameLoop(){
		while (isRunning){
			Graphics2D g = screenManager.getGraphics();
			draw(g);
			g.dispose();
			screenManager.update();
			
			try{
				Thread.sleep(20);
			}catch(Exception ex){}
		}
	}
	public abstract void draw(Graphics2D g);
}
