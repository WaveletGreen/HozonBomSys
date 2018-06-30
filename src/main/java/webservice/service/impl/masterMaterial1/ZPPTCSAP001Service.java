package webservice.service.impl.masterMaterial1;

import webservice.base.masterMaterial.TABLEOFZPPTCI001;
import webservice.base.masterMaterial.TABLEOFZPPTCO001;
import webservice.base.masterMaterial.ZPPTCSAP001_Service;
import webservice.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP001Service extends ZPPTCSAP001_Service implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) {
        super.getZPPTCSAP001().zppTCSAP001((Holder<TABLEOFZPPTCI001>) input, (Holder<TABLEOFZPPTCO001>) output);
    }
}
