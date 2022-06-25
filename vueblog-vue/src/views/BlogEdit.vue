<template>
  <div class="mcontaner">
    <Header></Header>

    <div class="m-content">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>

        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <!-- 王富文本编辑器 -->
          <div style="border: 1px solid #ccc">
            <Toolbar
              style="border-bottom: 1px solid #ccc"
              :editor="editor"
              :defaultConfig="toolbarConfig"
              :mode="mode"
            />
            <Editor
              style="height: 500px; overflow-y: hidden"
              v-model="ruleForm.content"
              :defaultConfig="editorConfig"
              :mode="mode"
              @onCreated="onCreated"
            />
          </div>
          <!-- 编辑结束 -->
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')"
            >立即创建</el-button
          >
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";

import Header from "@/components/Header.vue";
import request from "@/utils/request";
export default {
  name: "BlogEdit",
  components: { Header, Editor, Toolbar },
  data() {
    return {
      ruleForm: {
        id: "",
        title: "",
        description: "",
        content: "<p>你好！</p>",
      },
      // 编辑数据
      editor: null,
      toolbarConfig: {},
      editorConfig: { placeholder: "请输入内容..." },
      mode: "default",
      rules: {
        title: [
          { required: true, message: "请输入标题", trigger: "blur" },
          {
            min: 3,
            max: 25,
            message: "长度在 3 到 25 个字符",
            trigger: "blur",
          },
        ],
        description: [
          { required: true, message: "请输入摘要", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request
            .post("/blog/edit", this.ruleForm, {
              headers: {
                Authorization: localStorage.getItem("token"),
              },
            })
            .then((res) => {
              console.log(res);
              if (res.code === "0") {
                this.$message({
                  type: "success",
                  message: "编辑成功",
                });
                this.$router.push("/");
              } else {
                this.$message({
                  type: "error",
                  message: "编辑失败",
                });
              }
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //   富文本编辑
    onCreated(editor) {
      this.editor = Object.seal(editor);
      // 一定要用 Object.seal() ，否则会报错
    },
  },
  created() {
    const blogId = this.$route.params.blogId
      console.log(blogId)
      if(blogId) {
      request.get('/blog/detail/' + blogId).then(res => {
          const blog = res.data
          this.ruleForm.id = blog.id
          this.ruleForm.title = blog.title
          this.ruleForm.description = blog.description
          this.ruleForm.content = blog.content
        })
      }

  },
  //   mounted() {
  //     // 模拟 ajax 请求，异步渲染编辑器
  //     setTimeout(() => {
  //       this.html = "<p>模拟 Ajax 异步设置内容 HTML</p>";
  //     }, 1500);
  //   },
  beforeDestroy() {
    const editor = this.editor;
    if (editor == null) return;
    editor.destroy(); // 组件销毁时，及时销毁编辑器
  },
};
</script>
<style src="@wangeditor/editor/dist/css/style.css"></style>