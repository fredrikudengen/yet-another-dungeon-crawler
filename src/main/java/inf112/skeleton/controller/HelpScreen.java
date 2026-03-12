package inf112.skeleton.controller;

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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import inf112.skeleton.view.Menu.TitleScreen;

/**
 * HelpScreen — Controls & Keybinds reference for the dungeon crawler.
 *
 * Usage:
 * game.setScreen(new HelpScreen(game));
 *
 * Replace every occurrence of "YourGame" with your actual Game subclass name.
 */
public class HelpScreen implements Screen {

    // ── palette ───────────────────────────────────────────────────────────────
    private static final Color BG_COLOR = new Color(0.04f, 0.03f, 0.06f, 1f);
    private static final Color PANEL_COLOR = new Color(0.08f, 0.06f, 0.12f, 0.95f);
    private static final Color TITLE_COLOR = new Color(1.00f, 0.82f, 0.25f, 1f); // gold
    private static final Color KEY_BG = new Color(0.15f, 0.10f, 0.22f, 1f);
    private static final Color KEY_BORDER = new Color(0.60f, 0.35f, 0.80f, 1f);
    private static final Color TEXT_MAIN = new Color(0.90f, 0.85f, 1.00f, 1f);
    private static final Color TEXT_DIM = new Color(0.50f, 0.40f, 0.60f, 1f);
    private static final Color DIVIDER = new Color(0.30f, 0.18f, 0.42f, 1f);

    // ── keybind table {key label, action description} ───────────────────────
    private static final String[][] BINDS = {
            { "W / UP", "Move North" },
            { "S / DOWN", "Move South" },
            { "A / LEFT", "Move West" },
            { "D / RIGHT", "Move East" },
            { "ESC", "Pause / Back" },
    };

    // ── layout constants (tweak to fit your resolution) ───────────────────────
    private static final float PANEL_PAD_X = 80f; // horizontal margin to panel
    private static final float PANEL_PAD_Y = 60f; // vertical margin to panel
    private static final float ROW_HEIGHT = 44f; // height of each keybind row
    private static final float KEY_COL_W = 220f; // width of the key-label column
    private static final float HEADER_H = 90f; // space reserved for the title
    private static final float FOOTER_H = 50f; // space reserved for the hint

    // ── fields ────────────────────────────────────────────────────────────────
    private final Game game; // ← swap type with your Game class
    private final SpriteBatch batch;
    private final ShapeRenderer shapes;
    private final OrthographicCamera camera;

    /**
     * Use your project's pixel/bitmap font; these defaults use the built-in font.
     */
    private final BitmapFont titleFont;
    private final BitmapFont bodyFont;
    private final BitmapFont hintFont;

    private final GlyphLayout layout = new GlyphLayout();

    private float elapsedTime = 0f;

    // ── constructor ───────────────────────────────────────────────────────────

    /**
     * @param game Your main Game instance (same object passed everywhere else).
     */
    public HelpScreen(Game game) {
        this.game = game;

        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // ── fonts ──────────────────────────────────────────────────────────────
        // Replace these with your AssetManager / BitmapFont loader, e.g.:
        // titleFont = game.assets.get("fonts/pixel_large.fnt", BitmapFont.class);
        titleFont = new BitmapFont();
        titleFont.getData().setScale(2.6f);
        titleFont.setColor(TITLE_COLOR);

        bodyFont = new BitmapFont();
        bodyFont.getData().setScale(1.5f);
        bodyFont.setColor(TEXT_MAIN);

        hintFont = new BitmapFont();
        hintFont.getData().setScale(1.2f);
        hintFont.setColor(TEXT_DIM);
    }

    // ── Screen lifecycle ──────────────────────────────────────────────────────

    @Override
    public void show() {
        elapsedTime = 0f;
    }

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        handleInput();

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();

        // Clear
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapes.setProjectionMatrix(camera.combined);

