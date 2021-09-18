<template>
  <div>
    <div class="create-form">
      <el-form
        ref="dynamicValidateForm"
        :model="dynamicValidateForm"
        :rules="createRules"
        label-width="100px"
        class="demo-dynamic"
      >
        <el-form-item prop="username" label="姓名">
          <el-input v-model="dynamicValidateForm.username" />
        </el-form-item>
        <el-form-item prop="sex" label="性别">
          <el-select
            v-model="dynamicValidateForm.sex"
            placeholder="请选择"
          >
            <template>
              <el-option
                label="男"
                value="male"
              />
              <el-option
                label="女"
                value="female"
              />
            </template>
          </el-select>
        </el-form-item>
        <el-form-item prop="age" label="年龄">
          <el-input v-model="dynamicValidateForm.age" type="number" />
        </el-form-item>
        <el-form-item prop="idCard" label="身份证号码">
          <el-input v-model="dynamicValidateForm.idCard" max-length="18" />
        </el-form-item>

        <el-form-item prop="familyAddress" label="家庭住址">
          <el-input v-model="dynamicValidateForm.familyAddress" />
        </el-form-item>

        <el-form-item>
          <el-button @click="addChain">返回</el-button>
          <el-button
            type="primary"
            @click="submitForm('dynamicValidateForm')"
          >提交</el-button>
          <!--  <el-button @click="resetForm('dynamicValidateForm')">重置</el-button> -->
        </el-form-item>
        </el-form>
    </div>
  </div>
</template>

<script>
import {
  validUserIdCardNo
} from '@/utils/validate'
import { getParticipaterId, getUserId } from '@/utils/auth'
import { getSelectList, createChain, getSignInfo } from '@/api/table'
import uuidv4 from 'uuid/v4'
export default {
  data() {
    const validateUserIdCardNo = (rule, value, callback) => {
      if (value) {
        if (validUserIdCardNo(value)) {
          callback()
        } else {
          callback(new Error('请输入正确的身份证号码'))
        }
      }
    }
    return {
      signData: [],
      createRules: {
        familyAddress: [{ required: true, message: '请输入家庭住址', trigger: 'blur' }],
        role: [{ required: true, message: '请输入角色', trigger: 'blur' }],
        age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
        idCard: [
          { required: true, message: '身份证号码不能为空' }
          // { validator: validateUserIdCardNo, trigger: 'blur' }
        ],
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }]
      },
      test: '',
      roleList: [],

      level: 1,
      nextUuid: '',
      dynamicValidateForm: {
        adminUserId: '',
        idCard: '',
        sex: 'male',
        familyAddress: '',
        age: '',
        username: ''
      }
    }
  },
  created() {

    // this.getSeletData()
  },
  methods: {

    createUuid() {
      let uuid = uuidv4()
      uuid = uuid.split('-').join('')
      return uuid
    },
    getSeletData() {
      const userId = getUserId()
      const pId = getParticipaterId()
      this.dynamicValidateForm.userId = userId
      this.preUuid = this.dynamicValidateForm.itemList[0].itemId
      getSelectList().then((res) => {
        this.roleList = res.data
        this.roleList.forEach((item) => {
          if (item.id == pId) {
            this.dynamicValidateForm.itemList[0].id = item.id
          }
        })
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const userId = getUserId()
          this.dynamicValidateForm.adminUserId = userId
          console.log(this.dynamicValidateForm)
          createChain(this.dynamicValidateForm).then((res) => {
            if (res.code === 0) {
              this.$confirm('提交成功', '提交成功', {
                confirmButtonText: '返回',
                type: 'success',
                showCancelButton: false,
                closeOnClickModal: false
              }).then(() => {
                this.$router.push({ path: '/list' })
              })
            }
          })
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    removeChain(item) {
      const chainIndex = this.dynamicValidateForm.itemList.indexOf(item)
      console.log(this.dynamicValidateForm.itemList.length - 1)
      console.log(chainIndex)
      if (chainIndex !== this.dynamicValidateForm.itemList.length - 1) {
        const preItem = this.dynamicValidateForm.itemList[chainIndex - 1]
        this.dynamicValidateForm.itemList[chainIndex + 1].lastItemId =
          preItem.itemId
      }

      if (chainIndex !== -1) {
        this.dynamicValidateForm.itemList.splice(chainIndex, 1)
      }
    },
    addChain() {
      this.$router.push({ path: '/list/table' })
    }
  }
}
</script>

<style lang="scss">
.create-form {
  width: 70%;
  height: 900px;
  margin: 40px auto;
  .el-col .el-form-item__content,
  .el-input input {
    margin-left: 10px;
  }
  .el-col .el-input input {
    margin-left: 0;
  }
  .el-select {
    display: block;
  }
}
</style>
