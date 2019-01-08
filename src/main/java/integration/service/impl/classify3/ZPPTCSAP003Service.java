package integration.service.impl.classify3;

import integration.base.classify.TABLEOFZPPTCI003;
import integration.base.classify.TABLEOFZPPTCO003;
import integration.base.service.SAPForTCProxy;
import integration.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP003Service
//        extends SAPForTCProxy
        implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) throws Exception{
        return;
//        super.getSAPForTCProxyHttpSoap11Endpoint().zppTCSAP003((Holder<TABLEOFZPPTCI003>) input, (Holder<TABLEOFZPPTCO003>) output);
    }
}
