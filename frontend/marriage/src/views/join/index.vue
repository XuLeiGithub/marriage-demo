<template>
  <div class="app-container">
    <div class="btn-top">
      <el-button
        type="primary"
        plain
        @click="createCertificate"
      >新建证书</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list.slice((currentPage-1)*pageSize,currentPage*pageSize)"
    >
      element-loading-text="Loading"
      >
      <el-table-column align="center" label="序号" width="95">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column label="证书编号" width="190" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.certificateNumber }}
        </template>
      </el-table-column>
      <el-table-column label="证书状态" width="100" show-overflow-tooltip>
        <template slot-scope="scope">
          <div style="color:#0ba54b" v-if=" scope.row.certStatus == 'confirmed'">已确认</div>
          <div style="color:grey" v-if=" scope.row.certStatus == 'draft'">草稿</div>
        </template>
      </el-table-column>
      <el-table-column label="男方姓名" width="100" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.maleUsername }}
        </template>
      </el-table-column>
      <el-table-column
        label="男方公钥地址"
        width="210"
        align="center"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <span>{{ scope.row.malePublicAddress }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="女方姓名"
        width="110"
        align="center"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          {{ scope.row.femaleUsername }}
        </template>
      </el-table-column>
      <el-table-column label="女方公钥地址" min-width="210"  show-overflow-tooltip align="center">
        <template slot-scope="scope">
          {{ scope.row.femalePublicAddress }}
        </template>
      </el-table-column>
      <el-table-column label="证婚人姓名" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.witnessUsername }}
        </template>
      </el-table-column>
      <el-table-column label="证婚人公钥地址" min-width="210"  show-overflow-tooltip align="center">
        <template slot-scope="scope">
          {{ scope.row.witnessPublicAddress }}
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" min-width="120" show-overflow-tooltip>
        <template slot-scope="scope">
          {{ scope.row.marriageDesc }}
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
      <el-table-column label="操作" width="210" align="center" fixed="right">
        <template slot-scope="scope">
          <a class="btn-link" @click="handlCertificate(scope.row)">查看证书</a>
          <!--<span class="individer-line">|</span>
           <a
            class="btn-link"
            @click="handleSignData(scope.$index, scope.row)"
          >签名</a> <span class="individer-line">|</span>
          <a
            class="btn-link"
            @click="handleSupplier(scope.$index, scope.row)"
          >查看参与方</a><span class="individer-line">|</span>
          <a
            class="btn-link"
            @click="handleSign(scope.$index, scope.row)"
          >查看签名状态</a> -->
        </template>
      </el-table-column>
    </el-table>
    <el-pagination style=" margin-top: 20px;" align="center" :current-page="currentPage" :page-sizes="[5,10,20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="list.length" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    <el-dialog title="供应商信息" :visible.sync="dialogSupplierVisible">
      <el-table :data="supplierData">
        <el-table-column
          property="participaterDidId"
          label="参与者数字身份"
          width=""
        />
        <el-table-column
          property="participaterOrgName"
          label="参与者公司名"
          width="200"
          show-overflow-tooltip
        />
        <el-table-column label="分成比例" width="90">
          <template
            slot-scope="scope"
          >{{ scope.row.portion }}%</template></el-table-column>
        <el-table-column
          property="role"
          width="100"
          label="角色"
          show-overflow-tooltip
        />
      </el-table>
    </el-dialog>
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

    <el-dialog title="证书详情" :visible.sync="dialogSupplierVisible">
      <div class="certifacate-info">
        <div class="list">
          <dl>
            <dt>男方姓名:</dt>
            <dd>
              {{ certificateInfo.maleUsername
              }}<b
                :class="
                  certificateInfo.maleSignStatus === '已签名'
                    ? 'signed'
                    : 'unsigned'
                "
              >{{ certificateInfo.maleSignStatus }}</b>
            </dd>
          </dl>
          <dl>
            <dt>女方姓名:</dt>
            <dd>
              {{ certificateInfo.femaleUsername
              }}<b
                :class="
                  certificateInfo.femaleSignStatus === '已签名'
                    ? 'signed'
                    : 'unsigned'
                "
              >{{ certificateInfo.femaleSignStatus }}</b>
            </dd>
          </dl>
          <dl>
            <dt>证婚人姓名:</dt>
            <dd>
              {{ certificateInfo.witnessUsername
              }}<b
                :class="
                  certificateInfo.witnessSignStatus === '已签名'
                    ? 'signed'
                    : 'unsigned'
                "
              >{{ certificateInfo.witnessSignStatus }}</b>
            </dd>
          </dl>
          <dl>
            <dt>描述</dt>
            <dd>{{ certificateInfo.marriageDesc }}</dd>
          </dl>
        </div>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  getList,
  checkSign,
  getSignInfo,
  addSign,
  getCardInfo
} from '@/api/table'
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
      certificateInfo: {},
      activeName: 'first',
      list: [],
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
    this.fetchData()
    this.getSignList()
  },
  methods: {
    handleSizeChange(val) {
      this.currentPage = 1
      this.pageSize = val
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handlCertificate(row) {
      this.dialogSupplierVisible = true
      const id = row.certificateNumber
      console.log(id)
      getCardInfo(id).then((res) => {
        this.certificateInfo = res.data
        console.log(res)
      })
    },
    handleSigned() {
      const parms = this.form
      addSign(parms).then((res) => {
        this.signDataVisible = false
      })
    },
    handleSignedFe() {
      console.log(this.formFe)
      const parms = this.formFe
      addSign(parms).then((res) => {
        this.signDataVisible = false
        const msg = res.data
        this.$alert(msg, '标题名称', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'info'
            })
          }
        })
      })
    },
    getSignList() {
      const male = 'male'
      const female = 'female'
      getSignInfo(male).then((res) => {
        console.log(res)
        this.signUserData = res.data
      })
      getSignInfo(female).then((res) => {
        console.log(res)
        this.signUserDataFe = res.data
      })
    },
    handleSignData() {
      const id = getUserId()
      this.formFe.adminUserId = Number(id)
      this.form.adminUserId = Number(id)
      this.signDataVisible = true
    },
    createCertificate() {
      this.$router.push('/marriageInfo/createCer')
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
    handleSupplier(index, row) {
      this.dialogSupplierVisible = true
      this.supplierData = row.itemList
    },
    fetchData() {
      this.listLoading = true
      getList().then((response) => {
        console.log(response)
        this.list = response.data
        this.listLoading = false
      })
    }
  }
}
</script>
<style lang="scss">
.certifacate-info {
  width: 680px;
  height: 499px;
  overflow: hidden;
  background: url("../../assets/images/certificate-bg.png") no-repeat;
  .list {
    width: 500px;
    margin: 200px auto 0 auto;
    dl {
      display: flex;
      line-height: 21px;
      align-items: flex-start;
      dt {
        width: 100px;
        color: #000;
      }
      dd {
        width: 440px;
        margin: 0;
        b {
          margin-left: 10px;
          font-weight: normal;
          display: inline-block;
          height: 26px;
          padding: 0 8px;
          line-height: 24px;
          font-size: 12px;
          border-radius: 4px;
          box-sizing: border-box;
          &.signed {
            background-color: #ecf5ff;
            color: #409eff;
            border: 1px solid #d9ecff;
          }
          &.unsigned {
            background-color: #f4f4f5;
            border-color: #e9e9eb;
            color: #909399;
            border: 1px solid #e9e9eb;
          }
        }
      }
    }
  }
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
