package com.example.demo.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DTO.DevicesDTO;
import com.example.demo.VO.DevicesVO;
import com.example.demo.config.Result;
import com.example.demo.entity.Devices;
import com.example.demo.service.DevicesService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@RequestMapping("/devices")
public class DevicesController {
    @Resource
    DevicesService devicesService;

    @PostMapping
    public Result<?> add(@RequestBody DevicesDTO devicesDTO) {//保存 新增
        Devices devices = transDevicesDTO_PO(devicesDTO);
        devicesService.insert(devices);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {//删除
        devicesService.logicDelete(id);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody DevicesDTO devicesDTO) {//更新
        Devices devices = transDevicesDTO_PO(devicesDTO);
        devicesService.updateDevicesList(devices);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            DevicesDTO devicesDTO

    ) throws ParseException {//查询


        Page<DevicesVO> devicesListPage = devicesService.findDevicesListPage(pageNum, pageSize, devicesDTO);
        return Result.success(devicesListPage);
    }

    private Devices transDevicesDTO_PO(DevicesDTO devicesDTO) {
        Devices devices = new Devices();
        devices.setId(devicesDTO.getId());
        devices.setPeopleAddName(devicesDTO.getPeopleAddName());
        devices.setProjectsName(devicesDTO.getProjectsName());
        devices.setDeviceType(devicesDTO.getDeviceType());
        devices.setPosition(devicesDTO.getPosition());
        devices.setStatus(devicesDTO.getStatus());
        return devices;
    }
}
