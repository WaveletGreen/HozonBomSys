package com.connor.hozon.bom.sys.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.util.Result;
import com.connor.hozon.bom.sys.service.UserRolePrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @Author: haozt
 * @Date: 2018/11/6
 * @Description:
 */
public class HzFilter implements Filter {
    /**
     * 要过滤的url路径
     */
    private List<String> urlList;

    @Autowired
    private UserRolePrivilegeService userRolePrivilegeService;

    @Override
    public void init(FilterConfig filterConfig) {
        try{
            InputStream inputStream = HzFilter.class.getResourceAsStream("/hz_bom_privilege_url.properties");
            String line;
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            line = reader.readLine();
            while(line!=null){
                buffer.append(line);
                line = reader.readLine();
            }
            String paths = buffer.toString();
            String[] filePath = paths.split(",");
            urlList = new ArrayList();
            for(String path : filePath){
                urlList.add(path);
            }
            reader.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        String url = ((HttpServletRequest)servletRequest).getRequestURI();
        if(urlList.contains(url)){
            WriteResultRespDTO resultRespDTO = userRolePrivilegeService.hasPrivilege(url,urlList);
            if(WriteResultRespDTO.isSuccess(resultRespDTO)){
                filterChain.doFilter(servletRequest,servletResponse);
            }else {
                servletResponse.setContentType("text/html;charset=UTF-8");
                PrintWriter writer = servletResponse.getWriter();
                try {
//                Map<String,Object> map = new HashMap<>();
//                map.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
//                map.put(SystemStaticConst.MSG,"暂无权限！");
//                writer.write(JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect));
//                    writer.write(resultRespDTO.getErrMsg());

//                    writer.println("<html>");
//                    writer.println("<script>");
//                    writer.println("window.Ewin.alert({message: '没权限!'});");
//                    writer.println("</script>");
//                    writer.println("</html>");
//
                    writer.write(JSON.toJSONString(Result.build(false,resultRespDTO.getErrMsg()),SerializerFeature.DisableCircularReferenceDetect));
                }finally {
                    writer.flush();
                    writer.close();
                }
                return;
            }
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {
        if(urlList != null){
            urlList.clear();
            urlList = null;
        }
    }
}