        drawPanel(W, H);
        drawTitle(W, H);
        drawDivider(W, H);
        drawBinds(W, H);
        drawFooter(W, H);
    }

    // ── input ─────────────────────────────────────────────────────────────────

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)
                || Gdx.input.isKeyJustPressed(Input.Keys.H)
                || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setScreen(new TitleScreen(game));
        }
    }

    // ── drawing helpers ───────────────────────────────────────────────────────

    /** Dark frosted-glass panel with a purple border. */
    private void drawPanel(float W, float H) {
        float x = PANEL_PAD_X;
        float y = PANEL_PAD_Y;
        float w = W - PANEL_PAD_X * 2f;
        float h = H - PANEL_PAD_Y * 2f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(PANEL_COLOR);
        shapes.rect(x, y, w, h);
        shapes.end();
    }

    /** Pulsing gold title at the top of the panel. */
    private void drawTitle(float W, float H) {
        // Gentle alpha pulse using a sine wave
        float pulse = 0.85f + 0.15f * (float) Math.sin(elapsedTime * 3.0);
        titleFont.setColor(TITLE_COLOR.r, TITLE_COLOR.g, TITLE_COLOR.b, pulse);

        String title = "CONTROLS";
        layout.setText(titleFont, title);
        float tx = (W - layout.width) / 2f;
        float ty = H - PANEL_PAD_Y - 22f;

        batch.begin();
        titleFont.draw(batch, title, tx, ty);
        batch.end();

        // Reset alpha
        titleFont.setColor(TITLE_COLOR);
    }

    /** Horizontal rule between title and bind list. */
    private void drawDivider(float W, float H) {
        float lx = PANEL_PAD_X + 20f;
        float rx = W - PANEL_PAD_X - 20f;
        float ly = H - PANEL_PAD_Y - HEADER_H;

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(DIVIDER);
        shapes.rectLine(lx, ly, rx, ly, 1f);
        shapes.end();
    }

    /** Draws each keybind row: [KEY BOX] description */
    private void drawBinds(float W, float H) {
        float listTop = H - PANEL_PAD_Y - HEADER_H - 10f;
        float listLeft = PANEL_PAD_X + 30f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        for (int i = 0; i < BINDS.length; i++) {
            String key = BINDS[i][0];
            String action = BINDS[i][1];

            float rowY = listTop - i * ROW_HEIGHT;

            // Alternate row tint for readability
            if (i % 2 == 0) {
                shapes.begin(ShapeRenderer.ShapeType.Filled);
                shapes.setColor(0.10f, 0.07f, 0.15f, 0.40f);
                shapes.rect(listLeft - 8f, rowY - ROW_HEIGHT + 6f,
                        W - PANEL_PAD_X * 2f - 44f, ROW_HEIGHT - 2f);
                shapes.end();
            }

            // Key badge background
            shapes.begin(ShapeRenderer.ShapeType.Filled);
            shapes.setColor(KEY_BG);
            shapes.rect(listLeft, rowY - ROW_HEIGHT + 10f, KEY_COL_W - 10f, ROW_HEIGHT - 12f);
            shapes.end();

            // Key badge border
            shapes.begin(ShapeRenderer.ShapeType.Line);
            shapes.setColor(KEY_BORDER);
            shapes.rect(listLeft, rowY - ROW_HEIGHT + 10f, KEY_COL_W - 10f, ROW_HEIGHT - 12f);
            shapes.end();

            batch.begin();

            // Key label (centered in badge)
            bodyFont.setColor(TEXT_MAIN);
            layout.setText(bodyFont, key);
            float kx = listLeft + (KEY_COL_W - 10f - layout.width) / 2f;
            float ky = rowY - 6f;
            bodyFont.draw(batch, key, kx, ky);

            // Action description
            bodyFont.setColor(TEXT_DIM);
            float ax = listLeft + KEY_COL_W + 14f;
            bodyFont.draw(batch, action, ax, ky);

            batch.end();
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /** "Press ESC to return" hint at the bottom. */
    private void drawFooter(float W, float H) {
        String hint = "Press  ESC  to return";
        layout.setText(hintFont, hint);
        float fx = (W - layout.width) / 2f;
        float fy = PANEL_PAD_Y + FOOTER_H;

        batch.begin();
        hintFont.draw(batch, hint, fx, fy);
        batch.end();
    }

    // ── remaining Screen methods ───────────────────────────────────────────────

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
