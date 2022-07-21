package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MyCharacter {
    private AnimPlayer idle, jump, walkRight;
    private boolean isJump, isWalk, dir;
    private Vector2 pos;
    private Rectangle rect;
    private float dTime;
//    private int x,y;

    public MyCharacter() {
//        this.idle = idle;
//        this.jump = jump;
//        this.walkRight = walkRight;
//        this.isJump = isJump;
//        this.isWalk = isWalk;
//        this.dir = dir;
//        this.pos = pos;
//        this.rect = rect;

        idle = new AnimPlayer("hero/idle.png", 1, 1, 16.0f, Animation.PlayMode.LOOP);
        jump = new AnimPlayer("hero/jump.png", 1, 1, 16.0f, Animation.PlayMode.LOOP);
        walkRight = new AnimPlayer("hero/runRight.png", 4, 1, 16.0f, Animation.PlayMode.LOOP);

//        x = Gdx.graphics.getWidth()/2;
//        y = Gdx.graphics.getHeight()/2;
//        x = 0;
//        y = 0;
//        pos = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
//
//        rect = new Rectangle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,
//                walkRight.getFrame().getRegionWidth(),walkRight.getFrame().getRegionHeight());

        pos = new Vector2(Gdx.graphics.getWidth()/2 - idle.getFrame().getRegionWidth()/2,
                Gdx.graphics.getHeight()/2 - idle.getFrame().getRegionHeight()/2);
        rect = new Rectangle(pos.x, pos.y, walkRight.getFrame().getRegionWidth(), walkRight.getFrame().getRegionHeight());
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public void setDir(boolean dir) {
        this.dir = dir;
    }

    public void setWalk(boolean walk) {
        isWalk = walk;
    }

    public void setdTime(float dTime) {
        this.dTime = dTime;
    }

    public TextureRegion getFrame(){
//        dTime = Gdx.graphics.getDeltaTime();
        TextureRegion tmpTex; //= null;
        if (!isJump && !isWalk && !dir) {//Начинаем писать 1:47:10!!!
            idle.step(Gdx.graphics.getDeltaTime());
            if(idle.getFrame().isFlipX()) idle.getFrame().flip(true, false);//1:51:10 Про flip!!!2:01:30 говорит, зеркалировать текстурный регион или не зеркалировать по иксу и по игрику!
            tmpTex = idle.getFrame();
        } else if (!isJump && !isWalk && dir) {
            idle.step(Gdx.graphics.getDeltaTime());
            if(!idle.getFrame().isFlipX()) idle.getFrame().flip(true, false);
            tmpTex = idle.getFrame();
        } else  if (!isJump && isWalk && !dir) {
            walkRight.step(Gdx.graphics.getDeltaTime());
            if(walkRight.getFrame().isFlipX()) walkRight.getFrame().flip(true, false);
            tmpTex = walkRight.getFrame();
        } else  if (!isJump && isWalk && dir) {
            walkRight.step(Gdx.graphics.getDeltaTime());
           if(!walkRight.getFrame().isFlipX()) walkRight.getFrame().flip(true, false);
            tmpTex = walkRight.getFrame();
        } else  if (isJump && isWalk && dir) {
            jump.step(Gdx.graphics.getDeltaTime());
            if (!jump.getFrame().isFlipX()) jump.getFrame().flip(true, false);
            tmpTex = jump.getFrame();
        } else {
            jump.step(Gdx.graphics.getDeltaTime());
            if (jump.getFrame().isFlipX()) jump.getFrame().flip(true, false);
            tmpTex = jump.getFrame();
        }
        return tmpTex;
        }

    public Vector2 getPos() {
        return pos;
    }

//    public Rectangle getRect() {
//        return rect;
//    }

    public Rectangle getRect(OrthographicCamera camera) {
        float cx = Gdx.graphics.getWidth()/2 - ((rect.width/2) / camera.zoom);
        float cy = Gdx.graphics.getHeight()/2 - ((rect.height/2) / camera.zoom);
        float cW = rect.getWidth() / camera.zoom;
        float cH = rect.getHeight() / camera.zoom;
        return new Rectangle(cx , cy, cW, cH);
    }

    public void shapeDraw(ShapeRenderer renderer, OrthographicCamera camera) {
        float cx = Gdx.graphics.getWidth()/2 - ((rect.width/2) / camera.zoom);
        float cy = Gdx.graphics.getHeight()/2 - ((rect.height/2) / camera.zoom);
        float cW = rect.getWidth() / camera.zoom;
        float cH = rect.getHeight() / camera.zoom;
        renderer.rect(cx, cy, cW, cH);
    }
}

