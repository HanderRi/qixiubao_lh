package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@TableName("warns")
@Data
public class Warns {
    @TableId(type = IdType.AUTO)//id自增
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    private Integer id;//告警id
    private Integer projectsId;//所属项目ID
    private String projectsName;//所属项目名称
    private Integer cause;//1：摄像头告警 | 2：烟感告警 | 3：⼈⼯ 发现 | 4：巡检发现 | 5：随⼿拍发现 |6：巡检异常
    private String description;//整改描述
    private String createByName;//条目创建人姓名
    private Integer createById;//条目创建人ID
    private Date createTime;//创建时间
    private String updateByName;//最后修改人姓名
    private Integer updateById;//最后修改人ID
    private Date updateTime;//最后修改时间
    private Boolean isDeleted;//逻辑删除
}
