package com.example.demo.controller;

import com.example.demo.DTO.StorageDTO;
import com.example.demo.config.Result;
import com.example.demo.entity.Storage;
import com.example.demo.mapper.Storagemapper;
import com.example.demo.service.StorageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.example.demo.controller.StorageMessagesController.getStorage;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Resource
    StorageService storageService;

    @PostMapping
    public Result<?> save( @RequestBody StorageDTO storageDTO) {//保存 新增
        Storage storage = transStorageDTO_PO(storageDTO);
        storageService.insert(storage);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody StorageDTO storageDTO) {
        Storage storage = transStorageDTO_PO(storageDTO);
        storageService.updateStorageList(storage);
        return Result.success();
    }
    private Storage transStorageDTO_PO(StorageDTO storageDTO) {
        return getStorage(storageDTO);
    }
}
