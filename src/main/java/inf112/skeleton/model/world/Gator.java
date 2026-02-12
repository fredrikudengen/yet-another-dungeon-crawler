package inf112.skeleton.model.world;

public class Gator implements ModelObject {
    private double x, y, dx = 100, dy = 100;

    public Gator(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double width() {
        return 100;
    }

    @Override
    public double height() {
        return 100;
    }

    @Override
    public String appearance() {
        return "obligator.png";
    }

    @Override
    public String sound() {
        return "blipp.ogg";
    }

    @Override
    public void step(GameWorld world, double deltaTime, String direction) {
        if (direction.equals("up")) {
            y += 1;
        }
        if (direction.equals("down")) {
            y -= 1;
        }
        if (direction.equals("left")) {
            x -= 1;
        }
        if (direction.equals("right")) {
            x += 1;
        }
    }

}
