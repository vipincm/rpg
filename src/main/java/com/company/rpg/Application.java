package com.company.rpg;

import com.company.rpg.core.AppEngine;
import com.company.rpg.core.AppEngineFactory;

/**
 * Application entry point
 * 
 * @author Vipin
 */
public class Application {
	public static void main(String[] args) {
		AppEngineFactory engineProvider = new AppEngineFactory();
		AppEngine engine = engineProvider.get();
		engine.start();
	}
}
