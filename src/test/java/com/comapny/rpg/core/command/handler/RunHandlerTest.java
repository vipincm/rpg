package com.comapny.rpg.core.command.handler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.core.command.handler.RunHandler;
import com.company.rpg.model.Coordinates;
import com.company.rpg.model.EnemyCharacter;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.util.IOUtils;
import com.company.rpg.util.RandomUtils;

import org.junit.Before;
import org.junit.Test;

public class RunHandlerTest {
	// class under test
	private RunHandler handler;
	private IOUtils ioMock;
	private RandomUtils randomUtilsMock;

	@Before
	public void setup() {
		ioMock = mock(IOUtils.class);
		randomUtilsMock = mock(RandomUtils.class);
		handler = new RunHandler(ioMock, randomUtilsMock);
	}

	@Test
	public void characterShouldNotFleeIfThereIsNoEnemy() {
		// given
		AppContext contextMock = mock(AppContext.class, RETURNS_DEEP_STUBS);
		PlayerCharacter characterMock = mock(PlayerCharacter.class);
		Coordinates location = new Coordinates(5, 5);
		given(contextMock.getWorld().getPlayerCharacterLocation()).willReturn(location);
		given(contextMock.getWorld().hasPlayerCharacterEnemy()).willReturn(false);
		given(contextMock.getWorld().getPlayerCharacter()).willReturn(characterMock);
		Command cmd = mock(Command.class);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock, never()).triggerEvent();
		verify(contextMock.getWorld(), never()).fleePlayerCharacter();
	}
	
	@Test
	public void characterShouldFailToFleeFromEnemy() {
		// given
		AppContext contextMock = mock(AppContext.class, RETURNS_DEEP_STUBS);
		PlayerCharacter characterMock = mock(PlayerCharacter.class);
		Coordinates location = new Coordinates(5, 5);
		given(contextMock.getWorld().getPlayerCharacterLocation()).willReturn(location);
		EnemyCharacter enemy = mock(EnemyCharacter.class);
		given(enemy.isHostile()).willReturn(true);
		given(contextMock.getWorld().hasPlayerCharacterEnemy()).willReturn(true);
		given(contextMock.getWorld().getPlayerCharacterEnemy()).willReturn(enemy);
		given(contextMock.getWorld().getPlayerCharacter()).willReturn(characterMock);
		given(randomUtilsMock.nextInt(anyInt())).willReturn(1);
		Command cmd = mock(Command.class);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock).triggerEvent();
		verify(contextMock.getWorld(), never()).fleePlayerCharacter();
		verify(contextMock.getWorld(), never()).discardPlayerCharacterEnemy();
	}
	
	@Test
	public void characterShouldFleeFromOngoingFight() {
		// given
		AppContext contextMock = mock(AppContext.class, RETURNS_DEEP_STUBS);
		PlayerCharacter characterMock = mock(PlayerCharacter.class);
		Coordinates location = new Coordinates(5, 5);
		given(contextMock.getWorld().getPlayerCharacterLocation()).willReturn(location);
		EnemyCharacter enemy = mock(EnemyCharacter.class);
		given(enemy.isHostile()).willReturn(true);
		given(contextMock.getWorld().hasPlayerCharacterEnemy()).willReturn(true);
		given(contextMock.getWorld().getPlayerCharacterEnemy()).willReturn(enemy);
		given(contextMock.getWorld().getPlayerCharacter()).willReturn(characterMock);
		given(randomUtilsMock.nextBoolean()).willReturn(true);
		Command cmd = mock(Command.class);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock).triggerEvent();
		verify(contextMock.getWorld()).fleePlayerCharacter();
	}

}

