package cz.muni.fi.pv260.tron.sdk;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class ScreenManager {

    private static final DisplayMode modes[] =
    {
     new DisplayMode(1680, 1050, 32, 0),
     new DisplayMode(800, 600, 32, 0),
     new DisplayMode(800, 600, 24, 0),
     new DisplayMode(800, 600, 16, 0),
     new DisplayMode(640, 480, 32, 0),
     new DisplayMode(640, 480, 24, 0),
     new DisplayMode(640, 480, 16, 0),
    };

    private GraphicsDevice vc;

    public ScreenManager() {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = e.getDefaultScreenDevice();
    }

    public void setFullscreen() {
        DisplayMode dm = this.findFirstCompatibaleMode(modes);
        this.setDisplayMode(dm);
    }

    public DisplayMode[] getCompatibleDisplayModes() {
        return vc.getDisplayModes();
    }

    public DisplayMode findFirstCompatibaleMode(DisplayMode[] modes) {

        DisplayMode goodModes[] = vc.getDisplayModes();
        for (int x = 0; x < modes.length; x++) {
            for (int y = 0; y < goodModes.length; y++) {
                if (displayModesMatch(modes[x], goodModes[y])) {
                    return modes[x];
                }
            }
        }
        return null;
    }

    public DisplayMode getCurrentDM() {
        return vc.getDisplayMode();
    }

    public boolean displayModesMatch(DisplayMode m1, DisplayMode m2) {
        if (m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight()) {
            return false;
        }
        if (m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth()) {
            return false;
        }
        if (m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate()) {
            return false;
        }
        return true;
    }

    public void setDisplayMode(DisplayMode dm) {
        JFrame f = new JFrame();
        f.setUndecorated(true);
        f.setIgnoreRepaint(true);
        f.setResizable(false);
        vc.setFullScreenWindow(f);

        if (dm != null && vc.isDisplayChangeSupported()) {
            try {
                vc.setDisplayMode(dm);
            } catch (Exception ex) {
            }
            f.createBufferStrategy(2);
        }
    }

    public Graphics2D getGraphics() {
        Window w = vc.getFullScreenWindow();
        if (w != null) {
            BufferStrategy bs = w.getBufferStrategy();
            return (Graphics2D) bs.getDrawGraphics();
        }
        else {
            return null;
        }
    }

    public void update() {
        Window w = vc.getFullScreenWindow();
        if (w != null) {
            BufferStrategy bs = w.getBufferStrategy();
            if (!bs.contentsLost()) {
                bs.show();
            }
        }
    }

    public Window getFullScreenWindow() {
        return vc.getFullScreenWindow();
    }

    public int getWidth() {
        Window w = vc.getFullScreenWindow();
        if (w != null) {
            return w.getWidth();
        } else {
            return 0;
        }
    }

    public int getHeight() {
        Window w = vc.getFullScreenWindow();
        if (w != null) {
            return w.getHeight();
        } else {
            return 0;
        }
    }

    public void restoreScreen() {
        Window w = vc.getFullScreenWindow();
        if (w != null) {
            w.dispose();
        }
        vc.setFullScreenWindow(null);
    }

    public BufferedImage createCompatibaleimage(int w, int h, int t) {
        Window win = vc.getFullScreenWindow();
        if (win != null) {
            GraphicsConfiguration gc = win.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, t);
        } else {
            return null;
        }
    }
}
