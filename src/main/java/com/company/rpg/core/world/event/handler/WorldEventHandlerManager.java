package com.company.rpg.core.world.event.handler;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.world.event.WorldEvent;

/**
 * Manager for {@link WorldEvent} handlers
 *
 * <p>
 * Finds matching handler for requested {@link WorldEvent} and passes command to
 * found handler for processing.
 * </p>
 *
 * @author Vipin
 */
public class WorldEventHandlerManager {

	private final AppContext context;
	private Map<Class, WorldEventHandler> eventHandlerMap;

	public WorldEventHandlerManager(AppContext context, List<WorldEventHandler> handlers) {
		requireNonNull(context);
		requireNonNull(handlers);

		this.context = context;

		addHandlers(handlers);
	}

	private void addHandlers(List<WorldEventHandler> handlers) {
		eventHandlerMap = handlers.stream().collect(
				toMap(WorldEventHandler::getHandledEventType, Function.identity())
		);
	}

	/**
	 * Handles the incoming event.
	 *
	 * @param worldEvent
	 *            event to handle
	 */
	public void handle(WorldEvent worldEvent) {
        Optional.of(eventHandlerMap.get(worldEvent.getClass()))
                .ifPresent(handler -> handler.handleEvent(worldEvent, context));
	}
}
