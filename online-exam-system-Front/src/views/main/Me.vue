<template>
  <div class="content-bg me">
    <div class="tab-title">
      <div class="tabTitle-item active">个人中心</div>
    </div>
    <div class="tab-item">
			<div class="uploadPhoto">
				<div class="title">上传头像 :</div>
				<el-upload class="avatar-uploader" 
				:action="actionURL" 
				:headers="{ token }"
				:file-list="fileList"
				:limit="1"
				:show-file-list="true" 
        :auto-upload="autoUpload"
				:on-exceed="handleAvatarExceed"
				:on-change="handleAvatarChange"
				:on-remove="handleAvatarRemove"
				:on-success="handleAvatarSuccess" 
				:before-upload="beforeAvatarUpload">
					<img v-if="imageUrl" :src="imageUrl" class="avatar">
					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
			</div>
      
      <div class="userInfo">
        <div class="item">
          <span class="title">用户Id :</span>
          <div class="con">U{{userData.userId}}</div>
        </div>
        <div class="item">
          <span class="title">用户名 :</span>
          <div class="con">{{userData.userName}}</div>
        </div>
        <div class="item">
          <span class="title">邮 箱 :</span>
          <div class="con">{{userData.email}}</div>
        </div>
        <div class="item">
          <span class="title">手机号码 :</span>
          <div class="con">{{userData.phone}}</div>
        </div>
        <div class="item">
          <span class="title">性 别 :</span>
          <div class="con sex">{{userData.sex}}</div>
        </div>
        <div class="item">
          <span class="title">创建时间 :</span>
          <div class="con">{{userData.createDate}}</div>
        </div>
        <div class="item">
          <span class="title">身 份 :</span>
          <div class="con work">{{userData.work}}</div>
        </div>
        <div class="item-button">
          <el-button type="primary" @click="getUserData()&(dialogFormVisible = true)">编辑信息</el-button>
        </div>
      </div>   
     <el-dialog title="个人信息" :visible.sync="dialogFormVisible" width="30%">
       <el-form :model="form" :rules="rules" ref="meForm">
         <el-form-item label="用户名" :label-width="formLabelWidth" prop="name">
           <el-input v-model="form.userName" autocomplete="off"></el-input>
         </el-form-item>
      
        <el-form-item label="邮 箱" :label-width="formLabelWidth" prop="email">
           <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        
        <el-form-item label="手机号码" :label-width="formLabelWidth" prop="phone">
           <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>

         <el-form-item label="性别" :label-width="formLabelWidth" prop="sex">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
        </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateUser('meForm')">确 定</el-button>
        </div>
      </el-dialog>

  
    </div>
    
  </div>
</template> 

<script>
// import "../../assets/less/main/changeStatus.less"
import "@/assets/less/my_tab.less";
import "@/assets/less/main/me.less";

export default {
  name: "Me",
  data() {
    var checkName = (rule, value, callback) => { 
        var reg = /^[\u4e00-\u9fa5\w]{3,16}$/;
        if(!reg.test(value)){
            callback(new Error('用户名只能是3-16位汉字、字母、数字、下划线'));
        }else{
            callback()
        }
    };
    var checkPhone = (rule, value, callback) => { 
        if(value==""){
            callback()
        }
        var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
        if(!reg.test(value)){
            callback(new Error('请输入正确的手机号码'));
        }else{
            callback()
        }
    };
    var checkEmail = (rule, value, callback) => { 
        if(value==""){
            callback()
        }
        var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        if(!reg.test(value)){
            callback(new Error('请输入正确的邮箱地址'));
        }else{
            callback()
        }
    };
    return {
      dialogVisible: false,
      userData: [],
			imageUrl: '',
			token: "",
			fileList: [],
      autoUpload: true,
      actionURL: this.$baseURL+'/upload',
      dialogFormVisible: false,
      form: {
        userName: '',
        email: '',
        phone: '',
        sex: ''
      },
      formLabelWidth: '120px',
      rules:{
          name:[
              { validator: checkName, trigger: 'change' },
          ],
          phone:[
              { validator: checkPhone, trigger: 'blur' },
          ],
          email:[
              { validator: checkEmail, trigger: 'blur' },
          ]
      },
    };
  },
  created() {
    this.user_id = this.$root.user_id;
		this.token = localStorage.getItem("_token")
    this.getUserData();
  },

  methods: {
    updateUser(formName){
      this.$refs[formName].validate((valid) => {
          if (valid) {
             let request = {
               userId:this.userData.userId,
               userName:this.form.userName,
               email:this.form.email,
               phone:this.form.phone,
               sex:this.form.sex
              }
              this.$http.put('/updateUser',request).then(res =>{
                if(res.code == 200){
                  this.getUserData();
                  this.$message.success("修改成功");
                  this.dialogFormVisible = false
                }
              })
             
          }else{
            this.dialogFormVisible = true
            this.$message.error("修改失败");
            console.log('error submit!!');
            return false;
          }
      })
     
    },
    //路由跳转
    goRouter(path){
      this.$router.push(path)
    },
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    // 获取用户信息
    async getUserData() {
      const res = await this.$http.get(this.$api.user.getUserById)
      this.userData = res.data
      this.form.userName=this.userData.userName
      this.form.email=this.userData.email
      this.form.phone=this.userData.phone
      this.form.sex=this.userData.sex

      if(res.data.photo){
        this.fileList = [{
          name: res.data.photoName,
          url: this.$imageBaseUrl + res.data.photo
        }]
        this.imageUrl = this.$imageBaseUrl + res.data.photo
      }
    },
    //上传成功
    handleAvatarSuccess(res, file) {
      this.$store.commit('setUserPhoto',res.data.fileUrl);
      this.imageUrl = URL.createObjectURL(file.raw);
      let params = {
        userId: this.userData.userId,
        photo: res.data.fileUrl,
        photoName: res.data.fileName
      }
      this.$http.put(this.$api.user.userEdit,params).then(res =>{
        if(res.code === 200){
          this.$message.success('上传成功')
        }
      })
    },
    //删除
		async handleAvatarRemove(res, file){
      console.log(res,file);
      let uploadParams
			if(res.response){
        uploadParams = { fileName: res.response.data.fileUrl }
			}else{
        uploadParams = { fileName: this.userData.photo }
      }

      //删除图片
      let result = await this.$http.delete("/upload/delete", {params:uploadParams})

      //删除用户图片数据
      if(result.code === 200){
        this.imageUrl = ''
        let params = {
          userId: this.userData.userId,
          photo: '',
          photoName: ''
        }
        this.$http.put(this.$api.user.userEdit,params).then(res =>{
          if(res.code === 200){
            this.$message.success('删除成功')
            this.$store.commit('setUserPhoto',null);
          }
        })
      }

      if(this.autoUpload === false) this.imageUrl = ''
			
		},



		handleAvatarExceed(file,fileList){
			this.$message("图片只能上传一张,请删除图片再上传");
		},
    handleAvatarChange(file, fileList){
      console.log(123);
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg" || file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 和 png 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 10MB!");
      }

      return isJPG && isLt2M;
    },
  },
};
</script>

<style lang="less" scoped>



</style>