package de.gedoplan.sample.persistence;

import javax.annotation.Priority;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Producer for a transaction bound and request scoped entity manager.
 *
 * This alternative to {@link EntityManagerProducer} is currently activated.
 */
@Alternative
@Priority(1)
@RequestScoped
@Stateful
public class RequestScopedEntityManagerProducer
{
  @PersistenceContext(unitName = "default", type = PersistenceContextType.EXTENDED)
  private EntityManager entityManager;

  @Produces
  @RequestScoped
  public EntityManager getEntityManager()
  {
    return this.entityManager;
  }
}
