package inf112.skeleton.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

import inf112.skeleton.model.combat.Attack;
import inf112.skeleton.model.entities.Enemy;
import inf112.skeleton.model.entities.Entity;
import inf112.skeleton.model.entities.Player;
import inf112.skeleton.model.factories.RoomFactory;
import inf112.skeleton.model.generation.RoomTemplate;
import inf112.skeleton.model.world.Room;
import inf112.skeleton.view.GameView;
import inf112.skeleton.view.Menu.PauseScreen;

public class GameScreen implements Screen {
    private Game game;
    private GameView gameView;
    private Room currentRoom;
    private Player player;
    private PlayerInputHandler playerInputHandler;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        gameView = new GameView();
        player = new Player(200, 200);
        gameView.create(1280, 768);

        // Input via InputProcessor
        playerInputHandler = new PlayerInputHandler(player);
        Gdx.input.setInputProcessor(playerInputHandler);

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
        RoomFactory factory = new RoomTemplate(pattern);
        return factory.createRoom();
    }

    @Override
    public void render(float delta) {
        handleInputAndMovePlayer();

        gameView.updateCamera(player.getX(), player.getY());
        gameView.render(currentRoom);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game, this));
        }
    }

    // Needs testing
    private void handleAttack() {
        if (player.isAttacking() && player.getCurrentAttack().canUse()) {
            Attack attack = player.getCurrentAttack();

            float attackX = player.getX();
            float attackY = player.getY();
            float attackWidth = attack.getRange();
            float attackHeight = player.getHeight();

            // Hitbox logic
            switch (player.getFacingDirection()) {
                case RIGHT:
                    attackX = player.getX() + player.getWidth();
                    attackWidth = attack.getRange();
                    break;
                case LEFT:
                    attackX = player.getX() - player.getWidth();
                    attackWidth = attack.getRange();
                    break;
                case UP:
                    attackY = player.getY() + player.getHeight();
                    attackHeight = attack.getRange();
                    attackWidth = player.getWidth();
                    break;
                case DOWN:
                    attackY = player.getY() - player.getHeight();
                    attackHeight = attack.getRange();
                    attackWidth = player.getWidth();
                    break;
            }

            // Check collision
            for (Entity entity : currentRoom.getEntities()) {
                if (entity instanceof Enemy) {
                    if (attackX < entity.getX() + entity.getWidth() &&
                        attackX + attackWidth > entity.getX() &&
                        attackY < entity.getY() + entity.getHeight() &&
                        attackY + attackHeight > entity.getY()) {

                        applyDamageToEnemy((Enemy) entity, attack);
                    }
                }
            }
        }
    }

    // Needs testing
    private void applyDamageToEnemy(Enemy enemy, Attack attack) {
        System.out.println("Hit enemy for " + attack.getDamage() + " damage!");

        // TODO: Implement actual damage and enemy death

    }

    private void handleInputAndMovePlayer() {
        float dt = Gdx.graphics.getDeltaTime();
        int tileSize = gameView.getTileSize();

        float x = player.getX();
        float y = player.getY();

        float newX = x;
        float newY = y;

        float speed = player.getSpeed();

        if (player.isMovingUp())    newY += speed * dt;
        if (player.isMovingDown())  newY -= speed * dt;
        if (player.isMovingLeft())  newX -= speed * dt;
        if (player.isMovingRight()) newX += speed * dt;

        if (!currentRoom.isBlocked(newX, newY, player.getWidth(), player.getHeight(), tileSize)) {
            player.setPosition(newX, newY);
            return;
        }

        if (!currentRoom.isBlocked(newX, y, player.getWidth(), player.getHeight(), tileSize)) {
            player.setPosition(newX, y);
        }
        if (!currentRoom.isBlocked(x, newY, player.getWidth(), player.getHeight(), tileSize)) {
            player.setPosition(x, newY);
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
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        gameView.dispose();
    }
}