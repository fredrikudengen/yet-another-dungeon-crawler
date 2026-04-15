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

public class HelpScreen implements Screen {

    private static final Color GOLD = Color.valueOf("F4BB44");
    private static final Color DIVIDER = new Color(0.45f, 0.28f, 0.10f, 1f);
    private static final Color KEY_BG = new Color(0.30f, 0.18f, 0.08f, 1f);
    private static final Color KEY_BORDER = new Color(0.65f, 0.40f, 0.10f, 1f);
    private static final Color TEXT_DIM = new Color(0.70f, 0.60f, 0.50f, 1f);

    private static final String[][] BINDS = {
            { "W / Up Arrow", "Move / Attack Up" },
            { "S / Down Arrow", "Move / Attack Down" },
            { "A / Left Arrow", "Move / Attack Left" },
            { "D / Right Arrow", "Move / Attack Right" },
            { "ESC", "Pause / Back" },
    };

    private static final float PANEL_PAD_X = 80f;
    private static final float PANEL_PAD_Y = 60f;
    private static final float ROW_HEIGHT = 60f;
    private static final float KEY_COL_W = 500f;
    private static final float HEADER_H = 90f;
    private static final float FOOTER_H = 50f;

    private final Game game;
    private final SoundService sound;

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private OrthographicCamera camera;
    private BitmapFont titleFont;
    private BitmapFont bodyFont;
    private BitmapFont hintFont;
    private GlyphLayout layout;

    public HelpScreen(Game game, SoundService sound) {
        this.game = Objects.requireNonNull(game, "game must not be null");
        this.sound = Objects.requireNonNull(sound, "sound must not be null");
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        layout = new GlyphLayout();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 75;
        titleFont = generator.generateFont(parameter);
        titleFont.setColor(GOLD);

        parameter.size = 30;
        bodyFont = generator.generateFont(parameter);
        bodyFont.setColor(Color.WHITE);

        parameter.size = 24;
        hintFont = generator.generateFont(parameter);
        hintFont.setColor(TEXT_DIM);

        generator.dispose();
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

        drawDivider(W, H);
        drawTitle(W, H);
        drawBinds(W, H);
        drawFooter(W);
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)
                || Gdx.input.isKeyJustPressed(Input.Keys.H)
                || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setScreen(new TitleScreen(game, sound));
        }
    }

    private void drawTitle(float W, float H) {
        String title = "CONTROLS";
        layout.setText(titleFont, title);
        batch.begin();
        titleFont.draw(batch, title, (W - layout.width) / 2f, H - PANEL_PAD_Y - 22f);
        batch.end();
    }

    private void drawDivider(float W, float H) {
        float lx = PANEL_PAD_X + 20f;
        float rx = W - PANEL_PAD_X - 20f;
        float ly = H - PANEL_PAD_Y - HEADER_H;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(DIVIDER);
        shapes.rectLine(lx, ly, rx, ly, 1f);
        shapes.end();
    }

    private void drawBinds(float W, float H) {
        float listTop = H - PANEL_PAD_Y - HEADER_H - 10f;
        float listLeft = PANEL_PAD_X + 30f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        for (int i = 0; i < BINDS.length; i++) {
            String key = BINDS[i][0];
            String action = BINDS[i][1];
            float rowY = listTop - i * ROW_HEIGHT;

            drawRowBackground(i, listLeft, rowY, W);
            drawKeyBadge(listLeft, rowY);
            drawRowText(key, action, listLeft, rowY);
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawRowBackground(int index, float listLeft, float rowY, float W) {
        if (index % 2 == 0) {
            shapes.begin(ShapeRenderer.ShapeType.Filled);
            shapes.setColor(0.25f, 0.15f, 0.07f, 0.40f);
            shapes.rect(listLeft - 8f, rowY - ROW_HEIGHT + 6f,
                    W - PANEL_PAD_X * 2f - 44f, ROW_HEIGHT - 2f);
            shapes.end();
        }
    }

    private void drawKeyBadge(float listLeft, float rowY) {
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(KEY_BG);
        shapes.rect(listLeft, rowY - ROW_HEIGHT + 10f, KEY_COL_W - 10f, ROW_HEIGHT - 12f);
        shapes.end();

        shapes.begin(ShapeRenderer.ShapeType.Line);
        shapes.setColor(KEY_BORDER);
        shapes.rect(listLeft, rowY - ROW_HEIGHT + 10f, KEY_COL_W - 10f, ROW_HEIGHT - 12f);
        shapes.end();
    }

    private void drawRowText(String key, String action, float listLeft, float rowY) {
        batch.begin();
        bodyFont.setColor(GOLD);
        layout.setText(bodyFont, key);
        float kx = listLeft + (KEY_COL_W - 10f - layout.width) / 2f;
        bodyFont.draw(batch, key, kx, rowY - 15f);

        bodyFont.setColor(Color.WHITE);
        bodyFont.draw(batch, action, listLeft + KEY_COL_W + 14f, rowY - 15f);
        batch.end();
    }

    private void drawFooter(float W) {
        String hint = "Press ESC to return";
        layout.setText(hintFont, hint);
        batch.begin();
        hintFont.draw(batch, hint, (W - layout.width) / 2f, PANEL_PAD_Y + FOOTER_H);
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
        bodyFont.dispose();
        hintFont.dispose();
    }
}