package com.comapny.rpg.core.world;

import static org.fest.assertions.Assertions.assertThat;

import com.company.rpg.core.world.FileGameWorldRepository;
import com.company.rpg.core.world.GameWorld;
import com.company.rpg.core.world.GameWorldRepository;
import com.company.rpg.model.Coordinates;
import com.company.rpg.model.PlayerCharacter;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileGameWorldRepositoryTest {

	private static final String TEST_SAVE_FILE_PATH = "rpg_test.ser";

	// class under test
	private GameWorldRepository repository;

	@Before
	public void setup() {
		repository = new FileGameWorldRepository(TEST_SAVE_FILE_PATH);
	}

	@After
	public void tearDown() {
		new File(TEST_SAVE_FILE_PATH).delete();
	}

	@Test
	public void shouldReturnNullWhenNoSaveFound() throws IOException {
		// when
		GameWorld world = repository.retrieve();

		// then
		assertThat(world).isNull();
	}

	@Test
	public void shouldStoreWorldProperly() throws IOException {
		// given
		Coordinates edgeVertex = new Coordinates(1, 2);
		Coordinates characterLocation = new Coordinates(5, 5); 
		PlayerCharacter playerCharacter = new PlayerCharacter("Mark", 10, 10);
		GameWorld world = new GameWorld(edgeVertex, playerCharacter, characterLocation, null,null);
		
		// when
		repository.store(world);

		// then
		GameWorld retrievedWorld = repository.retrieve();
		assertThat(retrievedWorld).isNotNull();
		assertThat(retrievedWorld.getEdgeVertex()).isEqualTo(edgeVertex);
		assertThat(retrievedWorld.getPlayerCharacterLocation()).isEqualTo(characterLocation);
		assertThat(retrievedWorld.getPlayerCharacter()).isEqualTo(playerCharacter);
	}
}
