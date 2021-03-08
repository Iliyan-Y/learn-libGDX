package com.game.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.drop.screens.MainMenuScreen;

public class Drop extends Game {
  public SpriteBatch batch;
  public BitmapFont font;
  public Stage stage;
  public Skin skin;

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    stage = new Stage();
    skin = new Skin(Gdx.files.internal("uiskin.json"));
    this.setScreen(new MainMenuScreen(this));
  }

  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
    stage.dispose();
    skin.dispose();
  }

  @Override
  public void render() {
    super.render();
  }
}

