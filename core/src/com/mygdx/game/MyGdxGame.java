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
//		batmanAnim2 = new AnimPlayer("batmanadventure.png", 8, 1, 10.0f, true);
	}

	@Override
	public void render () {//Здесь отрисовываем!
		ScreenUtils.clear(new Color(Color.LIGHT_GRAY));

		Gdx.graphics.setTitle("Xo Xo!");

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) x--;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x++;

		batmanAnim.step(Gdx.graphics.getDeltaTime());

		batch.begin();

		batch.draw(batmanAnim.getTexture(), x, 0);//Почему отрисовывается только первый бэт?
//		batch.draw(batmanAnim2.getTexture(), x, 0);
		batch.end();
	}

	@Override
	public void dispose () {//Здесь закрываем/удаляем/освобождаем ресурсы!
		batch.dispose();
		batmanAnim.dispose();
	}
}
