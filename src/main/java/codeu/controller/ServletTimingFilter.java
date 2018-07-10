package codeu.controller;

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
    String uri = ((HttpServletRequest)servletRequest).getRequestURI(); // gets URI (e.g. /chat)
    String method = ((HttpServletRequest)servletRequest).getMethod(); // gets HTTP method (e.g. GET)
    long startTime = System.currentTimeMillis();
    filterChain.doFilter(servletRequest, servletResponse);
    // prints info (e.g. "STATS: GET - /about.jsp: 153ms")
    System.out.println("STATS: " + method + " - " + uri + ": " +
        (System.currentTimeMillis() - startTime) + "ms");
  }

  @Override
  public void destroy() {}
}
