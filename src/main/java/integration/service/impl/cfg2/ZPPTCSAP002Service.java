package integration.service.impl.cfg2;

import integration.base.feature.TABLEOFZPPTCI002;
import integration.base.feature.TABLEOFZPPTCO002;
import integration.base.service.SAPForTCProxy;
import integration.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP002Service
        extends SAPForTCProxy
        implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) throws Exception {
        super.getSAPForTCProxyHttpSoap11Endpoint().zppTCSAP002((Holder<TABLEOFZPPTCI002>) input, (Holder<TABLEOFZPPTCO002>) output);
    }
}
