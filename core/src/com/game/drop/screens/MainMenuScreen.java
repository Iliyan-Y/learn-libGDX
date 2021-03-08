package com.game.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.drop.Drop;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class MainMenuScreen implements Screen {
  final Drop game;
  OrthographicCamera camera;
  TextField textField;
  Skin skin;

  public MainMenuScreen(final Drop game) {
    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);
    skin = new Skin(Gdx.files.internal("uiskin.json"));
    textField = new TextField("", skin);
    textField.setPosition(200,200);
    textField.setSize(88, 18);
    game.stage.addActor(textField);
    Gdx.input.setInputProcessor(game.stage);
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
    game.stage.act(Gdx.graphics.getDeltaTime());
    game.stage.draw();
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
      skin.dispose();
  }

}
