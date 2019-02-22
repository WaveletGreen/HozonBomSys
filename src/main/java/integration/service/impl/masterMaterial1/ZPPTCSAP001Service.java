package integration.service.impl.masterMaterial1;

import integration.base.masterMaterial.TABLEOFZPPTCI001;
import integration.base.masterMaterial.TABLEOFZPPTCO001;
import integration.base.service.SAPForTCProxy;
import integration.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP001Service
        extends SAPForTCProxy
        implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) throws Exception {
        super.getSAPForTCProxyHttpSoap11Endpoint().zppTCSAP001((Holder<TABLEOFZPPTCI001>) input, (Holder<TABLEOFZPPTCO001>) output);
    }
}
