package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.MainClass;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("My GDX Game");
//		new Lwjgl3Application(new MyGdxGame(), config);

		new Lwjgl3Application(new Main(), config);




//		List<String> list = new ArrayList<>();
//		for (int i = 1; i < 7; i++) {
//			list.add("Строка " + i);
//		}
//
//		Iterator<String> iterator = list.iterator();
//		//При таком удалении итератор изменяет и свой размер на один, и размер list!!!
//
//		while (iterator.hasNext()) {
//			if (iterator.next().equals("Строка 2")) {
//				iterator.remove();
//			}
//		}
//
//		list.add("Строка 2");
//
//		for (String str: list) {
//			if (str.equals("Строка 2")) {
//				list.remove(str);
//			}
//		}
	}
}
