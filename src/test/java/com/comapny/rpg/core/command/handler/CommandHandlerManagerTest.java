package com.comapny.rpg.core.command.handler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.core.command.handler.CommandHandler;
import com.company.rpg.core.command.handler.CommandHandlerManager;
import com.company.rpg.util.IOUtils;

import java.util.Arrays;

import org.junit.Test;

public class CommandHandlerManagerTest {
	// class under test

	@Test
	public void shouldPassCommandToProperCommandHandler() {
		// given
		String action = "eat_sleep_code_repeat";
		CommandHandler handler = mock(CommandHandler.class);
		given(handler.getAction()).willReturn(action);
		
		IOUtils ioMock = mock(IOUtils.class);
		AppContext contextMock = mock(AppContext.class);
		
		CommandHandlerManager manager = new CommandHandlerManager(ioMock, contextMock, Arrays.asList(handler));
		Command cmd = Command.from(action);
		
		// when
		manager.handle(cmd);
		
		// then
		verify(handler).handle(cmd, contextMock);
	}
	
	@Test
	public void shouldPrintHelpOnUnknownCommand() {
		// given
		String action = "go_home_youre_drunk";
		CommandHandler handler = mock(CommandHandler.class);
		given(handler.getAction()).willReturn(action);
		
		IOUtils ioMock = mock(IOUtils.class);
		AppContext contextMock = mock(AppContext.class, RETURNS_DEEP_STUBS);
		
		CommandHandlerManager manager = new CommandHandlerManager(ioMock, contextMock, Arrays.asList(handler));
		Command cmd = Command.from("unknown_action");
		
		// when
		manager.handle(cmd);
		
		// then
		verify(handler, never()).handle(cmd, contextMock);
		verify(ioMock).say(anyString(), any());
	}
}
