package inf112.skeleton.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.skeleton.model.world.GameWorld;
import inf112.skeleton.model.world.Gator;
import inf112.skeleton.model.world.ModelObject;
import inf112.skeleton.view.GameView;

public class GameController implements ApplicationListener {
    private GameView gameView = new GameView();
    private GameWorld world;
    private boolean paused = false;

    @Override
    public void create() { // Called at startup

        // MODEL: our virtual world
        world = new GameWorld(1280, 768);
        world.add(new Gator(123, 456));

        // VIEW: graphics
        gameView.create(world.width(), world.height());
        Gdx.graphics.setForegroundFPS(60);
    }

    @Override
    public void dispose() {
        // Called at shutdown

        // Graphics and sound resources aren't managed by Java's garbage collector, so
        // they must generally be disposed of manually when no longer needed. But,
        // any remaining resources are typically cleaned up automatically when the
        // application exits, so these aren't strictly necessary here.
        // (We might need to do something like this when loading a new game level in
        // a large game, for instance, or if the user switches to another application
        // temporarily (e.g., incoming phone call on a phone, or something).
        gameView.dispose();
    }

    @Override
    public void render() {
        // Called when the application should draw a new frame (many times per second).

        // VIEW: draw stuff
        gameView.render(painter -> {
            for (ModelObject obj : world) {
                // commands to draw stuff
                painter.draw(obj.x(), obj.y(), obj.width(), obj.height(), obj.appearance());
            }
        });

        // Don't handle input this way – use event handlers!
        if (Gdx.input.justTouched()) { // check for mouse click
            gameView.playSound();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // check for key press
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            world.step(Gdx.graphics.getDeltaTime(), "up");
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            world.step(Gdx.graphics.getDeltaTime(), "down");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            world.step(Gdx.graphics.getDeltaTime(), "left");
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            world.step(Gdx.graphics.getDeltaTime(), "right");
        }
    }

    @Override
    public void resize(int width, int height) {
        // Called whenever the window is resized (including with its original size at
        // startup)
        gameView.resize(width, height);

        // if appView can change the world size, we'd have to update *world* here
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }
}
