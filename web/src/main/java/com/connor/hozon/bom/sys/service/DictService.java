package com.connor.hozon.bom.sys.service;

import com.connor.hozon.bom.common.base.dao.GenericDao;
import com.connor.hozon.bom.common.base.service.GenericService;
import com.connor.hozon.bom.sys.dao.DictDao;
import com.connor.hozon.bom.sys.entity.Dict;
import com.connor.hozon.bom.sys.entity.QueryDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


/**
 *@author linzf
 **/
@Service("dictService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class DictService extends GenericService<Dict, QueryDict> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private DictDao dictDao;
	@Override
	protected GenericDao<Dict, QueryDict> getDao() {
		return dictDao;
	}
}