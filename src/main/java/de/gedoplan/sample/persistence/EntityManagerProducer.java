package de.gedoplan.sample.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Producer for a transaction bound and transaction scoped entity manager.
 */
@ApplicationScoped
public class EntityManagerProducer
{
  @PersistenceContext(unitName = "default")
  @Produces
  private EntityManager entityManager;
}
