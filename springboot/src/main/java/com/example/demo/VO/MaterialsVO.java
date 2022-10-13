package com.example.demo.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MaterialsVO {
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    Integer id;//物料ID
    String name;//物料名称
    String pName;//父物料名称 (通过父物料id查到后再返回)
    Integer level;//物料级别
    String createByName;//条目创建人姓名
    Date createTime;//创建时间
    String updateByName;//最后修改人姓名
    Date updateTime;//最后修改时间
}
