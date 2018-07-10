package webservice.service.impl.masterMaterial1;

import org.springframework.stereotype.Service;
import webservice.Author;
import webservice.base.masterMaterial.TABLEOFZPPTCI001;
import webservice.base.masterMaterial.TABLEOFZPPTCO001;
import webservice.base.masterMaterial.ZPPTCO001;
import webservice.logic.MasterMaterial;
import webservice.service.i.ITransmitService;

import javax.xml.ws.Holder;
import java.util.List;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:传输主数据到SAP系统
 * Date: 2018/6/6 16:55
 */
@Service("transMasterMaterialService")
public class TransMasterMaterialService extends Author implements ITransmitService {
    /**
     * 服务拓展类
     */
    private ZPPTCSAP001Service zpptcsap001Service;
    /**
     * 输入参数容器
     */
    private Holder<TABLEOFZPPTCI001> inputContainer;
    /**
     * 输入参数
     */
    private TABLEOFZPPTCI001 input;
    /***
     * 封装好的映射bean，采用集合收集
     */
    private List<MasterMaterial> listOfMasterMaterial;
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
        zpptcsap001Service = new ZPPTCSAP001Service();
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
     * 执行传输，同步获取返回值，结构都一样
     *
     * @return 请看接口@{@link ITransmitService}定义
     */
    @Override
    public TABLEOFZPPTCO001 execute() {
        try {
            if (setClearInputEachTime) {
                out.getItem().clear();
            }
            //一定要有一个输出参数，否则报错
            if (t == null) {
                out.getItem().add(t = new ZPPTCO001());
            }
            inputContainer.value = input;
            outputContainer.value = out;
            //执行服务
            if (super.execute(zpptcsap001Service, inputContainer, outputContainer) != null)
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

    public List<MasterMaterial> getListOfMasterMaterial() {
        return listOfMasterMaterial;
    }

    public void setListOfMasterMaterial(List<MasterMaterial> listOfMasterMaterial) {
        this.listOfMasterMaterial = listOfMasterMaterial;

        for (MasterMaterial m :
                listOfMasterMaterial) {
            input.getItem().add(m.getZpptci001());
        }
    }
}
