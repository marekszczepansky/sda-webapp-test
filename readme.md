# Servlets

## InteliJ CE

[IntelliJ templates](src/main/readme.md)

## Web.xml

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>
  
  <servlet>
    <servlet-name>SimpleSDA</servlet-name>
    <servlet-class>sda.javapoz12.SimpleServlet</servlet-class>
  </servlet>
  <servlet>
    <servlet-name>AdvancedSDA</servlet-name>
    <servlet-class>sda.javapoz12.AdvancedServlet</servlet-class>
  </servlet>
  <servlet>
    <servlet-name>WebCache</servlet-name>
    <servlet-class>sda.javapoz12.WebCacheServlet</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>WebCache</servlet-name>
    <url-pattern>/web/*</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>SimpleSDA</servlet-name>
    <url-pattern>/servlet/*</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>AdvancedSDA</servlet-name>
    <url-pattern>/advanced/*</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>AdvancedSDA</servlet-name>
    <url-pattern>/zaawansowany/*</url-pattern>
  </servlet-mapping>
</web-app>
``` 

## Servlet Class

```java
@WebServlet("/actionServlet")
public class ActionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        out.println("Using POST Method to Read Form Data");
        request.getParameterMap().forEach((name, values) ->  out.println(name + ": " + Arrays.toString(values)));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("Using GET Method to display data");
        String clientIp = request.getRemoteAddr();
        String browser = request.getHeader("User-Agent");

        out.println("client ip: " + clientIp);
        out.println("client browser information: " + browser);
    }
}
```

## Servlet Filter
```java
@WebFilter(filterName = "FilterCharset", urlPatterns = {"/task/*", "/actionServlet"})
public class FilterCharset implements Filter {
    
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String clientIp = req.getRemoteAddr();
        String requestPath = "unknown";
        String servletPath = "unknown";

        if (req instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) req;
            servletPath = httpServletRequest.getServletPath();
            requestPath = httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURL().toString();
            if (httpServletRequest.getQueryString() != null) {
                requestPath += "?" + httpServletRequest.getQueryString();
            }
        }

        System.out.println(clientIp + " requests start for " + requestPath);
        System.out.println("servlet path matched " + servletPath);

        chain.doFilter(req, resp);

        System.out.println(clientIp + " requests end for " + requestPath);
    }

    public void init(FilterConfig config) throws ServletException {    }
    public void destroy() {   }

}
```

## Redirection & forward

```java
// redirection relative to request
response.sendRedirect("doList");
// forward relative to request
request.getRequestDispatcher("path").forward(request, response);
// forward in root servlet context
this.getServletContext().getRequestDispatcher("/path").forward(request, response);
```

## Include

```java
request.getRequestDispatcher("path").include(request, response);
request.getServletContext().getRequestDispatcher("/path").include(request, response);
```

## Cookies

```java
Cookie myCookie = new Cookie("name", "Marek");
myCookie.setMaxAge(Integer.MAX_VALUE);
response.addCookie(myCookie);

Optional<Cookie> myCookie = Arrays.stream(request.getCookies())
    .filter(cookie -> cookie.getName().equals("name"))
    .findFirst();
myCookie.ifPresent(cookie -> outputStream.println("Welcome back " + cookie.getValue()));
```

## HTML & JS injection

[https://github.com/OWASP/owasp-java-encoder](https://github.com/OWASP/owasp-java-encoder)

## Error handling
[https://www.baeldung.com/servlet-exceptions](https://www.baeldung.com/servlet-exceptions)
