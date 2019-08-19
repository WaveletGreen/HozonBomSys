/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzDictionaryLibraryReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzDictionaryLibraryReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzDictionaryLibraryRespDTO;
import com.connor.hozon.bom.resources.util.DateUtil;
import cn.net.connor.hozon.dao.pojo.depository.dictionaryLibrary.HzDictionaryLibrary;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/6
 * Time: 15:04
 * 数据字典工厂类
 */
public class HzDictionaryLibraryFactory {
    public static HzDictionaryLibrary addDictionaryDTOHzDictionaryLibrary(AddHzDictionaryLibraryReqDTO reqDTO){
        HzDictionaryLibrary library = new HzDictionaryLibrary();
        if(reqDTO.getPuid()==null){
            library.setPuid(UUID.randomUUID().toString());
        }else {
            library.setPuid(reqDTO.getPuid());
        }
        library.setProfessionCh(reqDTO.getProfessionCh());
        library.setProfessionEn(reqDTO.getProfessionEn());
        library.setClassificationCh(reqDTO.getClassificationCh());
        library.setClassificationEn(reqDTO.getClassificationEn());
        library.setGroupCode(reqDTO.getGroupCode());
        library.setGroupCh(reqDTO.getGroupCh());
        library.setGroupEn(reqDTO.getGroupEn());
        library.setFamillyCode(reqDTO.getFamillyCode());
        library.setFamillyCh(reqDTO.getFamillyCh());
        library.setFamillyEn(reqDTO.getFamillyEn());
        library.setEigenValue(reqDTO.getEigenValue());
        library.setValueDescCh(reqDTO.getValueDescCh());
        library.setValueDescEn(reqDTO.getValueDescEn());
        library.setType(reqDTO.getType());
        library.setValueSource(reqDTO.getValueSource());

        library.setEffectTime(reqDTO.getEffectTime());
        library.setFailureTime(reqDTO.getFailureTime());

        library.setNote(reqDTO.getNote());
        return library;
    }
    public static HzDictionaryLibrary updateDictionaryDTOHzDictionaryLibrary(UpdateHzDictionaryLibraryReqDTO reqDTO){
        HzDictionaryLibrary library = new HzDictionaryLibrary();
        library.setPuid(reqDTO.getPuid());
        library.setProfessionCh(reqDTO.getProfessionCh());
        library.setProfessionEn(reqDTO.getProfessionEn());
        library.setClassificationCh(reqDTO.getClassificationCh());
        library.setClassificationEn(reqDTO.getClassificationEn());
        library.setGroupCode(reqDTO.getGroupCode());
        library.setGroupCh(reqDTO.getGroupCh());
        library.setGroupEn(reqDTO.getGroupEn());
        library.setFamillyCode(reqDTO.getFamillyCode());
        library.setFamillyCh(reqDTO.getFamillyCh());
        library.setFamillyEn(reqDTO.getFamillyEn());
        library.setEigenValue(reqDTO.getEigenValue());
        library.setValueDescCh(reqDTO.getValueDescCh());
        library.setValueDescEn(reqDTO.getValueDescEn());
        library.setType(reqDTO.getType());
        library.setValueSource(reqDTO.getValueSource());

        library.setEffectTime("".equals(reqDTO.getEffectTime())?null:DateUtil.parseDefaultDate(reqDTO.getEffectTime()));
        library.setFailureTime(DateUtil.parseDefaultDate(reqDTO.getFailureTime()));

        library.setNote(reqDTO.getNote());
        return library;
    }
    public static HzDictionaryLibraryRespDTO libraryToRespDTO(HzDictionaryLibrary library){
        HzDictionaryLibraryRespDTO dto = new HzDictionaryLibraryRespDTO();
        dto.setPuid(library.getPuid());
        dto.setProfessionCh(library.getProfessionCh());
        dto.setProfessionEn(library.getProfessionEn());
        dto.setClassificationCh(library.getClassificationCh());
        dto.setClassificationEn(library.getClassificationEn());
        dto.setGroupCode(library.getGroupCode());
        dto.setGroupCh(library.getGroupCh());
        dto.setGroupEn(library.getGroupEn());
        dto.setFamillyCode(library.getFamillyCode());
        dto.setFamillyCh(library.getFamillyCh());
        dto.setFamillyEn(library.getFamillyEn());
        dto.setEigenValue(library.getEigenValue());
        dto.setValueDescCh(library.getValueDescCh());
        dto.setValueDescEn(library.getValueDescEn());
        dto.setType(library.getType());
        dto.setValueSource(library.getValueSource());
        dto.setEffectTime(DateUtil.formatDefaultDate(library.getEffectTime()));
        dto.setFailureTime(DateUtil.formatDefaultDate(library.getFailureTime()));
        dto.setNote(library.getNote());
        return  dto;
    }
}
