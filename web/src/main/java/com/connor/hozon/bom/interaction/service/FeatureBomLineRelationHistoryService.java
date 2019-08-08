/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.interaction.service;

import org.springframework.ui.Model;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/8 17:29
 * @Modified By:
 */
public interface FeatureBomLineRelationHistoryService {
    String checkStatus(String projectId, Long vehiclesId, Model model);
}
