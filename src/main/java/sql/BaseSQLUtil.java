package sql;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sql.redis.HzDBException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseSQLUtil extends SqlSessionDaoSupport implements IBaseSQLUtil {
    private static final Logger logger = LoggerFactory.getLogger(BaseSQLUtil.class);
//    //DefaultSqlSession这个类不是线程安全的，所以DefaultSqlSession这个类不可以被设置成单例模式的
//    private static SqlSession session;
//    private static getSqlSession() getSqlSession();
//    //    锁对象，保证能进入双重校验锁结构语句
//    private Object obj = new Object();

//    static {
//        SqlSessionFactory f = FactoryManager.getInstance();
//        session = f.openSession();
//        getSqlSession() = new getSqlSession()(f);
//    }


//    private void checkSessionStatus() {
//        if (getSqlSession() == null) {
//            synchronized (obj) {
//                if (getSqlSession() == null) {
//                    SqlSessionFactory f = FactoryManager.getInstance();
//                    getSqlSession() = new getSqlSession()(f);
//                }
//            }
//        }
//    }

    public <T> T executeQueryById(final T suppliers, final String by) {
//        checkSessionStatus();
        T result = null;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = (T) getSqlSession().selectOne(by);
            } else {
                result = (T) getSqlSession().selectOne(by, suppliers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public <T> int executeInsert(final T suppliers, final String by) {
//        checkSessionStatus();
        int result = 0;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = getSqlSession().insert(by);
            } else {
                // getSqlSession().insert(by, suppliers);
                result = getSqlSession().insert(by, suppliers);
            }
//            getSqlSession().commit();
        } catch (Exception e) {
//            getSqlSession().rollback(true);
            e.printStackTrace();
            throw e;
        } finally {
        }
        return result;
    }

    public <T> List<T> executeQuery(final T suppliers,final String by) {
        List<T> result = null;
//        checkSessionStatus();
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = getSqlSession().selectList(by);
            } else {
                result = getSqlSession().selectList(by, suppliers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public <T, E> List<T> executeQueryByPass(final T suppliers,final E pass, final String by) {
//        checkSessionStatus();
        List<T> result = null;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = getSqlSession().selectList(by);
            } else {
                result = getSqlSession().selectList(by, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    @Override
    public <T> T executeQueryByPass(final T t, final String pass,final String by, final boolean b) {
//        checkSessionStatus();
        T result = null;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (t == null) {
                result = getSqlSession().selectOne(by);
            } else {
                result = getSqlSession().selectOne(by, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    @Override
    public <T> T executeQueryByPass(final T t, final String by,final String... pass) {
//        checkSessionStatus();
        T result = null;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (t == null) {
                result = getSqlSession().selectOne(by);
            } else {
                result = getSqlSession().selectOne(by, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public <T> int executeUpdate(final T suppliers, final String by) {
//        checkSessionStatus();
        int result = 0;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = getSqlSession().update(by);
            } else {
                result = getSqlSession().update(by, suppliers);
            }
//            getSqlSession().commit(true);
        } catch (Exception e) {
//            if (getSqlSession() != null) {
//                getSqlSession().rollback(true);
//            }
            e.printStackTrace();
            throw e;

        } finally {
        }
        return result;
    }

    public <T> int executeDelete(final T suppliers, final String by) {
//        checkSessionStatus();
        int result = 0;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = getSqlSession().delete(by);
            } else {
                result = getSqlSession().delete(by, suppliers);
            }
//            getSqlSession().commit(true);
        } catch (Exception e) {
//            getSqlSession().rollback(true);
            e.printStackTrace();
            throw e;
        } finally {
        }
        return result;
    }

    @Override
    public <T> int executeDelete(final List<T> ts,final String by) {
//        checkSessionStatus();
        int result = 0;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (ts == null) {
                result = getSqlSession().delete(by);
            } else {
                result = getSqlSession().delete(by, ts);
            }
//            getSqlSession().commit(true);
        } catch (Exception e) {
//            getSqlSession().rollback(true);
            e.printStackTrace();
            throw e;
        } finally {
        }
        return result;
    }

    public int executeDeleteByPass(final String puid, final String by) {
//        checkSessionStatus();
        int result = 0;
        try {
            System.out.println("执行sql方法:" + by);
            if (puid == null) {
                result = getSqlSession().delete(by);
            } else {
                result = getSqlSession().delete(by, puid);
            }
//            getSqlSession().commit(true);
        } catch (Exception e) {
//            getSqlSession().rollback(true);
            e.printStackTrace();
            throw e;
        } finally {
        }
        return result;
    }


    @Override
    public int executeDeleteBySome(final String by, final String... condition) {
//        checkSessionStatus();
        int result = 0;
        try {
            System.out.println("执行sql方法:" + by);
            if (condition == null) {
                result = getSqlSession().delete(by);
            } else {
                result = getSqlSession().delete(by, condition);
            }
//            getSqlSession().commit(true);
        } catch (Exception e) {
//            getSqlSession().rollback(true);
            e.printStackTrace();
            throw e;
        } finally {
        }
        return result;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * follows
     * author haozt
     */

    /**
     * 查询一个list
     *
     * @param sqlMapId
     * @param param
     * @return
     */
    public List findForList(final String sqlMapId, final Object param) {
        try {
            logger.info("BaseSQLUtil execute sql:" + sqlMapId);
            if (param == null) {
                 return getSqlSession().selectList(sqlMapId);
            } else {
                return  getSqlSession().selectList(sqlMapId, param);
            }
        } catch (Exception e) {
            throw new HzDBException("SQL执行出错" + sqlMapId, e);
        }
    }

    /**
     * 插入一个实体
     *
     * @param sqlMapId mybatis 映射id
     * @param object   实体参数
     * @return
     */
    public int insert(final String sqlMapId, final Object object) {
        try {
            int result = getSqlSession().insert(sqlMapId, object);
            return result;
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new HzDBException("SQL执行出错" + sqlMapId, e);
        }
    }

    /**
     * 查询一个实体
     *
     * @param sqlMapId mybatis 映射id
     * @param param    实体参数
     * @return
     */
    public Object findForObject(final String sqlMapId, final Object param) {
        try {
            if (param != null) {
                return getSqlSession().selectOne(sqlMapId, param);
            } else {
                return getSqlSession().selectOne(sqlMapId);
            }
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new HzDBException("SQL执行出错" + sqlMapId, e);
        }
    }

    /**
     * 修改
     *
     * @param sqlMapId
     * @param param
     * @return
     */
    public int update(final String sqlMapId, final Object param) {
        try {
            int result = getSqlSession().update(sqlMapId, param);
            return result;
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new HzDBException("SQL执行出错" + sqlMapId, e);
        } finally {
        }
    }

    public int delete(final String sqlMapId, final Object param) {
        try {
            int result = getSqlSession().delete(sqlMapId, param);
            return result;
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new HzDBException("SQL执行出错" + sqlMapId, e);
        }
    }

    /**
     * 分页查询 逻辑分页
     *
     * @param sqlMapId         mybatis映射id
     * @param pageRequestParam 分页请求参数信息
     * @return
     */
    public Page findForPage(final String sqlMapId, final String totalMapId, PageRequestParam pageRequestParam) {
        Map map = new HashMap();
        map.putAll(pageRequestParam.getFilters());
        // 查询总数
        Number totalCount = (Number) findForObject(totalMapId, map);
        if (totalCount == null || totalCount.intValue() <= 0) {
            return new Page(pageRequestParam, 0);
        }
        if (totalCount != null && totalCount.intValue() <= (pageRequestParam.getPageNumber() - 1) * pageRequestParam.getPageSize()) {
            return new Page(pageRequestParam.getPageNumber(), pageRequestParam.getPageSize(), totalCount.intValue(), new ArrayList(0));
        }
        if (pageRequestParam.getPageSize() == 0) {
            pageRequestParam.setPageSize((int) totalCount);
        }
        Page page = new Page(pageRequestParam, totalCount.intValue());
        List list = findForList(sqlMapId, map, page.getFirstResult(), page.getPageSize());

        page.setResult(list);

        return page;
    }

    /**
     * 带有分页信息的查询  物理分页
     *
     * @param sqlMapId         mybatis映射id
     * @param pageRequestParam 分页请求参数信息
     * @return
     */
    public Page findPage(final String sqlMapId, final String totalMapId, PageRequestParam pageRequestParam) {
        Map map = new HashMap();
        map.putAll(pageRequestParam.getFilters());
        // 查询总数
        Number totalCount = (Number) findForObject(totalMapId, map);
        if (totalCount == null || totalCount.intValue() <= 0) {
            return new Page(pageRequestParam, 0);
        }
        if (totalCount != null && totalCount.intValue() <= (pageRequestParam.getPageNumber() - 1) * pageRequestParam.getPageSize()) {
            return new Page(pageRequestParam.getPageNumber(), pageRequestParam.getPageSize(), totalCount.intValue(), new ArrayList(0));
        }
        if (pageRequestParam.getPageSize() == 0) {
            pageRequestParam.setPageSize((int) totalCount);
        }
        Page page = new Page(pageRequestParam, totalCount.intValue());
        map.put("offset", (pageRequestParam.getPageNumber() - 1) * pageRequestParam.getPageSize());
        map.put("limit", pageRequestParam.getPageNumber() * pageRequestParam.getPageSize());
        List list = findForList(sqlMapId, map);
        page.setResult(list);
        return page;
    }

    /**
     * 查询列表
     *
     * @param sqlMapId mybatis映射id
     * @param param    查询参数
     * @param offset   查询起始位置(偏移量),从1开始
     * @param limit    查询数量,必须大于0
     * @return
     */
    public List findForList(final String sqlMapId, final Object param, final int offset, final int limit) {
        try {
            return getSqlSession().selectList(sqlMapId, param, new RowBounds(offset, limit));
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new HzDBException("SQL执行出错: " + sqlMapId, e);
        }

    }


    @Override
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public SqlSession getSqlSession() {
        try {
            return super.getSqlSession();
        }catch (Exception e){
            throw new HzDBException("获取数据库连接session失败!",e);
        }
        
    }
}
