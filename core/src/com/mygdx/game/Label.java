package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Label {
    BitmapFont bitmapFont;

    public Label() {
        //FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator();
        bitmapFont = new BitmapFont();
       // bitmapFont.getData().setScale(3);
    }

    public void draw(SpriteBatch batch, String text){
        bitmapFont.draw(batch,text, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }
}
