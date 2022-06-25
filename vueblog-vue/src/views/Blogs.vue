<template>
  <div>
    <Header></Header>

    <div class="block">
      <el-timeline>
        <el-timeline-item
          :timestamp="blog.created"
          placement="top"
          v-for="(blog, index) in blogs"
          :key="index"
        >
          <el-card>
            <h4>
              <router-link
                :to="{ name: 'BlogDetail', path:'/detail/:name', params: { blogId: blog.id } }"
              >
                {{ blog.title }}
              </router-link>
            </h4>
            <p>{{ blog.description }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>

    <el-pagination
      background
      layout="prev, pager, next"
      class="mpage"
      v-model:currentPage="currentPage"
      v-model:page-size="pageSize"
      :total="total"
      @current-change="page"
    >
    </el-pagination>
  </div>
</template>
<script>
import Header from "@/components/Header.vue";
import request from "@/utils/request";
export default {
  name: "Blogs",
  components: { Header },
  data() {
    return {
      blogs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5,
    };
  },
  methods: {
    page(currentPage) {
      request
        .get("/blog/list", {
          params: {
            currentPage: this.currentPage,
          },
        })
        .then((res) => {
          console.log(res);
          this.blogs = res.data.records;
          this.currentPage = res.data.current;
          this.total = res.data.total;
          this.pageSize = res.data.size;
        });
    },
  },
  created() {
    this.page(1);
  },
};
</script>

<style scoped>
.mpage {
  max-width: 50%;
  margin: 0 auto;
}
</style>