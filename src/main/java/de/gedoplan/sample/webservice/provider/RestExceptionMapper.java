package de.gedoplan.sample.webservice.provider;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.LogFactory;

@Provider
public class RestExceptionMapper implements ExceptionMapper<Exception>
{
  @Context
  ResourceInfo resourceInfo;

  @Override
  public Response toResponse(Exception exception)
  {
    if (!(exception instanceof ClientErrorException))
    {
      Class<?> classToLogFor = this.resourceInfo.getResourceClass();
      if (classToLogFor == null)
      {
        classToLogFor = getClass();
      }

      LogFactory.getLog(classToLogFor).error("Error processing request", exception);
    }

    if (exception instanceof WebApplicationException)
    {
      return ((WebApplicationException) exception).getResponse();
    }

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.toString()).build();
  }

}
