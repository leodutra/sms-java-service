package br.com.m4u.sms.api.domain.repositories;

import java.util.List;

import br.com.m4u.sms.api.domain.entities.Entity;
import br.com.m4u.sms.api.crosscutting.exceptions.IntegrationException;
import br.com.m4u.sms.api.domain.valueobjects.PaginationSpecification;

public interface IRepository<TEntity extends Entity<?>> {

	/// <summary>
    /// Retrieves a record by its identifier. If more than one is found, only the first one will be returned.
    /// </summary>
    /// <param name="id">Identifier of record do be retrieved.</param>
    /// <returns>The entity found or default.</returns>
	TEntity getById(Object id) throws IntegrationException;

    /// <summary>
    /// Saves a record to database.
    /// </summary>
    /// <param name="entity">Instance of record to be saved.</param>
    /// <returns>Saved record.</returns>
	TEntity insert(TEntity entity) throws IntegrationException;

    /// <summary>
    /// Updates a record in database.
    /// </summary>
    /// <param name="entity">Instance of record to be updated.</param>
    /// <returns>Number of affected rows.</returns>
    int update(TEntity entity) throws IntegrationException;

    /// <summary>
    /// Deletes a record from database. (physical deletion)
    /// </summary>
    /// <param name="id">Identifier of record to be deleted.</param>
    /// <returns>Number of affected rows.</returns>
    int delete(Object id) throws IntegrationException;

    /// <summary>
    /// Deletes a record from database. (physical deletion)
    /// </summary>
    /// <param name="entity">Instance of record to be deleted.</param>
    /// <returns>Number of affected rows.</returns>
    int delete(TEntity entity) throws IntegrationException;
    
    List<TEntity> listAll(PaginationSpecification pagination) throws IntegrationException;
}
