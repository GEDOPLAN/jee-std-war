package de.gedoplan.sample.service;

import de.gedoplan.sample.entity.Dummy;
import de.gedoplan.sample.persistence.DummyRepository;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional(rollbackOn = Throwable.class)
public class DummyCreateService implements Serializable
{
  @Inject
  DummyRepository dummyRepository;

  public void createEntries(int start, int count)
  {
    for (int i = start; i < start + count; ++i)
    {
      this.dummyRepository.merge(new Dummy(String.format("Dummy %03d", i)));
    }
  }
}
