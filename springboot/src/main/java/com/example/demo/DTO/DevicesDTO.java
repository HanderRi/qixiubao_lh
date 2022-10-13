package com.example.demo.DTO;
/*
*
* DTO接受前台数据交给后台数据封装
*/

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DevicesDTO {
    //查询需要用到的字段
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    Integer id;//设备ID
    String projectsName;//所属项目名称
    String peopleAddName;//安装人姓名
    Integer deviceType;//设备类型
    String position;//安装位置
    Integer status;//0正常、1异常、2离线

    Date minDate;//开始日期 用来查询给定时间段内设备
    Date maxDate;//结束日期
}
