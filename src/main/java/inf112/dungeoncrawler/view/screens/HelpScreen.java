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

public class HelpScreen implements Screen {

    private static final Color GOLD     = Color.valueOf("F4BB44");
    private static final Color DIVIDER  = new Color(0.45f, 0.28f, 0.10f, 1f); // warm brown
    private static final Color KEY_BG   = new Color(0.30f, 0.18f, 0.08f, 1f);
    private static final Color KEY_BORDER = new Color(0.65f, 0.40f, 0.10f, 1f);
    private static final Color TEXT_DIM = new Color(0.70f, 0.60f, 0.50f, 1f);

    private static final String[][] BINDS = {
            { "W", "Move North" },
            { "S", "Move South" },
            { "A", "Move West"  },
            { "D", "Move East"  },
            { "E", "Pause / Back" },
            { "SPACE", "Attack" }
    };

    private static final float PANEL_PAD_X = 80f;
    private static final float PANEL_PAD_Y = 60f;
    private static final float ROW_HEIGHT  = 60f;
    private static final float KEY_COL_W   = 220f;
    private static final float HEADER_H    = 90f;
    private static final float FOOTER_H    = 50f;

    private final Game game;
    private final SpriteBatch batch;
    private final ShapeRenderer shapes;
    private final OrthographicCamera camera;

    private BitmapFont titleFont;
    private BitmapFont bodyFont;
    private BitmapFont hintFont;

    private final GlyphLayout layout = new GlyphLayout();

    public HelpScreen(Game game) {
        this.game = game;

        batch  = new SpriteBatch();
        shapes = new ShapeRenderer();
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
    public void show() {}

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
        drawFooter(W, H);
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)
                || Gdx.input.isKeyJustPressed(Input.Keys.H)
                || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setScreen(new TitleScreen(game));
        }
    }

    private void drawTitle(float W, float H) {
        titleFont.setColor(GOLD);

        String title = "CONTROLS";
        layout.setText(titleFont, title);
        float tx = (W - layout.width) / 2f;
        float ty = H - PANEL_PAD_Y - 22f;

        batch.begin();
        titleFont.draw(batch, title, tx, ty);
        batch.end();

        titleFont.setColor(GOLD);
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
        float listTop  = H - PANEL_PAD_Y - HEADER_H - 10f;
        float listLeft = PANEL_PAD_X + 30f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        for (int i = 0; i < BINDS.length; i++) {
            String key    = BINDS[i][0];
            String action = BINDS[i][1];
            float rowY = listTop - i * ROW_HEIGHT;

            if (i % 2 == 0) {
                shapes.begin(ShapeRenderer.ShapeType.Filled);
                shapes.setColor(0.25f, 0.15f, 0.07f, 0.40f);
                shapes.rect(listLeft - 8f, rowY - ROW_HEIGHT + 6f,
                        W - PANEL_PAD_X * 2f - 44f, ROW_HEIGHT - 2f);
                shapes.end();
            }

            shapes.begin(ShapeRenderer.ShapeType.Filled);
            shapes.setColor(KEY_BG);
            shapes.rect(listLeft, rowY - ROW_HEIGHT + 10f, KEY_COL_W - 10f, ROW_HEIGHT - 12f);
            shapes.end();

            shapes.begin(ShapeRenderer.ShapeType.Line);
            shapes.setColor(KEY_BORDER);
            shapes.rect(listLeft, rowY - ROW_HEIGHT + 10f, KEY_COL_W - 10f, ROW_HEIGHT - 12f);
            shapes.end();

            batch.begin();

            bodyFont.setColor(GOLD);
            layout.setText(bodyFont, key);
            float kx = listLeft + (KEY_COL_W - 10f - layout.width) / 2f;
            float ky = rowY - 15f;
            bodyFont.draw(batch, key, kx, ky);

            bodyFont.setColor(Color.WHITE);
            float ax = listLeft + KEY_COL_W + 14f;
            bodyFont.draw(batch, action, ax, ky);

            batch.end();
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void drawFooter(float W, float H) {
        String hint = "Press  ESC  to return";
        layout.setText(hintFont, hint);
        float fx = (W - layout.width) / 2f;
        float fy = PANEL_PAD_Y + FOOTER_H;

        batch.begin();
        hintFont.draw(batch, hint, fx, fy);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        titleFont.dispose();
        bodyFont.dispose();
        hintFont.dispose();
    }
}