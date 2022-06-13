package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private TextureRegion region;
	private int x,y;
//	private AnimPlayer batmanAnim;
//	private AnimPlayer batmanAnim2;
	private Label label;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;
	private List<Coin> coinList;
	private Texture fon;
	private ShapeRenderer renderer;
	private Rectangle heroRect;
	private int score;
	private MyCharacter chip;

	private int[] foreGround, backGround;

	private World world;
	private Box2DDebugRenderer debugRenderer;

	@Override
	public void create () {//Здесь инициализируем поля!
		chip = new MyCharacter();

		world = new World(new Vector2(0, -9.81f), true);// Гравитация и to sleep
		debugRenderer = new Box2DDebugRenderer();

		BodyDef def = new BodyDef();
		def.gravityScale = 1.2f;//Масса тела
		def.position.set(new Vector2(130.50f,368.63f));
		def.type = BodyDef.BodyType.StaticBody;

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1;
		fixtureDef.friction = 1f;

		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(10,10);
		fixtureDef.shape = polygonShape;
		//polygonShape.dispose();

//		world.createBody(def).createFixture(fixtureDef);

		Body body = world.createBody(def);
		body.createFixture(fixtureDef);
		//body.createFixture(fixtureDef);

		fon = new Texture("maps/fon1.png");
//		map = new TmxMapLoader().load("maps/Second.tmx");
		map = new TmxMapLoader().load("maps/map3.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);

		System.out.printf("Step 1");

		foreGround = new int[1];
		foreGround[0] = map.getLayers().getIndex("Слой тайлов 1");
		backGround = new int[1];
		backGround[0] = map.getLayers().getIndex("Слой тайлов 2");


		batch = new SpriteBatch();
//		renderer = new ShapeRenderer();

//		batmanAnim = new AnimPlayer("Batman.png", 8, 1, 10.0f, Animation.PlayMode.LOOP);
		//heroRect = new Rectangle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,batmanAnim.getFrame().getRegionWidth(),batmanAnim.getFrame().getRegionHeight());

		label = new Label(36);

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//Почему всегда используем gdx.graphics.get...?
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

		chip.setWalk(false);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.position.x--;
			chip.setDir(false);
			chip.setWalk(true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.position.x++;
			chip.setDir(false);
			chip.setWalk(true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y++;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y--;
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

		camera.update();

		batch.begin();
		batch.draw(fon, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();

		//System.out.printf("Step 2");

		mapRenderer.setView(camera);
		mapRenderer.render(backGround);

		batch.begin();
		//batch.draw(fon, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	//	batmanAnim.step(Gdx.graphics.getDeltaTime());
		//batch.draw(batmanAnim.getFrame(), 50f, 50f, 50f, 50f);//Почему отрисовывается только первый бэт?
		batch.draw(chip.getFrame(), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		label.draw(batch, "Coins gathered: " + String.valueOf(score));

		for (int i=0;i<coinList.size();i++){
			coinList.get(i).draw(batch, camera);
			if (coinList.get(i).isOverlaps(chip.getRect(), camera)) {
				coinList.remove(i);
				score++;
			}
		}

		batch.end();




//		renderer.begin(ShapeRenderer.ShapeType.Line);
//		renderer.circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getHeight()/3);
//		renderer.end();

//		Color heroClr = new Color(Color.WHITE);
		mapRenderer.render(foreGround);
//		renderer.setColor(heroClr);
//		renderer.begin(ShapeRenderer.ShapeType.Line);
//		for (int i=0;i<coinList.size();i++){
////			coinList.get(i).shapeDraw(renderer,camera);
//			if (coinList.get(i).isOverlaps(chip.getRect(), camera)) {
//				coinList.remove(i);
////				heroClr = Color.BLUE;
//			}
//		}
//		renderer.setColor(heroClr);
//		renderer.rect(heroRect.x, heroRect.y,heroRect.width,heroRect.height);
//		renderer.end();


		world.step(1/60.0f, 3, 3);
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void dispose () {//Здесь закрываем/удаляем/освобождаем ресурсы!
		batch.dispose();
	//	batmanAnim.dispose();
		coinList.get(0).dispose();
		world.dispose();
	}
}
