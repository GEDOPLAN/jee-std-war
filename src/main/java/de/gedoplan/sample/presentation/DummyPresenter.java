package de.gedoplan.sample.presentation;

import de.gedoplan.sample.entity.Dummy;
import de.gedoplan.sample.persistence.DummyRepository;
import de.gedoplan.sample.service.ApplicationVersionService;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.Getter;

@Model
@Transactional(rollbackOn = Throwable.class)
public class DummyPresenter
{
  @Inject
  DummyRepository     dummyRepository;

  @Getter
  private List<Dummy> dummies;

  @PostConstruct
  void postConstruct()
  {
    this.dummies = this.dummyRepository.findAll();
  }

  @Inject
  ApplicationVersionService applicationVersionService;

  public String getApplicationCoordinates()
  {
    return this.applicationVersionService.getGav();
  }

  public String getApplicationName()
  {
    return this.applicationVersionService.getName();
  }

}
