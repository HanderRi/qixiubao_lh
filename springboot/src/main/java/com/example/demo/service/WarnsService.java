package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.WarnsDTO;
import com.example.demo.VO.WarnsVO;
import com.example.demo.entity.Warns;
import com.example.demo.mapper.Warnsmapper;
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
public class WarnsService {
    @Resource
    private Warnsmapper warnsmapper;

    public void updateMaterialsList(Warns warns) {
        warnsmapper.updateById(warns);
    }

    public void insert(Warns warns) {
        warns.setIsDeleted(false);
        warnsmapper.insert(warns);
    }

    public void logicDelete(Long id) {
        warnsmapper.deleteById(id);
    }

    public Page<WarnsVO> findMaterialsListPage(Integer pageNum,
                                               Integer pageSize,
                                               WarnsDTO warnsDTO) throws ParseException {

        Date minCreateTime, maxCreateTime, minUpdateTime, maxUpdateTime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        minCreateTime = dateFormat.parse("1900/01/01 00:00:00");
        maxCreateTime = dateFormat.parse("2300/01/01 00:00:00");
        minUpdateTime = dateFormat.parse("1900/01/01 00:00:00");
        maxUpdateTime = dateFormat.parse("2300/01/01 00:00:00");
        if (warnsDTO.getMinCreateTime() != null) {
            minCreateTime = warnsDTO.getMinCreateTime();
        }
        if (warnsDTO.getMaxCreateTime() != null) {
            maxCreateTime = warnsDTO.getMaxCreateTime();
        }
        if (warnsDTO.getMinUpdateTime() != null) {
            minUpdateTime = warnsDTO.getMinUpdateTime();
        }
        if (warnsDTO.getMaxUpdateTime() != null) {
            maxUpdateTime = warnsDTO.getMaxUpdateTime();
        }


        LambdaQueryWrapper<Warns> wrapper = Wrappers.<Warns>lambdaQuery();

        wrapper.between(Warns::getCreateTime, minCreateTime, maxCreateTime)
                .between(Warns::getUpdateTime, minUpdateTime, maxUpdateTime);
        if (warnsDTO.getId() != null) wrapper
                .eq(Warns::getId, warnsDTO.getId());
        if (warnsDTO.getProjectsName() != null) wrapper
                .like(Warns::getProjectsName, warnsDTO.getProjectsName());
        if (warnsDTO.getCreateByName() != null) wrapper
                .like(Warns::getCreateByName, warnsDTO.getCreateByName());

        Page<Warns> warnsPage = warnsmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<WarnsVO> finalPage = new Page<>();
        List<WarnsVO> list = new ArrayList<>();
        for (Warns warns : warnsPage.getRecords()) {
            WarnsVO warnsVO = new WarnsVO();
            warnsVO.setProjectsName(warns.getProjectsName());
            warnsVO.setCause(warns.getCause());
            warnsVO.setDescription(warns.getDescription());
            warnsVO.setCreateByName(warns.getCreateByName());
            warnsVO.setCreateTime(warns.getCreateTime());
            warnsVO.setUpdateByName(warns.getUpdateByName());
            warnsVO.setUpdateTime(warns.getUpdateTime());
            list.add(warnsVO);
        }
        finalPage.setRecords(list);
        return finalPage;
    }
}
