package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class StorageDTO {
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    Integer id;
    String projectsName;//所属项目名称
    Integer storageType;//出入库类型 对应tinyint 出|入
    String name;//物料名称
    Integer measure;//物料单位
    Integer number;//物料数量
    Date time;//出入库时间
    String destiny;//去向
    String peopleCheckName;//验收人姓名
    String prove;//证明材料
    String createByName;//创建人姓名
    Date createTime;//创建时间
    String updateByName;//最后修改人姓名
    Date updateTime;//最后修改时间

    Date minTime;//用来查询时间段内出入库信息
    Date maxTime;
}
