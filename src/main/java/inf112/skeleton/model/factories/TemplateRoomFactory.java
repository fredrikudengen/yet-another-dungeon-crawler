package inf112.skeleton.model.factories;

import inf112.skeleton.model.generation.RoomTemplate;
import inf112.skeleton.model.world.Room;
import java.util.Random;

public class TemplateRoomFactory implements RoomFactory {
    private final RoomTemplate[] templates;
    private final Random random;

    public TemplateRoomFactory(RoomTemplate template) {
        this.templates = new RoomTemplate[] { template };
        this.random = new Random();
    }

    @Override
    public Room createRoom() {
        RoomTemplate template = templates[random.nextInt(templates.length)];
        return template.createRoom();
    }
}