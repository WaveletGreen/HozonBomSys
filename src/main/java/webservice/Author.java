package webservice;

import webservice.base.helper.PropertyLoader;
import webservice.service.i.ICommon;
import webservice.service.i.IExecutor;
import webservice.service.i.ITransmitService;

import javax.xml.ws.Holder;
import java.net.PasswordAuthentication;
import java.util.Properties;

public class Author {
    private final static PropertyLoader LOADER = new PropertyLoader();
    private final static Properties properties = LOADER.getProperties();
    private static PasswordAuthentication author;

    /**
     * 每次执行@execute的时候都会清空input，默认不清空，可以多次带着input执行execute多次
     */
    protected boolean setClearInputEachTime = false;

    static {
        java.net.Authenticator.setDefault(new java.net.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                author = new PasswordAuthentication(properties.getProperty("username"),
                        properties.getProperty("password").toCharArray());
                return author;
            }
        });
    }

    public Author() {
    }

    /**
     * 验证输入参数是否正确
     *
     * @return 验证正确则返回true，反之返回false
     */
    public boolean validateInput(Holder<? extends ICommon> input) {
        if (input.value == null || input.value.getItem() == null || input.value.getItem().size() < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证输出参数是否正确
     *
     * @return 验证正确则返回true，反之返回false
     */
    public boolean validateOutput(Holder<? extends ICommon> output) {
        if (output.value == null || output.value.getItem() == null || output.value.getItem().size() < 0) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 执行传输，同步获取返回值
     *
     * @return 请看接口@{@link ITransmitService}定义
     */
    public Holder<? extends ICommon> execute(
            IExecutor serviceExecutor,
            Holder<? extends ICommon> inputContainer,
            Holder<? extends ICommon> outputContainer) {
        if (validateInput(inputContainer)) {
            serviceExecutor.doExecute(inputContainer, outputContainer);
            if (validateOutput(outputContainer)) {
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

    public void setClearInputEachTime(boolean setClearInputEachTime) {
        this.setClearInputEachTime = setClearInputEachTime;
    }
}
