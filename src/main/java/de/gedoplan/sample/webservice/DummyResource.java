package de.gedoplan.sample.webservice;

import de.gedoplan.sample.entity.Dummy;
import de.gedoplan.sample.persistence.DummyRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(DummyResource.PATH)
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Transactional(rollbackOn = Throwable.class)
public class DummyResource
{
  public static final String PATH              = "sample/dummy";
  public static final String ID_PARAMETER_NAME = "id";
  public static final String ID_TEMPLATE       = "{" + ID_PARAMETER_NAME + "}";

  @Inject
  DummyRepository            dummyRepository;

  @GET
  public List<Dummy> getAll()
  {
    return this.dummyRepository.findAll();
  }

  @GET
  @Path(ID_TEMPLATE)
  public Dummy get(@PathParam(ID_PARAMETER_NAME) Long id)
  {
    Dummy dummy = this.dummyRepository.findById(id);
    if (dummy != null)
    {
      return dummy;
    }

    throw new NotFoundException("No entry found for id " + id);
  }

  @PUT
  @Path(ID_TEMPLATE)
  public void put(@PathParam(ID_PARAMETER_NAME) Long id, Dummy dummy)
  {
    if (!id.equals(dummy.getId()))
    {
      throw new BadRequestException("id of updated object must be unchanged");
    }

    if (this.dummyRepository.findById(id) == null)
    {
      throw new NotFoundException();
    }

    this.dummyRepository.merge(dummy);
  }

  @POST
  public Response post(Dummy dummy, @Context UriInfo uriInfo) throws URISyntaxException
  {
    if (dummy.getId() != null)
    {
      throw new BadRequestException("id of new entry must not be set");
    }

    this.dummyRepository.persist(dummy);

    URI createdUri = uriInfo.getAbsolutePathBuilder().path(ID_TEMPLATE).resolveTemplate(ID_PARAMETER_NAME, dummy.getId()).build();
    return Response.created(createdUri).build();
  }

  @DELETE
  @Path(ID_TEMPLATE)
  public void delete(@PathParam(ID_PARAMETER_NAME) Long id)
  {
    this.dummyRepository.removeById(id);
  }
}
