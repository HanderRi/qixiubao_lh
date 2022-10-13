package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class WarnsDTO {
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    Integer id;//告警id
    String projectsName;//所属项目名称
    Integer cause;//1：摄像头告警 | 2：烟感告警 | 3：⼈⼯ 发现
    String description;//整改描述
    String createByName;//条目创建人姓名

    Date minCreateTime;//创建开始时间 用来查询符合创建时间
    Date maxCreateTime;//创建结束时间

    Date minUpdateTime;//最后修改开始时间 用来查询最后操作时间
    Date maxUpdateTime;//最后修改结束时间
}
