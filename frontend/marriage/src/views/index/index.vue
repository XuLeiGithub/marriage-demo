<template>
  <div>
    <div class="main">
      <div class="index-top">
        <div class="content">
          <div class="logo">
            <i class="icon">BCMS</i>基于区块链的登记结婚系统
          </div>
          <div class="btn-right">
            <router-link to="/login">登录</router-link>
            <!-- <span>|</span>
            <router-link to="/regist" class="register">免费注册</router-link> -->
          </div>
        </div>
      </div>

      <div class="index-banner">
        <div class="slogan">
          <h3>Block Chain Marry System</h3>
          <div class="summary">基于区块链的登记结婚系统</div>
        </div>
      </div>
      <div class="notice">
        <div class="content">
          <el-carousel height="50px" direction="vertical">
            <el-carousel-item v-for="item in noticeList" :key="item.index">
              <h3 class="medium">{{ item.marriageDesc }}</h3>
            </el-carousel-item>
          </el-carousel>
        </div>
      </div>
      <!-- <div class="index-content">
        <div class="item">
          <div class="total">{{ signData.totalChains }}</div>
          <div class="title">平台链路统计</div>
        </div>
        <div class="item">
          <div class="total">{{ signData.totalUsers }}</div>
          <div class="title">平台用户统计</div>
        </div>
      </div> -->
    </div>
    <div class="copyright">CopyRight © 2021 上海久誉软件系统有限公司</div>
  </div>
</template>

<script>
import { getNotice } from '@/api/table'
export default {
  data() {
    return {
      noticeList: [],
      signData: {
        totalChains: '',
        totalUsers: ''
      }
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      getNotice().then((res) => {
        const { data } = res
        console.log(res)
        this.noticeList = data
      })
    }
  }
}
</script>

<style lang="scss" >
.main {
  min-height: calc(100vh - 57px);
}
.index-top {
  width: 100%;
  background: #fff;
  height: 62px;
  color: #454a5b;
  z-index: 99;
  width: 100%;
  min-width: 1280px;
  box-shadow: 0 2px 10px 0 rgb(0 0 0 / 5%);
  .content {
    width: 1100px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .logo {
      padding-top: 14px;
      display: flex;
      align-items: center;
      .icon {
        margin-right: 10px;
        display: flex;
        padding: 4px 5px;
        color: #fff;
        font-size: 16px;
        font-weight: 700;
        background: #e3262e;
        letter-spacing: 2px;
        border-radius: 3px;
      }
    }
    .btn-right {
      padding-top: 20px;
      font-size: 14px;
      color: #1870e1;
      font-weight: normal;
      span {
        padding: 0 8px;
      }
    }
  }
}
.index-banner {
  width: 100%;
  height: 440px;
  background: url("../../assets/images/banner-index.png") no-repeat top center;
  background-size: cover;
  .slogan {
    width: 1200px;
    margin: 0 auto;
    color: #fff;
    text-align: center;
    h3 {
      margin: 0;
      padding: 160px 0 0 40px;
      font-size: 36px;
    }
    .summary {
      padding: 20px 0 0 40px;
      font-size: 18px;
    }
  }
}
.index-content {
  width: 1100px;
  height: 140px;
  margin: 20px auto;
  display: flex;
  overflow: hidden;
  background: #fff;
  box-shadow: 0 10px 20px 0 rgb(0 0 0 / 5%);
  border-radius: 4px;

  .item {
    width: 550px;
    text-align: center;
    .total {
      padding: 20px 0 10px 0;
      line-height: 42px;
      font-size: 42px;
      color: #ff8324;
    }
    .title {
      font-size: 18px;
    }
  }
  .item + .item {
    border-left: 1px solid #efefef;
  }
}
.copyright {
  padding: 20px 0;
  border-top: 1px solid #efefef;
  font-size: 12px;
  text-align: center;
  color: #454a5b;
}
.notice {
  width: 100%;
  height: 50px;
  background: #fff;
  border-bottom: 1px solid #eee;
  .content {
    width: 1100px;
    margin: 0 auto;
    .medium {
      font-weight: normal;
      font-size: 14px;
    }
  }
}
</style>
