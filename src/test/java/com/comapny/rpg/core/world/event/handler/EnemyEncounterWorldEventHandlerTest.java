package com.comapny.rpg.core.world.event.handler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.world.event.EnemyEncounterWorldEvent;
import com.company.rpg.core.world.event.handler.EnemyEncounterWorldEventHandler;
import com.company.rpg.model.EnemyCharacter;
import com.company.rpg.model.EnemyCharacter.Hostality;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.util.IOUtils;
import com.company.rpg.util.RandomUtils;

import org.junit.Before;
import org.junit.Test;

public class EnemyEncounterWorldEventHandlerTest {
	// class under test
	private EnemyEncounterWorldEventHandler handler;
	private IOUtils ioMock;
	private RandomUtils randomUtils;
	
	@Before
	public void setup() {
		ioMock = mock(IOUtils.class);
		randomUtils = mock(RandomUtils.class);
		handler = new EnemyEncounterWorldEventHandler(ioMock, randomUtils);
	}
	
	@Test
	public void shouldNotTriggerAnyActionOnEncouteringDeadEnemy() {
		// given
		PlayerCharacter player = mock(PlayerCharacter.class, RETURNS_DEEP_STUBS);
		
		EnemyCharacter enemy = mock(EnemyCharacter.class, RETURNS_DEEP_STUBS);
		given(enemy.isDead()).willReturn(true);
		
		AppContext context = mock(AppContext.class, RETURNS_DEEP_STUBS);
		EnemyEncounterWorldEvent event = new EnemyEncounterWorldEvent(player, enemy);
		
		// when
		handler.handleEvent(event, context);
		
		// then
		verify(ioMock).say(anyString(), anyString(), anyString());
		verify(player, never()).takeDamage(anyInt());
	}
	
	@Test
	public void shouldTreatEnemyCharacterWithAnyHostalityAsPotentialEnemy() {
		// given
		PlayerCharacter player = mock(PlayerCharacter.class, RETURNS_DEEP_STUBS);
		
		EnemyCharacter enemy = mock(EnemyCharacter.class, RETURNS_DEEP_STUBS);
		given(enemy.getHostality()).willReturn(Hostality.LOW);
		
		AppContext context = mock(AppContext.class, RETURNS_DEEP_STUBS);
		EnemyEncounterWorldEvent event = new EnemyEncounterWorldEvent(player, enemy);
		
		// when
		handler.handleEvent(event, context);
		
		// then
		verify(context.getWorld()).setPlayerCharacterEnemy(enemy);
	}
	
	@Test
	public void shouldTakeDamageWhenEncounteringHighlyHostileEnemy() {
		// given
		final int expectedDamage = 5;
		given(randomUtils.nextInt(anyInt())).willReturn(expectedDamage);

		PlayerCharacter player = mock(PlayerCharacter.class, RETURNS_DEEP_STUBS);
		
		EnemyCharacter enemy = mock(EnemyCharacter.class, RETURNS_DEEP_STUBS);
		given(enemy.getHostality()).willReturn(Hostality.HIGH);
		
		AppContext context = mock(AppContext.class, RETURNS_DEEP_STUBS);
		EnemyEncounterWorldEvent event = new EnemyEncounterWorldEvent(player, enemy);
		
		
		// when
		handler.handleEvent(event, context);
		
		// then
		verify(player).takeDamage(expectedDamage);
	}
	
}
