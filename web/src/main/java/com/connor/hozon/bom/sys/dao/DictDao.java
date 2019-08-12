package com.connor.hozon.bom.sys.dao;

import com.connor.hozon.bom.common.base.dao.GenericDao;
import com.connor.hozon.bom.sys.entity.Dict;
import com.connor.hozon.bom.sys.entity.QueryDict;
import org.springframework.stereotype.Repository;


/**
 *@author linzf
 **/
@Repository
public interface DictDao extends GenericDao<Dict, QueryDict> {

}