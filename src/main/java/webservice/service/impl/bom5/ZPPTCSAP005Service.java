package webservice.service.impl.bom5;

import webservice.base.bom.TABLEOFZPPTCI005;
import webservice.base.bom.TABLEOFZPPTCO005;
import webservice.base.bom.ZPPTCSAP005_Service;
import webservice.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP005Service extends ZPPTCSAP005_Service implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) throws Exception {
        super.getZPPTCSAP005().zppTCSAP005((Holder<TABLEOFZPPTCI005>) input, (Holder<TABLEOFZPPTCO005>) output);
    }
}
