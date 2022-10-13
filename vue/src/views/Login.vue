<template>
  <div style="width: 100%;height: 100vh;background-color: darkcyan;overflow: hidden">
    <div style="width: 400px;margin: 150px auto;">
      <div style="color: #cccccc;font-size: 30px;text-align: center;padding: 30px 0">欢迎登陆</div>
      <el-form :model="form" size="default" :rules="rules" ref="form">

        <el-form-item prop="username">
          <el-input v-model="form.username" >
            <template #prefix>
              <el-icon class="el-input__icon"><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" show-password>
            <template #prefix>
              <el-icon class="el-input__icon"><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-button style="width: 100%" type="primary" @click="login">登陆</el-button>
      </el-form>
    </div>
  </div>


</template>

<script>

import request from "@/utils/request";

export default {
  name: "Login",
  created() {
    sessionStorage.removeItem("user")
  },
  methods:{
    login(){
      this.$refs['form'].validate((valid) => {
        if(valid){
          request.post("/login",this.form).then(res => {
            if (res.code === '0'){
              this.$message({
                type:"success",
                message:"登陆成功"
              })
              sessionStorage.setItem("user",JSON.stringify(res.data))//缓存用户信息
              this.$router.push("/success")  //登陆成功之后跳转到主页
            } else {
              this.$message({
                type:"error",
                message:res.msg
              })
            }
          })

        }
      })
    },
  },
  data() {
    return{
      form:{},
      rules:{
        username:[
          {required: true, message:'请输入用户名',trigger:'blur'},
        ],
        password:[
          {required:true,message:'请输入密码', trigger:'blur'},
        ]
      },
    }
  }
}
</script>

<style scoped>

</style>