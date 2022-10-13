package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.RectifyExecDTO;
import com.example.demo.VO.RectifyExecVO;
import com.example.demo.entity.RectifyExec;
import com.example.demo.mapper.RectifyExecmapper;
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
public class RectifyExecService {
    @Resource
    private RectifyExecmapper rectifyExecmapper;
    @Resource
    private RectifyTaskmapper rectifyTaskmapper;

    public void updateRectifyExecList(RectifyExec rectifyExec) {
        rectifyExecmapper.updateById(rectifyExec);
    }

    public void insert(RectifyExec rectifyExec) {
        rectifyExec.setIsDeleted(false);
        rectifyExecmapper.insert(rectifyExec);
    }

    public void logicDelete(Long id) {
        rectifyExecmapper.deleteById(id);
    }

    public Page<RectifyExecVO> findRectifyExecPage(Integer pageNum,
                                                   Integer pageSize,
                                                   RectifyExecDTO rectifyExecDTO) throws ParseException {

        Date minCreateTime, maxCreateTime, minUpdateTime, maxUpdateTime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        minCreateTime = dateFormat.parse("1900/01/01 00:00:00");
        maxCreateTime = dateFormat.parse("2300/01/01 00:00:00");
        minUpdateTime = dateFormat.parse("1900/01/01 00:00:00");
        maxUpdateTime = dateFormat.parse("2300/01/01 00:00:00");
        if (rectifyExecDTO.getMinCreateTime() != null) {
            minCreateTime = rectifyExecDTO.getMinCreateTime();
        }
        if (rectifyExecDTO.getMaxCreateTime() != null) {
            maxCreateTime = rectifyExecDTO.getMaxCreateTime();
        }
        if (rectifyExecDTO.getMinUpdateTime() != null) {
            minUpdateTime = rectifyExecDTO.getMinUpdateTime();
        }
        if (rectifyExecDTO.getMaxUpdateTime() != null) {
            maxUpdateTime = rectifyExecDTO.getMaxUpdateTime();
        }
        LambdaQueryWrapper<RectifyExec> wrapper = Wrappers.<RectifyExec>lambdaQuery();
        wrapper.between(RectifyExec::getCreateTime, minCreateTime, maxCreateTime)
                .between(RectifyExec::getUpdateTime, minUpdateTime, maxUpdateTime);
        if (rectifyExecDTO.getProjectsName() != null) wrapper
                .like(RectifyExec::getProjectsName, rectifyExecDTO.getProjectsName());
        if (rectifyExecDTO.getStatus() != null) wrapper
                .eq(RectifyExec::getStatus, rectifyExecDTO.getStatus());
        if (rectifyExecDTO.getCreateByName() != null) wrapper
                .eq(RectifyExec::getCreateByName, rectifyExecDTO.getCreateByName());
        if (rectifyExecDTO.getPeopleReviewName() != null) wrapper
                .like(RectifyExec::getPeopleReviewName, rectifyExecDTO.getPeopleReviewName());
        if (rectifyExecDTO.getPeopleReviewOpinion() != null) wrapper
                .like(RectifyExec::getPeopleReviewOpinion, rectifyExecDTO.getPeopleReviewOpinion());
        if (rectifyExecDTO.getRectifyTaskId() != null) wrapper
                .eq(RectifyExec::getRectifyTaskId, rectifyExecDTO.getRectifyTaskId());
        Page<RectifyExec> rectifyExecPage = rectifyExecmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<RectifyExecVO> finalPage = new Page<RectifyExecVO>();
        List<RectifyExecVO> list = new ArrayList<RectifyExecVO>();
        for (RectifyExec rectifyExec : rectifyExecPage.getRecords()) {
            RectifyExecVO rectifyExecVO = new RectifyExecVO();
            rectifyExecVO.setProjectsName(rectifyExec.getProjectsName());
            rectifyExecVO.setStatus(rectifyExec.getStatus());
            rectifyExecVO.setPeopleReviewName(rectifyExec.getPeopleReviewName());
            rectifyExecVO.setPeopleReviewFeedback(rectifyExec.getPeopleReviewFeedback());
            String peopleExecName = "未指派";
            if (rectifyTaskmapper.selectById(rectifyExec.getRectifyTaskId()) != null)
                peopleExecName = rectifyTaskmapper.selectById(rectifyExec.getRectifyTaskId()).getPeopleExecName();
            rectifyExecVO.setPeopleExecName(peopleExecName);
            rectifyExecVO.setCreateByName(rectifyExec.getCreateByName());
            rectifyExecVO.setCreateTime(rectifyExec.getCreateTime());
            rectifyExecVO.setUpdateByName(rectifyExec.getUpdateByName());
            rectifyExecVO.setUpdateTime(rectifyExec.getUpdateTime());
            list.add(rectifyExecVO);
        }
        finalPage.setRecords(list);
        return finalPage;
    }
}
