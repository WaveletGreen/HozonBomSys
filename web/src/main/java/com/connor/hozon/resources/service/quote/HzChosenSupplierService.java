/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.resources.service.quote;

import com.connor.hozon.resources.domain.model.HzChosenSupplier;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 14:41
 * @Modified By:
 */
public interface HzChosenSupplierService {
    public Map<String, Object> load(HzChosenSupplier hzChosenSupplier) ;

    public JSONObject add(HzChosenSupplier hzChosenSupplier) ;

    public HzChosenSupplier selectById(Long id);

    public Map<String, Object> update(HzChosenSupplier hzChosenSupplier);

    public JSONObject delete(String ids) ;
}
