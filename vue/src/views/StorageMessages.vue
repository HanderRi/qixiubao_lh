<template>
  <div style="padding: 10px">
    <!--    功能区-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
      <el-button type="primary">查看日志</el-button>
    </div>
    <!--    搜索区-->
    <div style="margin: 10px 0">
      <el-form :model="query" ref="query" label-width="100px" style="background-color: lightblue">
        <el-row gutter="20">
          <el-col span="12">
            <el-form-item label="验收员" prop="peopleCheckName">
              <el-input v-model="peopleCheckName" placeholder="请输入验收员信息" clearable/>
            </el-form-item>
          </el-col>
          <el-col span="12">
            <el-form-item label="所属项目" prop="projectsId">
              <el-input v-model="projectsName" placeholder="请输入项目信息" clearable/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-col span="6">
          <el-form-item label="出/入" prop="storageType">
            <el-select v-model="storageType" placeholder="请选择出入库类型" clearable>
              <el-option label="出" value="0"/>
              <el-option label="入" value="1"/>
              <el-option label="全部" value="2"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-row>
          <el-form-item label="物料名称" prop="name">
            <el-input v-model="name" placeholder="请输入物料名称" clearable/>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="日期和时间" prop="timeChoose">
            <el-date-picker clearable
                            v-model="timeChoose"
                            type="daterange"
                            unlink-panels
                            range-separator="至"
                            start-placeholder="开始时间"
                            end-placeholder="结束时间"
                            format="YYYY/MM/DD hh:mm:ss"
                            value-format="YYYY/MM/DD hh:mm:ss"
                            :shortcuts="shortcuts"
                            :size="small"
            />
          </el-form-item>
          <el-button type="primary" style="margin-left: 60px" @click="load">查询</el-button>
          <el-button @click="resetField('query')">重置</el-button>
        </el-row>
      </el-form>
    </div>
    <!--    表格-->
    <el-table :data="tableData"
              border
              stripe
              style="width: 100%"
    >
      <el-table-column style="width: auto" prop="id" label="编号" sortable/>
      <el-table-column style="width: auto" prop="storageType" label="出/入" :formatter="storageTypeFun"/>
      <el-table-column style="width: auto" prop="name" label="物料名称"/>
      <el-table-column style="width: auto" prop="materialType" label="物料类别"/>
      <el-table-column style="width: auto" prop="number" label="物料数量"/>
      <el-table-column style="width: auto" prop="projectsId" label="所属项目"/>
      <el-table-column style="width: auto" prop="peopleCheckName" label="验收员"/>
      <el-table-column style="width: auto" prop="time" label="时间"/>
      <el-table-column style="width: auto" prop="prove" label="证明材料">
        <template #default="scope">
          <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.prove"
              :preview-src-list="[scope.row.prove]"
              :preview-teleported="true"
              fit="fill"
          />
        </template>
      </el-table-column>
      <el-table-column style="width: auto" fixed="right" label="操作">
        <template #default="scope">
          <el-button size="small" @click="handleDetail(scope.row)">详情</el-button>
          <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--    分页-->
    <el-pagination
        style="margin: 10px 0"
        v-model:currentPage="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[1, 2, 3, 4,]"
        :page-size="10 "
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
    <!--对话-->
    <el-dialog
        v-model="dialogVisible"
        title="物料出入库详情"
        width="30%"
        destroy-on-close
        @close="closeDialog"
    >
      <el-form :model="upDateForm" ref="upDateForm" label-width="120px">
        <el-form-item label="编号" prop="upDateForm.id">
          <!--编号不可以编辑-->
          <el-input v-model="upDateForm.id" placeholder="无" clearable :disabled="true"/>
        </el-form-item>
        <el-form-item label="出/入" prop="upDateForm.storageType">
          <el-select v-model="upDateForm.storageType" placeholder="无" clearable :disabled="valDisabled">
            <el-option v-for="item in options"
                       :key="item.value"
                       :label="item.label"
                       :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="物料名称" prop="upDateForm.name">
          <el-input v-model="upDateForm.name" placeholder="无" clearable :disabled="valDisabled"/>
        </el-form-item>
        <el-form-item label="物料类别" prop="upDateForm.materialType">
          <el-input v-model="upDateForm.materialType" placeholder="无" clearable :disabled="valDisabled"/>
        </el-form-item>
        <el-form-item label="物料数量" prop="upDateForm.number">
          <el-input v-model="upDateForm.number" placeholder="无" clearable :disabled="valDisabled"/>
        </el-form-item>
        <el-form-item label="所属项目" prop="upDateForm.projectsName">
          <el-input v-model="upDateForm.projectsName" placeholder="无" clearable :disabled="valDisabled"/>
        </el-form-item>
        <el-form-item label="验收员" prop="upDateForm.peopleCheckName">
          <el-input v-model="upDateForm.peopleCheckName" placeholder="无" clearable :disabled="valDisabled"/>
        </el-form-item>
        <el-form-item label="日期和时间" prop="upDateFormtime">
          <el-date-picker clearable
                          :disabled="valDisabled"
                          v-model="upDateForm.time"
                          type="datetime"
                          placeholder="无"
                          format="YYYY/MM/DD hh:mm:ss"
                          value-format="YYYY/MM/DD hh:mm:ss"
          />
        </el-form-item>
        <el-form-item v-model="upDateForm.prove" label="证明材料" prop="prove">
          <el-upload action="/api/files/upload" :on-success="filesUploadSuccess" clearable :disabled="valDisabled">
            <el-button type="primary" :disabled="valDisabled">文件上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false,valDisabled=true">取消</el-button>
        <el-button type="primary" @click="handelEdit">编辑</el-button>
        <el-button type="primary" @click="handekUpDate" :disabled="valDisabled">保存</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
