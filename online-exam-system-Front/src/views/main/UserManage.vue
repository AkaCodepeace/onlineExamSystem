<template>
  <div class="markTest">
    <TopTab title="用户管理">
      <div class="content">
        <el-tabs
          v-model="activeName"
          type="border-card"
          @tab-click="handleClick"
        >
          <el-tab-pane label="教师信息管理" name="tchManage">
            <div class="table">
              <el-table
                :data="tchManageList"
                style="width: 100%"
                v-loading="loading1"
              >
                <el-table-column label="用戶id" width="110">
                  <template slot-scope="scope"
                    >U{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="用戶名" min-width="150">
                  <template slot-scope="scope"
                    >{{  }}
                  </template>
                </el-table-column>
                <el-table-column label="性別" width="150">
                  <template slot-scope="scope"
                    >{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="手機電話" width="150">
                  <template slot-scope="scope"
                    >{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="郵箱" width="150">
                  <template slot-scope="scope"
                    >{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="身份" width="150">
                  <template slot-scope="scope"
                    >{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="密碼" width="150">
                  <template slot-scope="scope"
                    >{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="300px" fixed="right">
                  <template slot-scope="scope">
                    <el-button
                      type="info"
                      size="mini"
                      plain
                      @click="editTestPaper(scope.row.examId)"
                      >编辑</el-button
                    >
                    <el-button
                      type="danger"
                      size="mini"
                      plain
                      @click="deleteTestPaper(scope.row.examId)"
                      >删除</el-button
                    >
                  </template>
                </el-table-column>
              </el-table>
            </div>

            <div class="page">
              <el-pagination
                background
                layout="total, prev, pager, next,jumper"
                @current-change="currentChange1"
                :total="total1"
                :page-size="pageSize1"
              />
            </div>
          </el-tab-pane>

          <el-tab-pane label="学生信息管理" name="stuManage">
            <div class="table">
              <el-table
                :data="stuManageList"
                style="width: 100%"
                v-loading="loading2"
              >
                <el-table-column label="用戶id" width="110">
                  <template slot-scope="scope"
                    >U{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="用戶名" min-width="150">
                  <template slot-scope="scope"
                    >{{  }}
                  </template>
                </el-table-column>
                <el-table-column label="性別" width="150">
                  <template slot-scope="scope"
                    >{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="手機電話" width="150">
                  <template slot-scope="scope"
                    >{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="郵箱" width="150">
                  <template slot-scope="scope"
                    >{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="身份" width="150">
                  <template slot-scope="scope"
                    >{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="密碼" width="150">
                  <template slot-scope="scope"
                    >{{ }}
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="300px" fixed="right">
                  <template slot-scope="scope">
                    <el-button
                      type="info"
                      size="mini"
                      plain
                      @click="editTestPaper(scope.row.examId)"
                      >编辑</el-button
                    >
                    <el-button
                      type="danger"
                      size="mini"
                      plain
                      @click="deleteTestPaper(scope.row.examId)"
                      >删除</el-button
                    >
                  </template>
                </el-table-column>
              </el-table>
            </div>

            <div class="page">
              <el-pagination
                background
                layout="total, prev, pager, next,jumper"
                @current-change="currentChange2"
                :total="total2"
                :page-size="pageSize2"
              />
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </TopTab>
  </div>
</template>

<script>
import TopTab from "../../components/TopTab.vue";
import "../../assets/less/my_tab.less";
import "../../assets/less/main/markTest.less";
export default {
  name: "UserManage",
  components: {
    TopTab,
  },
  data() {
    return {
      activeName:"",
      //教師列表
      tchManageList:[],
      //學生
      stuManageList:[],

      // 教師分页
      loading1:false,
      currentPage1: 1,
      pageSize1: 10,
      total1: null,

      //學生分頁
      loading2:false,
      currentPage2: 1,
      pageSize2: 10,
      total2: null,
    };
  },
  created() {},
  methods: {
    //查詢教師列表
    getTchManageList() {
      console.log(1)
      this.loading1 = true;
      let params = {
        pageSize: this.pageSize1,
        currentPage: this.currentPage1,
        role:"teacher"
      };
      setTimeout(() => {
        this.$http.get("/getUserManageList", { params }).then(res => {
          this.tchManageList = res.data.content;
          this.total1 = res.data.total;
          this.loading1 = false;
        });
      }, 500);
    },

    //查詢學生列表
    getstuManageList() {
      console.log(2)
      // this.loading2 = true;
      // let params = {
      //   pageSize: this.pageSize2,
      //   currentPage: this.currentPage2,
      //   role:"student"
      // };
      // setTimeout(() => {
      //   this.$http.get("", { params }).then(res => {
      //     this.stuManageList = res.data.content;
      //     this.total2 = res.data.total;
      //     this.loading2 = false;
      //   });
      // }, 500);
    },

    //切换教師分页时触发
    currentChange1(val) {
      this.currentPage1 = val;
      this.getTchManageList();
    },
    //切换學生分页时触发
    currentChange2(val) {
      this.currentPage2 = val;
      this.getstuManageList();
    },
    
    handleClick() {
      if(this.activeName == 'tchManage'){
        this.getTchManageList();
      }else if(this.activeName == 'stuManage'){
        this.getstuManageList();
      }
    },

    //获取用户信息列表
    getTestPaperList() {},
  },
};
</script>

<style lang="less" scoped>
</style>


