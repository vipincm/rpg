package com.comapny.rpg.core.command.handler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.core.command.handler.ExitHandler;
import com.company.rpg.core.world.FileGameWorldRepository;
import com.company.rpg.core.world.GameWorld;
import com.company.rpg.util.IOUtils;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ExitHandlerTest {
	// class under test
	private ExitHandler handler;
	private IOUtils ioMock;
	private FileGameWorldRepository gameWorldRepositoryMock;

	@Before
	public void setup() {
		gameWorldRepositoryMock = mock(FileGameWorldRepository.class);
		ioMock = mock(IOUtils.class);
		handler = new ExitHandler(ioMock, gameWorldRepositoryMock);
	}

	@Test
	public void shouldSetGameStateOngoingToFalseOnExitHandle() {
		// given
		AppContext contextMock = mock(AppContext.class);
		Command cmd = mock(Command.class);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock).setOngoing(false);
	}

	@Test
	public void shouldExitGameWithoutSavingGameUponUserDecision() throws IOException {
		// given
		AppContext contextMock = mock(AppContext.class);
		Command cmd = mock(Command.class);
		given(ioMock.askYesNo(anyString())).willReturn(false);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock).setOngoing(false);
		verify(ioMock).askYesNo(anyString());
		verify(gameWorldRepositoryMock, never()).store(any(GameWorld.class));
	}
	
	@Test
	public void shouldExitGameSavingGameUponUserDecision() throws IOException {
		// given
		AppContext contextMock = mock(AppContext.class);
		Command cmd = mock(Command.class);
		given(ioMock.askYesNo(anyString())).willReturn(true);

		// when
		handler.handle(cmd, contextMock);

		// then
		verify(contextMock).setOngoing(false);
		verify(ioMock).askYesNo(anyString());
		verify(gameWorldRepositoryMock).store(any(GameWorld.class));
	}
}
