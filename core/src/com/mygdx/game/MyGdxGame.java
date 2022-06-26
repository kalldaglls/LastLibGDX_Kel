package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
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
	private PhysX physX;

	private int[] foreGround, backGround;

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private boolean start;
	private Body heroBody;
	private MapObject obj;

	private Music music;

	@Override
	public void create () {//Здесь инициализируем поля!
		chip = new MyCharacter();

		world = new World(new Vector2(0, -9.81f), true);// Гравитация и to sleep
		debugRenderer = new Box2DDebugRenderer();
		PolygonShape polygonShape = new PolygonShape();

		BodyDef def = new BodyDef();
		def.gravityScale = 1.0f;//Масса тела
		def.position.set(new Vector2(2f,404f));// Где в пространстве находится тело
		RectangleMapObject rectangle = (RectangleMapObject) obj;
		//def.position.set(new Vector2(rectangle.getRectangle().x+rectangle.getRectangle().width/2 , rectangle.getRectangle().y+rectangle.getRectangle().height/2));
		def.type = BodyDef.BodyType.StaticBody;//Есть три типа тела: статик, динамик и кинематик
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 0;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 0f;


		polygonShape.setAsBox(20,20);//Что-то вроде радиусов для квадрата!
		fixtureDef.shape = polygonShape;
		//polygonShape.dispose();

		world.createBody(def).createFixture(fixtureDef);

//		def.position.set(new Vector2(MathUtils.random(-50f, 150f),150f));
//			def.type = BodyDef.BodyType.DynamicBody;
//			def.gravityScale = 0f;

			float size = 20f;
//			polygonShape.setAsBox(size,size);
//			fixtureDef.shape = polygonShape;
//
//			world.createBody(def).createFixture(fixtureDef);

//		for (int i = 0; i < 10; i++) {
//			def.position.set(new Vector2(MathUtils.random(-50f, 150f),150f));
//			def.type = BodyDef.BodyType.DynamicBody;
//			def.gravityScale = MathUtils.random(5f, 10f);
//
//			size = MathUtils.random(3f, 15f);
//			polygonShape.setAsBox(size,size);
//			fixtureDef.shape = polygonShape;
//			fixtureDef.friction = MathUtils.random(0.5f, 1f);
//			fixtureDef.density = MathUtils.random(0.5f, 1f);
//			fixtureDef.restitution = MathUtils.random(0.1f, 0.3f);
//
//			world.createBody(def).createFixture(fixtureDef);
//		}

		def.position.set(new Vector2(10f,36f));
		def.type = BodyDef.BodyType.DynamicBody;
		def.gravityScale = 1f;
		size = 5f;
		polygonShape.setAsBox(size,size);
		fixtureDef.shape = polygonShape;
		heroBody = world.createBody(def);
		heroBody.createFixture(fixtureDef);

		polygonShape.dispose();

//		Body body = world.createBody(def);// Создаем несколько фикстур у тела!
//		body.createFixture(fixtureDef);
		//body.createFixture(fixtureDef);

		fon = new Texture("maps/fon1.png");
//		map = new TmxMapLoader().load("maps/Second.tmx");
		map = new TmxMapLoader().load("maps/map3.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);

		System.out.printf("Step 1");

		physX = new PhysX();
		if(map.getLayers().get("land")!= null){
			MapObjects mo = map.getLayers().get("land").getObjects();
			physX.addObjects(mo);
			MapObject mo1 = map.getLayers().get("Слой объектов 1").getObjects().get("hero");
			physX.addObject(mo1);
		}

//		if(map.getLayers().get("Слой объектов 1")!= null){
//			MapObjects mo = map.getLayers().get("Слой объектов 1").getObjects();
//			physX.addObjects(mo);
//			MapObject mo1 = map.getLayers().get("Слой объектов 1").getObjects().get("hero");
//			physX.addObject(mo1);
//		}

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

		camera.position.x = physX.getHero().getPosition().x;
		camera.position.y = physX.getHero().getPosition().y;


//		camera.position.x = o.getRectangle().x;
//		camera.position.y = o.getRectangle().y;
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

		music = Gdx.audio.newMusic(Gdx.files.internal("StartMusic.mp3"));
		music.setLooping(true);
		music.setVolume(0.025f);//Регулировка микшера

		music.play();
	}

	@Override
	public void render () {//Здесь отрисовываем!
		ScreenUtils.clear(new Color(Color.LIGHT_GRAY));

		Gdx.graphics.setTitle("Xo Xo!");

		chip.setWalk(false);
//		chip.setDir(false);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {//Пишем камеры для перса 1:58:12!!
			//heroBody.applyForceToCenter(new Vector2(-20f, 0f), true);
			//camera.position.x--;
			physX.setHeroForce(new Vector2(-30000, 0));
			chip.setDir(true);
			chip.setWalk(true);
//			chip.setDir(false);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//			heroBody.applyForceToCenter(new Vector2(20f, 0f), true);
//			camera.position.x++;
			physX.setHeroForce(new Vector2(30000, 0));
			chip.setDir(false);
			chip.setWalk(true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			physX.setHeroForce(new Vector2(0, 30000));
//			camera.position.y++;
//			heroBody.applyForceToCenter(new Vector2(0f,20f), true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//			camera.position.y--;
//			heroBody.applyForceToCenter(new Vector2(0f, -20000f), true);
			physX.setHeroForce(new Vector2(0, -10000));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
		if(Gdx.input.isKeyPressed(Input.Keys.S)) start = true;

//		camera.position.x = heroBody.getPosition().x;
//		camera.position.y = heroBody.getPosition().y;
		camera.position.x = physX.getHero().getPosition().x;
		camera.position.y = physX.getHero().getPosition().y;
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
			int state;
			state = coinList.get(i).draw(batch, camera);
			if (coinList.get(i).isOverlaps(chip.getRect(), camera)) {
				if (state == 0)coinList.get(i).setState();
				if (state == 2) {
					coinList.remove(i);
					score++;
				}
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


		if(start) world.step(1/60.0f, 3, 3);// Если это не прописать, то динам тела будут спать!
		debugRenderer.render(world, camera.combined);// Рисуем мир и объекты в нем!

		if(start) physX.step();//Включаем физику!
		physX.debugDraw(camera);//Покажи нам физику!

	}

	@Override
	public void dispose () {//Здесь закрываем/удаляем/освобождаем ресурсы!
		batch.dispose();
	//	batmanAnim.dispose();
		coinList.get(0).dispose();
		world.dispose();
		physX.dispose();
		music.stop();
		music.dispose();
	}
}
