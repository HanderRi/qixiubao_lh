package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.RectifyTaskDTO;
import com.example.demo.VO.MaterialsVO;
import com.example.demo.VO.RectifyTaskVO;
import com.example.demo.config.Result;
import com.example.demo.entity.RectifyTask;
import com.example.demo.service.RectifyTaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/rectifyTask")
public class RectifyTaskController {
    @Resource
    RectifyTaskService rectifyTaskService;

    private RectifyTask transRectifyTaskDTO_PO(RectifyTaskDTO rectifyTaskDTO){
        RectifyTask rectifyTask=new RectifyTask();
        rectifyTask.setId(rectifyTaskDTO.getId());
        rectifyTask.setProjectsName(rectifyTaskDTO.getProjectsName());
        rectifyTask.setPeopleExecName(rectifyTaskDTO.getPeopleExecName());
        rectifyTask.setWarnId(rectifyTaskDTO.getWarnId());
        rectifyTask.setCreateByName(rectifyTaskDTO.getCreateByName());
        return rectifyTask;
    }

    @PostMapping
    public Result<?> add(@RequestBody RectifyTaskDTO rectifyTaskDTO) {//保存 新增
        RectifyTask rectifyTask = transRectifyTaskDTO_PO(rectifyTaskDTO);
        rectifyTaskService.insert(rectifyTask);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {//删除
        rectifyTaskService.logicDelete(id);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody RectifyTaskDTO rectifyTaskDTO) {//保存 新增
        RectifyTask rectifyTask = transRectifyTaskDTO_PO(rectifyTaskDTO);
        rectifyTaskService.updateRectifyTaskList(rectifyTask);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            RectifyTaskDTO rectifyTaskDTO) throws ParseException {
        Page<RectifyTaskVO> rectifyTaskListPage =rectifyTaskService.findRectifyTaskListPage(pageNum, pageSize, rectifyTaskDTO);
        return Result.success(rectifyTaskListPage);
    }
}
