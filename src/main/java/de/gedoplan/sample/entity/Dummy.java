package de.gedoplan.sample.entity;

import de.gedoplan.baselibs.persistence.entity.GeneratedLongIdEntity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

@Entity
@Access(AccessType.FIELD)
@Getter
@Setter
@XmlRootElement
public class Dummy extends GeneratedLongIdEntity
{
  @NotNull
  @Size(min = 1)
  private String name;

  public Dummy(String name)
  {
    this.name = name;
  }

  protected Dummy()
  {
  }
}
