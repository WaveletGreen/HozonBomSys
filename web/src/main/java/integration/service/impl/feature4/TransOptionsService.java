package integration.service.impl.feature4;

import integration.Author;
import integration.base.relevance.TABLEOFZPPTCI004;
import integration.base.relevance.TABLEOFZPPTCO004;
import integration.base.relevance.ZPPTCO004;
import integration.service.i.ITransmitService;
import org.springframework.stereotype.Service;

import javax.xml.ws.Holder;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:传输主数据到SAP系统
 * Date: 2018/6/6 16:55
 */
@Service("transOptionsService")
public class TransOptionsService extends Author implements ITransmitService {
    /**
     * 调用服务方
     */
    private ZPPTCSAP004Service serviceExecutor;
    /**
     * 输入参数容器
     */
    private Holder<TABLEOFZPPTCI004> inputContainer;
    /**
     * 输入参数
     */
    private TABLEOFZPPTCI004 input;


    /**
     * 输出参数:临时的输出参数容器，需要一个临时输出参数容器，否则传不到SAP，该参数不应该有setter和getter
     */
    private TABLEOFZPPTCO004 out;
    /**
     * 输出参数:临时变量
     */
    private ZPPTCO004 t;
    /**
     * 输出参数:输出参数容器，要有setter和getter给调用方
     */
    private Holder<TABLEOFZPPTCO004> outputContainer;

    public TransOptionsService() {
//        serviceExecutor = new ZPPTCSAP004Service();
        //输入参数
        inputContainer = new Holder<>();
        input = new TABLEOFZPPTCI004();

        //输出参数
        outputContainer = new Holder<>();
        out = new TABLEOFZPPTCO004();
        t = new ZPPTCO004();
        out.getItem().add(t);
    }

    /**
     * 执行传输，同步获取返回值，结构都一样
     *
     * @return 请看接口@{@link ITransmitService}定义
     */
    @Override
    public TABLEOFZPPTCO004 execute() throws Exception {
        try {
            if (setClearInputEachTime) {
                out.getItem().clear();
            }
            //一定要有一个输出参数，否则报错
            if (t == null) {
                out.getItem().add(t = new ZPPTCO004());
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
        } catch (Exception e) {
            e.printStackTrace();
            if (setClearInputEachTime) {
                input.getItem().clear();
            }
            return null;
        }
    }

    /**
     * 获取输入参数
     *
     * @return
     */
    public TABLEOFZPPTCI004 getInput() {
        return input;
    }

    /***
     * 设置输入参数，在执行@execute之前设置输入参数，否则无法发送数据
     * @param input
     */
    public void setInput(TABLEOFZPPTCI004 input) {
        this.input = input;
    }

    /**
     * 获取输出参数
     *
     * @return @out，执行@execute之后获取的输出参数
     */
    public TABLEOFZPPTCO004 getOut() {
        return out;
    }

}
