package com.company.rpg.core.command.handler;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.core.world.GameWorld;
import com.company.rpg.model.Coordinates;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.util.IOUtils;

/**
 * Handles the world exploration logic
 * 
 * @author Vipin
 */
public class ExploreHandler extends CommandHandler {

	public static final String ACTION = "go";
	public static final String DESCR = "explores the world in the given direction <north|south|east|west>";

	public static final String NORTH = "north";
	public static final String SOUTH = "south";
	public static final String EAST = "east";
	public static final String WEST = "west";

	private final IOUtils io;

	public ExploreHandler(IOUtils io) {
		super(ACTION, DESCR);
		this.io = io;
	}

	@Override
	public void handle(Command cmd, AppContext context) {

		GameWorld world = context.getWorld();
		PlayerCharacter playerCharacter = world.getPlayerCharacter();

		if (playerCharacter.isNormal()) {
			if (cmd.getArgumentCount() == 1) {
				String direction = cmd.getArguments()[0];
				switch (direction) {
					case NORTH :
						moveCharacter(Coordinates.NORTH_VECTOR, context);
						break;
					case SOUTH :
						moveCharacter(Coordinates.SOUTH_VECTOR, context);
						break;
					case EAST :
						moveCharacter(Coordinates.EAST_VECTOR, context);
						break;
					case WEST :
						moveCharacter(Coordinates.WEST_VECTOR, context);
						break;
					default :
						io.say("%s doesn't understand that (%s) direction.\n", playerCharacter.getName(), direction);
						break;
				}
			} else {
				io.say("%s doesn't understand your command.\n", playerCharacter.getName());
				io.say("%s\t%s\n", ACTION, DESCR);
			}
		} else {
			io.say("%s is sort of busy right now..\n", playerCharacter.getName());
		}
	}

	private void moveCharacter(Coordinates direction, AppContext context) {
		GameWorld world = context.getWorld();
		PlayerCharacter playerCharacter = world.getPlayerCharacter();

		if (world.movePlayerCharacter(direction)) {
			context.triggerEvent();
		} else {
			io.say("Why would %s go there? He's on the edge of the world.\n", playerCharacter.getName());
		}
	}

}
