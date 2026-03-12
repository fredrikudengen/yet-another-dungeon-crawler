package inf112.skeleton.view.Menu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PauseScreenTest {

    private Game mockGame;
    private Screen mockGameScreen;

    @BeforeEach
    public void setUp() {
        mockGame = mock(Game.class);
        mockGameScreen = mock(Screen.class);

        // Mock LibGDX application to prevent NullPointerException
        Application mockApp = mock(Application.class);
        Gdx.app = mockApp;
    }

    @Test
    public void pauseScreen_resume_returnsToGameScreen() {
        PauseScreen screen = new PauseScreen(mockGame, mockGameScreen);
        screen.handleSelection(PauseScreen.MenuOption.RESUME);
        verify(mockGame).setScreen(mockGameScreen);
    }

    @Test
    public void pauseScreen_mainMenu_navigatesToTitleScreen() {
        PauseScreen screen = new PauseScreen(mockGame, mockGameScreen);
        screen.handleSelection(PauseScreen.MenuOption.MAIN_MENU);
        verify(mockGame).setScreen(any(TitleScreen.class));
    }

    @Test
    public void pauseScreen_exit_callsAppExit() {
        PauseScreen screen = new PauseScreen(mockGame, mockGameScreen);
        screen.handleSelection(PauseScreen.MenuOption.EXIT);
        verify(mockGame, never()).setScreen(any());
    }

    @Test
    public void pauseScreen_mainMenu_disposesGameScreen() {
        PauseScreen screen = new PauseScreen(mockGame, mockGameScreen);
        screen.handleSelection(PauseScreen.MenuOption.MAIN_MENU);
        verify(mockGameScreen).dispose();
    }
}