<template>
  <div class="app-container">
    <div class="btn-top">
      <el-button type="primary" plain @click="createChain">新建</el-button>
    </div>
    <el-table :data="userList.slice((currentPage-1)*pageSize,currentPage*pageSize)"
    >
      <el-table-column align="center" label="序号" width="95">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <!--  <el-table-column label="证书Id" width="180">
        <template slot-scope="scope">
          {{ scope.row.idCard }}
        </template>
      </el-table-column> -->
      <el-table-column label="姓名" width="90">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column label="性别" width="60" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.sex === "male" ? "男" : "女" }}</span>
        </template>
      </el-table-column>
      <el-table-column label="年龄" width="90">
        <template slot-scope="scope">
          {{ scope.row.age }}
        </template>
      </el-table-column>
      <el-table-column label="身份证号码" width="200">
        <template slot-scope="scope">
          {{ scope.row.idCard }}
        </template>
      </el-table-column>

      <el-table-column label="地址" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.familyAddress }}
        </template>
      </el-table-column>
      <el-table-column label="创建日期" prop="updateTime" width="210" align="center"   >
      </el-table-column>
      <el-table-column
        align="center"
        prop="created_at"
        label="操作"
        width="200"
      >
        <template slot-scope="scope">
          <a class="btn-link" @click="handleSigned(scope.row)">签名</a>
        </template>
      </el-table-column>
      <!-- <el-table-column label="查看供应商" width="110" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleSupplier(scope.$index, scope.row)"
            >查看</el-button
          >
        </template>
      </el-table-column> -->
      <!--  <el-table-column label="操作" width="210" align="center" fixed="right">
        <template slot-scope="scope">

          <a
            class="btn-link"
            @click="handlCertificate(scope.row)"
          >查看证书</a>

        </template>
      </el-table-column> -->
    </el-table>
    <el-pagination style="margin-top: 20px;" align="center" :current-page="currentPage" :page-sizes="[5,10,20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="userList.length" @size-change="handleSizeChange" @current-change="handleCurrentChange" />

    <!-- 签名状态弹框 -->

    <el-dialog title="签名" :visible.sync="signDataVisible">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="男方" name="first">
          <el-form
            ref="form"
            :model="form"
            label-width="120px"
            class="form-box"
          >
            <el-form-item label="请选择">
              <el-select v-model="form.userId" placeholder="请选择">
                <el-option
                  v-for="item in signUserData"
                  :key="item.id"
                  :label="item.username"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSigned">提交</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="女方" name="second">
          <el-form
            ref="form"
            :model="form"
            label-width="120px"
            class="form-box"
          >
            <el-form-item label="请选择">
              <el-select v-model="formFe.userId" placeholder="请选择">
                <el-option
                  v-for="item in signUserDataFe"
                  :key="item.id"
                  :label="item.username"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSignedFe">提交</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <el-dialog
      title="签名状态"
      :visible.sync="dialogSignVisible"
      width="30%"
      class="sign-box"
    >
      <div class="content">
        <el-divider
          content-position="left"
        ><i class="el-icon-check" />已签名({{ signedCount }})</el-divider>
        <div class="list">
          <template v-if="signData.signedOrgNames">
            <span v-for="item in signData.signedOrgNames" :key="item">
              <span>{{ item }}</span>
              <el-divider
                direction="vertical"
              /></span>
          </template>
        </div>
        <el-divider
          content-position="left"
          class="need-sign-title"
        ><i class="el-icon-edit" />需要签名({{ needSignCount }})</el-divider>
        <span
          v-for="(item, index) in signData.needSignOrgNames"
          :key="index"
          class="list"
        >
          <span>{{ item }}</span>
          <el-divider direction="vertical" />
        </span>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getList, checkSign, getUserList, addSign } from '@/api/table'
import { getUserId } from '@/utils/auth'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      currentPage: 1, // 当前页码
      total: 20, // 总条数
      pageSize: 10, // 每页的数据条
      activeName: 'first',
      userList: [],
      signUserData: [],
      signUserDataFe: [],
      form: {
        adminUserId: '',
        userId: ''
      },
      formFe: {
        adminUserId: '',
        userId: ''
      },
      signDataVisible: false,
      listLoading: true,
      supplierData: [],
      dialogSupplierVisible: false,
      dialogSignVisible: false,
      signData: {
        needSignOrgNames: [],
        signedOrgNames: []
      },
      needSignCount: '',
      signedCount: ''
    }
  },
  created() {
    // this.fetchData()
    this.getUserData()
  },
  methods: {
    handleSizeChange(val) {
      this.currentPage = 1
      this.pageSize = val
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSigned(row) {
        this.$confirm('确认签名, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(() => {
      const adminUserId = Number(getUserId())
      const userId = row.id
      const params = {
        adminUserId,
        userId
      }
      console.log(params)
      addSign(params).then(res => {
        console.log(res)
        this.signDataVisible = false
        const successInfo = res.data.txHash
        this.$confirm(successInfo + '', '签名成功', {
          confirmButtonText: '返回',
          type: 'success',
          showCancelButton: false,
          closeOnClickModal: false
        })
      })
        }).catch(() => {
          console.log('取消签名')
        });


    },
    handleSignedFe() {
      console.log(this.formFe)
      const parms = this.formFe
      addSign(parms).then((res) => {
        this.signDataVisible = false
        const successInfo = res.data.txHash
        this.$confirm(successInfo + '', '签名成功', {
          confirmButtonText: '返回',
          type: 'success',
          showCancelButton: false,
          closeOnClickModal: false
        })
      })
    },
    getUserData() {
      getUserList().then((res) => {
        console.log(res)
        this.userList = res.data
        this.userList = this.userList.sort((a,b)=>{
        return new Date(a.updateTime) > new Date(b.updateTime) ? -1 : 1;
      })
      })
    },
    handleSignData() {
      const id = getUserId()
      this.formFe.adminUserId = Number(id)
      this.form.adminUserId = Number(id)
      this.signDataVisible = true
    },
    createChain() {
      this.$router.push('/list/create')
    },
    handleSign(index, row) {
      const { chainId } = row
      checkSign(chainId).then((response) => {
        this.dialogSignVisible = true
        this.signData = response.data
        this.needSignCount = this.signData.needSignOrgNames.length
        this.signedCount = this.signData.signedOrgNames.length
      })
    },

    fetchData() {
      this.listLoading = true
      getList().then((response) => {
        this.userList = response.data
        this.listLoading = false
      })
    }
  }
}
</script>
<style lang="scss">
.el-message-box__message p {
  word-break: break-all;
}
.btn-top {
  padding-bottom: 20px;
}
.btn-link {
  color: #2d8cf0;
}
.individer-line {
  padding: 0 10px;
  color: #c2c2c2;
}
.el-divider--horizontal {
  margin: 35px 0;
}
.el-table th {
  background: #f8f8f9;
  color: #515a6e;
}
.sign-box {
  .need-sign-title {
    margin-top: 20px;
  }
  .el-dialog__body {
    padding-bottom: 60px;
    padding-top: 0;
  }
}
</style>
