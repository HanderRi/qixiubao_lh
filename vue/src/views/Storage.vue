<template>
  <div style="padding: 10px">
    <!--    操作功能区-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="save">保存</el-button>
      <el-button type="primary" @click="saveAndConfirm">保存并提交</el-button>
      <el-button @click="resetField('form')">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <!--    基本信息输入区-->
      <div style="color: black;font-size: 30px;font-weight:bold;text-align: center;padding: 20px 0">物料出/入库</div>
      <div style="color: black;background-color: darkgray;font-size: 25px;line-height: 50px;text-align: left">基本信息</div>
      <div>
        <el-form :model="form" ref="form" label-width="90px" style="background-color: lightgray">
          <el-row gutter="15">
            <el-col span="6">
              <el-form-item label="日期和时间" prop="time">
                <el-date-picker clearable
                                v-model="form.time"
                                type="datetime"
                                placeholder="选择日期和时间"
                                format="YYYY/MM/DD hh:mm:ss"
                                value-format="YYYY/MM/DD hh:mm:ss"
                />
              </el-form-item>
            </el-col>
            <el-col span="6">
              <el-form-item label="出/入库编号" prop="id">
                <el-input v-model="form.id" placeholder="请输入编号" clearable/>
              </el-form-item>
            </el-col>
            <el-col span="6">
              <el-form-item label="出/入" prop="storageType">
                <el-select v-model="form.storageType" placeholder="请选择出入库类型" clearable>
                  <el-option label="出" value="1"/>
                  <el-option label="入" value="0"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col span="6">
              <el-form-item label="所属项目" prop="projectsId">
                <el-select v-model="form.projectsId" placeholder="请选择项目" clearable>
                  <el-option label="1" value="1"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row gutter="15">
            <el-col span="6">
              <el-form-item label="验收员" prop="peopleCheckName">
                <el-input v-model="form.peopleCheckName" placeholder="请输入验收员信息" clearable/>
              </el-form-item>
            </el-col>
            <el-col span="6">
              <el-form-item label="物料名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入物料名称" clearable/>
              </el-form-item>
            </el-col>
            <el-col span="6">
              <el-form-item label="物料类别" prop="materialType">
                <el-select v-model="form.materialType" placeholder="请选择物料类别" clearable>
                  <el-option label="一级物料" value="一级物料"/>
                  <el-option label="二级物料" value="二级物料"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col span="6">
              <el-form-item label="物料数量" prop="number">
                <el-input-number v-model="form.number" placeholder="请输入物料数量" style="width: 90%">
                  <el-option label="1" value="1"/>
                </el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="填报人" prop="workersName">
              <el-input v-model="form.workersName" placeholder="请输入填报人信息" clearable>
              </el-input>
            </el-form-item>
          </el-row>
        </el-form>
      </div>
      <div
          style="color: black;margin-top: 60px;background-color: darkgray;font-size: 25px;line-height: 50px;text-align: left">
        验收材料上传
      </div>
      <div >
        <el-form-item v-model="form.prove" ref="upLodFile" >
          <el-upload action="/api/files/upload" :on-success="filesUploadSuccess" drag multiple>
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              拖拽文件到此 或 <em>点击上传</em>
            </div>
          </el-upload>
        </el-form-item>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Storage",
  data() {
    return {
      form: {},
      tableData: [],
    }
  },
  created() {
    // this.load()
  },
  methods: {
    load() {
      request.get("/storage", {
        params: {}
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save() {//点击 保存 响应

    },
    saveAndConfirm() {//点击 保存并提交 响应
      request.post("/storage", this.form).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "新增成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
      this.$router.push('/storageMessages')
    },
    resetField(formName) {//点击 重置 响应
      this.$refs[formName].resetFields()
    },
    filesUploadSuccess(response, file, fileList) {
      console.log(response)
      this.form.prove = response.data
    },
  }

}
</script>

<style scoped>

</style>