package br.com.m4u.sms.api.v1.resolution;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import br.com.m4u.sms.api.v1.resolution.ApiResponseResolver;
import br.com.m4u.sms.domain.model.Entity;
import br.com.m4u.sms.domain.model.Result;
import br.com.m4u.sms.domain.model.Result.Type;
import br.com.m4u.sms.infrastructure.crosscutting.test.categories.UnitTest;


@Category(UnitTest.class)
@RunWith(MockitoJUnitRunner.class)
public class ApiResponseResolverTest {

	@Mock
	private Result<Entity<Long>> resultMock;

	@Mock
	private UriBuilder uriBuilder;

	@Test
	public void should_sendStatus201WithCreatedLocation_when_domainAppCreatedNewResource() throws Exception {
		// Given
		ApiResponseResolver resolver = new ApiResponseResolver();

		URI dummyURI = new URI("http://localhost/api/entity/1");

		// FIXME not maintenance friendly
		UriInfo uriInfo = mock(UriInfo.class);
		given(uriInfo.getAbsolutePathBuilder()).willReturn(uriBuilder);
		given(uriBuilder.path(any(String.class))).willReturn(uriBuilder);
		given(uriBuilder.build()).willReturn(dummyURI);

		Entity<Long> dataDummy = new Entity<Long>() {};
		dataDummy.setId(1L);
		given(resultMock.getResultType()).willReturn(Type.SUCCESS);
		given(resultMock.getData()).willReturn(dataDummy);

		// When
		Response response = resolver.sendId(resultMock, uriInfo);

		// Then
		assertNotNull(response);
		verify(resultMock, atLeast(1)).getResultType();
		assertEquals(Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals(dummyURI, response.getLocation());
	}

	@Test
	public void should_sendStatus200_when_resultSuccessful() throws Exception {
		// Given
		ApiResponseResolver resolver = new ApiResponseResolver();

		Entity<Long> dataDummy = new Entity<Long>() {};
		dataDummy.setId(1L);

		given(resultMock.getResultType()).willReturn(Type.SUCCESS);
		given(resultMock.getData()).willReturn(dataDummy);

		// When
		Response response = resolver.send(resultMock);

		// Then
		assertNotNull(response);
		verify(resultMock, atLeast(1)).getResultType();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(resultMock, response.getEntity());
	}

	@Test
	public void should_sendStatus404_when_resultHasOperationFailure() throws Exception {
		// Given
		ApiResponseResolver resolver = new ApiResponseResolver();

		Entity<Long> dataDummy = new Entity<Long>() {};
		dataDummy.setId(1L);

		given(resultMock.getResultType()).willReturn(Type.RESOURCE_NOT_FOUND);
		given(resultMock.getData()).willReturn(dataDummy);

		// When
		Response response = resolver.send(resultMock);

		// Then
		assertNotNull(response);
		verify(resultMock, atLeast(1)).getResultType();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals(null, response.getEntity());
	}

	@Test
	public void should_sendStatus409_when_resultHasOperationFailure() throws Exception {
		// Given
		ApiResponseResolver resolver = new ApiResponseResolver();

		Entity<Long> dataDummy = new Entity<Long>() {};
		dataDummy.setId(1L);

		given(resultMock.getResultType()).willReturn(Type.VALIDATION_ERROR);
		given(resultMock.getData()).willReturn(dataDummy);

		// When
		Response response = resolver.send(resultMock);

		// Then
		assertNotNull(response);
		verify(resultMock, atLeast(1)).getResultType();
		assertEquals(Status.CONFLICT.getStatusCode(), response.getStatus());
		assertEquals(null, response.getEntity());
	}

	@Test
	public void should_sendStatus500_when_resultHasOperationFailure() throws Exception {
		// Given
		ApiResponseResolver resolver = new ApiResponseResolver();

		Entity<Long> dataDummy = new Entity<Long>() {};
		dataDummy.setId(1L);

		given(resultMock.getResultType()).willReturn(Type.INTERNAL_SERVER_ERROR);
		given(resultMock.getData()).willReturn(dataDummy);

		// When
		Response response = resolver.send(resultMock);

		// Then
		assertNotNull(response);
		verify(resultMock, atLeast(1)).getResultType();
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
		assertEquals(null, response.getEntity());
	}

	@Test
	public void should_sendStatus502_when_resultHasOperationFailure() throws Exception {
		// Given
		ApiResponseResolver resolver = new ApiResponseResolver();

		Entity<Long> dataDummy = new Entity<Long>() {};
		dataDummy.setId(1L);

		given(resultMock.getResultType()).willReturn(Type.OPERATION_FAILURE);
		given(resultMock.getData()).willReturn(dataDummy);

		// When
		Response response = resolver.send(resultMock);

		// Then
		assertNotNull(response);
		verify(resultMock, atLeast(1)).getResultType();
		assertEquals(Status.BAD_GATEWAY.getStatusCode(), response.getStatus());
		assertEquals(null, response.getEntity());
	}
}
