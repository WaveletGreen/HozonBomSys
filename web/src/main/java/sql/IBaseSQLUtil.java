package sql;

import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.page.PageRequestParam;

import java.util.List;

public interface IBaseSQLUtil {
    /**
     * 根据ID查找对象
     *
     * @param t   查找的对象，应该附带类型
     * @param by  需要执行的sql语句
     * @param <T> 传入的对象类型
     * @return 返回一组符合条件的对象
     */
    <T> T executeQueryById(T t, String by);

    /**
     * 查找符合条件的对象，返回一组对象
     *
     * @param t   需要查找的对象，该对象应该包含搜索条件
     * @param by  需要执行的sql语句
     * @param <T> 传入的对象类型
     * @return 返回一组符合条件的对象
     */
    <T> List<T> executeQuery(T t, String by);

    /**
     * 通过条件对象搜索目标对象
     *
     * @param t    需要搜索的对象
     * @param pass 搜索的条件对象
     * @param by   需要执行的sql语句
     * @param <T>  搜索的对象类型
     * @param <E>  所搜条件的对象类型
     * @return 返回符合条件的一组对象
     */
    <T, E> List<T> executeQueryByPass(T t, E pass, String by);


    /**
     * 通过条件对象搜索目标对象
     *
     * @param t    需要搜索的对象
     * @param pass 需要执行的sql语句
     * @param by   需要执行的sql语句
     * @param b    只是用来判断这个函数的补充条件
     * @param <T>
     * @return
     */
    <T> T executeQueryByPass(T t, String pass, String by, boolean b);

    /**
     * 通过条件对象搜索目标对象
     *
     * @param t    需要搜索的对象
     * @param pass 需要执行的sql参数
     * @param by   需要执行的sql语句
     * @param <T>
     * @return
     */
    <T> T executeQueryByPass(T t, String by, String... pass);

    /**
     * 执行插入，单个插入
     *
     * @param t   需要插入的对象，如果该对象的某个值不应该为空，而传入了控制，有可能报数据库插入异常
     * @param by  需要执行的sql语句
     * @param <T> 传入的对象类型
     * @return 插入成功返回1，失败返回0
     */
    <T> int executeInsert(T t, String by);

    /**
     * 更新对象
     *
     * @param t   需要更新的对象
     * @param by  需要执行的sql语句
     * @param <T> 传入的对象类型
     * @return 更新成功返回1，失败返回0
     */
    <T> int executeUpdate(T t, String by);

    /**
     * 删除对象
     *
     * @param t   需要删除的对象
     * @param by  需要执行的sql语句
     * @param <T>
     * @return
     */
    <T> int executeDelete(T t, String by);

    /**
     * 批量删除数据
     *
     * @param ts  数据源
     * @param by  执行的sql语句
     * @param <T> 参数类型
     * @return
     */
    <T> int executeDelete(List<T> ts, String by);

    /**
     * 根据主键直接删除对象
     *
     * @param puid
     * @param by
     * @return
     */
    int executeDeleteByPass(String puid, String by);

    /**
     * 根据主键直接删除对象
     *
     * @param condition
     * @param by
     * @return
     */
    int executeDeleteBySome(String by, String... condition);

    /**
     * 查询数据 返回list
     * @param sqlMapId
     * @param param
     * @return
     */
    List findForList(final String sqlMapId, final Object param);

    /**
     * 分页查询
     * @param sqlMapId
     * @param totalMapId
     * @param pageRequestParam
     * @return
     */
    Page findPage(final String sqlMapId, final String totalMapId, PageRequestParam pageRequestParam);

    /**
     * 获取一个对象
     * @param sqlMapId
     * @param param
     * @param <T>
     * @return
     */
    <T> T findForObject(final String sqlMapId, final Object param);

    /**
     * 插入记录
     * @param sqlMapId
     * @param param
     * @return
     */
    int insert(final String sqlMapId, final Object param);

    /**
     * 更新记录
     * @param sqlMapId
     * @param param
     * @return
     */
    int update(final String sqlMapId, final Object param);

    /**
     * 删除记录
     * @param sqlMapId
     * @param param
     * @return
     */
    int delete(final String sqlMapId, final Object param);

    /**
     * 查询list
     * @param sqlMapId
     * @param param
     * @param offset
     * @param limit
     * @return
     */
    List findForList(final String sqlMapId, final Object param, final int offset, final int limit);
}
