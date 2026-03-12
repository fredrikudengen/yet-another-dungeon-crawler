package inf112.skeleton.view.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import inf112.skeleton.controller.GameScreen;
import inf112.skeleton.controller.HelpScreen;

public class TitleScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private GlyphLayout layout;

    private BitmapFont titleFont;
    private BitmapFont menuFont;

    public enum MenuOption {
        START, CONTROLS, EXIT
    }

    private MenuOption[] options = MenuOption.values();
    private int currentIndex = 0;

    public TitleScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        layout = new GlyphLayout();

        titleFont = new BitmapFont();
        titleFont.getData().setScale(2.5f);
        titleFont.setColor(Color.WHITE);
        // Apply linear filtering to reduce blurriness
        titleFont.getRegion().getTexture().setFilter(
                com.badlogic.gdx.graphics.Texture.TextureFilter.Linear,
                com.badlogic.gdx.graphics.Texture.TextureFilter.Linear);

        menuFont = new BitmapFont();
        menuFont.getData().setScale(1.5f);
        menuFont.setColor(Color.WHITE);
        // reduce font blurriness
        menuFont.getRegion().getTexture().setFilter(
                com.badlogic.gdx.graphics.Texture.TextureFilter.Linear,
                com.badlogic.gdx.graphics.Texture.TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {
        handleInput();

        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();

        float screenWidth = Gdx.graphics.getWidth();

        String gameTitle = "MY AWESOME GAME";
        layout.setText(titleFont, gameTitle);
        float titleX = (screenWidth - layout.width) / 2;
        float titleY = Gdx.graphics.getHeight() - 300;
        titleFont.draw(batch, layout, titleX, titleY);

        float startY = Gdx.graphics.getHeight() / 2 + options.length * 25;
        for (int i = 0; i < options.length; i++) {
            String text = (i == currentIndex ? "> " : "  ") + options[i].name();

            // Highlight selected option
            if (i == currentIndex) {
                menuFont.setColor(Color.YELLOW);
            } else {
                menuFont.setColor(Color.WHITE);
            }

            layout.setText(menuFont, text);
            float x = (screenWidth - layout.width) / 2;
            float y = startY - i * 50;

            menuFont.draw(batch, layout, x, y);
        }

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            currentIndex = (currentIndex + 1) % options.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            currentIndex = (currentIndex - 1 + options.length) % options.length;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (options[currentIndex]) {
                case START:
                    game.setScreen(new GameScreen(game));
                    break;
                case CONTROLS:
                    game.setScreen(new HelpScreen(game));
                    break;
                case EXIT:
                    Gdx.app.exit();
                    break;
            }
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
        titleFont.dispose();
        menuFont.dispose();
    }
}