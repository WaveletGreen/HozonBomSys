package com.connor.hozon.bom.sys.service;

import cn.net.connor.hozon.dao.dao.sys.GenericDao;
import com.connor.hozon.bom.common.base.service.GenericService;
import cn.net.connor.hozon.dao.dao.sys.DictDao;
import cn.net.connor.hozon.dao.pojo.sys.Dict;
import cn.net.connor.hozon.dao.pojo.sys.QueryDict;
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