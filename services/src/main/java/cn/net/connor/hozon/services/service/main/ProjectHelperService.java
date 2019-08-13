/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.main;

import cn.net.connor.hozon.dao.pojo.depository.project.HzBrandRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzPlatformRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzProjectLibs;
import cn.net.connor.hozon.dao.pojo.depository.project.HzVehicleRecord;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/13 16:35
 * @Modified By:
 */
public interface ProjectHelperService {
    void doGetProjectTreeByProjectId(String projectPuid);

    HzBrandRecord getBrand();

    HzPlatformRecord getPlatform();

    HzVehicleRecord getVehicle();

    HzProjectLibs getProject();
}
