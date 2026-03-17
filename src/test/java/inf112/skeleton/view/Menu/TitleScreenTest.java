package inf112.skeleton.view.Menu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import inf112.skeleton.controller.GameScreen;
import inf112.skeleton.controller.HelpScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TitleScreenTest {

    private Game mockGame;

    @BeforeEach
    public void setUp() {
        mockGame = mock(Game.class);
        Gdx.app = mock(Application.class);
        Application mockApp = mock(Application.class);
        Gdx.app = mockApp;
    }

    @Test
    public void titleScreen_initialIndex_isZero() {
        TitleScreen screen = new TitleScreen(mockGame);
        assertEquals(0, screen.getCurrentIndex());
    }

    @Test
    public void titleScreen_start_navigatesToGameScreen() {
        try (MockedConstruction<GameScreen> mocked = mockConstruction(GameScreen.class)) {
            TitleScreen screen = new TitleScreen(mockGame);
            screen.handleSelection(TitleScreen.MenuOption.START);
            verify(mockGame).setScreen(any(GameScreen.class));
        }
    }

    @Test
    public void titleScreen_controls_navigatesToHelpScreen() {
        try (MockedConstruction<HelpScreen> mocked = mockConstruction(HelpScreen.class)) {
            TitleScreen screen = new TitleScreen(mockGame);
            screen.handleSelection(TitleScreen.MenuOption.CONTROLS);
            verify(mockGame).setScreen(any(HelpScreen.class));
        }
    }

    @Test
    public void titleScreen_exit_callsAppExit() {
        TitleScreen screen = new TitleScreen(mockGame);
        screen.handleSelection(TitleScreen.MenuOption.EXIT);
        verify(Gdx.app).exit();
    }

    @Test
    public void titleScreen_exit_doesNotChangeScreen() {
        TitleScreen screen = new TitleScreen(mockGame);
        screen.handleSelection(TitleScreen.MenuOption.EXIT);
        verify(mockGame, never()).setScreen(any());
    }
}