package inf112.dungeoncrawler.controller;

public class GameStateManager {
    private GameState currentState = GameState.MAIN_MENU;

    public void setState(GameState newState) {
        this.currentState = newState;
    }

    public GameState getState() {
        return currentState;
    }
}