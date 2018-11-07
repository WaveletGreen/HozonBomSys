package sql;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sql.redis.HzDBException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("baseSQLUtil")
public class BaseSQLUtil implements IBaseSQLUtil {
    private static final Logger logger = LoggerFactory.getLogger(BaseSQLUtil.class);
    private static SqlSession session;
    private static SqlSessionTemplate sqlSessionTemplate;
    //    锁对象，保证能进入双重校验锁结构语句
    private Object obj = new Object();

    static {
        SqlSessionFactory f = FactoryManager.getInstance();
        session = f.openSession();
        sqlSessionTemplate = new SqlSessionTemplate(f);
    }


    private void checkSessionStatus() {
        if (sqlSessionTemplate == null) {
            synchronized (obj) {
                if (sqlSessionTemplate == null) {
                    SqlSessionFactory f = FactoryManager.getInstance();
                    sqlSessionTemplate = new SqlSessionTemplate(f);
                }
            }
        }
    }

    public <T> T executeQueryById(T suppliers, String by) {
        checkSessionStatus();
        T result = null;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = (T) sqlSessionTemplate.selectOne(by);
            } else {
                result = (T) sqlSessionTemplate.selectOne(by, suppliers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public <T> int executeInsert(T suppliers, String by) {
        checkSessionStatus();
        int result = 0;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = sqlSessionTemplate.insert(by);
            } else {
                // sqlSessionTemplate.insert(by, suppliers);
                result = sqlSessionTemplate.insert(by, suppliers);
            }
//            sqlSessionTemplate.commit();
        } catch (Exception e) {
//            sqlSessionTemplate.rollback(true);
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public <T> List<T> executeQuery(T suppliers, String by) {
        List<T> result = null;
        checkSessionStatus();
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = sqlSessionTemplate.selectList(by);
            } else {
                result = sqlSessionTemplate.selectList(by, suppliers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public <T, E> List<T> executeQueryByPass(T suppliers, E pass, String by) {
        checkSessionStatus();
        List<T> result = null;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = sqlSessionTemplate.selectList(by);
            } else {
                result = sqlSessionTemplate.selectList(by, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    @Override
    public <T> T executeQueryByPass(T t, String pass, String by, boolean b) {
        checkSessionStatus();
        T result = null;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (t == null) {
                result = sqlSessionTemplate.selectOne(by);
            } else {
                result = sqlSessionTemplate.selectOne(by, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    @Override
    public <T> T executeQueryByPass(T t, String by, String... pass) {
        checkSessionStatus();
        T result = null;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (t == null) {
                result = sqlSessionTemplate.selectOne(by);
            } else {
                result = sqlSessionTemplate.selectOne(by, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public <T> int executeUpdate(T suppliers, String by) {
        checkSessionStatus();
        int result = 0;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = sqlSessionTemplate.update(by);
            } else {
                result = sqlSessionTemplate.update(by, suppliers);
            }
//            sqlSessionTemplate.commit(true);
        } catch (Exception e) {
//            if (sqlSessionTemplate != null) {
//                sqlSessionTemplate.rollback(true);
//            }
            e.printStackTrace();

        } finally {
        }
        return result;
    }

    public <T> int executeDelete(T suppliers, String by) {
        checkSessionStatus();
        int result = 0;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = sqlSessionTemplate.delete(by);
            } else {
                result = sqlSessionTemplate.delete(by, suppliers);
            }
//            sqlSessionTemplate.commit(true);
        } catch (Exception e) {
//            sqlSessionTemplate.rollback(true);
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    @Override
    public <T> int executeDelete(List<T> ts, String by) {
        checkSessionStatus();
        int result = 0;
        try {
            logger.info("BaseSQLUtil execute sql:" + by);
            if (ts == null) {
                result = sqlSessionTemplate.delete(by);
            } else {
                result = sqlSessionTemplate.delete(by, ts);
            }
//            sqlSessionTemplate.commit(true);
        } catch (Exception e) {
//            sqlSessionTemplate.rollback(true);
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    public int executeDeleteByPass(String puid, String by) {
        checkSessionStatus();
        int result = 0;
        try {
            System.out.println("执行sql方法:" + by);
            if (puid == null) {
                result = sqlSessionTemplate.delete(by);
            } else {
                result = sqlSessionTemplate.delete(by, puid);
            }
//            sqlSessionTemplate.commit(true);
        } catch (Exception e) {
//            sqlSessionTemplate.rollback(true);
            e.printStackTrace();
        } finally {
        }
        return result;
    }


    @Override
    public int executeDeleteBySome(String by, String... condition) {
        checkSessionStatus();
        int result = 0;
        try {
            System.out.println("执行sql方法:" + by);
            if (condition == null) {
                result = sqlSessionTemplate.delete(by);
            } else {
                result = sqlSessionTemplate.delete(by, condition);
            }
//            sqlSessionTemplate.commit(true);
        } catch (Exception e) {
//            sqlSessionTemplate.rollback(true);
            e.printStackTrace();
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
//        SqlSession session = null;
        checkSessionStatus();
        List result = null;
        try {
//            SqlSessionFactory f = FactoryManager.getInstance();
//            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + sqlMapId);
            if (param == null) {
                result = sqlSessionTemplate.selectList(sqlMapId);
            } else {
                result = sqlSessionTemplate.selectList(sqlMapId, param);
            }
            // session.commit();
        } catch (Exception e) {
            throw new HzDBException("SQL执行出错" + sqlMapId, e);
        } finally {
//            if (session != null)
//                session.close();
        }
        return result;
    }

    /**
     * 插入一个实体
     *
     * @param sqlMapId mybatis 映射id
     * @param object   实体参数
     * @return
     */
    public int insert(final String sqlMapId, final Object object) {
//        SqlSession session = null;
        checkSessionStatus();
        try {
//            SqlSessionFactory f = FactoryManager.getInstance();
//            session = f.openSession();
            int result = sqlSessionTemplate.insert(sqlMapId, object);
//            sqlSessionTemplate.commit();
            return result;
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new HzDBException("SQL执行出错" + sqlMapId, e);
        } finally {
//            if (session != null)
//                session.close();
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
//        SqlSession session = null;
        checkSessionStatus();
        try {
//            SqlSessionFactory f = FactoryManager.getInstance();
//            session = f.openSession();
            if (param != null) {
                return sqlSessionTemplate.selectOne(sqlMapId, param);
            } else {
                return sqlSessionTemplate.selectOne(sqlMapId);
            }
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new HzDBException("SQL执行出错" + sqlMapId, e);
        } finally {
//            if (session != null)
//                session.close();
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
//        SqlSession session = null;
        checkSessionStatus();
        try {
//            SqlSessionFactory factory = FactoryManager.getInstance();
//            session = factory.openSession();
            int result = sqlSessionTemplate.update(sqlMapId, param);
//            sqlSessionTemplate.commit();
            return result;
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new HzDBException("SQL执行出错" + sqlMapId, e);
        } finally {
//            if (session != null)
//                session.close();
        }
    }

    public int delete(final String sqlMapId, final Object param) {
//        SqlSession session = null;
        checkSessionStatus();
        try {
//            SqlSessionFactory f = FactoryManager.getInstance();
//            session = f.openSession();
            int result = sqlSessionTemplate.delete(sqlMapId, param);
//            sqlSessionTemplate.commit();
            return result;
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new HzDBException("SQL执行出错" + sqlMapId, e);
        } finally {
//            if (session != null)
//                session.close();
        }
    }

    /**
     * 带有分页信息的查询
     *
     * @param sqlMapId         mybatis映射id
     * @param pageRequestParam 分页请求参数信息 逻辑分页
     * @return
     */
    public Page findForPage(final String sqlMapId, final String totalMapId, PageRequestParam pageRequestParam) {
        Map filters = new HashMap();
        filters.putAll(pageRequestParam.getFilters());
        // 查询总数
        Number totalCount = (Number) findForObject(totalMapId, filters);
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
        List list = findForList(sqlMapId, filters, page.getFirstResult(), page.getPageSize());

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
        Map filters = new HashMap();
        filters.putAll(pageRequestParam.getFilters());
        // 查询总数
        Number totalCount = (Number) findForObject(totalMapId, filters);
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
        filters.put("offset", (pageRequestParam.getPageNumber() - 1) * pageRequestParam.getPageSize());
        filters.put("limit", pageRequestParam.getPageNumber() * pageRequestParam.getPageSize());
        List list = findForList(sqlMapId, filters);
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
//        SqlSession session = null;
        checkSessionStatus();
        try {
//            SqlSessionFactory f = FactoryManager.getInstance();
//            session = f.openSession();
            return sqlSessionTemplate.selectList(sqlMapId, param, new RowBounds(offset, limit));
        } catch (Exception e) {
//            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new HzDBException("SQL执行出错: " + sqlMapId, e);
        }

    }

}
