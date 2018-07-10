package webservice.service.i;

import org.springframework.context.annotation.Configuration;

/**
 * 该服务传输主数据到SAP，只需要传入输出参数即可，不用传入输出参数，
 * 输出参数会从SAP根据输入参数返回，但是输出参数还是要定义的，否则会报错
 */
@Configuration
public interface ITransmitService {
    /**
     * 传输数据
     *
     * @return 从SAP方返回的处理信息
     * <p>
     * <strong> 如果输入参数没有传入，则返回null</strong>
     * <p>
     * <strong>如果输出参数没有，则返回null</strong>
     */
    <T> T execute() throws Exception;
}
