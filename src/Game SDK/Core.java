
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class Core implements KeyListener, MouseListener,
MouseMotionListener  {
	private boolean isRunning;
	public CoreInterface delegate;
	
	public void run(){
		try{
			runGameLoop();
		} finally {
			this.delegate.gameDidFinish();
		}
	}
	
	public void stop(){
		isRunning = false;
	}
	
	private int tickTime(){
		return 20;
	}
	
	public void runGameLoop(){
		isRunning = true;
		
		while (isRunning){
			this.performGameTick();
			this.delegate.gameDidTick();
			try{
				Thread.sleep( tickTime() );
			} catch(Exception ex){
				this.delegate.gameDidFinish();
			}
		}
	}
	
	public void performGameTick(){
		
	}
	
	public void keyPressed(KeyEvent e) {
		
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
