<template>
  <div class="create-certificate">
    <el-form ref="form" :model="form" label-width="120px" class="form-box">

      <el-form-item label="请选择男方">
        <el-select
          v-model="form.maleUserId"
          placeholder="请选择男方"
          @change = 'changeMan'

        >
          <el-option
            v-for="item in signUserData"
            :key="item.id"
            :label="item.username"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="请选择女方">
        <el-select
          v-model="form.femaleUserId"
          placeholder="请选择女方"
          @change = 'changeMan'
        >
          <el-option
            v-for="item in signUserDataFe"
            :key="item.id"
            :label="item.username"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="请选择证婚人">
        <el-select
          v-model="form.witnessUserId"
          placeholder="请选择证婚人"
          :disabled = witnessDisabled
        >
          <el-option
            v-for="item in witnessData"
            :key="item.id"
            :label="item.username"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item />
      <el-form-item>
        <el-button type="primary" @click="handleSignedCer">提交</el-button>
      </el-form-item>
    </el-form></div>
</template>

<script>
import { getSignInfo, createCertificate ,getUserList} from '@/api/table'
import { getUserId } from '@/utils/auth'
export default {
  data() {
    return {
      userList:[],
      signUserData: [],
      signUserDataFe: [],
      witnessData:[], //征婚人数据
      form: {
        adminUserId: '',
        maleUserId: '',
        femaleUserId: '',
        witnessUserId:''//证婚人
      },
      witnessDisabled: true
    }
  },
  created() {
    this.getSignList()
    this.getAlllist()

  },
  methods: {
    handleSignedCer() {
      const params = this.form
      createCertificate(params).then(res => {
        this.$router.push('/marriageInfo/list')
      })
    },
    getSignList() {
      const id = getUserId()
      this.form.adminUserId = Number(id)
      const male = 'male'
      const female = 'female'
      getSignInfo(male).then(res => {
        console.log(res)
        this.signUserData = res.data
      })
      getSignInfo(female).then(res => {
        console.log(res)
        this.signUserDataFe = res.data
      })
    },
    getAlllist(){
      getUserList().then((res) => {
        console.log(res)
        this.userList = res.data
      })
    },
    // 更改人员时的回调
    changeMan(){
      this.form.witnessUserId =''
      if(this.form.maleUserId !=='' && this.form.femaleUserId !=='' ){
        this.witnessDisabled = false
      }else{
        this.witnessDisabled = true
      }
     this.witnessData =  this.userList.filter( item=> item.id !==this.form.maleUserId && item.id !== this.form.femaleUserId)
     console.log( this.witnessData)
    }
  }
}
</script>

<style lang="scss" scoped>
.create-certificate{
    width: 600px;
    margin: 40px 0 80px 40px;
}
</style>
