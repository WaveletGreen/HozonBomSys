//package com.connor.hozon.webservice.breakpoint;
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
// * Created by K on 2018/7/3.
// */
//@Configuration
//public class KCxfConfig {
//
//    /*@Bean webservice和项目一起发布不要加这段
//    public ServletRegistrationBean dispatcherServlet() {
//        return new ServletRegistrationBean(new CXFServlet(), "/kk/*");
//    }*/
//    @Bean(name = Bus.DEFAULT_BUS_ID)
//    public SpringBus springBus() {
//        return new SpringBus();
//    }
//    @Bean
//    public KWebService userService() {
//        return new KWebServiceImpl();
//    }
//    @Bean
//    public Endpoint endpoint() {
//        EndpointImpl endpoint = new EndpointImpl(springBus(), userService());
//        endpoint.publish("/user");
//        return endpoint;
//    }
//}
