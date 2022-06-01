package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion region;
	private int x,y;
	AnimPlayer batmanAnim;
	AnimPlayer batmanAnim2;
	Label label;

	@Override
	public void create () {//Здесь инициализируем поля!
		batch = new SpriteBatch();
		batmanAnim = new AnimPlayer("Batman.png", 8, 1, 10.0f, Animation.PlayMode.LOOP);
		//batmanAnim2 = new AnimPlayer("runRight.png", 8, 1, 10.0f, Animation.PlayMode.LOOP);
	}

	@Override
	public void render () {//Здесь отрисовываем!
		ScreenUtils.clear(new Color(Color.LIGHT_GRAY));

		Gdx.graphics.setTitle("Xo Xo!");

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) x--;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x++;

		batmanAnim.step(Gdx.graphics.getDeltaTime());

		batch.begin();

		batch.draw(batmanAnim.getFrame(), x, 50, 50f, 50f);//Почему отрисовывается только первый бэт?
		//batch.draw(batmanAnim2.getFrame(), x, 0, 50f, 50f);
		batch.end();
	}

	@Override
	public void dispose () {//Здесь закрываем/удаляем/освобождаем ресурсы!
		batch.dispose();
		batmanAnim.dispose();
	}
}
