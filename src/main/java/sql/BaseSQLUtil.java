package sql;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sql.redis.DatabaseException;

import java.util.List;

@Service("baseSQLUtil")
public class BaseSQLUtil implements IBaseSQLUtil {
    private static final Logger logger = LoggerFactory.getLogger(BaseSQLUtil.class);

    public <T> T executeQueryById(T suppliers, String by) {
        SqlSession session = null;
        T result = null;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = (T) session.selectOne(by);
            } else {
                // session.insertOne(by, suppliers);
                result = (T) session.selectOne(by, suppliers);
            }
            // session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // session.rollback(true);
        } finally {
            // if (session != null)
            // session.close();
        }
        return result;
    }

    public <T> int executeInsert(T suppliers, String by) {
        SqlSession session = null;
        int result = 0;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = session.insert(by);
            } else {
                // session.insertOne(by, suppliers);
                result = session.insert(by, suppliers);
            }
            session.commit();
        } catch (Exception e) {
            session.rollback(true);
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }

    public <T> List<T> executeQuery(T suppliers, String by) {
        SqlSession session = null;
        List<T> result = null;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = session.selectList(by);
            } else {
                result = session.selectList(by, suppliers);
            }
            // session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // session.rollback(true);
        } finally {
            // if (session != null)
            // session.close();
        }
        return result;
    }

    @Override
    public <T> T executeQueryByPass(T t, String pass, String by, boolean b) {
        SqlSession session = null;
        T result = null;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + by);
            if (t == null) {
                result = session.selectOne(by);
            } else {
                result = session.selectOne(by, pass);
            }
            // session.commit(true);
        } catch (Exception e) {
            e.printStackTrace();
            // session.rollback(true);
        } finally {
            // if (session != null)
            // session.close();
        }
        return result;
    }

    public <T, E> List<T> executeQueryByPass(T suppliers, E pass, String by) {
        SqlSession session = null;
        List<T> result = null;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = session.selectList(by);
            } else {
                result = session.selectList(by, pass);
            }
            // session.commit(true);
        } catch (Exception e) {
            e.printStackTrace();
            // session.rollback(true);
        } finally {
            // if (session != null)
            // session.close();
        }
        return result;
    }

    public <T> int executeUpdate(T suppliers, String by) {
        SqlSession session = null;
        int result = 0;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = session.update(by);
            } else {
                result = session.update(by, suppliers);
            }
            session.commit(true);
        } catch (Exception e) {
            if (session != null) {
                session.rollback(true);
            }
            e.printStackTrace();

        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }

    public <T> int executeDelete(T suppliers, String by) {
        SqlSession session = null;
        int result = 0;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + by);
            if (suppliers == null) {
                result = session.delete(by);
            } else {
                result = session.delete(by, suppliers);
            }
            session.commit(true);
        } catch (Exception e) {
            session.rollback(true);
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }

    @Override
    public <T> int executeDelete(List<T> ts, String by) {
        SqlSession session = null;
        int result = 0;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + by);
            if (ts == null) {
                result = session.delete(by);
            } else {
                result = session.delete(by, ts);
            }
            session.commit(true);
        } catch (Exception e) {
            session.rollback(true);
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }

    /**
     * follows
     * author haozt
     */

    /**
     * 查询一个list
     * @param sqlMapId
     * @param param
     * @return
     */
    public List findForList(final String sqlMapId, final Object param) {
        SqlSession session = null;
        List result = null;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + sqlMapId);
            if (param == null) {
                result = session.selectList(sqlMapId);
            } else {
                result = session.selectList(sqlMapId, param);
            }
            // session.commit();
        } catch (Exception e) {
           throw new DatabaseException("SQL执行出错"+sqlMapId,e);
        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }

    /**
     * 插入一个实体
     *
     * @param sqlMapId  mybatis 映射id
     * @param object  实体参数
     * @return
     */
    public int insert(final String sqlMapId, final Object object) {
        SqlSession session = null;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            int result = session.insert(sqlMapId, object);
            session.commit();
            return result;
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new DatabaseException("SQL执行出错"+sqlMapId,e);
        } finally {
            if (session != null)
                session.close();
        }

    }

    /**
     * 查询一个实体
     *
     * @param sqlMapId  mybatis 映射id
     * @param param  实体参数
     * @return
     */
    public Object findForObject(final String sqlMapId, final Object param){
        SqlSession session = null;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            if (param != null) {
                return session.selectOne(sqlMapId, param);
            } else {
                return session.selectOne(sqlMapId);
            }
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new DatabaseException("SQL执行出错"+sqlMapId,e);
        } finally {
            if (session != null)
                session.close();
        }
    }

    /**
     * 修改
     * @param sqlMapId
     * @param param
     * @return
     */
    public int update(final String sqlMapId, final Object param){
        SqlSession session = null;
        try {
            SqlSessionFactory factory = FactoryManager.getInstance();
            session = factory.openSession();
            int result = session.update(sqlMapId, param);
            session.commit();
            return result;
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new DatabaseException("SQL执行出错"+sqlMapId,e);
        } finally {
            if (session != null)
                session.close();
        }
    }

    public int delete(final String sqlMapId, final Object param){
        SqlSession session = null;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            int result = session.delete(sqlMapId, param);
            session.commit();
            return result;
        } catch (Exception e) {
            logger.error("SQL执行出错: " + sqlMapId, e);
            throw new DatabaseException("SQL执行出错"+sqlMapId,e);
        } finally {
            if (session != null)
                session.close();
        }
    }
}
