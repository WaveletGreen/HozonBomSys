package integration.service.tc.breakpoint;

import javax.jws.WebService;

/**
 * 断点信息服务接口
 */
@WebService
public interface BreakPointService {
    String execute(String user);
}
