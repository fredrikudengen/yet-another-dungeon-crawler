package inf112.dungeoncrawler.view.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.graphics.Texture;

public class TitleScreenTest {
    
    @Test
    void menuOption() {
        TitleScreen.MenuOption[] options = TitleScreen.MenuOption.values();

        assertEquals(3, options.length);
    }

    @Test 
    void menuOptionsCorrect() {
        TitleScreen.MenuOption[] options = TitleScreen.MenuOption.values();

        assertEquals(TitleScreen.MenuOption.START, options[0]);
        assertEquals(TitleScreen.MenuOption.HELP, options[1]);
        assertEquals(TitleScreen.MenuOption.EXIT, options[2]);
    }

    @Test
    void animationFrameShouldLoop() {
        Texture[] frames = new Texture[2];
        int index = 1;
        index = (index + 1) % frames.length;

        assertEquals(0, index);
        
    }

    @Test
    void animateShouldChangeFrame() {
        TitleScreen screen = new TitleScreen(null); 
        Texture[] frames = new Texture[2];

        int index = screen.animate(1f, 0.25f, frames, 0, true); 
        assertEquals(1, index); 
    }

    @Test
    void animatePlayerShouldChangeFrame() {
        TitleScreen screen = new TitleScreen(null); 
        Texture[] frames = new Texture[2];

        int index = screen.animate(1f, 0.25f, frames, 0, true); 
        assertEquals(1, index); 
    }

    @Test
    void animateShouldNotChangeFrame() {
        TitleScreen screen = new TitleScreen(null); 
        Texture[] frames = new Texture[2];

        int index = screen.animate(0.1f, 0.25f, frames, 0, true); 
        assertEquals(0, index); 
    }
}
