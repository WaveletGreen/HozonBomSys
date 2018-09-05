package test;

import com.connor.hozon.bom.bomSystem.dao.impl.cfg0.HzDerivativeMaterielBasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.impl.cfg0.HzDerivativeMaterielDetailDaoImpl;

public class testPreferenceSetting {
    public static void main(String[] args) {
        System.out.println(
                HzDerivativeMaterielBasicDaoImpl.class.getCanonicalName());
        System.out.println(
                HzDerivativeMaterielBasicDaoImpl.class.getSuperclass().getCanonicalName());
        Object obj = HzDerivativeMaterielBasicDaoImpl.class.getGenericInterfaces()[0].getTypeName();
        Object obj2 = HzDerivativeMaterielBasicDaoImpl.class.getGenericInterfaces();

        Object obj3 = HzDerivativeMaterielDetailDaoImpl.class.getGenericInterfaces()[0].getTypeName();
        Object obj4 = HzDerivativeMaterielDetailDaoImpl.class.getGenericInterfaces();

        HzDerivativeMaterielBasicDaoImpl basicDao = new HzDerivativeMaterielBasicDaoImpl();
        basicDao.deleteByPrimaryKey(null);
        System.out.println();
    }
}