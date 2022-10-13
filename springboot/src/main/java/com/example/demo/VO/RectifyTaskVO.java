package com.example.demo.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RectifyTaskVO {
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    String projectsName;//所属项目名称
    Integer warnId;//所属告警id
    String description;//任务描述
    String peopleExecName;//整改执行人
    String createByName;//条目创建人姓名
    Date createTime;//创建时间
    String updateByName;//最后修改人姓名
    Date updateTime;//最后修改时间
}
