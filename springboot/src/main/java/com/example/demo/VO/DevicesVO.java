package com.example.demo.VO;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/*
* VO将后台数据给前台数据封装
*/
@Data
public class DevicesVO {
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    Integer id;//设备ID
    String projectsName;//所属项目名称
    Integer deviceType;//设备类型
    String peopleAddName;//安装人姓名
    String position;//安装位置
    Date addTime;//安装时间
    Integer status;//0正常、1异常、2离线


}
