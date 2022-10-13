package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RectifyExecDTO {
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    Integer id;
    String projectsName;//所属项目名称
    Integer status;//0待审核 1待整改 2整改中 3整改完成
    String peopleReviewName;//审核人姓名
    Integer peopleReviewOpinion;//审核人意见
    String peopleReviewFeedback;//审核人反馈
    String rectifyTaskId;//所属巡检任务
    String createByName;//条目创建人姓名

    Date minCreateTime;//创建开始时间 用来查询符合创建时间
    Date maxCreateTime;//创建结束时间

    Date minUpdateTime;//最后操作开始时间 用来查询最后操作时间
    Date maxUpdateTime;//最后操作结束时间
}
