package de.gedoplan.sample.debug;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;

@Interceptor
@Transactional
public class TxLoggerInterceptor
{
  @Inject
  Log log;

  @AroundInvoke
  public Object logTx(InvocationContext invocationContext) throws Exception
  {
    this.log.trace("Enter @Transactional for " + invocationContext.getMethod());
    try
    {
      return invocationContext.proceed();
    }
    finally
    {
      this.log.trace("Leave @Transactional for " + invocationContext.getMethod());
    }
  }
}
