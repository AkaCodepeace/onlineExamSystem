<template>
  <div class="userManage">
    <TopTab title="用户管理">
        
      <div class="content">
        <el-tabs
          v-model="activeName"
          type="border-card"
          @tab-click="handleClick">
      
        <!-- 教师表格 -->
        <el-tab-pane label="教师信息管理" name="tchManage">

          <!-- 查询部分 -->
          <div class="list-header"  >
            <div class="query">
              <el-input v-model="tchKeyword" size="small" placeholder="请输入教师编号或者教师名称" prefix-icon="el-icon-search"></el-input>
              <el-button type="primary" size="small" @click="getTchManageList('teacher',tchKeyword)">查询</el-button>
            </div>
          </div>

          <!-- 表格部分 -->
          <div class="table">
              <el-table
                :data="tchManageList"
                style="width: 100%"
                v-loading="loading1"
              >
                <el-table-column label="用戶id" width="110">
                  <template slot-scope="scope"
                    >U{{scope.row.userId}}
                  </template>
                </el-table-column>
                <el-table-column label="用戶名" min-width="150">
                  <template slot-scope="scope"
                    >{{scope.row.userName}}
                  </template>
                </el-table-column>
                <el-table-column label="性別" width="150">
                  <template slot-scope="scope"
                    >{{scope.row.sex}}
                  </template>
                </el-table-column>
                <el-table-column label="手机号码" width="200">
                  <template slot-scope="scope"
                    >{{scope.row.phone}}
                  </template>
                </el-table-column>
                <el-table-column label="邮箱" width="200">
                  <template slot-scope="scope"
                    >{{scope.row.email}}
                  </template>
                </el-table-column>
                <el-table-column label="身份" width="150">
                  <template slot-scope="scope"
                    >{{scope.row.work}}
                  </template>
                </el-table-column>
                <el-table-column label="密碼" width="150">
                  <template slot-scope="scope"
                    >{{scope.row.password}}
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="300px" fixed="right">
                  <template slot-scope="scope">
                    <el-button
                      type="primary"
                      size="mini"
                      plain
                      @click="getEditDialog(scope.$index,scope.row,'teacher')"
                      >编辑</el-button
                    >
                    
                    <el-button
                      type="danger"
                      size="mini"
                      plain
                      @click="getDeleteDialog(scope.row.userId,scope.$index,'teacher')"
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

          <!-- 学生表格 -->
        <el-tab-pane label="学生信息管理" name="stuManage">

          <!-- 查询部分 -->
          <div class="list-header"  >
            <div class="query">
              <el-input v-model="stuKeyword" size="small" placeholder="请输入学生编号或者学生名称" prefix-icon="el-icon-search"></el-input>
              <el-button type="primary" size="small" @click="getstuManageList('student',stuKeyword)">查询</el-button>
            </div>
          </div>

          <!-- 表格部分 -->
          <div class="table">
              <el-table
                :data="stuManageList"
                style="width: 100%"
                v-loading="loading2"
              >
                <el-table-column label="用戶id" width="110">
                  <template slot-scope="scope"
                    >U{{scope.row.userId}}
                  </template>
                </el-table-column>
                <el-table-column label="用戶名" min-width="150">
                  <template slot-scope="scope"
                    >{{scope.row.userName}}
                  </template>
                </el-table-column>
                <el-table-column label="性別" width="150">
                  <template slot-scope="scope"
                    >{{scope.row.sex}}
                  </template>
                </el-table-column>
                <el-table-column label="手机号码" width="200">
                  <template slot-scope="scope"
                    >{{scope.row.phone}}
                  </template>
                </el-table-column>
                <el-table-column label="邮箱" width="200">
                  <template slot-scope="scope"
                    >{{scope.row.email}}
                  </template>
                </el-table-column>
                <el-table-column label="身份" width="150">
                  <template slot-scope="scope"
                    >{{scope.row.work}}
                  </template>
                </el-table-column>
                <el-table-column label="密碼" width="150">
                  <template slot-scope="scope"
                    >{{scope.row.password}}
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="300px" fixed="right">
                  <template slot-scope="scope">
                    <el-button
                      type="primary"
                      size="mini"
                      plain
                      @click="getEditDialog(scope.$index,scope.row,'student')"
                      >编辑</el-button
                    >
                    <el-button
                      type="danger"
                      size="mini"
                      plain
                      @click="getDeleteDialog(scope.row.userId,scope.$index,'student')"
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


         <!-- 编辑弹窗 -->
        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" :append-to-body="true">
          <el-form :model="form" :rules="rules" ref="userForm">
            <el-form-item label="用户名" :label-width="formLabelWidth" prop="name">
              <el-input v-model="form.userName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="邮 箱" :label-width="formLabelWidth" prop="email">
              <el-input v-model="form.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" :label-width="formLabelWidth" prop="phone">
              <el-input v-model="form.phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
              <el-input v-model="form.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
              <el-radio v-model="form.sex" label="男">男</el-radio>
              <el-radio v-model="form.sex" label="女">女</el-radio>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateUserAndPassword('userForm')">确 定</el-button>
          </div>
        </el-dialog>
          <!-- 删除弹框 -->
        <el-dialog  
          title="提示"
          :visible.sync="dialogVisible"
          width="30%"
          :append-to-body="true"
        >
          <span>是否确认删除用户U{{userId}}？</span>
          <span slot="footer" class="dialog-footer" >
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="deleteUserManage(userId)">确 定</el-button>
          </span>
        </el-dialog>  

        </el-tabs>
      </div>
    </TopTab>
  </div>
