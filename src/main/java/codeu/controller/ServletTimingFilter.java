package codeu.controller;

import codeu.model.data.Statistic;
import codeu.model.data.Statistic.Type;
import codeu.model.store.persistence.PersistentStorageAgent;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter class responsible for timing any servlet GET and POST methods
 */
public class ServletTimingFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    long startTime = System.currentTimeMillis();
    // runs servlet method (what would be done without the filter)
    filterChain.doFilter(servletRequest, servletResponse);
    // creates statistic and saves it
    long elapsedTime = System.currentTimeMillis() - startTime;
    String method = ((HttpServletRequest) servletRequest).getMethod(); // gets HTTP method (eg GET)
    String uri = ((HttpServletRequest) servletRequest).getRequestURI(); // gets URI (eg /chat)
    Type type = Type.getFromMethodAndURI(method, uri);
    if (type != null) {
      Statistic statistic = new Statistic(type, elapsedTime);
      PersistentStorageAgent.getInstance().writeThrough(statistic);
    }
  }

  @Override
  public void destroy() {}
}
