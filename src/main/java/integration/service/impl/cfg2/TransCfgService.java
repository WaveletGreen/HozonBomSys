package integration.service.impl.cfg2;

import integration.Author;
import integration.base.feature.TABLEOFZPPTCI002;
import integration.base.feature.TABLEOFZPPTCO002;
import integration.base.feature.ZPPTCO002;
import integration.logic.Features;
import integration.option.ActionFlagOption;
import integration.service.i.ITransmitService;
import org.springframework.stereotype.Service;

import javax.xml.ws.Holder;
import java.util.UUID;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:传输主数据到SAP系统
 * Date: 2018/6/6 16:55
 */
@Service("transCfgService")
public class TransCfgService extends Author implements ITransmitService {
    /**
     * 调用服务方
     */
    private ZPPTCSAP002Service serviceExecutor;
    /**
     * 输入参数容器
     */
    private Holder<TABLEOFZPPTCI002> inputContainer;
    /**
     * 输入参数
     */
    private TABLEOFZPPTCI002 input;


    /**
     * 输出参数:临时的输出参数容器，需要一个临时输出参数容器，否则传不到SAP，该参数不应该有setter和getter
     */
    private TABLEOFZPPTCO002 out;
    /**
     * 输出参数:临时变量
     */
    private ZPPTCO002 t;
    /**
     * 输出参数:输出参数容器，要有setter和getter给调用方
     */
    private Holder<TABLEOFZPPTCO002> outputContainer;

    public TransCfgService() {
        serviceExecutor = new ZPPTCSAP002Service();
        //输入参数
        inputContainer = new Holder<>();
        input = new TABLEOFZPPTCI002();

        //输出参数
        outputContainer = new Holder<>();
        out = new TABLEOFZPPTCO002();
        t = new ZPPTCO002();
        out.getItem().add(t);
    }

    /**
     * 执行传输，同步获取返回值，结构都一样
     *
     * @return 请看接口@{@link ITransmitService}定义
     */
    @Override
    public TABLEOFZPPTCO002 execute() throws Exception {
        //一定要有一个输出参数，否则报错
        if (setClearInputEachTime) {
            out.getItem().clear();
        }
        if (t == null) {
            out.getItem().add(t = new ZPPTCO002());
        }
        inputContainer.value = input;
        outputContainer.value = out;
        //执行服务
        if (super.execute(serviceExecutor, inputContainer, outputContainer) != null)
            out = outputContainer.value;
        else {
            out = null;
        }
        //如果需要在每次execute之后清空input，则需要设置clearEachTime=true
        if (setClearInputEachTime) {
            input.getItem().clear();
        }
        return out;
    }

    /**
     * 获取输入参数
     *
     * @return
     */
    public TABLEOFZPPTCI002 getInput() {
        return input;
    }

    /***
     * 设置输入参数，在执行@execute之前设置输入参数，否则无法发送数据
     * @param input
     */
    public void setInput(TABLEOFZPPTCI002 input) {
        this.input = input;
    }

    /**
     * 获取输出参数
     *
     * @return @out，执行@execute之后获取的输出参数
     */
    public TABLEOFZPPTCO002 getOut() {
        return out;
    }

    public static void main(String[] args) throws Exception {
        TransCfgService transCfgService=new TransCfgService();
        Features features = new Features();
        String packnum = UUID.randomUUID().toString().replaceAll("-", "");
        //数据包号
        features.setPackNo(packnum);
        //行号
        features.setLineNum(packnum.substring(0, 5));
        //动作描述代码
        features.setActionFlag(ActionFlagOption.ADD);
        //特性编码
        features.setFeaturesCode("HZDCZZZC");
        //特性描述
        features.setFeaturesDescribe("电池装置总成");
        //特性值编码
        features.setPropertyValuesCode("1BC03");
        //特性值描述
        features.setPropertyValuesDescribe("高续航大容量电池");
        //            featuresList.add(features);
        transCfgService.getInput().getItem().add(features.getZpptci002());
        transCfgService.execute();
        System.out.println();
    }

}
