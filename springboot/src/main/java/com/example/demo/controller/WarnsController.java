package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.WarnsDTO;
import com.example.demo.VO.WarnsVO;
import com.example.demo.config.Result;
import com.example.demo.entity.Warns;
import com.example.demo.service.WarnsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/warns")
public class WarnsController {
    @Resource
    WarnsService warnsService;

    private Warns transWarnsDTO_PO(WarnsDTO warnsDTO){
        Warns warns=new Warns();
        warns.setId(warnsDTO.getId());
        warns.setProjectsName(warnsDTO.getProjectsName());
        warns.setCause(warnsDTO.getCause());
        warns.setDescription(warnsDTO.getDescription());
        warns.setCreateByName(warnsDTO.getCreateByName());
        return warns;
    }

    @PostMapping
    public Result<?> add(@RequestBody WarnsDTO warnsDTO) {//保存 新增
        Warns warns = transWarnsDTO_PO(warnsDTO);
        warnsService.insert(warns);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {//删除
        warnsService.logicDelete(id);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody WarnsDTO warnsDTO) {//保存 新增
        Warns warns = transWarnsDTO_PO(warnsDTO);
        warnsService.updateMaterialsList(warns);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            WarnsDTO warnsDTO) throws ParseException {
        Page<WarnsVO> warnsVOPage =warnsService.findMaterialsListPage(pageNum, pageSize, warnsDTO);
        return Result.success(warnsVOPage);
    }
}
