package inf112.dungeoncrawler.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PauseOverlay {

    public enum MenuOption {
        RESUME, MAIN_MENU, EXIT
    }

    private final Game game;
    private final Runnable onResume;

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont titleFont;
    private BitmapFont menuFont;
    private GlyphLayout layout;

    private final MenuOption[] options = MenuOption.values();
    private int currentIndex = 0;

    public PauseOverlay(Game game, Runnable onResume) {
        this.game      = game;
        this.onResume  = onResume;
    }

    public void init() {
        batch  = new SpriteBatch();
        shapes = new ShapeRenderer();
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
    }

    public void render() {
        handleInput();

        float W = Gdx.graphics.getWidth();
        float H = Gdx.graphics.getHeight();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(0f, 0f, 0f, 0.55f);
        shapes.rect(0, 0, W, H);
        shapes.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();

        layout.setText(titleFont, "PAUSED");
        titleFont.draw(batch, layout, (W - layout.width) / 2f, H * 0.80f);

        float startY = H * 0.55f;
        for (int i = 0; i < options.length; i++) {
            String text = (i == currentIndex ? "> " : "  ") + options[i].name().replace("_", " ");
            menuFont.setColor(i == currentIndex ? Color.valueOf("F4BB44") : Color.WHITE);
            layout.setText(menuFont, text);
            menuFont.draw(batch, layout, (W - layout.width) / 2f, startY - i * 90);
        }

        batch.end();
    }

    void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            currentIndex = (currentIndex + 1) % options.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            currentIndex = (currentIndex - 1 + options.length) % options.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (options[currentIndex]) {
                case RESUME:
                    onResume.run();
                    break;
                case MAIN_MENU:
                    game.setScreen(new TitleScreen(game));
                    break;
                case EXIT:
                    Gdx.app.exit();
                    break;
            }
        }
    }

    public void dispose() {
        batch.dispose();
        shapes.dispose();
        titleFont.dispose();
        menuFont.dispose();
    }
}