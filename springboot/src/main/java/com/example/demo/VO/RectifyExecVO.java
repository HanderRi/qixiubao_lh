package com.example.demo.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RectifyExecVO {
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    String projectsName;//所属项目名称
    Integer status;//0待审核 1待整改 2整改中 3整改完成
    String peopleReviewName;//审核人姓名
    Integer peopleReviewOpinion;//审核人意见
    String peopleReviewFeedback;//审核人反馈
    String peopleExecName;//整改执行人(通过整改任务id查询rectifyTask表后返回)
    String createByName;//条目创建人姓名
    Date CreateTime;//创建时间
    String updateByName;//最后修改人姓名
    Date updateTime;//最后修改时间

}
