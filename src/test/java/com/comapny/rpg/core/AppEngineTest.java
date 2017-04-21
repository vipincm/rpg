package com.comapny.rpg.core;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.AppEngine;
import com.company.rpg.core.command.Command;
import com.company.rpg.core.command.CommandInput;
import com.company.rpg.core.command.handler.CommandHandlerManager;
import com.company.rpg.core.world.GameWorld;
import com.company.rpg.core.world.GameWorldProvider;
import com.company.rpg.core.world.event.WorldEvent;
import com.company.rpg.core.world.event.handler.WorldEventHandlerManager;
import com.company.rpg.util.IOUtils;

import org.junit.Before;
import org.junit.Test;

public class AppEngineTest {

	// class under test
	private AppEngine gameEngine;
	private CommandInput commandInputMock;
	private CommandHandlerManager commandHandlerMock;
	private WorldEventHandlerManager eventHandlerMock;
	private GameWorldProvider worldBuilder;
	private AppContext contextMock;
	private IOUtils ioMock;

	@Before
	public void setup() {
		commandInputMock = mock(CommandInput.class);
		commandHandlerMock = mock(CommandHandlerManager.class);
		worldBuilder = mock(GameWorldProvider.class);
		contextMock = mock(AppContext.class, RETURNS_DEEP_STUBS);
		eventHandlerMock = mock(WorldEventHandlerManager.class);
		ioMock = mock(IOUtils.class);
		gameEngine = new AppEngine(commandInputMock, commandHandlerMock, eventHandlerMock, worldBuilder, contextMock,
				ioMock);
	}

	@Test
	public void shouldInitializedGameContextWithProvidedWorldDuringStart() {
		// given
		GameWorld worldMock = mock(GameWorld.class);
		given(worldBuilder.get()).willReturn(worldMock);

		given(contextMock.isOngoing()).willReturn(false);

		// when
		gameEngine.start();

		// then
		verify(contextMock).setWorld(worldMock);
	}

	@Test
	public void shouldProcessUserInputOnGameEngineStart() {
		// given
		Command cmd = Command.from("go south");
		given(commandInputMock.get()).willReturn(cmd);
		given(contextMock.isOngoing()).willReturn(true, false);

		// when
		gameEngine.start();

		// then
		verify(commandHandlerMock).handle(cmd);
	}

	@Test
	public void shouldProcessWorldEventWhenTriggerEventIsSet() {
		// given
		GameWorld worldMock = mock(GameWorld.class);

		WorldEvent eventMock = mock(WorldEvent.class);
		given(worldMock.getEvent()).willReturn(eventMock);

		Command cmd = Command.from("go south");
		given(commandInputMock.get()).willReturn(cmd);

		given(contextMock.getWorld()).willReturn(worldMock);
		given(contextMock.isOngoing()).willReturn(true, false);
		given(contextMock.isTriggerEvent()).willReturn(true, false);

		// when
		gameEngine.start();

		// then
		verify(contextMock).resetTriggerEvent();
		verify(worldMock).getEvent();
		verify(eventHandlerMock).handle(eventMock);
	}

	@Test
	public void shouldNotProcessWorldEventWhenTriggerEventIsNotSet() {
		// given
		GameWorld worldMock = mock(GameWorld.class);

		WorldEvent eventMock = mock(WorldEvent.class);
		given(worldMock.getEvent()).willReturn(eventMock);

		Command cmd = Command.from("go south");
		given(commandInputMock.get()).willReturn(cmd);

		given(contextMock.getWorld()).willReturn(worldMock);
		given(contextMock.isOngoing()).willReturn(true, false);
		given(contextMock.isTriggerEvent()).willReturn(false, false);

		// when
		gameEngine.start();

		// then
		verify(contextMock, never()).resetTriggerEvent();
		verify(worldMock, never()).getEvent();
		verify(eventHandlerMock, never()).handle(eventMock);
	}
}
