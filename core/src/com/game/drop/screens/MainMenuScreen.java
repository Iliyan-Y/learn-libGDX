package com.game.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.drop.Drop;

public class MainMenuScreen implements Screen {
  final Drop game;
  OrthographicCamera camera;

  public MainMenuScreen(final Drop game) {
    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0.1f, 0.6f, 0.1f, 1);
    camera.update();
    game.batch.setProjectionMatrix(camera.combined);

    game.batch.begin();
    game.font.draw(game.batch, "Welcome to DROP !", 100, 150);
    game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
    game.batch.end();

    if(Gdx.input.isTouched()) {
      game.setScreen(new GameScreen(game));
      dispose();
    }
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {

  }

}