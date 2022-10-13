package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.RectifyExecDTO;
import com.example.demo.VO.RectifyExecVO;
import com.example.demo.config.Result;

import com.example.demo.entity.RectifyExec;
import com.example.demo.service.RectifyExecService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/rectifyExec")
public class RectifyExecController {
    @Resource
    RectifyExecService rectifyExecService;

    private RectifyExec transRectifyExecDTO_PO(RectifyExecDTO rectifyExecDTO) {
        RectifyExec rectifyExec = new RectifyExec();
        rectifyExec.setId(rectifyExecDTO.getId());
        rectifyExec.setProjectsName(rectifyExecDTO.getProjectsName());
        rectifyExec.setCreateByName(rectifyExecDTO.getCreateByName());
        rectifyExec.setStatus(rectifyExecDTO.getStatus());
        rectifyExec.setPeopleReviewName(rectifyExecDTO.getPeopleReviewName());
        rectifyExec.setPeopleReviewOpinion(rectifyExecDTO.getPeopleReviewOpinion());
        rectifyExec.setPeopleReviewFeedback(rectifyExecDTO.getPeopleReviewFeedback());
        return rectifyExec;
    }

    @PostMapping
    public Result<?> add(@RequestBody RectifyExecDTO rectifyExecDTO) {
        RectifyExec rectifyExec = transRectifyExecDTO_PO(rectifyExecDTO);
        rectifyExecService.insert(rectifyExec);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {//删除
        rectifyExecService.logicDelete(id);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody RectifyExecDTO rectifyExecDTO) {//更新
        RectifyExec rectifyExec = transRectifyExecDTO_PO(rectifyExecDTO);
        rectifyExecService.updateRectifyExecList(rectifyExec);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            RectifyExecDTO rectifyExecDTO) throws ParseException {

        Page<RectifyExecVO> rectifyExecListPage = rectifyExecService.findRectifyExecPage(pageNum, pageSize, rectifyExecDTO);
        return Result.success(rectifyExecListPage);
    }

}
