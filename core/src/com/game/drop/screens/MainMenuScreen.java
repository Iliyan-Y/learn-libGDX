package com.game.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.drop.Drop;

public class MainMenuScreen implements Screen {
  final Drop game;
  OrthographicCamera camera;
  TextField textField;
  Label label;
  Button button;
  boolean play = false;
  String playerName = "Player";

  public MainMenuScreen(final Drop game) {
    this.game = game;
    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);

    label = new Label("Player name", game.skin, "default");
    label.setPosition(340, 275);
    label.setSize(120, 25);

    textField = new TextField("", game.skin);
    textField.setPosition(325, 250);
    textField.setSize(130, 25);
    textField.setTextFieldListener(new TextField.TextFieldListener() {
      @Override
      public void keyTyped(TextField textField, char key) {
        playerName = textField.getText();
      }
    });

    button = new TextButton("Play", game.skin, "default");
    button.setSize(60, 60);
    button.setPosition(350, 178);
    button.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        play = true;
        return true;
      }
    });

    game.stage.addActor(textField);
    game.stage.addActor(label);
    game.stage.addActor(button);
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
    game.stage.act(Gdx.graphics.getDeltaTime());
    game.stage.draw();
    game.batch.end();

    if (play) {
      game.setScreen(new GameScreen(game, playerName));
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
