package inf112.skeleton.model.world;

public interface ModelObject {
    // TODO: document this!

    double x();
    double y();
    double width();
    double height();
    String appearance();
    String sound();
    void step(GameWorld world, double deltaTime, String direction);
}
