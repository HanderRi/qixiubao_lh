package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
@TableName("devices")
@Data
public class Devices {
    @TableId(type = IdType.AUTO)//id自增
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    private Integer id;//设备ID
    private Integer projectsId;//所属项目ID
    private String projectsName;//所属项目名称
    private String peopleAddName;//安装人姓名
    private Integer peopleAddId;//安装员ID
    private Date addTime;//安装时间
    private Integer deviceType;//设备类型
    private String position;//安装位置
    private String address;//硬件地址
    private Integer status;//0正常、1异常、2离线
    private Boolean isDeleted;//逻辑删除
}
