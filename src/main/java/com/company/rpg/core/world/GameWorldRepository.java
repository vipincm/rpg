package com.company.rpg.core.world;

import java.io.IOException;

/**
 * Repository interface for persisting game progress from {@link GameWorld}
 * 
 * @author Vipin
 */
public interface GameWorldRepository {

	/**
	 * Stores {@link GameWorld}
	 * 
	 * @param world
	 *            to store
	 * @throws IOException
	 *             if storing fails
	 */
	void store(GameWorld world) throws IOException;

	/**
	 * Retrieves stored {@link GameWorld}
	 * 
	 * @return {@link GameWorld} if was stored, null otherwise
	 * @throws IOException
	 *             if retrieval of stored game fails
	 */
	GameWorld retrieve() throws IOException;

}