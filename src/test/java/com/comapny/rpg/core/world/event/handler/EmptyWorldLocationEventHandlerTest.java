package com.comapny.rpg.core.world.event.handler;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.world.event.EmptyWorldLocationEvent;
import com.company.rpg.core.world.event.handler.EmptyWorldLocationEventHandler;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.util.IOUtils;

import org.junit.Before;
import org.junit.Test;

public class EmptyWorldLocationEventHandlerTest {
	// class under test
	private EmptyWorldLocationEventHandler handler;
	private IOUtils ioMock;
	
	@Before
	public void setup() {
		ioMock = mock(IOUtils.class);
		handler = new EmptyWorldLocationEventHandler(ioMock);
	}
	
	@Test
	public void emptyWorldLocationEventShouldDiscardEnemy() {
		// given
		PlayerCharacter player = mock(PlayerCharacter.class, RETURNS_DEEP_STUBS);
		
		AppContext context = mock(AppContext.class, RETURNS_DEEP_STUBS);
		EmptyWorldLocationEvent event = new EmptyWorldLocationEvent(player);
		
		// when
		handler.handleEvent(event, context);
		
		// then
		verify(context.getWorld()).discardPlayerCharacterEnemy();
	}
	
}
