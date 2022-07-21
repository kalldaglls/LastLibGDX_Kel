package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coin {
    private Texture texture;
    private AnimPlayer animPlayer;
    private Vector2 position;
    private Rectangle rectangle;
    private float width;
    private float height;
    private Sound sound;
    private int state;
    private float time;

    public Coin(Vector2 position) {
        animPlayer = new AnimPlayer("Full Coinss.png", 8, 1, 10, Animation.PlayMode.LOOP);
        this.position = new Vector2(position);
//        this.width = width;
//        this.height = height;
        rectangle = new Rectangle(position.x, position.y, animPlayer.getFrame().getRegionWidth(), animPlayer.getFrame().getRegionHeight());
        sound = Gdx.audio.newSound(Gdx.files.internal("SoundEffect.mp3"));
        //sound.play(0.5f, 1, 0);//pitch растягивает звук
    }

    public void setState() {
        sound.play(0.5f, 1, 0);
        time = 0.125f;
        state = 1;
    }

    public int draw(SpriteBatch batch, OrthographicCamera camera){
        animPlayer.step(Gdx.graphics.getDeltaTime());
//        float cx = (position.x - camera.position.x)/camera.zoom + Gdx.graphics.getWidth()/2;// Не понял конструкцию?
//        float cy = (position.y - camera.position.y)/camera.zoom + Gdx.graphics.getHeight()/2;
////        camera.zoom = 0.5f;
//
//        batch.draw(animPlayer.getFrame(), cx, cy);

        float cx = (rectangle.x - camera.position.x)/camera.zoom + Gdx.graphics.getWidth()/2;
        float cy = (rectangle.y - camera.position.y)/camera.zoom + Gdx.graphics.getHeight()/2;
        float cW = rectangle.getWidth() / camera.zoom / 2;
        float cH = rectangle.getHeight() / camera.zoom / 2;
        batch.draw(animPlayer.getFrame(), cx, cy, cW, cH);

        if (state == 1) time -= Gdx.graphics.getDeltaTime();
        if (time < 0) {
            state = 2;
        }

//        camera.zoom = 0.25f;

        return state;
    }

//    public void shapeDraw(ShapeRenderer renderer, OrthographicCamera camera){
//
//        float cx = (rectangle.x - camera.position.x)/camera.zoom + Gdx.graphics.getWidth()/2;
//        float cy = (rectangle.y - camera.position.y)/camera.zoom + Gdx.graphics.getHeight()/2;
//
//        renderer.rect(cx, cy, rectangle.getWidth(), rectangle.getHeight());
//    }

    public void shapeDraw(ShapeRenderer renderer, OrthographicCamera camera) {
        float cx = (rectangle.x - camera.position.x)/camera.zoom + Gdx.graphics.getWidth()/2;
        float cy = (rectangle.y - camera.position.y)/camera.zoom + Gdx.graphics.getHeight()/2;
        float cW = rectangle.getWidth() / camera.zoom / 2;
        float cH = rectangle.getHeight() / camera.zoom / 2;
        renderer.rect(cx, cy, cW, cH);
//        camera.zoom = 0.25f;
    }

    public boolean isOverlaps(Rectangle heroRect, OrthographicCamera camera) {
        float cx = (rectangle.x - camera.position.x)/camera.zoom + Gdx.graphics.getWidth()/2;
        float cy = (rectangle.y - camera.position.y)/camera.zoom + Gdx.graphics.getHeight()/2;
//        Rectangle rect = new Rectangle(cx, cy, rectangle.width, rectangle.height);
//        return rect.overlaps(heroRect);
        float cW = rectangle.getWidth() * camera.zoom;
        float cH = rectangle.getHeight() * camera.zoom;
        Rectangle rect = new Rectangle(cx, cy, cW, cH);
        return heroRect.overlaps(rect);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void dispose(){
        animPlayer.dispose();
        sound.dispose();
    }
}
