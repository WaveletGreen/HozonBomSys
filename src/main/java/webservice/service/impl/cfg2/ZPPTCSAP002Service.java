package webservice.service.impl.cfg2;

import webservice.base.bom.TABLEOFZPPTCI005;
import webservice.base.bom.TABLEOFZPPTCO005;
import webservice.base.bom.ZPPTCSAP005_Service;
import webservice.base.cfg.TABLEOFZPPTCI002;
import webservice.base.cfg.TABLEOFZPPTCO002;
import webservice.base.cfg.ZPPTCSAP002;
import webservice.base.cfg.ZPPTCSAP002_Service;
import webservice.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP002Service extends ZPPTCSAP002_Service implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) {
        super.getZPPTCSAP002().zppTCSAP002((Holder<TABLEOFZPPTCI002>) input, (Holder<TABLEOFZPPTCO002>) output);
    }
}
