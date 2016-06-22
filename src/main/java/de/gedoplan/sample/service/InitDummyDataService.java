package de.gedoplan.sample.service;

import de.gedoplan.sample.persistence.DummyRepository;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;

@ApplicationScoped
@Transactional(rollbackOn = Throwable.class)
public class InitDummyDataService implements Serializable
{
  @Inject
  DummyRepository    dummyRepository;

  @Inject
  DummyCreateService dummyCreateService;

  @Inject
  private Log        log;

  void createDemoData(@Observes @Initialized(ApplicationScoped.class) Object event)
  {
    /*
     * Observers for scope lifecycle events unfortunately do not have the request scope activated. So any @RequestScoped bean used
     * here will fail. As a workaround the code is moved into a @PostConstruct method, which will be exceuted, when this observer
     * is called for the first time, and which have an active request scope as per specification.
     */
  }

  @PostConstruct
  void postConstruct()
  {
    /*
     * Unfortunately, lifecycle methods are not intercepted by @TransActional. That means, that countAll and createEntries would
     * use a separate transaction each.
     */
    try
    {
      if (this.dummyRepository.countAll() == 0)
      {
        this.dummyCreateService.createEntries(1, 10);
      }
    }
    catch (Exception e)
    {
      this.log.warn("Cannot create test data", e);
    }
  }
}
