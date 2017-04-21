package com.company.rpg.core.command.handler;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.core.world.GameWorld;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.model.PlayerCharacter.State;
import com.company.rpg.util.IOUtils;
import com.company.rpg.util.RandomUtils;

/**
 * Handles the escape from an attack logic.
 * 
 * @author Vipin
 */
public class RunHandler extends CommandHandler {

	private static final String ACTION = "run";
	private static final String DESCR = "run away from an ongoing fight";

	private final IOUtils io;
	private final RandomUtils randomGenerator;

	public RunHandler(IOUtils io, RandomUtils randomGenerator) {
		super(ACTION, DESCR);
		this.io = io;
		this.randomGenerator = randomGenerator;
	}

	@Override
	public void handle(Command cmd, AppContext context) {
		GameWorld world = context.getWorld();
		PlayerCharacter playerCharacter = world.getPlayerCharacter();
		
		if (isNoReasonToFlee(world)) {
			handleNoReasonToFlee(playerCharacter);
		} else if (randomGenerator.nextBoolean()) {
			handleFleeSuccess(context, world, playerCharacter);
		} else {
			handleFleeFailure(context, world, playerCharacter);
		}
	}

	private boolean isNoReasonToFlee(GameWorld world) {
		return !world.hasPlayerCharacterEnemy() || 
				world.getPlayerCharacterEnemy().isDead() ||
			   !world.getPlayerCharacterEnemy().isHostile();
	}

	private void handleNoReasonToFlee(PlayerCharacter playerCharacter) {
		io.say("%s is scratching his head. There is nothing to run from.\n", playerCharacter.getName());
	}

	private void handleFleeSuccess(AppContext context, GameWorld world, PlayerCharacter playerCharacter) {
		io.say("%s is escaping %s to previous safe location.\n", playerCharacter.getName(), 
				world.getPlayerCharacterEnemy().getName());
		playerCharacter.setState(State.NORMAL);
		world.fleePlayerCharacter();
		context.triggerEvent();
	}

	private void handleFleeFailure(AppContext context, GameWorld world, PlayerCharacter playerCharacter) {
		io.say("%s is not able to escape from %s. Let him fight like a man.\n", playerCharacter.getName(), 
				world.getPlayerCharacterEnemy().getName());
		context.triggerEvent();
	}

}
