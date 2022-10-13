<template>
  <div style="height: 60px;line-height: 60px;border-bottom: 1px solid #ccc;display: flex">
    <div style="width: 200px;padding-left: 30px;font-weight: bold;color: dodgerblue">企修宝</div>
    <div style="flex: 1"></div>
    <div style="width: 180px">
      <WebSocket/>
      <el-dropdown>
        <span class="el-dropdown-link">
          <br>{{user.nickName}}<el-icon class="el-icon--right"><arrow-down/></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/welcome')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import WebSocket from "@/components/WebSocket";
export default {
  name: "Header",
  props:['user'],
  data(){
    return{
      user:{}
    }
  },
  components: {WebSocket},
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
  },
}
</script>

<style scoped>

</style>