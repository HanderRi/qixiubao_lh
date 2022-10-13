package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.StorageDTO;
import com.example.demo.VO.StorageVO;
import com.example.demo.config.Result;
import com.example.demo.entity.Storage;
import com.example.demo.mapper.Storagemapper;
import com.example.demo.service.StorageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;


@RestController
@RequestMapping("/storageMessages")
public class StorageMessagesController {

    @Resource
    StorageService storageService;


    @PutMapping
    public Result<?> update(@RequestBody StorageDTO storageDTO) {//更新
        Storage storage = transStorageDTO_PO(storageDTO);
        storageService.updateStorageList(storage);
        return Result.success();
    }

    @PostMapping
    public Result<?> save(@RequestBody StorageDTO storageDTO) {//保存 新增
        Storage storage = transStorageDTO_PO(storageDTO);
        storageService.insert(storage);
        return Result.success();
    }



    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {//删除
        storageService.logicDelete(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            StorageDTO storageDTO

    ) throws ParseException {//查询


        Page<StorageVO> storagePage = storageService.findStorageListPage(pageNum, pageSize, storageDTO);
        return Result.success(storagePage);
    }

    private Storage transStorageDTO_PO(StorageDTO storageDTO) {
        return getStorage(storageDTO);
    }

    @NotNull
    static Storage getStorage(StorageDTO storageDTO) {
        Storage storage = new Storage();
        storage.setId(storageDTO.getId());
        storage.setProjectsName(storageDTO.getProjectsName());
        storage.setStorageType(storageDTO.getStorageType());
        storage.setName(storageDTO.getName());
        storage.setMeasure(storageDTO.getMeasure());
        storage.setNumber(storageDTO.getNumber());
        storage.setTime(storageDTO.getTime());
        storage.setDestiny(storageDTO.getDestiny());
        storage.setPeopleCheckName(storageDTO.getPeopleCheckName());
        storage.setProve(storageDTO.getProve());
        storage.setCreateByName(storageDTO.getCreateByName());
        storage.setCreateTime(storageDTO.getCreateTime());
        storage.setUpdateByName(storageDTO.getUpdateByName());
        storage.setUpdateTime(storageDTO.getUpdateTime());
        return storage;
    }

}
