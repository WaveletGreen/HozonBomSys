package integration.service.impl.produceAttr7;

import integration.base.productAttributes.TABLEOFZPPTCI007;
import integration.base.productAttributes.TABLEOFZPPTCO007;
import integration.base.service.SAPForTCProxy;
import integration.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP007Service
//        extends SAPForTCProxy
        implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output)throws Exception{
//        super.getSAPForTCProxyHttpSoap11Endpoint().zppTCSAP007((Holder<TABLEOFZPPTCI007>) input, (Holder<TABLEOFZPPTCO007>) output);
    }
}
