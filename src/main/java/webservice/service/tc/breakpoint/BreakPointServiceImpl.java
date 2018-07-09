package webservice.service.tc.breakpoint;

import java.util.Date;

/**
 * 断点信息服务实现层
 */
public class BreakPointServiceImpl implements BreakPointService {
    @Override
    public String execute(String user) {
        return user + ":hello" + "(" + new Date() + ")";
    }
}