package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private TextureRegion region;
	private int x,y;
	private AnimPlayer batmanAnim;
	private AnimPlayer batmanAnim2;
	private Label label;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private List<Coin> coinList;

	private int[] foreGrounde, backGrounde;

	@Override
	public void create () {//Здесь инициализируем поля!
		map = new TmxMapLoader().load("maps/Second.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);

		batch = new SpriteBatch();
		batmanAnim = new AnimPlayer("Batman.png", 8, 1, 10.0f, Animation.PlayMode.LOOP);
//		batmanAnim2 = new AnimPlayer("batmanadventure.png", 8, 1, 10.0f, true);
		label = new Label(36);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		RectangleMapObject o = (RectangleMapObject) map.getLayers().get("Mapik").getObjects().get("camera");
		camera.position.x = o.getRectangle().x;
		camera.position.y = o.getRectangle().y;
		camera.update();

		coinList = new ArrayList<>();
		MapLayer ml = map.getLayers().get("монетки");
		if (ml != null){
			MapObjects mo = ml.getObjects();
			if (mo.getCount()>0){
				for (int i=0;i<mo.getCount();i++){
					RectangleMapObject tmpMo = (RectangleMapObject) ml.getObjects().get(i);
					Rectangle rect = tmpMo.getRectangle();
					coinList.add(new Coin(new Vector2(rect.x,rect.y)));
				}
			}
		}
	}

	@Override
	public void render () {//Здесь отрисовываем!
		ScreenUtils.clear(new Color(Color.LIGHT_GRAY));

		Gdx.graphics.setTitle("Xo Xo!");

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x--;
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x++;
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y++;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y--;
		camera.update();

		mapRenderer.setView(camera);
		mapRenderer.render();

		batmanAnim.step(Gdx.graphics.getDeltaTime());

		batch.begin();

		batch.draw(batmanAnim.getFrame(), Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()/8);//Почему отрисовывается только первый бэт?
//		batch.draw(batmanAnim2.getTexture(), x, 0);
		label.draw(batch, "Welcome to Gotham!");
		batch.end();
	}

	@Override
	public void dispose () {//Здесь закрываем/удаляем/освобождаем ресурсы!
		batch.dispose();
		batmanAnim.dispose();
	}
}
