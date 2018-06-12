// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class LogoutServletTest {

  private LogoutServlet logoutServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;

  @Before
  public void setup() {
    logoutServlet = new LogoutServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
  }

  @Test
  public void testDoGetRedirectsToFrontPage() throws IOException, ServletException {
    HttpSession mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    logoutServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockResponse).sendRedirect("/");
  }

  @Test
  public void testDoGetUnsetsUserAttribute() throws IOException, ServletException {
    HttpSession mockSession = Mockito.mock(HttpSession.class);
    mockSession.setAttribute("user", "this_should_be_null_when_the_user_logs_out");
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    logoutServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockSession).setAttribute("user", null);
  }
}
