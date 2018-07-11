package integration.service.tc.breakpoint;//package webservice.service.tc.breakpoint;
//
//import org.apache.cxf.Bus;
//import org.apache.cxf.bus.spring.SpringBus;
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.xml.ws.Endpoint;
//
///**
// *
// */
//@Configuration
//public class CxfConfig {
//
////    @Bean
////    public ServletRegistrationBean dispatcherServlet() {
////        return new ServletRegistrationBean(new CXFServlet(), "/demo/*");
////    }
//
//    @Bean(name = Bus.DEFAULT_BUS_ID)
//    public SpringBus springBus() {
//        return new SpringBus();
//    }
//
//    @Bean
//    public BreakPointService demoService() {
//        return new BreakPointServiceImpl();
//    }
//
//    @Bean
//    public Endpoint endpoint() {
//        EndpointImpl endpoint = new EndpointImpl(springBus(), demoService());
//        endpoint.publish("/service");
//        return endpoint;
//    }
//}
