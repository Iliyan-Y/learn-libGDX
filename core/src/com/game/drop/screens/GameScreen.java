package com.game.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.game.drop.Drop;

import java.util.Iterator;

public class GameScreen implements Screen {
    final Drop game;

    private Texture dropImage;
    private Texture bucketImage;
    private Sound dropSound;
    private Music rainMusic;
    private OrthographicCamera camera;
    private Rectangle bucket;
    private Vector3 touchPos;
    private Array<Rectangle> raindrops;
    private long lastDropTime; //store time in nanosec
    int dropsGathered;
    String playerName;

    public enum State {
        Running, Paused
    }

    State state = State.Running;

    public GameScreen(final Drop game, String playerName) {
        this.game = game;
        this.playerName = playerName;
        dropImage = new Texture(Gdx.files.internal("drop.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));
        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        touchPos = new Vector3();
        raindrops = new Array<Rectangle>();
        spawnRaindrop();
        game.font.getData().setScale(game.scale == 1 ? 1 : game.scale / 2);

    }

    @Override
    public void render(float delta) {
        switch (state) {
            case Running:
                resume();
                break;
            case Paused:
                pause();
                break;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        ScreenUtils.clear(0.1f, 0.6f, 0.1f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Game is Paused !", game.width / 2, 220);
        game.font.draw(game.batch, "Tap anywhere to begin!", game.width / 2, 100);
        game.batch.end();
        if (Gdx.input.isTouched()) {
            this.state = State.Running;
        }
    }

    @Override
    public void resume() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // show drops collected
        game.font.draw(game.batch, "Drops collected: " + dropsGathered, 10, 440);
        game.font.draw(game.batch, playerName, 10, 462);
        //display the bucket
        game.batch.draw(bucketImage, bucket.x, bucket.y);
        // render the drops
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        game.batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }

        //get smooth movement based on the last frame
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.x -= 400 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.x += 400 * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            this.state = State.Paused;

        //bucket stay in the screen
        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;

        // spawn the raindrops over time
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        //move the raindrops
        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                dropSound.play();
                iter.remove();
            }
            ;
            //catch the raindrop
            if (raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
                dropsGathered += 1;
            }
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        rainMusic.play();
    }


    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }
}
