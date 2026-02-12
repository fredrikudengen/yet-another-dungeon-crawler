package inf112.skeleton.model.factories;

import inf112.skeleton.model.generation.RoomTemplate;
import inf112.skeleton.model.world.Room;
import java.util.Random;

/**
 * Factory that creates rooms based on predefined templates.
 * Can randomly select from multiple templates if provided.
 */
public class TemplateRoomFactory implements RoomFactory {
    private final RoomTemplate[] templates;
    private final Random random;

    /**
     * Creates a factory with a single template.
     *
     * @param template The template to use for room creation
     */
    public TemplateRoomFactory(RoomTemplate template) {
        this.templates = new RoomTemplate[] { template };
        this.random = new Random();
    }

    /**
     * Creates a factory with multiple templates.
     * Will randomly choose one when creating a room.
     *
     * @param templates Array of templates to choose from
     */
    public TemplateRoomFactory(RoomTemplate[] templates) {
        this.templates = templates;
        this.random = new Random();
    }

    @Override
    public Room createRoom() {
        // Pick a random template if we have multiple
        RoomTemplate template = templates[random.nextInt(templates.length)];
        return template.createRoom();
    }
}