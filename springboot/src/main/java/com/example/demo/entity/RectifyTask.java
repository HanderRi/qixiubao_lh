package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@TableName("rectify_task")
@Data
public class RectifyTask {
    @TableId(type = IdType.AUTO)//id自增
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    private Integer id;//整改任务ID
    private Integer projectsId;//所属项目ID
    private String projectsName;//所属项目名称
    private Integer warnId;//所属告警id
    private String description;//任务描述
    private String peopleExecName;//整改执行人
    private String peopleExecId;//整改执行人Id
    private String createByName;//条目创建人姓名
    private Integer createById;//条目创建人ID
    private Date createTime;//创建时间
    private String updateByName;//最后修改人姓名
    private Integer updateById;//最后修改人ID
    private Date updateTime;//最后修改时间
    private Boolean isDeleted;//逻辑删除
}
