package com.connor.hozon.webservice.breakpoint;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by K on 2018/7/3.
 */
@Configuration
@WebFilter(filterName = "kFilter",urlPatterns = "/*")
public class KFilter implements Filter{

    private Map<String, ? extends ServletRegistration> listBean;
    private ServletContext servletContext;
    public void destroy() {
// TODO Auto-generated method stub

    }


    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
// TODO Auto-generated method stub
        chain.doFilter(request, response);
    }


    public void init(FilterConfig arg0) throws ServletException {
// TODO Auto-generated method stub
        listBean = arg0.getServletContext().getServletRegistrations();
        if(listBean.get("CXFServlet")==null){
            servletContext = arg0.getServletContext();//
            ServletRegistration.Dynamic dynamic = servletContext.addServlet("CXFServlet", new CXFServlet());//注意此处和第4步的代码是同样的功能
            dynamic.addMapping("/ws/*");
        }

    }
}
