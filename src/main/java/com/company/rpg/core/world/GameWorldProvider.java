package com.company.rpg.core.world;

import com.company.rpg.core.world.event.generator.WorldEventGenerator;
import com.company.rpg.model.Coordinates;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.model.RpgCharacter;
import com.company.rpg.util.FileUtils;
import com.company.rpg.util.IOUtils;

import java.io.IOException;
import java.util.List;

/**
 * Builds or retrieves saved {@link GameWorld} 
 * 
 * @author Vipin
 */
public class GameWorldProvider {
	private static final Coordinates WORLD_EDGE_VERTEX = new Coordinates(9, 9);
	private static final Coordinates CHARACTER_START_POINT = new Coordinates(0, 0);
	
	private final IOUtils io;
	private final GameWorldRepository gameWorldRepository;
	private final WorldEventGenerator eventGenerationStrategy;
	private final GameCharacterRoleBuilder gameCharacterRoleBuilder;

	public GameWorldProvider(IOUtils io, GameWorldRepository gameWorldRepository,
			WorldEventGenerator worldEventGenerationStrategy) {
		this.io = io;
		this.gameWorldRepository = gameWorldRepository;
		this.eventGenerationStrategy = worldEventGenerationStrategy;
		this.gameCharacterRoleBuilder = new GameCharacterRoleBuilder();
	}

	/**
	 * Builds (or retrieves saved) {@link GameWorld}
	 * 
	 * @return {@link GameWorld} instance
	 */
	public GameWorld get() {
		GameWorld world = retrieveWorld();

		if (world == null || !io.askYesNo("Restore saved game")) {
			world = createNewWorld();
		}

		return world;
	}

	private PlayerCharacter createNewCharacter() {
		return gameCharacterRoleBuilder.build();
	}

	private GameWorld createNewWorld() {
		List<String> topicList=FileUtils.listFileContents("topics");
		io.printList(topicList);
		String topicNumber = io.ask("Please choose topic number? ");
		String topicName=getTopicName(topicList, topicNumber);
		
		return new GameWorld(WORLD_EDGE_VERTEX, createNewCharacter(), CHARACTER_START_POINT, eventGenerationStrategy,topicName);
	}
	/**
	 * Getting topic name from the number.
	 * @param list
	 * @param topicNumber
	 * @return
	 */
	private String getTopicName(List<String> list, String topicNumber){
		 Integer topicSequence =Integer.parseInt(topicNumber);
		 if(topicSequence>list.size()){
			 io.print("Invalid topic choosen. Try again! ");
			 System.exit(0);
		 }
		return list.get(topicSequence-1).replace(" ", "_").toLowerCase();
	}


	private GameWorld retrieveWorld() {
		GameWorld world = null;
		try {
			world = gameWorldRepository.retrieve();
		} catch (IOException e) {
			io.say("Error retrieving world.\n");
		}

		return world;
	}

	private class GameCharacterRoleBuilder {
		private static final int DEFAULT_MAX_HEALTH = 100;
		private static final int DEFAULT_ATTACK_EXP = 100;

		/**
		 * Retrieves user input and build the character based on this
		 * 
		 * @return {@link RpgCharacter} from user input
		 */
		public PlayerCharacter build() {
			String name = io.ask("What is the name of your character? ");
			return new PlayerCharacter(name, DEFAULT_MAX_HEALTH, DEFAULT_ATTACK_EXP);
		}
	}
}
