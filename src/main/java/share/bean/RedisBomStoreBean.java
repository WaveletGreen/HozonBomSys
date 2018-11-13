package share.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 为快速获得BOM数据和快速结算，从redis中获取数据更快，只不过合众服务器承载不了大批量的BOM数据，不在存储BOM数据到内存中
 */
@Deprecated
@Data
public class RedisBomStoreBean implements Serializable {
    private static final long serialVersionUID = -6170812250921490604L;
    private ArrayList<RedisBomBean> store;
}
