package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Coin {
    private Texture texture;
    private AnimPlayer animPlayer;
    private Vector2 position;

    public Coin(Vector2 position) {
        animPlayer = new AnimPlayer("Full Coinss.png", 8, 1, 10, Animation.PlayMode.LOOP);
        this.position = new Vector2(position);
    }

    public void draw(SpriteBatch batch, OrthographicCamera camera){
        animPlayer.step(Gdx.graphics.getDeltaTime());
        float cx = (position.x - camera.position.x)/camera.zoom + Gdx.graphics.getWidth();
        float cy = (position.y - camera.position.y)/camera.zoom + Gdx.graphics.getHeight();
        //camera.zoom = 2.5f;

        batch.draw(animPlayer.getFrame(), cx, cy);
    }

    public void dispose(){
        animPlayer.dispose();
    }
}
