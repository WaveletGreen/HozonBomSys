package webservice.service.impl.options4;

import webservice.base.options.TABLEOFZPPTCI004;
import webservice.base.options.TABLEOFZPPTCO004;
import webservice.base.options.ZPPTCSAP004_Service;
import webservice.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP004Service extends ZPPTCSAP004_Service implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) {
        super.getZPPTCSAP004().zppTCSAP004((Holder<TABLEOFZPPTCI004>) input, (Holder<TABLEOFZPPTCO004>) output);
    }
}
