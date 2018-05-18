package sql;

import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
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
     * 查询数据 返回list
     * @param sqlMapId
     * @param param
     * @return
     */
    List findForList(final String sqlMapId, final Object param);
}
