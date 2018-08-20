package com.connor.hozon.bom.activiti.cfg;

import com.connor.hozon.bom.activiti.ActImpl;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by K on 2018/8/20.
 */
@Component
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

    @Autowired
    ActImpl actImpl;

    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        System.out.println("开始配置ActivitiEngine");
        List<ActivitiEventListener> activitiEventListener=new ArrayList<>();
        activitiEventListener.add(actImpl);
        Map<String, List<ActivitiEventListener>> typedListener=new HashMap<>();
        typedListener.put("PROCESS_COMPLETED",activitiEventListener);
        springProcessEngineConfiguration.setTypedEventListeners(typedListener);
        DataSource dataSource=new DataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST = 192.168.9.234)(PORT = 1521))(CONNECT_DATA=(SERVER = DEDICATED)(SERVICE_NAME=tc)))");
        dataSource.setUsername("activiti");
        dataSource.setPassword("infodba");
        springProcessEngineConfiguration.setDataSource(dataSource);
        springProcessEngineConfiguration.setAsyncExecutorActivate(true);
        springProcessEngineConfiguration.setHistoryLevel(HistoryLevel.AUDIT);
        //springProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
        //springProcessEngineConfiguration.setDatabaseSchema("ACT"); //自动建表 似乎没用
    }
}
