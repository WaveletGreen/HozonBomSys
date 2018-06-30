package webservice.service.impl.masterMaterial;

import org.springframework.stereotype.Service;
import webservice.Author;
import webservice.base.maindatas.*;
import webservice.service.i.ITransmitService;

import javax.xml.ws.Holder;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:传输主数据到SAP系统
 * Date: 2018/6/6 16:55
 */
@Service("transMasterMaterialService")
public class TransMasterMaterialService extends Author implements ITransmitService {
    /**
     * webservice调用的服务
     */
    private ZPPTCSAP001_Service service;
    /**
     * webservice服务执行者
     */
    private ZPPTCSAP001 serviceExecutor;

    /**
     * 输入参数容器
     */
    private Holder<TABLEOFZPPTCI001> inputContainer;
    /**
     * 输入参数
     */
    private TABLEOFZPPTCI001 input;


    /**
     * 输出参数:临时的输出参数容器，需要一个临时输出参数容器，否则传不到SAP，该参数不应该有setter和getter
     */
    private TABLEOFZPPTCO001 out;
    /**
     * 输出参数:临时变量
     */
    private ZPPTCO001 t;
    /**
     * 输出参数:输出参数容器，要有setter和getter给调用方
     */
    private Holder<TABLEOFZPPTCO001> outputContainer;

    public TransMasterMaterialService() {
        service = new ZPPTCSAP001_Service();
        serviceExecutor = service.getZPPTCSAP001();

        //输入参数
        inputContainer = new Holder<>();
        input = new TABLEOFZPPTCI001();

        //输出参数
        outputContainer = new Holder<>();
        out = new TABLEOFZPPTCO001();
        t = new ZPPTCO001();
        out.getItem().add(t);
    }

    /**
     * 执行传输，同步获取返回值
     *
     * @return 请看接口@{@link ITransmitService}定义
     */
    public Holder<TABLEOFZPPTCO001> execute() {
        if (t == null) {
            out.getItem().add(t = new ZPPTCO001());
        }
        inputContainer.value = input;
        outputContainer.value = out;
        if (validateInput(inputContainer)) {
            serviceExecutor.zppTCSAP001(inputContainer, outputContainer);
            if (validateOutput(outputContainer)) {
                out = outputContainer.value;
                return outputContainer;
            } else {
                System.out.println("接受方没有返回数据");
                return null;
            }
        } else {
            System.out.println("发送方没有输入参数");
            return null;
        }
    }

    /**
     * 获取输入参数
     *
     * @return
     */
    public TABLEOFZPPTCI001 getInput() {
        return input;
    }

    /***
     * 设置输入参数，在执行@execute之前设置输入参数，否则无法发送数据
     * @param input
     */
    public void setInput(TABLEOFZPPTCI001 input) {
        this.input = input;
    }

    /**
     * 获取输出参数
     *
     * @return @out，执行@execute之后获取的输出参数
     */
    public TABLEOFZPPTCO001 getOut() {
        return out;
    }

}
