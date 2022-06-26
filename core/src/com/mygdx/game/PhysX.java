package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class PhysX {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private Body hero;
    public Contact cl;

    public PhysX() {
        world = new World(new Vector2(0, -8.91f), true);
        debugRenderer = new Box2DDebugRenderer();
        cl = new Contact();
        world.setContactListener(cl);
    }

    public void step(){
        world.step(1/60f,3,3);
    }

    public void debugDraw(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
    }

    public Body getHero() {
        return hero;
    }

    public void setHeroForce(Vector2 force){hero.applyForceToCenter(force, true);}

    public void addObject(MapObject obj) {
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape poly_h = new PolygonShape();
        CircleShape circle = new CircleShape();

        String name= (String)obj.getProperties().get("name");

            switch ((String)obj.getProperties().get("type")){//Выбирает, какой из типов Body мы используем!
                case "StaticBody":
                    def.type = BodyDef.BodyType.StaticBody;
                    break;
                case "DynamicBody":
                    def.type = BodyDef.BodyType.DynamicBody;
                    break;
                case "KinematicBody":
                    def.type = BodyDef.BodyType.KinematicBody;
                    break;
                //default:
            }

        def.gravityScale = (float)obj.getProperties().get("gravityScale");
        def.awake =  (boolean)obj.getProperties().get("awake");

            fdef.restitution = (float) obj.getProperties().get("restitution");
            fdef.density = (float) obj.getProperties().get("density");
            fdef.friction = (float) obj.getProperties().get("friction");

            //String name = (String) obj.getProperties().get("name");
            //fdef.shape = poly_h;


            switch (name) {
                case "hero" :
                    def.gravityScale =(float)obj.getProperties().get("gravityScale");
                    RectangleMapObject rect2 = (RectangleMapObject) obj;
                    def.position.set(new Vector2(rect2.getRectangle().x + rect2.getRectangle().width/2, rect2.getRectangle().y + rect2.getRectangle().height/2));
                    poly_h.setAsBox(rect2.getRectangle().width/2, rect2.getRectangle().height/2);// Делим пополам, потому что координата в середине объекта!
                    fdef.shape = poly_h;
                    break;
                case "circle" :
                    EllipseMapObject ellipseMapObject = (EllipseMapObject) obj;
                    def.position.set(new Vector2(ellipseMapObject.getEllipse().x + ellipseMapObject.getEllipse().width/2,
                            ellipseMapObject.getEllipse().y +ellipseMapObject.getEllipse().height/2));
                    circle.setRadius(ellipseMapObject.getEllipse().width/2);
                    fdef.shape = circle;
                    break;
//                case "rock" :
//                    EllipseMapObject ellipseMapObject2 = (EllipseMapObject) obj;
//                    def.position.set(new Vector2(ellipseMapObject2.getEllipse().x + ellipseMapObject2.getEllipse().width/2,
//                            ellipseMapObject2.getEllipse().y +ellipseMapObject2.getEllipse().height/2));
//                    circle.setRadius(ellipseMapObject2.getEllipse().width/2);
//                    fdef.shape = circle;
//                    System.out.println();
//                    //def.awake = true;
//                    break;
            }

        if (obj.getName().equals("hero")) {
            hero = world.createBody(def);
            hero.createFixture(fdef).setUserData(name);
//            def.position.add(0,5);
            poly_h.setAsBox(3,5, new Vector2(0, -7),0);
            fdef.shape = poly_h;
            fdef.isSensor = true;
            hero.createFixture(fdef).setUserData("sensor");
            poly_h.setAsBox(5,100, new Vector2(0, 7),0);
            fdef.shape = poly_h;
            fdef.isSensor = true;
            hero.createFixture(fdef).setUserData("sensor");
            poly_h.setAsBox(3,5, new Vector2(7, 0),0);
            fdef.shape = poly_h;
            fdef.isSensor = true;
            hero.createFixture(fdef).setUserData("sensor");
            poly_h.setAsBox(3,5, new Vector2(-7, 0),0);
            fdef.shape = poly_h;
            fdef.isSensor = true;
            hero.createFixture(fdef).setUserData("sensor");
        } else {
            world.createBody(def).createFixture(fdef).setUserData(name);
        }


//        if(obj.getName().equals("hero")) {
//                def.gravityScale =(float)obj.getProperties().get("gravityScale");
//                RectangleMapObject rect2 = (RectangleMapObject) obj;
//                def.position.set(new Vector2(rect2.getRectangle().x + rect2.getRectangle().width/2, rect2.getRectangle().y + rect2.getRectangle().height/2));
//                poly_h.setAsBox(rect2.getRectangle().width/2, rect2.getRectangle().height/2);// Делим пополам, потому что координата в середине объекта!
//                fdef.shape = poly_h;
//                hero = world.createBody(def);
//                hero.createFixture(fdef).setUserData(name);
//            } else {
//                world.createBody(def).createFixture(fdef).setUserData(name);
//            }

            //world.createBody(def).createFixture(fdef).setUserData(name);
        poly_h.dispose();
        circle.dispose();
    }

    public void addObjects(MapObjects objects) {
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape poly_h = new PolygonShape();//Класс, описывающий форму тела!
        CircleShape circle = new CircleShape();


        Iterator<MapObject> objectIterator = objects.iterator();
        while (objectIterator.hasNext()){
            MapObject obj = objectIterator.next();

            switch ((String)obj.getProperties().get("type")){//Выбирает, какой из типов Body мы используем!
                case "StaticBody":
                    def.type = BodyDef.BodyType.StaticBody;
                    break;
                case "DynamicBody":
                    def.type = BodyDef.BodyType.DynamicBody;
                    break;
                case "KinematicBody":
                    def.type = BodyDef.BodyType.KinematicBody;
                    break;
                //default:
            }


//            if (obj.getName().equals("hero")){
//                hero = world.createBody(def);
//                hero.createFixture(fdef).setUserData(name);
//            } else{
//                world.createBody(def).createFixture(fdef).setUserData(name);
//            }

            //def.angle = (float) obj.getProperties().get("angle")*MathUtils.radiansToDegrees;
            def.awake =  (boolean)obj.getProperties().get("awake");
            fdef.restitution = (float) obj.getProperties().get("restitution");
            fdef.density = (float) obj.getProperties().get("density");
            fdef.friction = (float) obj.getProperties().get("friction");

            String name = (String) obj.getProperties().get("name");
            fdef.shape = poly_h;


            switch (name) {
                case "wall1" :
                    def.gravityScale =(float)obj.getProperties().get("gravityScale");
                    RectangleMapObject rect = (RectangleMapObject) obj;
                    def.position.set(new Vector2(rect.getRectangle().x + rect.getRectangle().width/2, rect.getRectangle().y + rect.getRectangle().height/2));
                    poly_h.setAsBox(rect.getRectangle().width/2, rect.getRectangle().height/2);// Делим пополам, потому что координата в середине объекта!
                    fdef.shape = poly_h;
                    System.out.println();
                    break;

                case "wall2" :
                    def.gravityScale =(float)obj.getProperties().get("gravityScale");
                    RectangleMapObject rect2 = (RectangleMapObject) obj;
                    //def.angularVelocity = (float) obj.getProperties().get("angle")*MathUtils.radiansToDegrees;
                    //def.angle = 45f;//*MathUtils.radiansToDegrees;
                    float hw = rect2.getRectangle().width/2,
                          hh = rect2.getRectangle().height/2;

                    def.position.set(new Vector2(rect2.getRectangle().x + rect2.getRectangle().width/2, rect2.getRectangle().y + rect2.getRectangle().height/2));
                    //rect2.getRectangle().setCenter(new Vector2(rect2.getRectangle().x + rect2.getRectangle().width/2, rect2.getRectangle().y + rect2.getRectangle().height/2));
                    float theAngle = obj.getProperties().get("angle", 0f, Float.class);
                    Vector2 Vcent = new Vector2();
                        theAngle *= -1f;
                       poly_h.setAsBox(hw, hh, Vcent.set(hw,-hh).rotateDeg(theAngle), theAngle*MathUtils.degreesToRadians);
                       def.position.set(rect2.getRectangle().getCenter(Vcent).add(-hw,hh));
//                    def.position.set(rect2.getRectangle().getCenter(Vcent).add(rect2.getRectangle().x -rect2.getRectangle().width, rect2.getRectangle().y + rect2.getRectangle().height));
//                    //poly_h.setAsBox(rect2.getRectangle().width/2, rect2.getRectangle().height/2);
//                    Vector2 VcentNew = new Vector2(rect2.getRectangle().x + rect2.getRectangle().width/2, rect2.getRectangle().y + rect2.getRectangle().height/2);
//                    poly_h.setAsBox(rect2.getRectangle().width/2, rect2.getRectangle().height/2, new Vector2(0, 0),(float) obj.getProperties().get("angle")*MathUtils.radiansToDegrees);// Делим пополам, потому что координата в середине объекта!
//                    rect2.getRectangle().setCenter(rect2.getRectangle().getCenter(new Vector2(VcentNew.x - rect2.getRectangle().width/2, VcentNew.y)));
//                    fdef.shape = poly_h;
                    //fdef.friction = (float) obj.getProperties().get("friction");

                    break;
                case "circle" :
                    EllipseMapObject ellipseMapObject = (EllipseMapObject) obj;
                    def.position.set(new Vector2(ellipseMapObject.getEllipse().x + ellipseMapObject.getEllipse().width/2,
                            ellipseMapObject.getEllipse().y +ellipseMapObject.getEllipse().height/2));
                    circle.setRadius(ellipseMapObject.getEllipse().width/2);
                    fdef.shape = circle;
                    break;
                case "rock" :
                    EllipseMapObject ellipseMapObject2 = (EllipseMapObject) obj;
                    def.position.set(new Vector2(ellipseMapObject2.getEllipse().x + ellipseMapObject2.getEllipse().width/2,
                            ellipseMapObject2.getEllipse().y +ellipseMapObject2.getEllipse().height/2));
                    circle.setRadius(ellipseMapObject2.getEllipse().width/2);
                    fdef.shape = circle;
                    System.out.println();
                    //def.awake = true;
                    break;

//                case "lader1" :
//                    def.gravityScale =(float)obj.getProperties().get("gravityScale");
//                    RectangleMapObject rect3 = (RectangleMapObject) obj;
//                    def.position.set(new Vector2(rect3.getRectangle().x + rect3.getRectangle().width/2, rect3.getRectangle().y + rect3.getRectangle().height/2));
//                    def.angle = (float)obj.getProperties().get("angle");
//                    poly_h.setAsBox(rect3.getRectangle().width/2, rect3.getRectangle().height/2);// Делим пополам, потому что координата в середине объекта!
////                    fdef.shape = poly_h;
//
//                    break;
            }
            System.out.println();
            Body newBody = world.createBody(def);
            newBody.createFixture(fdef).setUserData(name);
            newBody.setTransform(def.position,def.angle*MathUtils.radiansToDegrees);

        }
//        Array<Body> bodies = new Array<>(world.getBodyCount());
//        world.getBodies(bodies);
//        bodies.get(0).getFixtureList();
        poly_h.dispose();
        circle.dispose();
    }

    public void dispose(){
        world.dispose();
        debugRenderer.dispose();
    }

    public class Contact implements ContactListener {
        private int count;

        public boolean isOnGround() {return count>0;}

        @Override
        public void beginContact(com.badlogic.gdx.physics.box2d.Contact contact) {
            Fixture fa = contact.getFixtureA();
            Fixture fb = contact.getFixtureB();

            if (fa.getUserData() != null) {
                String s = (String)fa.getUserData();
                if (s.contains("sensor")){
                    count++;
                }
            }

            if (fb.getUserData() != null) {
                String s = (String)fb.getUserData();
                if (s.contains("rock")){
                    count++;
                }
            }
            if (fa.getUserData() != null) {
                String s = (String)fa.getUserData();
                if (s.contains("rock")){
                    fa.getBody().setAwake(true);
                }
            }

            if (fb.getUserData() != null) {
                String s = (String)fb.getUserData();
                if (s.contains("rock")){
                    fb.getBody().setAwake(true);
                }
            }
        }

        @Override
        public void endContact(com.badlogic.gdx.physics.box2d.Contact contact) {
            Fixture fa = contact.getFixtureA();
            Fixture fb = contact.getFixtureB();

            if (fa.getUserData() != null) {
                String s = (String)fa.getUserData();
                if (s.contains("sensor")){
                    count--;
                }
            }

            if (fb.getUserData() != null) {
                String s = (String)fb.getUserData();
                if (s.contains("sensor")){
                    count--;
                }
            }
        }

        @Override
        public void preSolve(com.badlogic.gdx.physics.box2d.Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(com.badlogic.gdx.physics.box2d.Contact contact, ContactImpulse impulse) {

        }
    }
}
