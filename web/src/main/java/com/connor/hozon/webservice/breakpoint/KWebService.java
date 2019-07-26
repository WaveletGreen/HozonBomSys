package com.connor.hozon.webservice.breakpoint;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by K on 2018/7/3.
 */
@WebService
public interface KWebService {


    @WebMethod
    TCBPI getTCBPI(String input);

}
