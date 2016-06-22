package de.gedoplan;

import static org.junit.Assert.*;

import de.gedoplan.sample.entity.Dummy;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class DummyTest
{
  @Test
  public void testDummy() throws Exception
  {
    Dummy dummy = new Dummy("Dummy");
    assertThat(dummy.getName(), is("Dummy"));
  }

}
