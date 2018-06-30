package webservice.service.impl.classify3;

import webservice.base.classify.TABLEOFZPPTCI003;
import webservice.base.classify.TABLEOFZPPTCO003;
import webservice.base.classify.ZPPTCSAP003_Service;
import webservice.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP003Service extends ZPPTCSAP003_Service implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) {
        super.getZPPTCSAP003().zppTCSAP003((Holder<TABLEOFZPPTCI003>) input, (Holder<TABLEOFZPPTCO003>) output);
    }
}
