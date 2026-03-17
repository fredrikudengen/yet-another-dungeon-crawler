package inf112.dungeoncrawler.view.Menu;

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
}
