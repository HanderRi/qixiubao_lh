package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.RectifyTaskDTO;
import com.example.demo.VO.RectifyTaskVO;
import com.example.demo.entity.RectifyTask;
import com.example.demo.mapper.RectifyTaskmapper;
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
public class RectifyTaskService {
    @Resource
    private RectifyTaskmapper rectifyTaskmapper;

    public void updateRectifyTaskList(RectifyTask rectifyTask) {
        rectifyTaskmapper.updateById(rectifyTask);
    }

    public void insert(RectifyTask rectifyTask) {
        rectifyTask.setIsDeleted(false);
        rectifyTaskmapper.insert(rectifyTask);
    }

    public void logicDelete(Long id) {
        rectifyTaskmapper.deleteById(id);
    }

    public Page<RectifyTaskVO> findRectifyTaskListPage(Integer pageNum,
                                                     Integer pageSize,
                                                     RectifyTaskDTO rectifyTaskDTO) throws ParseException {
        Date minCreateTime, maxCreateTime, minUpdateTime, maxUpdateTime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        minCreateTime = dateFormat.parse("1900/01/01 00:00:00");
        maxCreateTime = dateFormat.parse("2300/01/01 00:00:00");
        minUpdateTime = dateFormat.parse("1900/01/01 00:00:00");
        maxUpdateTime = dateFormat.parse("2300/01/01 00:00:00");
        if (rectifyTaskDTO.getMinCreateTime() != null) {
            minCreateTime = rectifyTaskDTO.getMinCreateTime();
        }
        if (rectifyTaskDTO.getMaxCreateTime() != null) {
            maxCreateTime = rectifyTaskDTO.getMaxCreateTime();
        }
        if (rectifyTaskDTO.getMinUpdateTime() != null) {
            minUpdateTime = rectifyTaskDTO.getMinUpdateTime();
        }
        if (rectifyTaskDTO.getMaxUpdateTime() != null) {
            maxUpdateTime = rectifyTaskDTO.getMaxUpdateTime();
        }
        LambdaQueryWrapper<RectifyTask> wrapper = Wrappers.lambdaQuery();

        wrapper.between(RectifyTask::getCreateTime, minCreateTime, maxCreateTime)
                .between(RectifyTask::getUpdateTime, minUpdateTime, maxUpdateTime);
        if(rectifyTaskDTO.getId()!=null)wrapper
                .eq(RectifyTask::getId,rectifyTaskDTO.getId());
        if(rectifyTaskDTO.getProjectsName()!=null)wrapper
                .like(RectifyTask::getProjectsName,rectifyTaskDTO.getProjectsName());
        if(rectifyTaskDTO.getWarnId()!=null)wrapper
                .eq(RectifyTask::getWarnId,rectifyTaskDTO.getWarnId());
        Page<RectifyTask> rectifyTaskPage = rectifyTaskmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<RectifyTaskVO> finalPage = new Page<>();
        List<RectifyTaskVO> list = new ArrayList<>();
        for (RectifyTask rectifyTask : rectifyTaskPage.getRecords()) {
            RectifyTaskVO rectifyTaskVO =new RectifyTaskVO();
            rectifyTaskVO.setProjectsName(rectifyTask.getProjectsName());
            rectifyTaskVO.setWarnId(rectifyTask.getWarnId());
            rectifyTaskVO.setDescription(rectifyTask.getDescription());
            rectifyTaskVO.setCreateByName(rectifyTask.getCreateByName());
            rectifyTaskVO.setCreateTime(rectifyTask.getCreateTime());
            rectifyTaskVO.setUpdateByName(rectifyTask.getUpdateByName());
            rectifyTaskVO.setUpdateTime(rectifyTask.getUpdateTime());
            list.add(rectifyTaskVO);
        }
        finalPage.setRecords(list);
        return finalPage;
    }

}
