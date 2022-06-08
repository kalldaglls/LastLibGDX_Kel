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

	private int[] foreGround, backGround;

	@Override
	public void create () {//Здесь инициализируем поля!
//		map = new TmxMapLoader().load("maps/Second.tmx");
		map = new TmxMapLoader().load("maps/map3.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);

//		foreGround = new int[1];
//		foreGround[0] = map.getLayers().getIndex("Слой тайлов 2");
//		backGround = new int[1];
//		backGround[0] = map.getLayers().getIndex("Слой тайлов 1");


		batch = new SpriteBatch();
		batmanAnim = new AnimPlayer("Batman.png", 8, 1, 10.0f, Animation.PlayMode.LOOP);

		label = new Label(36);

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		RectangleMapObject o = (RectangleMapObject) map.getLayers().get("Слой объектов 1").getObjects().get("camera");
		camera.position.x = o.getRectangle().x;
		camera.position.y = o.getRectangle().y;
		camera.zoom = 0.5f;
		camera.update();

		coinList = new ArrayList<>();
//		coinList.add(new Coin(new Vector2(0,0)));
		MapLayer ml = map.getLayers().get("Монетки");//Берем слой с монетками
		if (ml != null){//Если слой существует...
			MapObjects mo = ml.getObjects();//Берем все объекты со слоя
			if (mo.getCount()>0){
				for (int i=0;i<mo.getCount();i++){
					RectangleMapObject tmpMo = (RectangleMapObject) ml.getObjects().get(i);//Берем очередной объект и кастим его в прямоугольный объект
					Rectangle rect = tmpMo.getRectangle();//Вытаскиваем из него прямоугольник
					coinList.add(new Coin(new Vector2(rect.x,rect.y)));//Добавляем монетку в x y.
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

		batch.draw(batmanAnim.getFrame(), 50f, 50f, 50f, 50f);//Почему отрисовывается только первый бэт?

		label.draw(batch, "Welcome to Gotham!");

		for (int i=0;i<coinList.size();i++){
			coinList.get(i).draw(batch, camera);
		}

		batch.end();

		//mapRenderer.render(foreGround);
	}

	@Override
	public void dispose () {//Здесь закрываем/удаляем/освобождаем ресурсы!
		batch.dispose();
		batmanAnim.dispose();
		coinList.get(0).dispose();
	}
}
