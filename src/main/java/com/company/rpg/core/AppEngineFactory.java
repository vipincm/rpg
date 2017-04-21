package com.company.rpg.core;

import java.util.Arrays;

import com.company.rpg.core.command.Command;
import com.company.rpg.core.command.CommandInput;
import com.company.rpg.core.command.handler.AttackHandler;
import com.company.rpg.core.command.handler.CommandHandlerManager;
import com.company.rpg.core.command.handler.ExitHandler;
import com.company.rpg.core.command.handler.ExploreHandler;
import com.company.rpg.core.command.handler.MapHandler;
import com.company.rpg.core.command.handler.RunHandler;
import com.company.rpg.core.command.handler.StatusHandler;
import com.company.rpg.core.world.FileGameWorldRepository;
import com.company.rpg.core.world.GameWorldProvider;
import com.company.rpg.core.world.event.generator.RandomWorldEventGenerator;
import com.company.rpg.core.world.event.handler.EmptyWorldLocationEventHandler;
import com.company.rpg.core.world.event.handler.EnemyEncounterWorldEventHandler;
import com.company.rpg.core.world.event.handler.WorldEventHandlerManager;
import com.company.rpg.util.IOUtils;
import com.company.rpg.util.RandomUtils;

/**
 * Builds and injects all elements required by GameEngine.
 * <p>
 * TODO: Replace this with DI framework injectors when possible
 *
 * @author Vipin
 */
public class AppEngineFactory {
	// path name for serializing the game state.
	private static final String GAME_SAVE_PATH = "rpg.ser";
	private final IOUtils io = new IOUtils();
	private final RandomUtils randomNumberGenerator = new RandomUtils();

	/**
	 * Creates a new {@link AppEngine} instance.
	 *
	 * @return new {@link AppEngine} instance
	 */
	public AppEngine get() {
		AppContext context = getGameContext();
		GameWorldProvider worldBuilder = createGameWorldBuilder(context);
		CommandInput cmdInput = createCmdInput(context);
		CommandHandlerManager cmdHandlerManager = createHandlerManager(context);
		WorldEventHandlerManager eventHandlerManager = createEventHandlerManager(context);
		return new AppEngine(cmdInput, cmdHandlerManager, eventHandlerManager, worldBuilder, context, io);
	}

	private GameWorldProvider createGameWorldBuilder(AppContext context) {
		return new GameWorldProvider(io, new FileGameWorldRepository(GAME_SAVE_PATH),
				new RandomWorldEventGenerator(randomNumberGenerator,
						 context));
	}

	private AppContext getGameContext() {
		AppContext context = new AppContext();
		context.setOngoing(true);
		return context;
	}

	private CommandInput createCmdInput(AppContext context) {
		return () -> Command
				.from(io.ask("So, what should %s do now? ", context.getWorld().getPlayerCharacter().getName()));
	}

	private CommandHandlerManager createHandlerManager(AppContext gameContext) {
		// Add new command handlers here
		ExploreHandler exploreHandler = new ExploreHandler(io);
		RunHandler fleeHandler = new RunHandler(io, randomNumberGenerator);
		ExitHandler exitHandler = new ExitHandler(io, new FileGameWorldRepository(GAME_SAVE_PATH));
		AttackHandler attackHandler = new AttackHandler(io, randomNumberGenerator);
		StatusHandler statusHander = new StatusHandler(io);
		MapHandler mapHandler = new MapHandler(io);

		return new CommandHandlerManager(io, gameContext,
				Arrays.asList(exploreHandler, exitHandler, fleeHandler, attackHandler, statusHander, mapHandler));
	}

	private WorldEventHandlerManager createEventHandlerManager(AppContext gameContext) {
		// Add new world event handlers here
		EmptyWorldLocationEventHandler emptyLocationHandler = new EmptyWorldLocationEventHandler(io);
		EnemyEncounterWorldEventHandler enemyEncounterHandler = new EnemyEncounterWorldEventHandler(io,
				randomNumberGenerator);

		return new WorldEventHandlerManager(gameContext, Arrays.asList(emptyLocationHandler, enemyEncounterHandler));
	}

}
