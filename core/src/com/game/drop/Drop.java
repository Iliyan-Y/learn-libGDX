package com.game.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.drop.screens.MainMenuScreen;

public class Drop extends Game {
  public SpriteBatch batch;
  public BitmapFont font;
  public Stage stage;
  public Skin skin;
  public int width;
  public int height;
  public float scale;

  @Override
  public void create() {
     width = Gdx.graphics.getWidth();
     height = Gdx.graphics.getHeight();
    scale = width > 800 ? 3.2f : 1f;
    batch = new SpriteBatch();
    font = new BitmapFont();
    stage = new Stage(new ScreenViewport());
    skin = new Skin(Gdx.files.internal("uiskin.json"));
    this.setScreen(new MainMenuScreen(this));
    Gdx.input.setInputProcessor(stage);
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

