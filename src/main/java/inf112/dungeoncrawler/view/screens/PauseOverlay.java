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
import inf112.dungeoncrawler.sound.SoundService;

import java.util.Objects;

public class PauseOverlay {

    private static final Color GOLD = Color.valueOf("F4BB44");

    public enum MenuOption {
        RESUME, MAIN_MENU, EXIT
    }

    private final Game game;
    private final SoundService sound;
    private final Runnable onResume;
    private final MenuOption[] options = MenuOption.values();

    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont titleFont;
    private BitmapFont menuFont;
    private GlyphLayout layout;

    private int currentIndex = 0;

    public PauseOverlay(Game game, SoundService sound, Runnable onResume) {
        this.game = Objects.requireNonNull(game, "game must not be null");
        this.sound = Objects.requireNonNull(sound, "sound must not be null");
        this.onResume = Objects.requireNonNull(onResume, "onResume must not be null");
    }

    public void init() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        layout = new GlyphLayout();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 75;
        titleFont = generator.generateFont(parameter);
        titleFont.setColor(GOLD);

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
        shapes.setColor(0.2f, 0.13f, 0.07f, 1);
        shapes.rect(0, 0, W, H);
        shapes.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        layout.setText(titleFont, "PAUSED");
        titleFont.draw(batch, layout, (W - layout.width) / 2f, H * 0.80f);

        float startY = H * 0.55f;
        float totalHeight = (options.length - 1) * 90; // height of all menu items
        float menuStartY = startY + (totalHeight / 2f); // center the block

        for (int i = 0; i < options.length; i++) {
            String text = options[i].name().replace("_", " ");
            String prefix = (i == currentIndex ? "> " : "  ");
            menuFont.setColor(i == currentIndex ? GOLD : Color.WHITE);
            layout.setText(menuFont, prefix + text);
            float prefixWidth = menuFont.getSpaceXadvance() * prefix.length(); // Get prefix width
            menuFont.draw(batch, layout, (W - layout.width) / 2f - prefixWidth + 20, menuStartY - i * 90);
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
                case RESUME -> {
                    currentIndex = 0;
                    onResume.run();
                }
                case MAIN_MENU -> game.setScreen(new TitleScreen(game, sound));
                case EXIT -> Gdx.app.exit();
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