package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.DevicesDTO;
import com.example.demo.VO.DevicesVO;
import com.example.demo.entity.Devices;
import com.example.demo.mapper.Devicesmapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class DevicesService {
    @Resource
    private Devicesmapper devicesmapper;

    public void updateDevicesList(Devices devices) {
        devicesmapper.updateById(devices);
    }

    //插入
    public void insert(Devices devices) {
        devices.setIsDeleted(false);
        devicesmapper.insert(devices);
    }

    public void logicDelete(Long id) {
        devicesmapper.deleteById(id);
    }
    public Page<DevicesVO> findDevicesListPage(Integer pageNum,
                                               Integer pageSize,
                                               DevicesDTO devicesDTO) throws ParseException {
        System.out.println("物料出入库查询开始...");
        System.out.println(devicesDTO);

        Date mintime, maxtime;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        mintime = dateFormat.parse("1900/01/01 00:00:00");
        maxtime = dateFormat.parse("2300/01/01 00:00:00");
        if (devicesDTO.getMinDate() != null) {
            mintime = devicesDTO.getMinDate();
        }
        if (devicesDTO.getMaxDate() != null) {
            maxtime = devicesDTO.getMaxDate();
        }

        LambdaQueryWrapper<Devices> wrapper = Wrappers.<Devices>lambdaQuery();
        wrapper.between(Devices::getAddTime, mintime, maxtime);
        if(devicesDTO.getId()!=null) wrapper
                .eq(Devices::getId, devicesDTO.getId());
        if(devicesDTO.getProjectsName()!=null|| !Objects.equals(devicesDTO.getProjectsName(), ""))wrapper
                .like(Devices::getProjectsName,devicesDTO.getProjectsName());
        if(devicesDTO.getPosition()!=null)wrapper
                .like(Devices::getPosition,devicesDTO.getPosition());
        if(devicesDTO.getDeviceType()!=null) wrapper
                .eq(Devices::getDeviceType, devicesDTO.getDeviceType());
        if(devicesDTO.getStatus()!=null) wrapper
                .eq(Devices::getStatus, devicesDTO.getStatus());
        Page<Devices> devicesPage = devicesmapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        Page<DevicesVO> finalPage = new Page<DevicesVO>();
        List<DevicesVO> list = new ArrayList<DevicesVO>();
        for (Devices devices : devicesPage.getRecords()) {//PO向VO转换
            DevicesVO devicesVO=new DevicesVO();
            devicesVO.setId(devices.getId());
            devicesVO.setProjectsName(devices.getProjectsName());
            devicesVO.setDeviceType(devices.getDeviceType());
            devicesVO.setPeopleAddName(devices.getPeopleAddName());
            devicesVO.setPosition(devices.getPosition());
            devicesVO.setAddTime(devices.getAddTime());
            devicesVO.setStatus(devices.getStatus());
            list.add(devicesVO);
        }
        finalPage.setRecords(list);
        return finalPage;
    }

}
