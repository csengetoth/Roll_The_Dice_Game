package util.jpa;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.inject.persist.Transactional;

/**
 * Generic JPA DAO class that provides JPA for the entity class.
 *
 * @param <T> the entity class type
 */
public abstract class GenericJpaDao<T> {

    protected Class<T> entityClass;
    protected EntityManager entityManager;

    /**
     * Makes a {@code GenericJpaDao} object.
     *
     * @param entityClass the {@link Class} object represents the entity
     *                    class
     */
    public GenericJpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Returns the basis {@link EntityManager} instance.
     *
     * @return the basis {@link EntityManager} instance
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Sets the basis {@link EntityManager} instance.
     *
     * @param entityManager the basis {@link EntityManager} instance
     */
    @Inject
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Persists the specified entity instance in the database.
     *
     * @param entity the entity instance to be persisted in the database
     */
    @Transactional
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    /**
     * Returns the entity instance with the given primary key from the
     * database. The method returns an empty {@link Optional} object when
     * the instance does not exists.
     *
     * @param primaryKey the primary key
     * @return an {@link Optional} object wrapping the entity instance with
     * the primary key
     */
    @Transactional
    public Optional<T> find(Object primaryKey) {
        return Optional.ofNullable(entityManager.find(entityClass, primaryKey));
    }

    /**
     * Returns the list of all instances of the entity class from the database.
     *
     * @return the list of all instances of the entity class from the database
     */
    @Transactional
    public List<T> findAll() {
        TypedQuery<T> typedQuery = entityManager.createQuery("FROM " + entityClass.getSimpleName(), entityClass);
        return typedQuery.getResultList();
    }

    /**
     * Removes the specified entity instance from the database.
     *
     * @param entity the entity instance to be removed from the database
     */
    @Transactional
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    /**
     * Updates the specified entity instance in the database.
     *
     * @param entity the entity instance to be updated in the database
     */
    @Transactional
    public void update(T entity) {
        entityManager.merge(entity);
    }

}
