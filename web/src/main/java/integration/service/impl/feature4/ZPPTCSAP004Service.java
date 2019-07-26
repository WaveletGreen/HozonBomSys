package integration.service.impl.feature4;

import integration.base.relevance.TABLEOFZPPTCI004;
import integration.base.relevance.TABLEOFZPPTCO004;
import integration.base.service.SAPForTCProxy;
import integration.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP004Service
//        extends SAPForTCProxy
        implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output)throws Exception {
//        super.getSAPForTCProxyHttpSoap11Endpoint().zppTCSAP004((Holder<TABLEOFZPPTCI004>) input, (Holder<TABLEOFZPPTCO004>) output);
    }
}
