<template>
  <div>

    <el-container>
      <el-header>
        <img class="mlogo" src="https://www.markerhub.com/dist/images/logo/markerhub-logo.png" alt="">
      </el-header>
      <el-main>
        <el-form :model="form" :rules="rules" ref="form"  label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="form.password"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('form')">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>

      </el-main>
    </el-container>

  </div>
</template>

<script>
import request from '@/utils/request'
  export default {
    name: "Login",
    data() {
      return {
        form: {
          username: 'tututu',
          password: ''
        },
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请选择密码', trigger: 'change' }
          ]
        }
      };
    },
    created() {
        this.$store.commit("REMOVE_INFO")
    },
    methods: {
      submitForm(form) {
        this.$refs[form].validate((valid) => {
          if (valid) {
           
            // 全局的this
            const _this = this
           request.post('/account/login', this.form).then(res => {

              if (res.code === "0") {

                    // 这里面的this就是axios的this了
            //   console.log(res)
              const jwt = res.data.jwt
              const userInfo = res.data
            //   console.log(jwt + "*********" + userInfo)

            //   // 把数据共享出去
              _this.$store.commit("SET_TOKEN", jwt)
              _this.$store.commit("SET_USERINFO", userInfo)

            //   // 获取
              console.log(_this.$store.getters.getUser)
              _this.$router.push("/")
              this.$message({
                type: "success",
                message: res.msg,
              });
             
            } else {
              console.log(res);
              this.$message({
                type: "error",
                message: res.msg,
              });
              _this.$store.commit("REMOVE_INFO")
            }

            })

          } 
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style scoped>
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    /*background-color: #E9EEF3;*/
    color: #333;
    text-align: center;
    line-height: 160px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }

  .mlogo {
    height: 60%;
    margin-top: 10px;
  }

  .demo-ruleForm {
    max-width: 500px;
    margin: 0 auto;
  }

</style>