</template>

<script>
import TopTab from "../../components/TopTab.vue";
import "../../assets/less/my_tab.less";
import "@/assets/less/main/userManage.less";
export default {
  name: "UserManage",
  components: {
    TopTab,
  },
  data() {
    var checkName = (rule, value, callback) => {
      var reg = /^[\u4e00-\u9fa5\w]{3,16}$/;
      if (!reg.test(value)) {
        callback(new Error("用户名只能是3-16位汉字、字母、数字、下划线"));
      } else {
        callback();
      }
    };
    var checkPhone = (rule, value, callback) => {
      if (value == "") {
        callback();
      }
      var reg =
        /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
      if (!reg.test(value)) {
        callback(new Error("请输入正确的手机号码"));
      } else {
        callback();
      }
    };
    var checkEmail = (rule, value, callback) => {
      if (value == "") {
        callback();
      }
      var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
      if (!reg.test(value)) {
        callback(new Error("请输入正确的邮箱地址"));
      } else {
        callback();
      }
    };
    var checkPassword = (rule, value, callback) => {
      var reg = /^\w{6,16}$/;
      if (!reg.test(value)) {
        callback(new Error("密码只能是6-16位字母、数字、下划线"));
      } else {
        callback();
      }
    };
    return {
      tchKeyword:'',
      stuKeyword:'',
      work:'',
      userId:0,
      index: 0,
      userData: [],
      dialogVisible:false,//删除窗口
      dialogFormVisible: false,//编辑窗口
      role: "",
      activeName: "tchManage",
      //教師列表
      tchManageList: [],
      //學生
      stuManageList: [],

      // 教師分页
      loading1: false,
      currentPage1: 1,
      pageSize1: 10,
      total1: null,

      //學生分頁
      loading2: false,
      currentPage2: 1,
      pageSize2: 10,
      total2: null,

      form: {
        userName: "",
        email: "",
        phone: "",
        password: "",
        sex: "",
      },
      formLabelWidth: "120px",
      rules: {
        name: [{ validator: checkName, trigger: "change" }],
        phone: [{ validator: checkPhone, trigger: "blur" }],
        email: [{ validator: checkEmail, trigger: "blur" }],
        password: [{ validator: checkPassword, trigger: "change" }],
      },
    };
  },
  created() {
       this.getTchManageList("teacher");
  },
  methods: {
    updateUserAndPassword(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let request = {
            userId: this.userData.userId,
            work: this.userData.work,
            userName: this.form.userName,
            email: this.form.email,
            phone: this.form.phone,
            password: this.form.password,
            sex: this.form.sex,
          };
          this.$http.put("/updateUserAndPassword", request).then((res) => {
            if (res.code == 200) {
              this.$message.success("修改成功");
              this.dialogFormVisible = false;
              if(this.role == "teacher"){
                this.$set(this.tchManageList, this.index, res.data);
              }else if(this.role == "student"){
                this.$set(this.stuManageList, this.index, res.data);
              }
              
            }
          });
        } else {
          this.dialogFormVisible = true;
          this.$message.error("修改失败");
          console.log("error submit!!");
          return false;
        }
      });
    },
    //打开编辑弹窗
    getEditDialog(index, row,role) {
      this.role = role
      this.index = index;
      let params = {
        userId: row.userId,
      };
      this.$http.get("/getUserMessageById", { params }).then((res) => {
          (this.userData = res.data),
          (this.form.userName = this.userData.userName),
          (this.form.email = this.userData.email),
          (this.form.phone = this.userData.phone),
          (this.form.password = this.userData.password),
          (this.form.sex = this.userData.sex);
        this.dialogFormVisible = true;
      });
    },

    //删除用户
    deleteUserManage(id) {
      console.log(id)
      let params = {
        userId: id,
      };
      this.$http.delete("/deleteUserManageById", { params }).then((res) => {
        if (res.code == 200) {
          this.$message.success("删除成功");
           if(this.role == "teacher"){
                this.tchManageList.splice(this.index,1);
                this.total1 = this.total1-1
            }else if(this.role == "student"){
                this.stuManageList.splice(this.index,1);
                this.total2 = this.total2-1
            }
        } else {
          this.$message.error("删除失败");
        }
        this.dialogVisible=false;
      });
    },

    //打开删除弹窗
    getDeleteDialog(id,index,role){
      console.log(index)
      this.role= role
      this.index=index
      this.userId = id
      this.dialogVisible=true
    },

    //查詢教師列表
    getTchManageList(user) {
      this.loading1 = true;
      let params = {
        pageSize: this.pageSize1,
        currentPage: this.currentPage1,
        keyword: this.tchKeyword,
        role: user,
      };
      setTimeout(() => {
        this.$http.get("/getUserManageList", { params }).then((res) => {
          this.tchManageList = res.data.content;
          this.total1 = parseInt(res.data.total);
          this.loading1 = false;
        });
      }, 500);
    },

    //查詢學生列表
    getstuManageList(user) {
      this.loading2 = true;
      let params = {
        pageSize: this.pageSize2,
        currentPage: this.currentPage2,
        keyword:this.stuKeyword,
        role: user,
      };
      setTimeout(() => {
        this.$http.get("/getUserManageList", { params }).then((res) => {
          this.stuManageList = res.data.content;
          this.total2 = parseInt(res.data.total);
          this.loading2 = false;
        });
      }, 500);
    },

    //切换教師分页时触发
    currentChange1(val) {
      this.currentPage1 = val;
      this.getTchManageList("teacher");
    },
    //切换學生分页时触发
    currentChange2(val) {
      this.currentPage2 = val;
      this.getstuManageList("student");
    },

    handleClick() {
      if (this.activeName == "tchManage") {
        this.role = "teacher";
        this.getTchManageList(this.role);
      } else if (this.activeName == "stuManage") {
        this.role = "student";
        this.getstuManageList(this.role);
      }
    },

    //获取用户信息列表
    getTestPaperList() {},
  },
};
</script>

<style lang="less" scoped>
</style>


