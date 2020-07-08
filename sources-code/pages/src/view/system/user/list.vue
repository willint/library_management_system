<template>
  <div class="table" style="height:100%;">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 用户管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container" style="min-height:100%;">
      <div class="handle-box">
        <el-input v-model="query.phone" placeholder="登录账号" class="handle-input mr10"></el-input>
        <el-input v-model="query.user_name" placeholder="姓名" class="handle-input mr10"></el-input>

        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button v-show="permissions.add" type="primary" icon="add" @click="handleAdd">新增</el-button>
      </div>
      <el-table
        :data="tableData.results"
        border
        class="table"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column prop="username" label="姓名" width="120"></el-table-column>
        <el-table-column prop="phone" label="登录账号" width="110"></el-table-column>
        <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
        <el-table-column prop="status" label="状态" width="60" :formatter="statusFormatter"></el-table-column>
        <el-table-column prop="createDate" label="创建时间" :formatter="formatter"></el-table-column>
        <el-table-column label="操作" width="240" align="center">
          <template slot-scope="scope">
            <el-button
              v-show="permissions.edit && scope.row.isSys != '1'"
              type="text"
              icon="el-icon-view"
              @click="handleRestPwd(scope.$index, scope.row)"
            >密码</el-button>
            <el-button
              v-show="permissions.edit && scope.row.isSys != '1'"
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button>

            <el-button
              v-show="permissions.del && scope.row.isSys != '1'"
              type="text"
              icon="el-icon-delete"
              class="red"
              @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button>
            <el-button
              v-show="permissions.userRole && scope.row.isSys != '1'"
              type="text"
              icon="el-icon-setting"
              @click="handleUserRole(scope.$index, scope.row)"
            >角色</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          :current-page="tableData.page"
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :total="tableData.total"
        ></el-pagination>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog :title="form.title" :visible.sync="editVisible" width="40%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="姓名" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="登录账号" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="筛选状态" class="handle-select mr10">
            <el-option key="1" label="有效" value="1"></el-option>
            <el-option key="2" label="无效" value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="saveEdit"
          v-loading.fullscreen.lock="fullscreenLoading"
        >确 定</el-button>
      </span>
    </el-dialog>
    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>

    <!--权限列表-->
    <el-dialog :title="form.title" :visible.sync="userRoleVisible" width="60%">
      <div class="drag-box">
        <div class="drag-box-item">
          <div class="item-title">已拥有角色</div>
          <draggable @remove="removeHandle" :options="dragOptions">
            <transition-group tag="div" id="myRole" class="item-ul">
              <div
                v-for="item in myRole"
                class="drag-list"
                :key="item.id"
                :id="item.id"
              >{{item.name}}</div>
            </transition-group>
          </draggable>
        </div>
        <div class="drag-box-item">
          <div class="item-title">未拥有角色</div>
          <draggable @remove="removeHandle" :options="dragOptions">
            <transition-group tag="div" id="otherRole" class="item-ul">
              <div
                v-for="item in otherRole"
                class="drag-list"
                :key="item.id"
                :id="item.id"
              >{{item.name}}</div>
            </transition-group>
          </draggable>
        </div>
      </div>
    </el-dialog>

    <!--权限列表-->
    <el-dialog
      title="密码重置"
      :visible.sync="restPwdVisible"
      :close-on-click-modal="false"
      width="40%"
    >
      <el-form ref="restpwdform" :model="pwdform" :rules="restrules" label-width="100px">
        <el-form-item label="新密码" prop="password">
          <el-input type="password" v-model="pwdform.password" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="pwdform.checkPass" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="restPwdVisible = false">取 消</el-button>
        <el-button type="primary" @click="restpwd" v-loading.fullscreen.lock="fullscreenLoading">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import apiService from "../../../service/common/apiService";
