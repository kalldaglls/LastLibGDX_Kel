package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class PhysX {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private Body hero;

    public PhysX() {
        world = new World(new Vector2(0, -8.91f), true);
        debugRenderer = new Box2DDebugRenderer();
    }

    public void step(){
        world.step(1/60f,3,3);
    }

    public void debugDraw(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
    }

    public void addObject(MapObjects objects) {
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape poly_h = new PolygonShape();
        CircleShape circle = new CircleShape();


        Iterator<MapObject> objectIterator = objects.iterator();
        while (objectIterator.hasNext()){
            MapObject obj = objectIterator.next();

            switch ((String)obj.getProperties().get("type")){
                case "StaticBody":
                    def.type = BodyDef.BodyType.StaticBody;
                    break;
                case "DynamicBody":
                    def.type = BodyDef.BodyType.DynamicBody;
                    break;
                case "KinematicBody":
                    def.type = BodyDef.BodyType.KinematicBody;
                    break;
                default:
            }


            fdef.restitution = (float) obj.getProperties().get("restitution");
            fdef.density = (float) obj.getProperties().get("density");
            fdef.friction = (float) obj.getProperties().get("friction");
            String name = (String) obj.getProperties().get("name");

//            if (obj.getName().equals("hero")){
//                hero = world.createBody(def);
//                hero.createFixture(fdef).setUserData(name);
//            } else{
//                world.createBody(def).createFixture(fdef).setUserData(name);
//            }



            switch (name) {
                case "wall" :
                    def.gravityScale =(float)obj.getProperties().get("gravityScale");
                    RectangleMapObject rect = (RectangleMapObject) obj;
                    def.position.set(new Vector2(rect.getRectangle().x + rect.getRectangle().width/2, rect.getRectangle().x + rect.getRectangle().height/2));
                    poly_h.setAsBox(rect.getRectangle().width/2, rect.getRectangle().height/2);
                    break;
                case "circle" :
                    EllipseMapObject ellipseMapObject = (EllipseMapObject) obj;
                    def.position.set(new Vector2(ellipseMapObject.getEllipse().x + ellipseMapObject.getEllipse().width/2,
                            ellipseMapObject.getEllipse().y +ellipseMapObject.getEllipse().height/2));
                    circle.setRadius(ellipseMapObject.getEllipse().width/2);
                    fdef.shape = circle;
                    break;
            }
            System.out.println();
            world.createBody(def).createFixture(fdef).setUserData(name);

        }
        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);
        bodies.get(0).getFixtureList();
        poly_h.dispose();
        circle.dispose();
    }

    public void dispose(){
        world.dispose();
        debugRenderer.dispose();
    }
}
