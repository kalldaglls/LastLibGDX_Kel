package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MyCharacter {
    private AnimPlayer idle, jump, walkRight;
    private boolean isJump, isWalk, dir;
    private Vector2 pos;
    private Rectangle rect;
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
        pos = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        rect = new Rectangle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2,
                walkRight.getFrame().getRegionWidth(),walkRight.getFrame().getRegionHeight());
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

    public TextureRegion getFrame(){
        TextureRegion tmpTex = null;
        if (!isJump && !isWalk && !dir) {
            idle.step(Gdx.graphics.getDeltaTime());
            idle.getFrame().flip(false, false);
            tmpTex = idle.getFrame();
        } else if (!isJump && !isWalk && dir) {
            idle.step(Gdx.graphics.getDeltaTime());
            idle.getFrame().flip(true, false);
            tmpTex = idle.getFrame();
        } else  if (!isJump && isWalk && !dir) {
            walkRight.step(Gdx.graphics.getDeltaTime());
            walkRight.getFrame().flip(false, false);
            tmpTex = walkRight.getFrame();
        } else  if (!isJump && isWalk && dir) {
            walkRight.step(Gdx.graphics.getDeltaTime());
            walkRight.getFrame().flip(true, false);
            tmpTex = walkRight.getFrame();
        }
        return tmpTex;
        }

    public Vector2 getPos() {
        return pos;
    }

    public Rectangle getRect() {
        return rect;
    }
}

