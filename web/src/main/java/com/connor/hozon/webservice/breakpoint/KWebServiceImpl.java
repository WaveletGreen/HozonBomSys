package com.connor.hozon.webservice.breakpoint;

/**
 * Created by K on 2018/7/3.
 */
public class KWebServiceImpl implements KWebService {

    private final TCBPI bean;

    public KWebServiceImpl(){
        this.bean=new TCBPI();
    }

    @Override
    public TCBPI getTCBPI(String input) {
        return this.bean;
    }
}