import common from "../../../service/common/common";
import draggable from "vuedraggable";
export default {
  name: "basetable",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "" || value == undefined) {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.pwdform.password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      dragOptions: {
        animation: 120,
        scroll: true,
        group: "sortlist",
        ghostClass: "ghost-style"
      },
      myRole: [],
      otherRole: [],
      tableData: {
        total: 0,
        page: 1,
        results: []
      },
      cur_page: 1,
      multipleSelection: [],
      select_word: "",
      del_list: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      userRoleVisible: false,
      permissions: {
        del: common.permCheck("user:del"),
        edit: common.permCheck("user:edit"),
        add: common.permCheck("user:add"),
        userRole: common.permCheck("role:userRole")
      },
      query: {
        phone: "",
        user_name: ""
      },
      fullscreenLoading: false,
      stateMap: {
        "1": "有效",
        "0": "无效"
      },
      form: {
        title: "",
        name: "",
        date: "",
        address: "",
        status: "1"
      },
      idx: -1,
      rules: {
        phone: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        username: [{ required: true, message: "请输入密码", trigger: "blur" }]
      },
      restrules: {
        password: [
          { required: true, message: "请输入新密码", trigger: "blur" }
        ],
        checkPass: [{ validator: validatePass, trigger: "blur" }]
      },
      pwdform: {},
      restPwdVisible: false
    };
  },
  created() {
    this.getData();
  },
  computed: {
    data() {
      return this.tableData;
    }
  },
  components: {
    draggable
  },
  methods: {
    // 分页导航
    handleCurrentChange(val) {
      this.tableData.page = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      var _this = this;
      const loading = _this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
      apiService.pageList(
        "/system/user/pageList",
        _this.tableData.page,
        10,
        _this.query,
        function(data) {
          loading.close();
          _this.tableData = data;
        }
      );
    },
    search() {
      this.is_search = true;
      this.handleCurrentChange(1);
    },
    formatter(row, column) {
      return common.formatter(row.createDate, "yyyy-MM-dd hh:mm:ss");
    },
    statusFormatter(row, column) {
      return this.stateMap[row.status];
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    handleAdd() {
      this.form = {
        title: "新增",
        status: "1"
      };
      if (this.$refs["form"]) {
        this.$refs["form"].clearValidate();
      }
      this.editVisible = true;
    },
    handleEdit(index, row) {
      this.idx = index;
      this.form = {
        id: row.id,
        username: row.username,
        phone: row.phone,
        email: row.email,
        status: row.status,
        title: "编辑"
      };
      if (this.$refs["form"]) {
        this.$refs["form"].clearValidate();
      }
      this.editVisible = true;
    },
    handleUserRole(index, row) {
      var _this = this;
      _this.myRole = [];
      _this.otherRole = [];
      this.userRoleVisible = true;
      this.idx = index;
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
      apiService.post("/system/role/userRole", { userId: row.id }, function(
        data
      ) {
        loading.close();
        if (apiService.isOK(data)) {
          //成功
          _this.myRole = data.body.userRole;
          _this.otherRole = data.body.userNoRole;
        } else {
          _this.$message.error(apiService.getMsg(data));
        }
      });
    },

    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 保存编辑
    saveEdit() {
      var _this = this;
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
      this.$refs["form"].validate(valid => {
        if (valid) {
          apiService.post("/system/user/saveOrUpdate", _this.form, function(
            data
          ) {
            loading.close();
            if (apiService.isOK(data)) {
              //成功
              _this.handleCurrentChange(1);
              _this.editVisible = false;
              _this.$message.success("操作成功");
            } else {
              _this.$message.error(apiService.getMsg(data));
            }
          });
        } else {
          loading.close();
          return false;
        }
      });
    },
    // 确定删除
    deleteRow() {
      var _this = this;
      const item = this.tableData.results[this.idx];
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
      apiService.post("/system/user/del", { id: item.id }, function(data) {
        loading.close();
        if (apiService.isOK(data)) {
          //成功
          _this.handleCurrentChange(1);
          _this.$message.success("删除成功");
        } else {
          _this.$message.error(apiService.getMsg(data));
        }
        _this.delVisible = false;
      });
    },
    removeHandle(event) {
      var _this = this;
      const item = this.tableData.results[this.idx];
      if (event.from.id === "otherRole" && event.to.id === "myRole") {
        //添加
        apiService.post(
          "/system/role/addUserRole",
          { userId: item.id, id: event.item.id },
          function(data) {
            if (apiService.isOK(data)) {
              _this.$message.success(`添加成功 `);
            } else {
              _this.$message.error(apiService.getMsg(data));
            }
          }
        );
      } else if (event.from.id === "myRole" && event.to.id === "otherRole") {
        //移除
        apiService.post(
          "/system/role/delUserRole",
          { userId: item.id, id: event.item.id },
          function(data) {
            if (apiService.isOK(data)) {
              _this.$message.success(`移除成功 `);
            } else {
              _this.$message.error(apiService.getMsg(data));
            }
          }
        );
      }
    },
    handleRestPwd(index, row) {
      this.idx = index;
      this.pwdform = {};
      if (this.$refs["restpwdform"]) {
        this.$refs["restpwdform"].clearValidate();
      }
      this.restPwdVisible = true;
    },
    restpwd() {
      var _this = this;
      _this.$refs["restpwdform"].validate(valid => {
        const item = this.tableData.results[this.idx];
        if (valid) {
          _this.pwdform.id = item.id;
          apiService.post("/system/user/resetPwd", _this.pwdform, function(
            data
          ) {
            _this.fullscreenLoading = false;
            if (apiService.isOK(data)) {
              //成功
              _this.restPwdVisible = false;
              _this.$message.success("操作成功");
            } else {
              _this.$message.error(apiService.getMsg(data));
            }
          });
        }
      });
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}
.del-dialog-cnt {
  font-size: 16px;
  text-align: center;
}
.table {
  width: 100%;
  font-size: 14px;
}
.red {
  color: #ff0000;
}
.mr10 {
  margin-right: 10px;
}
.drag-box {
  display: flex;
  user-select: none;
}
.drag-box-item {
  flex: 1;
  max-width: 330px;
  min-width: 300px;
  background-color: #eff1f5;
  margin-right: 16px;
  border-radius: 6px;
  border: 1px #e1e4e8 solid;
}
.item-title {
  padding: 8px 8px 8px 12px;
  font-size: 14px;
  line-height: 1.5;
  color: #24292e;
  font-weight: 600;
}
.item-ul {
  padding: 0 8px 8px;
  height: 500px;
  overflow-y: scroll;
}
.item-ul::-webkit-scrollbar {
  width: 0;
}
.drag-list {
  border: 1px #e1e4e8 solid;
  padding: 10px;
  margin: 5px 0 10px;
  list-style: none;
  background-color: #fff;
  border-radius: 6px;
  cursor: pointer;
  -webkit-transition: border 0.3s ease-in;
  transition: border 0.3s ease-in;
}
.drag-list:hover {
  border: 1px solid #20a0ff;
}
.drag-title {
  font-weight: 400;
  line-height: 25px;
  margin: 10px 0;
  font-size: 22px;
  color: #1f2f3d;
}
.ghost-style {
  display: block;
  color: transparent;
  border-style: dashed;
}
</style>
