package sql;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/9
 * @Description:
 */
public interface BaseSQLDAO<T> {
    List findForList(final String sqlMapId, final Object param);

    Page findPage(final String sqlMapId, final String totalMapId, PageRequestParam pageRequestParam);

    T findForObject(final String sqlMapId, final Object param);

    int insert(final String sqlMapId, final Object param);

    int update(final String sqlMapId, final Object param);

    int delete(final String sqlMapId, final Object param);

    List findForList(final String sqlMapId, final Object param, final int offset, final int limit);

}
