package com.comapny.rpg.core.world;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.company.rpg.core.world.GameWorld;
import com.company.rpg.core.world.event.WorldEvent;
import com.company.rpg.core.world.event.generator.WorldEventGenerator;
import com.company.rpg.model.Coordinates;
import com.company.rpg.model.EnemyCharacter;
import com.company.rpg.model.PlayerCharacter;

import org.junit.Before;
import org.junit.Test;

public class GameWorldTest {
	// class under test
	private GameWorld gameWorld;
	private WorldEventGenerator eventGeneratorStrategy;
	private PlayerCharacter characterRoleMock;

	@Before
	public void setup() {
		eventGeneratorStrategy = mock(WorldEventGenerator.class);
		characterRoleMock = mock(PlayerCharacter.class);
		gameWorld = new GameWorld(new Coordinates(9, 9), characterRoleMock, new Coordinates(0, 0), eventGeneratorStrategy,"gladiator");
	}

	@Test
	public void shouldAskForGenerationOfNewWorldEventOnGet() {
		// given
		WorldEvent mockEvent = mock(WorldEvent.class);
		given(eventGeneratorStrategy.generate()).willReturn(mockEvent);
		gameWorld.movePlayerCharacter(new Coordinates(3, 3));

		// when
		WorldEvent peekedEvent = gameWorld.getEvent();
		
		// then
		verify(eventGeneratorStrategy).generate();
		assertThat(peekedEvent).isSameAs(mockEvent);
	}
	
	@Test
	public void shouldReturnCachedWorldEventForAlreadyRequestedLocation() {
		// given
		WorldEvent cachedEvent = mock(WorldEvent.class);
		given(eventGeneratorStrategy.generate()).willReturn(cachedEvent);
		gameWorld.movePlayerCharacter(new Coordinates(3, 3));

		// when
		WorldEvent peekedEvent = gameWorld.getEvent();
		
		// then
		verify(eventGeneratorStrategy).generate();
		assertThat(peekedEvent).isSameAs(cachedEvent);
	}
	
	@Test
	public void shouldAllowCharacterMoveWithinWorldBounderies() {
		// given
		Coordinates originalCoordinates = gameWorld.getPlayerCharacterLocation();

		// when
		boolean characterMoved = gameWorld.movePlayerCharacter(Coordinates.SOUTH_VECTOR);
		
		// then
		assertThat(characterMoved).isTrue();
		assertThat(originalCoordinates).isNotEqualTo(gameWorld.getPlayerCharacterLocation());
	}

	@Test
	public void shouldNotAllowCharacterMoveOutsideWorldBounderies() {
		// given
		Coordinates originalCoordinates = gameWorld.getPlayerCharacterLocation();
		
		// when
		boolean characterMoved = gameWorld.movePlayerCharacter(Coordinates.NORTH_VECTOR);
		
		// then
		assertThat(characterMoved).isFalse();
		assertThat(originalCoordinates).isEqualTo(gameWorld.getPlayerCharacterLocation());
	}
	
	@Test
	public void shouldMoveToOriginalCharacterLocationWhenFleeing() {
		// when
		EnemyCharacter enemyMock = mock(EnemyCharacter.class);
		gameWorld.setPlayerCharacterEnemy(enemyMock);
		
		Coordinates originalCharacterLocation = gameWorld.getPlayerCharacterLocation();
		gameWorld.movePlayerCharacter(Coordinates.SOUTH_VECTOR);
		
		// when
		gameWorld.fleePlayerCharacter();
		
		// then
		assertThat(gameWorld.getPlayerCharacterLocation()).isEqualTo(originalCharacterLocation);
		assertThat(gameWorld.hasPlayerCharacterEnemy()).isFalse();
	}
}
