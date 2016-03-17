package cz.muni.fi.pv260.tron.game;

import cz.muni.fi.pv260.tron.sdk.Core;
import cz.muni.fi.pv260.tron.sdk.CoreInterface;
import cz.muni.fi.pv260.tron.sdk.WindowController;

import java.awt.*;

public class TronGameController extends WindowController implements CoreInterface {

    private TronGameCore gameCore;

    private TronGameView gameView;

    public static void main(String[] args) {
        TronGameController game = new TronGameController();
        game.run();
    }

    public void run() {
        this.makeWindowAndShow();

        this.gameCore = new TronGameCore();
        this.gameCore.initWithPlaygroundSize(this.windowBounds());
        this.gameCore.delegate = this;
        this.setupInputEventListenersToCore(this.gameCore);

        this.gameView = new TronGameView(this.windowBounds(), gameCore);
        this.gameCore.run();
    }

    public void setupInputEventListenersToCore(Core core) {
        Window w = this.applicationWindow();
        w.addKeyListener(core);
        w.addMouseListener(core);
        w.addMouseMotionListener(core);
    }

    public void gameDidTick() {
        this.gameView.draw(this.graphicsContext());
        this.updateGraphicsContext();
    }

    public void gameDidFinish() {
        System.exit(1);
    }
}
