package share.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class RedisBomStoreBean implements Serializable {
    private static final long serialVersionUID = -6170812250921490604L;
    private ArrayList<RedisBomBean> store;

    public RedisBomStoreBean() {
    }

    public void store(ArrayList<RedisBomBean> beans) {
        store = beans;
    }

    public ArrayList<RedisBomBean> getStore() {
        return store;
    }

}
