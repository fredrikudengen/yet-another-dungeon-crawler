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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator; 
import inf112.skeleton.controller.GameScreen;

public class TitleScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private GlyphLayout layout;

    private BitmapFont titleFont;
    private BitmapFont menuFont;

    private enum MenuOption {
        START, OPTIONS, EXIT
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

        FreeTypeFontGenerator titleGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter(); 
        
        parameter.size = 75;
        titleFont = titleGenerator.generateFont(parameter);
        titleFont.setColor(Color.valueOf("F4BB44"));    // Gold

        //titleGenerator.dispose();

        parameter.size = 30;
        menuFont = titleGenerator.generateFont(parameter); 
        menuFont.setColor(Color.WHITE); 
        
        titleGenerator.dispose();

        //menuFont = new BitmapFont();
        //menuFont.getData().setScale(2f);
        //menuFont.setColor(Color.WHITE);

    }

    @Override
    public void render(float delta) {
        handleInput();

        ScreenUtils.clear(0.2f, 0.13f, 0.07f, 1);   // Brown

        batch.begin();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight(); 

        String gameTitle = "Dungeon Crawler";
        layout.setText(titleFont, gameTitle);
        float titleX = (screenWidth - layout.width) / 2;
        float titleY = screenHeight * 0.75f;
        titleFont.draw(batch, layout, titleX, titleY);

        float startY = Gdx.graphics.getHeight() / 2;
        for (int i = 0; i < options.length; i++) {
            String text = (i == currentIndex ? "> " : "  ") + options[i].name();

            // Highlight selected option
            if (i == currentIndex) {
                menuFont.setColor(Color.valueOf("F4BB44"));
            } else {
                menuFont.setColor(Color.WHITE);
            }

            layout.setText(menuFont, text);

            float x = screenWidth * 0.65f;
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
                case OPTIONS:
                    System.out.println("Options - Not implemented yet");
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