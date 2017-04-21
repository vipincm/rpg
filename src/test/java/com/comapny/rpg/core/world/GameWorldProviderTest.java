package com.comapny.rpg.core.world;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.company.rpg.core.world.FileGameWorldRepository;
import com.company.rpg.core.world.GameWorld;
import com.company.rpg.core.world.GameWorldProvider;
import com.company.rpg.core.world.GameWorldRepository;
import com.company.rpg.core.world.event.generator.WorldEventGenerator;
import com.company.rpg.util.IOUtils;

public class GameWorldProviderTest {
	// class under test
	private GameWorldProvider gameWorldProvider;
	private WorldEventGenerator eventGenerationMock;
	private GameWorldRepository gameWorldRepository;
	private IOUtils ioMock;
	
	@Before
	public void setup() {
		ioMock = mock(IOUtils.class);
		eventGenerationMock = mock(WorldEventGenerator.class);
		gameWorldRepository = mock(FileGameWorldRepository.class);
		gameWorldProvider = new GameWorldProvider(ioMock, gameWorldRepository, eventGenerationMock);
	}
	
	
	@Test
	public void shouldRestoreFoundSavedGameUponUserDecision() throws IOException {
		// given
		GameWorld storedWorldMock = mock(GameWorld.class);
		given(gameWorldRepository.retrieve()).willReturn(storedWorldMock);
		given(ioMock.askYesNo(anyString())).willReturn(true);
		
		// when
		GameWorld world = gameWorldProvider.get();
		
		// then
		assertThat(world).isNotNull();
		assertThat(world).isSameAs(storedWorldMock);
	}
	
}
