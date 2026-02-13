package inf112.skeleton.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.skeleton.model.factories.*;
import inf112.skeleton.model.generation.RoomTemplate;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.view.GameView;

/**
 * Example GameController showing complete integration of the grid system.
 * This demonstrates how Room, RoomFactory, and WorldRenderer work together.
 */
public class GameController implements ApplicationListener {
    private GameView gameView;
    private Room currentRoom;

    private float playerX = 160;
    private float playerY = 160;
    private float playerSpeed = 200; // pixels per second

    @Override
    public void create() {
        gameView = new GameView();
        gameView.create(1280, 768);

        currentRoom = createRoom();

        gameView.updateCamera(playerX, playerY);
    }

    private Room createRoom() {

        String[] pattern = {
                "####################",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#...####...####....#",
                "#..................#",
                "#..................#",
                "#....##########....#",
                "#..................#",
                "#..................#",
                "#....##########....#",
                "#..................#",
                "####################"
        };

        RoomTemplate template = new RoomTemplate(pattern);
        RoomFactory factory = new TemplateRoomFactory(template);
        return factory.createRoom();
    }

    @Override
    public void render() {
        // should use InputHandler in real game
        handleInput();

        gameView.updateCamera(playerX, playerY);

        gameView.render(currentRoom);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    /**
     * This should be in the Player class. 
     */
    private void handleInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        int tileSize = gameView.getTileSize();

        float newX = playerX;
        float newY = playerY;

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            newY += playerSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            newY -= playerSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            newX -= playerSpeed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            newX += playerSpeed * deltaTime;
        }

        if (!currentRoom.isBlocked(newX, newY, tileSize)) {
            playerX = newX;
            playerY = newY;
        } else {
            if (!currentRoom.isBlocked(newX, playerY, tileSize)) {
                playerX = newX;
            }
            if (!currentRoom.isBlocked(playerX, newY, tileSize)) {
                playerY = newY;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        gameView.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        gameView.dispose();
    }
}