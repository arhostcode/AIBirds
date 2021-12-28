package space.ardyc.aibirds.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import space.ardyc.aibirds.bird.Bird;
import space.ardyc.aibirds.utils.AddonsRender;
import space.ardyc.aibirds.utils.Point;

import java.util.Iterator;

public class WorldRenderer {

    private World world;

    private SpriteBatch spriteRenderer;
    private BitmapFont fontRenderer;
    private AddonsRender addonsRender;

    private Texture birdTexture;
    private Texture lowerPipeTexture;
    private Texture upperPipeTexture;

    public WorldRenderer(World world){
        this.world = world;
        spriteRenderer = new SpriteBatch();
        addonsRender = new AddonsRender(new Point(340, 550));
        loadTextures();
        initFontRenderer();

    }

    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        spriteRenderer.begin();
        Iterator<Bird> birdIterator = world.getBirdsList().iterator();
        while (birdIterator.hasNext()) {
            Bird bird = birdIterator.next();
            if (bird.isAlive()) {
                renderBird(bird);
            }
        }
        renderPipes();
        renderTextInfo();
        spriteRenderer.end();
        addonsRender.render(world.getBirdsList());

    }

    private void loadTextures() {
        birdTexture = new Texture("flappy.png");
        lowerPipeTexture = new Texture("tube_down.png");
        upperPipeTexture = new Texture("tube_up.png");
    }

    private void initFontRenderer() {
        fontRenderer = new BitmapFont();
        fontRenderer.setColor(0, 0, 0, 1);
    }

    private void renderTextInfo() {
        fontRenderer.draw(spriteRenderer, "Population: " + world.nowPopulationCount, 20, 20);
        fontRenderer.draw(spriteRenderer, "Generation: " + world.nowGenerationNumber, 150, 20);
        fontRenderer.draw(spriteRenderer, "Now result: " + world.nowPopulationResult, 260, 20);
    }

    private void renderBird(Bird bird) {
        spriteRenderer.draw(birdTexture, bird.getBox().x, bird.getBox().y, bird.getBox().width, bird.getBox().height);
    }

    private void renderPipes() {
        spriteRenderer.draw(lowerPipeTexture, world.lowerPipeBoundingBox.x, world.lowerPipeBoundingBox.y, world.lowerPipeBoundingBox.width, world.lowerPipeBoundingBox.height);
        spriteRenderer.draw(upperPipeTexture, world.upperPipeBoundingBox.x, world.upperPipeBoundingBox.y, world.upperPipeBoundingBox.width, world.upperPipeBoundingBox.height);
    }

    public void dispose() {
        spriteRenderer.dispose();
        fontRenderer.dispose();
        birdTexture.dispose();
        lowerPipeTexture.dispose();
        upperPipeTexture.dispose();
        addonsRender.dispose();
    }
}
