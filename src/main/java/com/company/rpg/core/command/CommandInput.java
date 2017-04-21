package com.company.rpg.core.command;


/**
 * Interface used for obtaining commands from User
 * 
 * @author Vipin
 */
public interface CommandInput {
	/**
	 * Retrieves command from user
	 * 
	 * @return retrieved {@link Command}
	 */
	Command get();
}
