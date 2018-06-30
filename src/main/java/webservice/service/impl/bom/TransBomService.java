package webservice.service.impl.bom;

import org.springframework.stereotype.Service;
import webservice.Author;
import webservice.base.bom.*;
import webservice.base.maindatas.*;
import webservice.service.i.ITransmitService;

import javax.xml.ws.Holder;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:传输主数据到SAP系统
 * Date: 2018/6/6 16:55
 */
@Service("transMasterMaterialService")
public class TransBomService extends Author implements ITransmitService {
    /**
     * webservice调用的服务
     */
    private ZPPTCSAP005_Service service;
    /**
     * webservice调用的服务
     */
    private ZPPTCSAP005 serviceExecutor;
    /**
     * 输入参数容器
     */
    private Holder<TABLEOFZPPTCI005> inputContainer;
    /**
     * 输入参数
     */
    private TABLEOFZPPTCI005 input;


    /**
     * 输出参数:临时的输出参数容器，需要一个临时输出参数容器，否则传不到SAP，该参数不应该有setter和getter
     */
    private TABLEOFZPPTCO005 out;
    /**
     * 输出参数:临时变量
     */
    private ZPPTCO005 t;
    /**
     * 输出参数:输出参数容器，要有setter和getter给调用方
     */
    private Holder<TABLEOFZPPTCO005> outputContainer;

    public TransBomService() {
        service = new ZPPTCSAP005_Service();
        serviceExecutor = service.getZPPTCSAP005();
        //输入参数
        inputContainer = new Holder<>();
        input = new TABLEOFZPPTCI005();

        //输出参数
        outputContainer = new Holder<>();
        out = new TABLEOFZPPTCO005();
        t = new ZPPTCO005();
        out.getItem().add(t);
    }

    /**
     * 执行传输，同步获取返回值，结构都一样
     *
     * @return 请看接口@{@link ITransmitService}定义
     */
    @Override
    public TABLEOFZPPTCO005 execute() {
        if (t == null) {
            out.getItem().add(t = new ZPPTCO005());
        }
        inputContainer.value = input;
        outputContainer.value = out;
        if (validateInput(inputContainer)) {
            serviceExecutor.zppTCSAP005(inputContainer, outputContainer);
            if (validateOutput(outputContainer)) {
                out = outputContainer.value;
                return out;
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
    public TABLEOFZPPTCI005 getInput() {
        return input;
    }

    /***
     * 设置输入参数，在执行@execute之前设置输入参数，否则无法发送数据
     * @param input
     */
    public void setInput(TABLEOFZPPTCI005 input) {
        this.input = input;
    }

    /**
     * 获取输出参数
     *
     * @return @out，执行@execute之后获取的输出参数
     */
    public TABLEOFZPPTCO005 getOut() {
        return out;
    }

}
