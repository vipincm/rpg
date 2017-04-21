package com.company.rpg.core.command.handler;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.util.IOUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Manager for Command handlers
 * 
 * <p>
 * Finds matching handler for requested {@link Command} and passes command to
 * found handler for processing.
 * </p>
 * 
 * @author Vipin
 */
public class CommandHandlerManager {

	private final AppContext context;
	private final IOUtils io;
	private Map<String, CommandHandler> cmdHandlerMap;

	public CommandHandlerManager(IOUtils io, AppContext context, List<CommandHandler> handlers) {
		requireNonNull(io);
		requireNonNull(context);
		requireNonNull(handlers);

		this.io = io;
		this.context = context;
		
		addHandlers(handlers);
	}

	private void addHandlers(List<CommandHandler> handlers) {
		cmdHandlerMap = handlers.stream().collect(
				toMap(CommandHandler::getAction, 
					  Function.identity(),
					  (u,v) -> { throw new IllegalStateException(String.format("Action name not unique %s", u)); },
					  TreeMap::new)
				);
	}

	/**
	 * Handles the incoming command
	 * 
	 * @param command
	 *            to handle
	 */
	public void handle(Command command) {

		CommandHandler handler = cmdHandlerMap.get(command.getAction());
		if (handler != null) {
			handler.handle(command, context);
		} else {
			printUnknown(command);
			printHelp();
		}
	}

	/**
	 * Prints user command help
	 */
	public void printHelp() {
		io.say("%s understands only the following commands:\n", context.getWorld().getPlayerCharacter().getName());
		
		cmdHandlerMap.forEach((action, handler) -> {
			printCommandHelp(action, handler.getDescription());
		});
	}
	
	private void printUnknown(Command command) {
		io.say("%s? I don't think %s knows what you mean..\n", command.getAction(), 
				context.getWorld().getPlayerCharacter().getName());
	}

	private void printCommandHelp(String action, String description) {
		io.say("\t%-20s\t%s\n", action, description);
	}
}
