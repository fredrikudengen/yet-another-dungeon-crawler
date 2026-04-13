package inf112.dungeoncrawler.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import inf112.dungeoncrawler.model.factories.GameWorldBuilder;
import inf112.dungeoncrawler.model.factories.RoomFactory;
import inf112.dungeoncrawler.model.generation.RoomLoader;
import inf112.dungeoncrawler.model.world.GameWorld;
import inf112.dungeoncrawler.sound.SoundService;

import java.util.Objects;

public class TitleScreen implements Screen {

    private static final String ROOM_PATH = "rooms/room3.txt";

    public enum MenuOption {
        START, HELP, EXIT
    }

    private final Game game;
    private final SoundService sound;
    private final MenuOption[] options = MenuOption.values();

    private SpriteBatch batch;
    private GlyphLayout layout;
    private BitmapFont titleFont;
    private BitmapFont menuFont;

    private Texture[] flameFrames;
    private Texture[] playerFrames;
    private Texture floor;

    private float flameTimer  = 0;
    private int   flameFrame  = 0;
    private float playerTimer = 0;
    private int   playerFrame = 0;
    private int   currentIndex = 0;

    public TitleScreen(Game game, SoundService sound) {
        this.game  = Objects.requireNonNull(game,  "game must not be null");
        this.sound = Objects.requireNonNull(sound, "sound must not be null");
    }

    @Override
    public void show() {
        batch  = new SpriteBatch();
        layout = new GlyphLayout();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 75;
        titleFont = generator.generateFont(parameter);
        titleFont.setColor(Color.valueOf("F4BB44"));

        parameter.size = 30;
        menuFont = generator.generateFont(parameter);
        menuFont.setColor(Color.WHITE);

        generator.dispose();

        flameFrames = new Texture[]{
                new Texture("titlescreen/flamme1.png"),
                new Texture("titlescreen/flamme2.png")
        };
        playerFrames = new Texture[]{
                new Texture("player/withoutWeapon/Walk/Player_walk_left1.png"),
                new Texture("player/withoutWeapon/Walk/Player_walk_left2.png")
        };
        floor = new Texture("titlescreen/menyfloor.png");
    }

    @Override
    public void render(float delta) {
        handleInput();
        ScreenUtils.clear(0.2f, 0.13f, 0.07f, 1);

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();

        batch.begin();
        drawTitle(W, H);
        drawMenu(W, H);
        drawFloor(W, H);
        drawAnimations(delta, W, H);
        batch.end();
    }

    private void drawTitle(float W, float H) {
        String line1 = "Yet Another";
        String line2 = "Dungeon-Crawler";

        layout.setText(titleFont, line1);
        titleFont.draw(batch, layout, (W - layout.width) / 2f, H * 0.85f);

        layout.setText(titleFont, line2);
        titleFont.draw(batch, layout, (W - layout.width) / 2f, H * 0.85f - 80);
    }

    private void drawMenu(float W, float H) {
        float startY = H * 0.55f;
        for (int i = 0; i < options.length; i++) {
            String text = (i == currentIndex ? "> " : "  ") + options[i].name();
            menuFont.setColor(i == currentIndex ? Color.valueOf("F4BB44") : Color.WHITE);
            layout.setText(menuFont, text);
            menuFont.draw(batch, layout, W * 0.60f, startY - i * 90);
        }
    }

    private void drawFloor(float W, float H) {
        float tileW  = 300;
        float tileH  = 260;
        float floorY = H * 0.08f - 75;
        for (float x = 0; x < W; x += tileW) {
            batch.draw(floor, x, floorY, tileW, tileH);
        }
    }

    private void drawAnimations(float delta, float W, float H) {
        flameFrame  = advanceFrame(delta, flameFrames,  flameFrame,  true);
        playerFrame = advanceFrame(delta, playerFrames, playerFrame, false);

        float torchY = H * 0.40f;
        batch.draw(flameFrames[flameFrame],   W * 0.78f, torchY,      280, 280);
        batch.draw(flameFrames[flameFrame],   W * 0.05f, torchY,      280, 280);
        batch.draw(playerFrames[playerFrame], W * 0.15f, H * 0.12f,  350, 350);
    }

    private int advanceFrame(float delta, Texture[] frames, int currentFrame, boolean isFlame) {
        if (isFlame) {
            flameTimer += delta;
            if (flameTimer > 0.25f) { flameTimer = 0; return (currentFrame + 1) % frames.length; }
        } else {
            playerTimer += delta;
            if (playerTimer > 0.25f) { playerTimer = 0; return (currentFrame + 1) % frames.length; }
        }
        return currentFrame;
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            currentIndex = (currentIndex + 1) % options.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            currentIndex = (currentIndex - 1 + options.length) % options.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (options[currentIndex]) {
                case START -> game.setScreen(createGameScreen());
                case HELP  -> game.setScreen(new HelpScreen(game, sound));
                case EXIT  -> Gdx.app.exit();
            }
        }
    }

    private GameScreen createGameScreen() {
        RoomFactory roomFactory = new RoomLoader(ROOM_PATH);
        GameWorld world = new GameWorldBuilder(roomFactory.createRoom(), sound).build();
        return new GameScreen(game, world, sound);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

    @Override
    public void dispose() {
        batch.dispose();
        titleFont.dispose();
        menuFont.dispose();
        floor.dispose();
        for (Texture t : flameFrames)  t.dispose();
        for (Texture t : playerFrames) t.dispose();
    }
}