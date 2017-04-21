package com.company.rpg.core.world.event.generator;

import java.io.Serializable;
import java.util.List;

import com.company.rpg.model.EnemyCharacter;
import com.company.rpg.model.EnemyCharacter.Hostality;
import com.company.rpg.util.FileUtils;
import com.company.rpg.util.RandomUtils;

/**
 * Random Enemy factory
 * 
 * @author Vipin
 */
public class RandomEnemyFactory implements EnemyFactory, Serializable {
	private static final long serialVersionUID = 1L;
	private final  String topicName;
	private List<String> enemyNames ;
	private static final int MIN = 1;
	private static final int maxHealth = 100;
	private static final int maxAttack = 50;

	private final RandomUtils random;

	
	public RandomEnemyFactory(RandomUtils random, String topicName) {
		this.random = random;
		this.topicName=topicName;
	}
	
	
	@Override
	public EnemyCharacter createEnemy() {
		enemyNames =FileUtils.listFileContents(topicName);
		return new EnemyCharacter(getRandomName(), getRandomHealth(), getRandomAttack(), getRandomHostility());
	}

	private int getRandomAttack() {
		return Math.max(MIN, random.nextInt(maxAttack));
	}

	private String getRandomName() {
		return enemyNames.get(random.nextInt(enemyNames.size() - 1));
	}

	private int getRandomHealth() {
		return Math.max(MIN, random.nextInt(maxHealth));
	}

	private Hostality getRandomHostility() {
		return Hostality.values()[random.nextInt(Hostality.values().length - 1)];
	}
}
