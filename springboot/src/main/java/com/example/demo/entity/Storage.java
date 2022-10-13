package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;


@TableName("storage")
@Data
public class Storage {
    @TableId(type = IdType.AUTO)//id自增
    private Integer id;//出入库ID
    private Integer projectsId;//所属项目ID
    private String projectsName;//所属项目名称
    private Integer storageType;//出入库类型 对应tinyint 出|入
    private String name;//物料名称
    private Integer measure;//物料单位
    private Integer number;//物料数量
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    private Date time;//出入库时间
    private String destiny;//去向
    private String peopleCheckName;//验收人姓名
    private Integer peopleCheckId;//验收人ID
    private Integer checkStatus;//验收状态 未验收|审核中|已验收
    private String picture;//现场照片
    private String position;//工地位置
    private Integer level;//堆放整⻬度 差|中等|良好
    private Boolean isObstructed;//是否妨碍交通 是|否
    private String workersName;//施工方名称
    private String prove;//证明材料
    private Integer improveStatus;//改进状态 ⽆需改进|未改进|改进中|改进完成
    private Date improveTime;//改进时间
    private String improvePic;//改进照片
    private String createByName;//条目创建人姓名
    private Integer createById;//条目创建人ID
    private Date createTime;//创建时间
    private String updateByName;//最后修改人姓名
    private Integer updateById;//最后修改人ID
    private Date updateTime;//最后修改时间
    private Boolean isDeleted;//逻辑删除
}
