package de.gedoplan.sample.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.sample.entity.Dummy;
import de.gedoplan.sample.entity.Dummy_;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@Transactional(rollbackOn = Throwable.class)
@ApplicationScoped
public class DummyRepository extends SingleIdEntityRepository<Long, Dummy>
{
  public Dummy findByName(String name)
  {
    return findSingleByProperty(Dummy_.name, name);
  }
}
