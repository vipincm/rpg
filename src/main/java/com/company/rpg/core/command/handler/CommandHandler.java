package com.company.rpg.core.command.handler;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;

/**
 * Abstract {@link Command} handler class. Encapsulates user action logic
 * interacting with the game world.
 * 
 * <p>
 * Override {@link #handle(Command)} when subclassing.
 * <p>
 * 
 * @author Vipin
 */
public abstract class CommandHandler {

	private final String action;
	private final String description;

	public CommandHandler(String action, String description) {
		this.action = action;
		this.description = description;
	}

	/**
	 * Handles the given command
	 * 
	 * @param cmd
	 *            to handle
	 * @param context
	 *            game context
	 */
	public abstract void handle(Command cmd, AppContext context);

	/**
	 * @return action name
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @return action description
	 */
	public String getDescription() {
		return description;
	}
}
