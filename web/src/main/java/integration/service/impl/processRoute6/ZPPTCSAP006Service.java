package integration.service.impl.processRoute6;

import cn.net.connor.hozon.common.exception.HzBomException;
import integration.service.i.IExecutor;

import javax.xml.ws.Holder;

/**
 * 执行服务
 */
public class ZPPTCSAP006Service
//        extends SAPForTCProxy
        implements IExecutor {
    @Override
    public <T, E> void doExecute(Holder<T> input, Holder<E> output) {
        try {
//            super.getSAPForTCProxyHttpSoap11Endpoint().zppTCSAP006((Holder<TABLEOFZPPTCI006>) input, (Holder<TABLEOFZPPTCO006>) output);
        }catch (Exception e){
            e.printStackTrace();
            throw new HzBomException("与SAP系统外部服务连接失败!");
        }
    }
}
