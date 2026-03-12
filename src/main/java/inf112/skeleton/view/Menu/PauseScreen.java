package inf112.skeleton.view.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseScreen implements Screen {

    private Game game;
    private Screen gameScreen;
    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout layout;

    public enum MenuOption {
        RESUME, MAIN_MENU, EXIT
    }

    private MenuOption[] options = MenuOption.values();
    private int currentIndex = 0;

    public PauseScreen(Game game, Screen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        layout = new GlyphLayout();

        font = new BitmapFont();
        font.getData().setScale(1.5f);
        font.getRegion().getTexture().setFilter(
                com.badlogic.gdx.graphics.Texture.TextureFilter.Linear,
                com.badlogic.gdx.graphics.Texture.TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {
        handleInput();

        ScreenUtils.clear(0, 0, 0, 0.7f);

        batch.begin();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        String title = "PAUSED";
        font.setColor(Color.WHITE);
        layout.setText(font, title);
        float titleX = (screenWidth - layout.width) / 2;
        float titleY = screenHeight - 200;
        font.draw(batch, layout, titleX, titleY);

        float startY = screenHeight / 2 + options.length * 25;
        for (int i = 0; i < options.length; i++) {
            String text = (i == currentIndex ? "> " : "  ") + options[i].name().replace("_", " ");

            if (i == currentIndex) {
                font.setColor(Color.YELLOW);
            } else {
                font.setColor(Color.WHITE);
            }

            layout.setText(font, text);
            float x = (screenWidth - layout.width) / 2;
            float y = startY - i * 50;

            font.draw(batch, layout, x, y);
        }

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            currentIndex = (currentIndex + 1) % options.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            currentIndex = (currentIndex - 1 + options.length) % options.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(gameScreen);
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            handleSelection(options[currentIndex]);
        }
    }

    public void handleSelection(MenuOption option) {
        switch (option) {
            case RESUME:
                game.setScreen(gameScreen);
                break;
            case MAIN_MENU:
                gameScreen.dispose();
                game.setScreen(new TitleScreen(game));
                break;
            case EXIT:
                Gdx.app.exit();
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
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
        font.dispose();
    }
}