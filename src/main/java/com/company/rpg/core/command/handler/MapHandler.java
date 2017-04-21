package com.company.rpg.core.command.handler;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.core.world.GameWorld;
import com.company.rpg.core.world.event.EmptyWorldLocationEvent;
import com.company.rpg.core.world.event.EnemyEncounterWorldEvent;
import com.company.rpg.core.world.event.WorldEvent;
import com.company.rpg.model.Coordinates;
import com.company.rpg.util.IOUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the visual (ASCII) representation of the map.
 * 
 * @author Vipin
 */
public class MapHandler extends CommandHandler {

	private static final String ACTION = "map";
	private static final String DESCR = "displays the world map";
	public static final Character DISCOVERED = '_';
	private static final Character UNDISCOVERED = 'X';
	private static final Character ENEMY = 'E';
	private static final Character PLAYER = 'P';
	private static final Character UNKNOWN = '?';
	private static final Coordinates startPoint = new Coordinates(0, 0);
	private static final Map<Class<? extends WorldEvent>, Character> eventToMapSymbol;

	private final IOUtils io;

	static {
		eventToMapSymbol = new HashMap<>();
		eventToMapSymbol.put(EmptyWorldLocationEvent.class, DISCOVERED);
		eventToMapSymbol.put(EnemyEncounterWorldEvent.class, ENEMY);
	}

	public MapHandler(IOUtils io) {
		super(ACTION, DESCR);
		this.io = io;
	}

	@Override
	public void handle(Command cmd, AppContext context) {
		GameWorld world = context.getWorld();
		Coordinates worldEdge = world.getEdgeVertex();

		for (int y = 0; y <= worldEdge.getY(); y++) {
			for (int x = 0; x <= worldEdge.getX(); x++) {
				Coordinates coordinates = new Coordinates(x, y);
				if (coordinates.equals(world.getPlayerCharacterLocation())) {
					printMapSymbol(PLAYER);
				} else if (coordinates.equals(startPoint)) {
					printMapSymbol(DISCOVERED);
				} else {
					WorldEvent event = world.peek(coordinates);
					if (event != null) {
						Character symbol = eventToMapSymbol.get(event.getClass());
						if (symbol != null) {
							printMapSymbol(symbol);
						} else {
							printMapSymbol(UNKNOWN);
						}
					} else {
						printMapSymbol(UNDISCOVERED);
					}
				}
			}
			io.say('\n');
		}

		io.say("\nLegend:\n");
		io.say(" [%c] - discovered terrain\n", DISCOVERED);
		io.say(" [%c] - undiscovered terrain\n", UNDISCOVERED);
		io.say(" [%c] - enemy\n", ENEMY);
		io.say(" [%c] - player\n", PLAYER);

	}

	private void printMapSymbol(Character character) {
		io.say("|%c|", character);
	}

}
