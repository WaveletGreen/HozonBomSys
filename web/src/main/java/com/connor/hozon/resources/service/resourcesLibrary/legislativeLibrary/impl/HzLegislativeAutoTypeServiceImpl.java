package com.connor.hozon.resources.service.resourcesLibrary.legislativeLibrary.impl;

import cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary.HzLegislativeAutoType;
import com.connor.hozon.bom.resources.domain.dto.response.HzLegislativeAutoTypeResDTO;
import com.connor.hozon.resources.domain.query.HzLegislativeAutoTypeQuery;
import com.connor.hozon.resources.mybatis.resourcesLibrary.legislativeLibrary.HzLegislativeAutoTypeDao;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.resourcesLibrary.legislativeLibrary.HzLegislativeAutoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("HzLegislativeAutoTypeService")
public class HzLegislativeAutoTypeServiceImpl implements HzLegislativeAutoTypeService {

    @Autowired
    private HzLegislativeAutoTypeDao hzLegislativeAutoTypeDao;
    /**
     * 分页获取法规件库整车级的数据
     * @param query
     * @return
     */
    @Override
    public Page<HzLegislativeAutoTypeResDTO> findHzLegislativeMotorcycleTypeToPage(HzLegislativeAutoTypeQuery query) {
        try {
            Page<HzLegislativeAutoType> libraries = hzLegislativeAutoTypeDao.findAutoTypeToPage(query);
            int num = (libraries.getPageNumber() - 1) * libraries.getPageSize();
            if (libraries == null || libraries.getResult() == null) {
                return new Page<>(libraries.getPageNumber(), libraries.getPageSize(), 0);
            }
            List<HzLegislativeAutoType> list = libraries.getResult();
            List<HzLegislativeAutoTypeResDTO> respDTOList = new ArrayList<>();
            for (HzLegislativeAutoType library : list) {
                HzLegislativeAutoTypeResDTO respDTO = new HzLegislativeAutoTypeResDTO();
                respDTO.setNo(++num);
                respDTO.setPuid(library.getPuid());
                respDTO.setNoticeNo(library.getNoticeNo());
                respDTO.setAutoType(library.getAutoType());
                respDTO.setVinNo(library.getVinNo());
                respDTO.setBatteryManufacturers(library.getBatteryManufacturers());
                respDTO.setBatteryModel(library.getBatteryModel());
                respDTO.setProductionMode(library.getProductionMode());
                respDTO.setMotorManufacturers(library.getMotorManufacturers());
                respDTO.setMotorModel(library.getMotorModel());
                respDTO.setRemarks(library.getRemarks());
                respDTOList.add(respDTO);
            }
            return new Page<>(libraries.getPageNumber(), libraries.getPageSize(), libraries.getTotalCount(), respDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得单条整车级数据
     * @param puid
     * @return
     */
    @Override
    public HzLegislativeAutoTypeResDTO findHzLegislativeMotorcycleTypeById(String puid) {
        return null;
    }
}
