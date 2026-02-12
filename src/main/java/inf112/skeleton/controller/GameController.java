package inf112.skeleton.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.skeleton.model.entities.Player;
import inf112.skeleton.model.factories.RoomFactory;
import inf112.skeleton.model.factories.TemplateRoomFactory;
import inf112.skeleton.model.generation.RoomTemplate;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.view.GameView;

public class GameController implements ApplicationListener {
    private GameView gameView;
    private Room currentRoom;
    private Player player;

    @Override
    public void create() {
        gameView = new GameView();
        player = new Player(200, 200);
        gameView.create(1280, 768);

        // Input via InputProcessor
        Gdx.input.setInputProcessor(new InputHandler(player));

        currentRoom = createExampleRoom();
        currentRoom.addEntity(player);

        gameView.updateCamera(player.getX(), player.getY());
    }

    private Room createExampleRoom() {
        String[] pattern = {
                "####################",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "#..................#",
                "####################"
        };

        RoomTemplate template = new RoomTemplate(pattern);
        RoomFactory factory = new TemplateRoomFactory(template);
        return factory.createRoom();
    }

    @Override
    public void render() {
        handleInputAndMovePlayer();

        gameView.updateCamera(player.getX(), player.getY());
        gameView.render(currentRoom);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void handleInputAndMovePlayer() {
        float dt = Gdx.graphics.getDeltaTime();
        int tileSize = gameView.getTileSize();

        float x = player.getX();
        float y = player.getY();

        float newX = x;
        float newY = y;

        float speed = player.getSpeed(); // pixels/second

        if (player.isMovingUp())    newY += speed * dt;
        if (player.isMovingDown())  newY -= speed * dt;
        if (player.isMovingLeft())  newX -= speed * dt;
        if (player.isMovingRight()) newX += speed * dt;

        // Kollisjon mot rommet
        if (!currentRoom.isBlocked(newX, newY, tileSize)) {
            player.setPosition(newX, newY);
            return;
        }

        // “Slide” langs vegger
        if (!currentRoom.isBlocked(newX, y, tileSize)) {
            player.setPosition(newX, y);
        }
        if (!currentRoom.isBlocked(player.getX(), newY, tileSize)) {
            player.setPosition(player.getX(), newY);
        }
    }

    @Override
    public void resize(int width, int height) {
        gameView.resize(width, height);
    }

    @Override public void pause() {}

    @Override public void resume() {}

    @Override
    public void dispose() {
        gameView.dispose();
    }
}
