package sql;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
                // session.insert(by, suppliers);
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
                // session.insert(by, suppliers);
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

    public List findForList(final String sql, final Object param) {
        SqlSession session = null;
        List result = null;
        try {
            SqlSessionFactory f = FactoryManager.getInstance();
            session = f.openSession();
            logger.info("BaseSQLUtil execute sql:" + sql);
            if (param == null) {
                result = session.selectList(sql);
            } else {
                result = session.selectList(sql, param);
            }
            // session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // session.rollback(true);
        } finally {
            if (session != null)
                session.close();
        }
        return result;
    }
}
