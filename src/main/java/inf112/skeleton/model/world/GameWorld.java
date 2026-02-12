package inf112.skeleton.model.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld {
    double width;
    double height;
    double currentTime = 0;

    public GameWorld (double width, double height) {
        super();
        this.width = width;
        this.height = height;
    }

    public void step(double deltaTime) {
        currentTime += deltaTime;

    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public double currentTime() {
        return currentTime;
    }

}
