package webservice.service.impl.processRoute6;

import webservice.base.processRoute.TABLEOFZPPTCI006;
import webservice.base.processRoute.TABLEOFZPPTCO006;
import webservice.base.processRoute.ZPPTCSAP006_Service;
import webservice.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP006Service extends ZPPTCSAP006_Service implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) {
        super.getZPPTCSAP006().zppTCSAP006((Holder<TABLEOFZPPTCI006>) input, (Holder<TABLEOFZPPTCO006>) output);
    }
}
