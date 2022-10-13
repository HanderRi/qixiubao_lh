package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.MaterialsDTO;
import com.example.demo.VO.MaterialsVO;
import com.example.demo.entity.Materials;
import com.example.demo.mapper.Materialsmapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MaterialsService {
    @Resource
    private Materialsmapper materialsmapper;

    public void updateMaterialsList(Materials materials) {
        materialsmapper.updateById(materials);
    }

    public void insert(Materials materials) {
        materials.setIsDeleted(false);
        materialsmapper.insert(materials);
    }

    public void logicDelete(Long id) {
        materialsmapper.deleteById(id);
    }

    public Page<MaterialsVO> findMaterialsListPage(Integer pageNum,
                                                   Integer pageSize,
                                                   MaterialsDTO materialsDTO) throws ParseException {

        Date minCreateTime, maxCreateTime, minUpdateTime, maxUpdateTime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        minCreateTime = dateFormat.parse("1900/01/01 00:00:00");
        maxCreateTime = dateFormat.parse("2300/01/01 00:00:00");
        minUpdateTime = dateFormat.parse("1900/01/01 00:00:00");
        maxUpdateTime = dateFormat.parse("2300/01/01 00:00:00");
        if (materialsDTO.getMinCreateTime() != null) {
            minCreateTime = materialsDTO.getMinCreateTime();
        }
        if (materialsDTO.getMaxCreateTime() != null) {
            maxCreateTime = materialsDTO.getMaxCreateTime();
        }
        if (materialsDTO.getMinUpdateTime() != null) {
            minUpdateTime = materialsDTO.getMinUpdateTime();
        }
        if (materialsDTO.getMaxUpdateTime() != null) {
            maxUpdateTime = materialsDTO.getMaxUpdateTime();
        }


        LambdaQueryWrapper<Materials> wrapper = Wrappers.<Materials>lambdaQuery();

        wrapper.between(Materials::getCreateTime,minCreateTime,maxCreateTime)
                .between(Materials::getUpdateTime,minUpdateTime,maxUpdateTime);
        if(materialsDTO.getId()!=null)wrapper
                .eq(Materials::getId,materialsDTO.getId());
        if(materialsDTO.getName()!=null) wrapper
                .like(Materials::getName,materialsDTO.getName());
        if(materialsDTO.getLevel()!=null) wrapper
                .like(Materials::getLevel,materialsDTO.getLevel());
        if(materialsDTO.getCreateByName()!=null) wrapper
                .like(Materials::getCreateByName,materialsDTO.getCreateByName());

        Page<Materials> materialsPage = materialsmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<MaterialsVO> finalPage = new Page<MaterialsVO>();
        List<MaterialsVO> list = new ArrayList<MaterialsVO>();
        for(Materials materials:materialsPage.getRecords()){
            MaterialsVO materialsVO=new MaterialsVO();
            materialsVO.setId(materials.getId());
            materialsVO.setName(materials.getName());
            String pName="已是一级材料";
            if(materialsmapper.selectById(materials.getPid())!=null)
                pName=materialsmapper.selectById(materials.getPid()).getName();
            materialsVO.setPName(pName);
            materialsVO.setLevel(materials.getLevel());
            materialsVO.setCreateByName(materials.getCreateByName());
            materialsVO.setCreateTime(materials.getCreateTime());
            materialsVO.setUpdateByName(materials.getUpdateByName());
            materialsVO.setUpdateTime(materials.getUpdateTime());
            list.add(materialsVO);
        }
        finalPage.setRecords(list);
        return finalPage;
    }
}
