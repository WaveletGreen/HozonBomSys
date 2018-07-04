//package webservice.service.tc.breakpoint;
//
//import org.apache.cxf.transport.servlet.CXFServlet;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import java.util.Map;
//
///**
// * 断点信息过滤器
// */
//@Component
//@WebFilter(filterName = "breakPointFilter", urlPatterns = "/breakpoint/service"/***该路径暂时没用，所有请求都会进来***/)
//public class BreakPointFilter implements Filter {
//
//    private Map<String, ? extends ServletRegistration> listBean;
//    private ServletContext servletContext;
//    private static final Logger logger = LoggerFactory.getLogger(BreakPointFilter.class);
//
//    public void destroy() {
//    }
//
//    /***
//     * 有个异常无法捕获
//     * @param request
//     * @param response
//     * @param chain
//     */
//    public void doFilter(ServletRequest request, ServletResponse response,
//                         FilterChain chain) {
//        try {
//            chain.doFilter(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("调用webservice服务发生异常", e);
//        }
//    }
//
//
//    public void init(FilterConfig arg0) throws ServletException {
//        listBean = arg0.getServletContext().getServletRegistrations();
//        if (listBean.get("CXFServlet") == null) {
//            servletContext = arg0.getServletContext();//
//            ServletRegistration.Dynamic dynamic = servletContext.addServlet("CXFServlet", new CXFServlet());//注意此处和第4步的代码是同样的功能
//            dynamic.addMapping("/breakpoint/*");
//        }
//    }
//}
