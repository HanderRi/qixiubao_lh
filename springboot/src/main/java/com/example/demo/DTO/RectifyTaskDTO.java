package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RectifyTaskDTO {
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    Integer id;
    String projectsName;//所属项目名称
    String peopleExecName;//整改执行人
    String createByName;//条目创建人姓名
    Integer warnId;//告警号

    Date minCreateTime;//创建开始时间 用来查询符合创建时间
    Date maxCreateTime;//创建结束时间

    Date minUpdateTime;//最后操作开始时间 用来查询最后操作时间
    Date maxUpdateTime;//最后操作结束时间
}
