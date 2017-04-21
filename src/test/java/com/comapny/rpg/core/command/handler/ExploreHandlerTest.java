package com.comapny.rpg.core.command.handler;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.command.Command;
import com.company.rpg.core.command.handler.ExploreHandler;
import com.company.rpg.core.world.GameWorld;
import com.company.rpg.core.world.event.generator.RandomWorldEventGenerator;
import com.company.rpg.core.world.event.generator.WorldEventGenerator;
import com.company.rpg.model.Coordinates;
import com.company.rpg.model.PlayerCharacter;
import com.company.rpg.util.IOUtils;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ExploreHandlerTest {
	// class under test
	private ExploreHandler handler;

	private IOUtils ioMock;
	private WorldEventGenerator eventGeneratorMock;

	private TestData testData;

	public ExploreHandlerTest(TestData testData) {
		this.testData = testData;
	}

	@Before
	public void setup() {
		ioMock = mock(IOUtils.class);
		eventGeneratorMock = mock(RandomWorldEventGenerator.class);
		handler = new ExploreHandler(ioMock);
	}

	@Parameters
	public static Collection<Object[]> generateTestData() {
		return Arrays.asList(
				new Object[]{new TestData("go east",  new Coordinates(4, 4), new Coordinates(5, 4), true)},
				new Object[]{new TestData("go east",  new Coordinates(9, 0), new Coordinates(9, 0), false)},
				new Object[]{new TestData("go west",  new Coordinates(4, 4), new Coordinates(3, 4), true)},
				new Object[]{new TestData("go west",  new Coordinates(0, 0), new Coordinates(0, 0), false)},
				new Object[]{new TestData("go north",  new Coordinates(4, 4), new Coordinates(4, 3), true)},
				new Object[]{new TestData("go north",  new Coordinates(0, 0), new Coordinates(0, 0), false)},
				new Object[]{new TestData("go south",  new Coordinates(4, 4), new Coordinates(4, 5), true)},
				new Object[]{new TestData("go south",  new Coordinates(0, 9), new Coordinates(0, 9), false)}
				);
	}
	
	@Test
	public void characterShouldTryToMoveInTheGivenDirection() {
		// given
		GameWorld world = new GameWorld(
				new Coordinates(9, 9), 
				new PlayerCharacter("Mark", 100, 100), 
				testData.characterCurrentLocation, 
				eventGeneratorMock,"gladiator");
		
		AppContext context = new AppContext();
		context.setWorld(world);

		// when
		handler.handle(Command.from(testData.command), context);

		// then
		assertThat(context.isTriggerEvent()).isEqualTo(testData.triggerEvent);
		assertThat(world.getPlayerCharacterLocation()).isEqualTo(testData.characterExpectedLocation);
	}


	private static class TestData {
		public String command;
		public Coordinates characterCurrentLocation;
		public Coordinates characterExpectedLocation;
		public boolean triggerEvent;
		
		public TestData(String direction, Coordinates characterCurrentLocation, Coordinates characterExpectedLocation,
				boolean triggerEvent) {
			super();
			this.command = direction;
			this.characterCurrentLocation = characterCurrentLocation;
			this.characterExpectedLocation = characterExpectedLocation;
			this.triggerEvent = triggerEvent;
		}
		
	}
}
