/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflifefx;

import java.util.function.Consumer;

import javafx.animation.AnimationTimer;

/**
 * An extension of an {@link AnimationTimer} that allows the user to select their framerate.<p>
 * 
 * Also allows for a more convenient syntax to declare the main loop routine
 * 
 * @author Brendon
 *
 */
public class MainLoop extends AnimationTimer {

    private final long updateGraphicsEvery;
    private final Consumer<Long> doEveryUpdate;
    private long lastTime = IDEALFRAMERATENS;

    /**
     * @param updateEveryNS How often to run the loop.
     * @param f The main-loop body. Its parameter is the number of nanoseconds since the last update.
     */
    public MainLoop(long updateEveryNS, Consumer<Long> f) {
        this.updateGraphicsEvery = updateEveryNS;
        doEveryUpdate = f;
    }

    @Override
    public void handle(long currentTime) {

        long nanosElapsed = currentTime - lastTime;

        if (nanosElapsed < updateGraphicsEvery) {
            return;

        } else {
            lastTime = currentTime;
            doEveryUpdate.accept(nanosElapsed);

        }

    }

    public final static long NANOSPERSECOND = 1000000000;
    public final static long IDEALFRAMERATENS = (long)(1 / 60.0 * NANOSPERSECOND);

}
