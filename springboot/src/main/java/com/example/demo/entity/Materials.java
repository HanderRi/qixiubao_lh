package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
@TableName("materials")
@Data
public class Materials {
    @TableId(type = IdType.AUTO)//id自增
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    private Integer id;//物料ID
    private String name;//物料名称
    private Integer pid;//父物料ID
    private Integer level;//物料级别
    private String createByName;//条目创建人姓名
    private Integer createById;//条目创建人ID
    private Date createTime;//创建时间
    private String updateByName;//最后修改人姓名
    private Integer updateById;//最后修改人ID
    private Date updateTime;//最后修改时间
    private Boolean isDeleted;//逻辑删除
}
