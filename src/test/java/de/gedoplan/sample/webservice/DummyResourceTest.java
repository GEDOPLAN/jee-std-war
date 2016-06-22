package de.gedoplan.sample.webservice;

import static org.junit.Assert.*;

import de.gedoplan.baselibs.utils.util.ApplicationProperties;
import de.gedoplan.sample.entity.Dummy;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DummyResourceTest
{
  private static final String serverUrl           = "http://localhost:8080";
  private static final String serverUrlWebContext = serverUrl + "/" + ApplicationProperties.getProperty("project.artifactId");

  private static Client       client;
  private static WebTarget    baseTarget;

  private static Long         firstDummyId;
  private static String       createdDummyLocation;

  @BeforeClass
  public static void beforeClass()
  {
    client = ClientBuilder.newClient();
    baseTarget = client.target(serverUrlWebContext).path(RestApplication.PATH).path(DummyResource.PATH);
  }

  @AfterClass
  public static void afterClass()
  {
    client.close();
  }

  @Test
  public void test_01_getAll()
  {
    System.out.println("----- test_01_GetAll -----");

    List<Dummy> dummies = baseTarget.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Dummy>>()
    {
    });

    assertFalse("Dummy list must not be empty", dummies.isEmpty());

    System.out.printf("Found %d dummies:\n", dummies.size());
    for (Dummy dummy : dummies)
    {
      System.out.println("  " + dummy.toDebugString());

      if (firstDummyId == null)
      {
        firstDummyId = dummy.getId();
      }
    }
  }

  @Test
  public void test_02_get()
  {
    System.out.println("----- test_02_get -----");

    if (firstDummyId == null)
    {
      System.out.println("Call test_01_getAll to obtain id of object to be retrieved");
      test_01_getAll();
    }

    WebTarget dummyTarget = baseTarget.path(firstDummyId.toString());
    Dummy dummy = dummyTarget.request(MediaType.APPLICATION_JSON).get(Dummy.class);

    assertNotNull("Dummy should not be null", dummy);
    assertEquals("Dummy ID", firstDummyId, dummy.getId());

    System.out.println("Dummy: " + dummy.toDebugString());
  }

  @Test
  public void test_03_post()
  {
    System.out.println("----- test_03_post -----");

    Dummy dummy = new Dummy("Donald Duck");

    Response response = baseTarget.request().post(Entity.json(dummy));

    try
    {
      StatusType statusInfo = response.getStatusInfo();
      System.out.printf("Response status: %03d %s\n", statusInfo.getStatusCode(), statusInfo.getReasonPhrase());

      switch (statusInfo.getStatusCode())
      {
      case 201:
        if (createdDummyLocation == null)
        {
          createdDummyLocation = response.getHeaderString(HttpHeaders.LOCATION);
        }
        System.out.printf("URI: %s\n", response.getHeaderString(HttpHeaders.LOCATION));
        break;

      case 500:
        System.out.println(response.readEntity(String.class));
        break;
      }
      assertEquals("Response status", 201, statusInfo.getStatusCode());
    }
    finally
    {
      response.close();
    }
  }

  @Test
  public void test_04_delete()
  {
    System.out.println("----- test_04_delete -----");

    if (createdDummyLocation == null)
    {
      System.out.println("Call test_03_post to obtain URL of object to be deleted");
      test_03_post();
    }

    Response response = client.target(createdDummyLocation).request().delete();

    StatusType statusInfo = response.getStatusInfo();
    System.out.printf("Response status: %03d %s\n", statusInfo.getStatusCode(), statusInfo.getReasonPhrase());

    assertEquals("Response status", 204, statusInfo.getStatusCode());
  }
}
