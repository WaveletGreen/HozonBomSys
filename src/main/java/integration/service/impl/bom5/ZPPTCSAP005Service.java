package integration.service.impl.bom5;

import integration.base.bom.TABLEOFZPPTCI005;
import integration.base.bom.TABLEOFZPPTCO005;
import integration.base.service.SAPForTCProxy;
import integration.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP005Service
        extends SAPForTCProxy
        implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) throws Exception {
        super.getSAPForTCProxyHttpSoap11Endpoint().zppTCSAP005((Holder<TABLEOFZPPTCI005>) input, (Holder<TABLEOFZPPTCO005>) output);
    }
}
