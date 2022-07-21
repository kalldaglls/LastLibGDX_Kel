package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Label {
   private BitmapFont bitmapFont;

    public Label(int size) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("comic shanns.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = size;
        fontParameter.characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM,.!/':;0123456789+-=";
        bitmapFont = new BitmapFont();
       // bitmapFont.getData().setScale(3);
    }

//    public void draw(SpriteBatch batch, String text){
//        bitmapFont.draw(batch,text, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
//    }

    public void draw(SpriteBatch batch, String text, int x, int y) {
        bitmapFont.draw(batch, text, x, y + bitmapFont.getLineHeight());
    }
}
