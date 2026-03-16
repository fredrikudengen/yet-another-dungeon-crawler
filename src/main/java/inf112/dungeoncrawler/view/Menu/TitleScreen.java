package inf112.dungeoncrawler.view.Menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import inf112.dungeoncrawler.controller.GameScreen;

public class TitleScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private GlyphLayout layout;
    private BitmapFont titleFont;
    private BitmapFont menuFont;

    private Texture[] flameFrames;
    private Texture[] playerFrames;
    private Texture floor; 

    private float flameTimer = 0;
    private int flameFrame = 0; 
    private float playerTimer = 0;
    private int playerFrame = 0; 

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
        
        // title
        parameter.size = 75;
        titleFont = titleGenerator.generateFont(parameter);
        titleFont.setColor(Color.valueOf("F4BB44"));    // Gold

        // menu
        parameter.size = 30;
        menuFont = titleGenerator.generateFont(parameter); 
        menuFont.setColor(Color.WHITE); 
        
        titleGenerator.dispose();

        flameFrames = new Texture[] {
            new Texture("StartMenu/flamme1.png"),
            new Texture("StartMenu/flamme2.png")
        };

        playerFrames = new Texture[] {
            new Texture("Player/withoutWeapon/Walk/Player_walk_left1.png"), 
            new Texture("Player/withoutWeapon/Walk/Player_walk_left2.png")
        };

        floor = new Texture("StartMenu/menyfloor.png"); 
    }

    @Override
    public void render(float delta) {
        handleInput();
        ScreenUtils.clear(0.2f, 0.13f, 0.07f, 1);   // Brown
        batch.begin();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight(); 

        // title
        String gameTitle1 = "Yet Another";
        String gameTitle2 = "Dungeon-Crawler";

        layout.setText(titleFont, gameTitle1);
        float titleX = (screenWidth - layout.width) / 2;
        float titleY = screenHeight * 0.85f;
        titleFont.draw(batch, layout, titleX, titleY);

        layout.setText(titleFont, gameTitle2);
        float titleX2 = (screenWidth - layout.width) / 2;
        float titleY2 = titleY - 80;
        titleFont.draw(batch, layout, titleX2, titleY2);


        // menu
        float startY = screenHeight * 0.55f; 
        for (int i = 0; i < options.length; i++) {
            String text = (i == currentIndex ? "> " : "  ") + options[i].name();

            // Highlight selected option
            if (i == currentIndex) {
                menuFont.setColor(Color.valueOf("F4BB44"));
            } else {
                menuFont.setColor(Color.WHITE);
            }

            layout.setText(menuFont, text);
            float x = screenWidth * 0.60f;
            float y = startY - i * 90;
            menuFont.draw(batch, layout, x, y);
        }
        
        // floor
        float tileSize = 300; 
        float tileHeight = 260; 
        float floorTop = screenHeight * 0.08f - 75; 
        for (float x = 0; x < screenWidth; x += tileSize) {
            batch.draw(floor, x, floorTop, tileSize, tileHeight);
        }

        flameFrame = animate(delta, 0.25f, flameFrames, flameFrame, true);
        playerFrame = animate(delta, 0.25f, playerFrames, playerFrame, false); 

        Texture currentFlame = flameFrames[flameFrame];
        Texture currentPlayer = playerFrames[playerFrame];

        // flame
        float torchY = screenHeight * 0.40f;
        batch.draw(currentFlame, screenWidth * 0.78f, torchY, 280, 280);
        batch.draw(currentFlame, screenWidth * 0.05f, torchY, 280, 280);

        // player
        batch.draw(currentPlayer, screenWidth * 0.15f, screenHeight * 0.12f, 350, 350);

        batch.end();
    }

    private int animate(float delta, float interval, Texture[] frames, int index, boolean isFlame) {
        if (isFlame) {
            flameTimer += delta;

            if (flameTimer > interval) {
                index = (index + 1) % frames.length;
                flameTimer = 0;
            }
        } else {
            playerTimer += delta;

            if (playerTimer > interval) {
                index = (index + 1) % frames.length;
                playerTimer = 0;
            }

        }
        return index;
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
        floor.dispose(); 
        
        for (Texture t : flameFrames) t.dispose();
        for (Texture t : playerFrames) t.dispose();
    }
}