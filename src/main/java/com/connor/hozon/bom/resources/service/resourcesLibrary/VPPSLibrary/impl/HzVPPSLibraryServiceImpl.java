package com.connor.hozon.bom.resources.service.resourcesLibrary.VPPSLibrary.impl;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzVPPSLibraryReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzVPPSLibraryRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzVPPSLibraryQuery;
import com.connor.hozon.bom.resources.mybatis.resourcesLibrary.VPPSLibrary.HzVPPSLibraryDao;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.resourcesLibrary.VPPSLibrary.HzVPPSLibraryService;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.resourcesLibrary.VPPSLibrary.HzVPPSLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/4
 * Time: 19:54
 */
@Service("HzVPPSLibraryService")
public class HzVPPSLibraryServiceImpl implements HzVPPSLibraryService {

    @Autowired
    private HzVPPSLibraryDao hzVPPSLibraryDao;

    /**
     * 分页获取VPPS库的数据
     *
     * @param query
     * @return
     */
    @Override
    public Page<HzVPPSLibraryRespDTO> findHzVPPSLibraryToPage(HzVPPSLibraryQuery query) {
        try {
            Page<HzVPPSLibrary> libraries = hzVPPSLibraryDao.findVPPSLibraryToPage(query);
            int num = (libraries.getPageNumber() - 1) * libraries.getPageSize();
            if (libraries == null || libraries.getResult() == null) {
                return new Page<>(libraries.getPageNumber(), libraries.getPageSize(), 0);
            }
            List<HzVPPSLibrary> list = libraries.getResult();
            List<HzVPPSLibraryRespDTO> respDTOList = new ArrayList<>();
            for (HzVPPSLibrary library : list) {
                HzVPPSLibraryRespDTO respDTO = new HzVPPSLibraryRespDTO();
                respDTO.setNo(++num);
                respDTO.setPuid(library.getPuid());
                respDTO.setVppsLevel(library.getVppsLevel());
                respDTO.setVsgCode(library.getVsgCode());
                respDTO.setVppsCode(library.getVppsCode());
                respDTO.setVppsEnDesc(library.getVppsEnDesc());
                respDTO.setVppsChDesc(library.getVppsChDesc());
                respDTO.setUpc(library.getUpc());
                respDTO.setFna(library.getFna());
                respDTO.setFnaChDesc(library.getFnaChDesc());
                respDTO.setStandardPartCode(library.getStandardPartCode());
                respDTOList.add(respDTO);
            }
            return new Page<>(libraries.getPageNumber(), libraries.getPageSize(), libraries.getTotalCount(), respDTOList);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 添加一条数据
     *
     * @param reqDTO
     * @return
     */
    @Override
    public OperateResultMessageRespDTO insertHzVPPSLibrary(AddHzVPPSLibraryReqDTO reqDTO) {
        try {
            boolean b = PrivilegeUtil.writePrivilege();
            if (!b) {
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            HzVPPSLibrary library = new HzVPPSLibrary();
            library.setPuid(UUID.randomUUID().toString());
            library.setVppsLevel(reqDTO.getVppsLevel());
            library.setVsgCode(reqDTO.getVsgCode());
            library.setVppsCode(reqDTO.getVppsCode());
            library.setVppsEnDesc(reqDTO.getVppsEnDesc());
            library.setVppsChDesc(reqDTO.getVppsChDesc());
            library.setUpc(reqDTO.getUpc());
            library.setFna(reqDTO.getFna());
            library.setFnaChDesc(reqDTO.getFnaChDesc());
            library.setStandardPartCode(reqDTO.getStandardPartCode());
            int i = hzVPPSLibraryDao.insert(library);
            if (i > 0) {
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
        return null;
    }

    /**
     * 根据puid查询单条数据
     *
     * @param puid
     * @return
     */
    @Override
    public HzVPPSLibraryRespDTO findHzVPPSLibraryById(String puid) {
        HzVPPSLibrary library = hzVPPSLibraryDao.findVPPSLibrary(puid);
        try {
            if (library != null) {
                HzVPPSLibraryRespDTO respDTO = new HzVPPSLibraryRespDTO();
                respDTO.setPuid(library.getPuid());
                respDTO.setVppsLevel(library.getVppsLevel());
                respDTO.setVsgCode(library.getVsgCode());
                respDTO.setVppsCode(library.getVppsCode());
                respDTO.setVppsEnDesc(library.getVppsEnDesc());
                respDTO.setVppsChDesc(library.getVppsChDesc());
                respDTO.setUpc(library.getUpc());
                respDTO.setFna(library.getFna());
                respDTO.setFnaChDesc(library.getFnaChDesc());
                respDTO.setStandardPartCode(library.getStandardPartCode());
                return respDTO;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 编辑一条数据
     *
     * @param reqDTO
     * @return
     */
    @Override
    public OperateResultMessageRespDTO updateHzVPPSLibrary(AddHzVPPSLibraryReqDTO reqDTO) {
        try {
            boolean b = PrivilegeUtil.writePrivilege();
            if (!b) {
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            HzVPPSLibrary library = new HzVPPSLibrary();
            library.setPuid(reqDTO.getPuid());
            library.setVppsLevel(reqDTO.getVppsLevel());
            library.setVsgCode(reqDTO.getVsgCode());
            library.setVppsCode(reqDTO.getVppsCode());
            library.setVppsEnDesc(reqDTO.getVppsEnDesc());
            library.setVppsChDesc(reqDTO.getVppsChDesc());
            library.setUpc(reqDTO.getUpc());
            library.setFna(reqDTO.getFna());
            library.setFnaChDesc(reqDTO.getFnaChDesc());
            library.setStandardPartCode(reqDTO.getStandardPartCode());
            int i = hzVPPSLibraryDao.update(library);
            if (i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    /**
     * 根据puid删除一条数据
     *
     * @param puid
     * @return
     */
    @Override
    public OperateResultMessageRespDTO deleteHzVPPSLibrary(String puid) {
        try {
            boolean b  = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }

            int i = hzVPPSLibraryDao.delete(puid);
            if (i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }
}
