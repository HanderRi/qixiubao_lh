package com.example.demo.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class WarnsVO {
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    String projectsName;//所属项目名称
    Integer cause;//1：摄像头告警 | 2：烟感告警 | 3：⼈⼯ 发现 | 4：巡检发现
    String description;//整改描述
    String createByName;//条目创建人姓名
    Date createTime;//创建时间
    String updateByName;//最后修改人姓名
    Date updateTime;//最后修改时间
}
