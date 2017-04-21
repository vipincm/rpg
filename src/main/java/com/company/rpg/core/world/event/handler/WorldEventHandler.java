package com.company.rpg.core.world.event.handler;

import java.lang.reflect.ParameterizedType;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.world.event.WorldEvent;

/**
 * Abstract world event handler.
 * 
 * <p>
 * Override {@link #handleEvent(WorldEvent, AppContext)}
 * </p>
 * 
 * @author Vipin
 */
public abstract class WorldEventHandler<T extends WorldEvent> {

	/**
	 * Handles the incoming world event
	 * 
	 * @param event
	 *            to handle
	 * @param context
	 *            game context
	 */
	public abstract void handleEvent(T event, AppContext context);

	/**
	 * @return handled event type
	 */
	public Class<T> getHandledEventType() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
