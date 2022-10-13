<template>
  <div>
    <el-menu
        style="width: 200px;min-height: calc(100vh - 50px)"
        :default-active="path"
        router
        :default-openeds="['1','2','3']"
        class="el-menu-vertical-demo"
    >
      <el-sub-menu index="1">
        <template #title>
          <el-icon>
            <location/>
          </el-icon>
          <span>项目告警管理</span>
        </template>
        <el-menu-item index="/WarningDevices">告警设备管理</el-menu-item>
        <el-menu-item index="/WarningMessages">告警信息处理</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="2">
        <template #title>
          <el-icon>
            <location/>
          </el-icon>
          <span>施工材料管理</span>
        </template>
        <el-menu-item index="/Storage">材料出/入库</el-menu-item>
        <el-menu-item index="/StorageMessages">材料出/入库列表</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="3">
        <template #title>
          <el-icon>
            <location/>
          </el-icon>
          <span>整改管理</span>
        </template>
        <el-menu-item index="/Rectify">整改列表</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "Aside",
  data(){
    return{
      path:this.$route.path
    }
  },
}
</script>

<style scoped>

</style>
