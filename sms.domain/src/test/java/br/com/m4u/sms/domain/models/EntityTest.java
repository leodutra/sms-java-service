package br.com.m4u.sms.domain.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import br.com.m4u.sms.domain.model.Entity;
import br.com.m4u.sms.infrastructure.crosscutting.test.categories.UnitTest;


@Category(UnitTest.class)
public class EntityTest {

	@Test
	public void should_getId_when_idWasSet() throws Exception {

		// Given
		Entity<Long> longIdEntity = new Entity<Long>() {};
		Entity<Integer> intIdEntity = new Entity<Integer>() {};
		Entity<String> stringIdEntity = new Entity<String>() {};

		Long longId = 1L;
		Integer intId = 2;
		String stringId = "A";

		// When
		longIdEntity.setId(longId);
		intIdEntity.setId(intId);
		stringIdEntity.setId(stringId);

		// Then
		Assert.assertEquals(longId, longIdEntity.getId());
		Assert.assertEquals(intId, intIdEntity.getId());
		Assert.assertEquals(stringId, stringIdEntity.getId());
	}

	@Test
	public void should_getNullId_when_idWasNotSet() throws Exception {

		// Given
		Entity<Long> longIdEntity = new Entity<Long>() {};
		Entity<Integer> intIdEntity = new Entity<Integer>() {};
		Entity<String> stringIdEntity = new Entity<String>() {};

		// Then
		Assert.assertNull(longIdEntity.getId());
		Assert.assertNull(intIdEntity.getId());
		Assert.assertNull(stringIdEntity.getId());
	}
}
