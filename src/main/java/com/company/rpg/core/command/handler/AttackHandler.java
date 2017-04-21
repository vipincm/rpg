package com.company.rpg.core.command.handler;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.core.world.GameWorld;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.model.PlayerCharacter.State;
import com.company.rpg.model.RpgCharacter;
import com.company.rpg.util.IOUtils;
import com.company.rpg.util.RandomUtils;

/**
 * Handles the character attack logic. 
 * 
 * @author Vipin
 */
public class AttackHandler extends CommandHandler {

	private static final String ACTION = "attack";
	private static final String DESCR = "attacks the encountered enemy";

	private static final int EXPERIENCE_GAIN = 5;
	private static final int HEALTH_RECOVERY = 5;

	private final IOUtils io;
	private final RandomUtils randomUtils;

	public AttackHandler(IOUtils io, RandomUtils randomGenerator) {
		super(ACTION, DESCR);
		this.io = io;
		this.randomUtils = randomGenerator;
	}

	@Override
	public void handle(Command cmd, AppContext context) {
		GameWorld world = context.getWorld();
		PlayerCharacter playerCharacter = world.getPlayerCharacter();

		if (!world.hasPlayerCharacterEnemy()) {
			io.say("%s is shadow boxing.\n", playerCharacter.getName());
		} else if (world.getPlayerCharacterEnemy().isDead()) {
			io.say("Why would %s attack a corpse?\n", playerCharacter.getName());
		} else {
			attack(context, world, playerCharacter);
		}
	}

	private void attack(AppContext context, GameWorld world, PlayerCharacter playerCharacter) {
		RpgCharacter enemy = world.getPlayerCharacterEnemy();
		int damage = randomUtils.nextInt(playerCharacter.getAttack());
		enemy.takeDamage(damage);
		
		if (enemy.isDead()) {
			handleEnemyKilled(world, playerCharacter, enemy);
		} else if(damage == 0){
			handleNoDamageGiven(context, playerCharacter, enemy);
		} else {
			handleDamageGiven(context, playerCharacter, enemy, damage);
		}
	}

	private void handleEnemyKilled(GameWorld world, PlayerCharacter playerCharacter, RpgCharacter enemy) {
		io.say("%s has killed and then eaten the %s.\n", playerCharacter.getName(), enemy.getName());
		io.say(" +%d combat experience gained\n", EXPERIENCE_GAIN);
		
		playerCharacter.setAttack(playerCharacter.getAttack() + EXPERIENCE_GAIN);
		
		if (playerCharacter.getHealth() < playerCharacter.getMaxHealth()) {
			int healthRecovered = Math.min(HEALTH_RECOVERY,	playerCharacter.getMaxHealth() - playerCharacter.getHealth());
			io.say(" +%d health recovered from food\n", healthRecovered);
			playerCharacter.incrHealth(healthRecovered);
		}
		
		playerCharacter.setState(State.NORMAL);
		world.discardPlayerCharacterEnemy();
	}

	private void handleNoDamageGiven(AppContext context, PlayerCharacter playerCharacter, RpgCharacter enemy) {
		io.say("%s has attacked %s, but no damage given.\n", playerCharacter.getName(), enemy.getName());
		context.triggerEvent();
	}

	private void handleDamageGiven(AppContext context, PlayerCharacter playerCharacter, RpgCharacter enemy, int damage) {
		io.say("%s is attacking %s. %d damage given.\n", playerCharacter.getName(), enemy.getName(), damage);
		context.triggerEvent();
	}
}
