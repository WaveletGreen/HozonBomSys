package webservice.service.impl.produceAttr7;

import webservice.base.productAttributes.TABLEOFZPPTCI007;
import webservice.base.productAttributes.TABLEOFZPPTCO007;
import webservice.base.productAttributes.ZPPTCSAP007_Service;
import webservice.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP007Service extends ZPPTCSAP007_Service implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output)throws Exception{
        super.getZPPTCSAP007().zppTCSAP007((Holder<TABLEOFZPPTCI007>) input, (Holder<TABLEOFZPPTCO007>) output);
    }
}
