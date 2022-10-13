package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.MaterialsDTO;
import com.example.demo.VO.MaterialsVO;
import com.example.demo.config.Result;
import com.example.demo.entity.Materials;
import com.example.demo.service.MaterialsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/materials")
public class MaterialsController {
    @Resource
    MaterialsService materialsService;

    private Materials transMaterialsDTO_PO(MaterialsDTO materialsDTO){
        Materials materials=new Materials();
        materials.setId(materialsDTO.getId());
        materials.setName(materialsDTO.getName());
        materials.setLevel(materialsDTO.getLevel());
        materials.setCreateByName(materialsDTO.getCreateByName());
        return materials;
    }

    @PostMapping
    public Result<?> add(@RequestBody MaterialsDTO materialsDTO) {//保存 新增
        Materials materials = transMaterialsDTO_PO(materialsDTO);
        materialsService.insert(materials);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {//删除
        materialsService.logicDelete(id);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody MaterialsDTO materialsDTO) {//保存 新增
        Materials materials = transMaterialsDTO_PO(materialsDTO);
        materialsService.updateMaterialsList(materials);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            MaterialsDTO materialsDTO) throws ParseException {
        Page<MaterialsVO> materialsDTOPage =materialsService.findMaterialsListPage(pageNum, pageSize, materialsDTO);
        return Result.success(materialsDTOPage);
    }
}