// @ is an alias to /src


import request from "@/utils/request";

export default {
  name: 'StorageMessages',
  components: {},
  data() {
    return {
      timeChoose:'',
      query: {//用于查询
      },
      peopleCheckName:'',
      projectsName: '',
      storageType: '',
      name: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],//列表数据
      dialogVisible: false,//对话框默认不显示
      upDateForm: {//设置初始值
      },//用于更新
      valDisabled: true,//用于控制弹出的表单中个输入框的编辑状态 默认不可编辑
      options: [//select选项
        {
          value: 0,
          label: '出',
        },
        {
          value: 1,
          label: '入',
        },
      ]
    }
  },
  created() {
    this.load()
  },
  methods: {
    storageTypeFun(row, colum) {//转换出入库类型 后台得到 0 显示 出 ；得到 1 显示 入
      switch (row.storageType) {
        case 0:
          return "出";
        case 1:
          return "入";
      }
    },

    load() {
      request.get("/storageMessages", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          peopleCheckName: this.peopleCheckName,
          projectsName: this.projectsName,
          storageType: this.storageType,
          name: this.name,
          minTime:this.timeChoose[0],
          maxTime:this.timeChoose[1],
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.records.length
      })
    },

    add() {//跳转到物料出入操作页面
      this.$router.push('/storage')
    },
    resetField(formName) {//点击 重置 响应
      this.$refs[formName].resetFields()
    },

    handleSizeChange(pageSize) {//改变当前每页个数
      // this.pageSize = pageSize 如果上面没有使用 v-model绑定 v-model:page-size="pageSize" 需要加上这个
      this.load()
    },
    handleCurrentChange(pageNum) {//改变当前页码
      // this.currentPage = pageNum 如果上面没有使用 v-model绑定 v-model:currentPage="currentPage" 需要加上这个
      this.load()
    },

    handleDetail(row) {//查看物料出入库详情信息 弹出对话框 可在其中更改数据信息
      //通过row初始化表单的值
      console.log(row)
      this.upDateForm = row
      console.log(this.upDateForm)
      // this.query = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    closeDialog() {
      this.valDisabled = true
    },
    handleDelete(id) {//删除一条记录 响应删除按钮
      console.log(id)
      request.delete("storageMessages/" + id).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            messeage: res.msg
          })
        }
        this.load()
      })
    },
    handelEdit() {//点击对话框中的 编辑 按钮响应
      //点击后 表单可编辑 显示保存更新的按钮
      this.valDisabled = false
    },
    handekUpDate() {
      request.put("/storageMessages", this.upDateForm).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()//刷新表格数据
        this.dialogVisible = false//关闭表格
      })
    },
    filesUploadSuccess(response, file, fileList) {
      console.log(response)
      this.upDateForm.prove = response.data
    },
  }
}
</script>
