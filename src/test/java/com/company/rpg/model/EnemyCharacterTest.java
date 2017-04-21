package com.company.rpg.model;

import static org.fest.assertions.Assertions.assertThat;
import com.company.rpg.model.EnemyCharacter.Hostality;

import org.junit.Test;

import com.company.rpg.model.EnemyCharacter;

public class EnemyCharacterTest {

	@Test
	public void friendlyCharacterShouldTurnToHostileCreatureWhenTakingDamage() {
		// given
		final int damage = 5;
		final String name = "Hobbit";
		final int maxHealth = 100;
		final int attack = 10;
		final Hostality hostality = Hostality.LOW;
		EnemyCharacter character = new EnemyCharacter(name, maxHealth, attack, hostality);
		assertThat(character.isHostile()).isFalse();
		
		// when
		character.takeDamage(damage);
		
		// then
		assertThat(character.isHostile()).isTrue();
		assertThat(character.getHealth()).isEqualTo(maxHealth - damage);
	}
}
