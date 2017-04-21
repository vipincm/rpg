package com.company.rpg.util;

import java.io.Serializable;
import java.util.Random;

/**
 * Random object generator
 * 
 * @author Vipin
 */
public class RandomUtils implements Serializable {


	private static final long serialVersionUID = -7432541237785280483L;
	private final Random random = new Random();

	/**
	 * Generates random integer limited from top (inclusive)
	 * 
	 * @param limit
	 *            top limit (should be gt than 0)
	 * @return random integer
	 */
	public int nextInt(int limit) {
		if(limit >0) {
			return random.nextInt(limit + 1);
			
		}
		throw new IllegalArgumentException("limit should not be 0.");
		
	}

	/**
	 * Generates a random boolean
	 * 
	 * @return random boolean
	 */
	public boolean nextBoolean() {
		return nextInt(1) == 1;
	}

}
