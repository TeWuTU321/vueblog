<template>
  <div>
    <Header></Header>

    <div class="mblog">
      <h2>{{ blog.title }}</h2>
      <el-link icon="el-icon-edit" v-if="ownBlog">
        <router-link :to="{ name: 'BlogEdit', params: { blogId: blog.id } }">
          编辑
        </router-link>
      </el-link>
      <el-divider direction="vertical"></el-divider>
      <span><el-link @click="this.$router.back">返回</el-link></span>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>
    </div>
  </div>
</template>
<script>
import request from "@/utils/request";
import Header from "../components/Header";
import "github-markdown-css";

export default {
  name: "BlogDetail",
  components: { Header },
  data() {
    return {
      blog: {
        id: "",
        title: "",
        content: "",
      },
      ownBlog: false,
    };
  },
  created() {
    let blogId = this.$route.params.blogId;
    // 第二次传进来参数为零的话，就从session存储中获取
    if (!blogId) {
      blogId = sessionStorage.getItem("blogId");
    } else {
      sessionStorage.setItem("blogId", blogId);
      console.log(blogId);
    }
    request.get("/blog/detail/" + blogId).then((res) => {
      const blog = res.data;
      this.blog.id = blog.id;
      this.blog.title = blog.title;
      //渲染内容
      //   var MardownIt = require("markdown-it");
      //   var md = new MardownIt();
      //   var result = md.render(blog.content);
      //   this.blog.content = result;
      this.blog.content = blog.content;
      // 权限，当前博客的用户id，与当前登录用户id是否一致，若不同则不显示
      this.ownBlog = blog.userId === this.$store.getters.getUser.id;
    });
  },
};
</script>

<style scoped>
.mblog {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  min-height: 700px;
  padding: 20px 15px;
}
</style>