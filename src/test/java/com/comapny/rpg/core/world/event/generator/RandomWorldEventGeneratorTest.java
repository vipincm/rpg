package com.comapny.rpg.core.world.event.generator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.company.rpg.core.AppContext;
import com.company.rpg.core.world.event.EmptyWorldLocationEvent;
import com.company.rpg.core.world.event.WorldEvent;
import com.company.rpg.core.world.event.generator.RandomWorldEventGenerator;
import com.company.rpg.util.RandomUtils;

public class RandomWorldEventGeneratorTest {
	// class under test
	private RandomWorldEventGenerator generator;
	private RandomUtils randomUtilsMock;
	private AppContext contextMock;

	@Before
	public void setup() {
		randomUtilsMock = mock(RandomUtils.class);
		contextMock = mock(AppContext.class, RETURNS_DEEP_STUBS);
		generator = new RandomWorldEventGenerator(randomUtilsMock, contextMock);
	}

	@Test
	public void shouldGenerateEmptyWorldEventForGivenRandomNumber() {
		// given
		given(randomUtilsMock.nextBoolean()).willReturn(true);

		// when
		WorldEvent event = generator.generate();

		// then
		assertThat(event).isInstanceOf(EmptyWorldLocationEvent.class);
	}

}
