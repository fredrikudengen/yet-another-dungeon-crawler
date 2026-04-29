package inf112.dungeoncrawler.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import inf112.dungeoncrawler.sound.SoundService;

import java.util.Objects;

public class GameOverScreen implements Screen {

    private static final Color GOLD = Color.valueOf("F4BB44");
    private static final Color DIVIDER = new Color(0.55f, 0.34f, 0.12f, 1f);
    private static final Color TEXT_DIM = new Color(0.70f, 0.60f, 0.50f, 1f);

    private final Game game;
    private final SoundService sound;
    private final int killedEnemies;
    private final int roomsCleared; // Add this line

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private OrthographicCamera camera;
    private BitmapFont titleFont;
    private BitmapFont statsFont;
    private BitmapFont bodyFont;
    private BitmapFont hintFont;
    private GlyphLayout layout;

    public GameOverScreen(Game game, SoundService sound, int killedEnemies, int roomsCleared) {
        this.game = Objects.requireNonNull(game, "game must not be null");
        this.sound = Objects.requireNonNull(sound, "sound must not be null");
        this.killedEnemies = killedEnemies;
        this.roomsCleared = roomsCleared;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        layout = new GlyphLayout();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();

        p.size = 110;
        titleFont = gen.generateFont(p);
        titleFont.setColor(GOLD);
        p.size = 55;
        statsFont = gen.generateFont(p);
        statsFont.setColor(GOLD);
        p.size = 30;
        bodyFont = gen.generateFont(p);
        bodyFont.setColor(TEXT_DIM);
        p.size = 24;
        hintFont = gen.generateFont(p);
        hintFont.setColor(TEXT_DIM);

        gen.dispose();
    }

    @Override
    public void render(float delta) {
        handleInput();
        ScreenUtils.clear(0.2f, 0.13f, 0.07f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapes.setProjectionMatrix(camera.combined);

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();

        drawTitle(W, H);
        drawDivider(W, H * 0.70f);
        drawStats(W, H);
        drawDivider(W, H * 0.44f);
        drawFooter(W);
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new TitleScreen(game, sound));
        }
    }

    private void drawTitle(float W, float H) {
        String title = "GAME OVER";
        layout.setText(titleFont, title);
        batch.begin();
        titleFont.draw(batch, title, (W - layout.width) / 2f, H - 100f);
        batch.end();
    }

    private void drawDivider(float W, float y) {
        float margin = W * 0.15f;
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(DIVIDER);
        shapes.rect(margin, y - 1.5f, W - margin * 2f, 3f);
        shapes.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawStats(float W, float H) {
        float centerX = W / 2f;
        float centerY = H * 0.55f;

        float spacing = 50f;

        String enemiesText = "ENEMIES SLAIN: " + killedEnemies;
        String roomsText = "ROOMS CLEARED: " + roomsCleared;

        batch.begin();

        // Enemies
        layout.setText(statsFont, enemiesText);
        float enemiesX = centerX - layout.width / 2f;
        float enemiesY = centerY + spacing;
        statsFont.draw(batch, enemiesText, enemiesX, enemiesY);

        // Rooms
        layout.setText(statsFont, roomsText);
        float roomsX = centerX - layout.width / 2f;
        float roomsY = centerY - spacing;
        statsFont.draw(batch, roomsText, roomsX, roomsY);

        batch.end();
    }

    private void drawFooter(float W) {
        String hint = "Press  ESC  to return to title";
        layout.setText(hintFont, hint);
        batch.begin();
        hintFont.draw(batch, hint, (W - layout.width) / 2f, 100f);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        titleFont.dispose();
        statsFont.dispose();
        bodyFont.dispose();
        hintFont.dispose();
    }
}