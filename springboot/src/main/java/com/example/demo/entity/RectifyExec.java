package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@TableName("rectify_exec")
@Data
public class RectifyExec {
    @TableId(type = IdType.AUTO)//id自增
    @JsonFormat(pattern = "YYYY/MM/dd hh:mm:ss",timezone = "GMT+8")
    private Integer id;//整改执行ID
    private Integer rectifyTaskId;//整改任务id
    private Integer projectsId;//所属项目ID
    private String projectsName;//所属项目名称
    private String execPicture;//执行照片
    private String description;//整改描述
    private Integer status;//0待审核 1待整改 2整改中 3整改完成
    private String peopleReviewName;//审核人姓名
    private Integer peopleReviewId;//审核人id
    private Integer peopleReviewOpinion;//审核人意见 0 通过 1 不通过
    private String peopleReviewFeedback;//审核人反馈
    private String createByName;//条目创建人姓名
    private Integer createById;//条目创建人ID
    private Date createTime;//创建时间
    private String updateByName;//最后修改人姓名
    private Integer updateById;//最后修改人ID
    private Date updateTime;//最后修改时间
    private Boolean isDeleted;//逻辑删除
}
