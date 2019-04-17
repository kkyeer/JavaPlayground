package concurrent.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author: kkyeer
 * @Description:
 * @Date:Created in 22:34 2019/4/16
 * @Modified By:
 */
public class StatelessServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
