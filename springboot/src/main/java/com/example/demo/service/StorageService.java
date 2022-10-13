package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.DTO.StorageDTO;
import com.example.demo.VO.StorageVO;
import com.example.demo.entity.Storage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.mapper.Storagemapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class StorageService {
    @Resource
    private Storagemapper storagemapper;

    //更新出入库列表
    public void updateStorageList(Storage storage) {
        storagemapper.updateById(storage);
    }

    //插入
    public void insert(Storage storage) {
        storage.setIsDeleted(false);
        storagemapper.insert(storage);
    }

    //逻辑删除
    public void logicDelete(Long id) {
        storagemapper.deleteById(id);
    }

    //分页查询
    public Page<StorageVO> findStorageListPage(Integer pageNum,
                                               Integer pageSize,
                                               StorageDTO storageDTO) throws ParseException {
        System.out.println("物料出入库查询开始...");
        System.out.println(storageDTO);
        Date mintime, maxtime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        mintime = dateFormat.parse("1900/01/01 00:00:00");
        maxtime = dateFormat.parse("2300/01/01 00:00:00");
        if (storageDTO.getMaxTime() != null) {
            mintime = storageDTO.getMinTime();
        }
        if (storageDTO.getMinTime() != null) {
            maxtime = storageDTO.getMaxTime();
        }
        LambdaQueryWrapper<Storage> wrapper = Wrappers.<Storage>lambdaQuery();
        wrapper.like(Storage::getName, storageDTO.getName())
                .between(Storage::getTime, mintime, maxtime);
        if (storageDTO.getStorageType() != null) {
            if (storageDTO.getStorageType() == 0 || storageDTO.getStorageType() == 1) wrapper
                    .eq(Storage::getStorageType, storageDTO.getStorageType());
        }
        if (!Objects.equals(storageDTO.getProjectsName(), "")) wrapper
                .like(Storage::getProjectsName, storageDTO.getProjectsName());
        if (!Objects.equals(storageDTO.getPeopleCheckName(), "")) wrapper
                .eq(Storage::getPeopleCheckName, storageDTO.getPeopleCheckName());


        Page<Storage> storagePage = storagemapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<StorageVO> finalPage = new Page<StorageVO>();
        List<StorageVO> list = new ArrayList<StorageVO>();
        for (Storage storage : storagePage.getRecords()) {
            StorageVO storageVO = new StorageVO();
            storageVO.setId(storage.getId());
            storageVO.setStorageType(storage.getStorageType());
            storageVO.setCheckStatus(storage.getCheckStatus());
            storageVO.setDestiny(storage.getDestiny());
            storageVO.setMeasure(storage.getMeasure());
            storageVO.setProjectsName(storage.getProjectsName());
            storageVO.setName(storage.getName());
            storageVO.setNumber(storage.getNumber());
            storageVO.setTime(storage.getTime());
            storageVO.setProve(storage.getProve());
            storageVO.setPeopleCheckName(storage.getPeopleCheckName());
            storageVO.setDestiny(storage.getDestiny());
            storageVO.setCreateByName(storage.getCreateByName());
            storageVO.setCreateTime(storage.getCreateTime());
            storageVO.setUpdateTime(storage.getUpdateTime());
            list.add(storageVO);
        }
        finalPage.setRecords(list);
        return finalPage;
    }

}
