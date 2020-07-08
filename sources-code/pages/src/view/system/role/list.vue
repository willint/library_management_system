<template>
  <div class="table" style="height:100%;">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 角色管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container" style="min-height:100%;">
      <div class="handle-box">
        <el-input v-model="query.name" placeholder="角色名称" class="handle-input mr10"></el-input>
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
        <el-table-column prop="name" label="角色名称" width="120"></el-table-column>
        <el-table-column prop="remarks" label="说明"></el-table-column>
        <el-table-column prop="createDate" label="创建时间" width="160" :formatter="formatter"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              v-show="permissions.menu"
              type="text"
              icon="el-icon-setting"
              @click="handlePer(scope.$index, scope.row)"
              v-if="scope.row.isSys != '1'"
            >权限</el-button>
            <el-button
              v-show="permissions.edit"
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.$index, scope.row)"
              v-if="scope.row.isSys != '1'"
            >编辑</el-button>

            <el-button
              v-show="permissions.del"
              type="text"
              icon="el-icon-delete"
              class="red"
              @click="handleDelete(scope.$index, scope.row)"
              v-if="scope.row.isSys != '1'"
            >删除</el-button>
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
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="角色名称" prop="remarks">
          <el-input type="textarea" v-model="form.remarks"></el-input>
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

    <el-dialog
      id="permissions"
      :close-on-click-modal="false"
      :title="form.title"
      :visible.sync="perVisible"
      width="40%"
    >
      <el-tree
        ref="tree"
        :data="menuTree"
        show-checkbox
        node-key="id"
        default-expand-all
        :default-checked-keys="myMenu"
        :props="defaultProps"
        v-loading="loading2"
      ></el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button v-show="!loading2" @click="perVisible = false">取 消</el-button>
        <el-button v-show="!loading2" type="primary" @click="addRoleMenu">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import apiService from "../../../service/common/apiService";
import common from "../../../service/common/common";
export default {
  name: "basetable",
  data() {
    return {
      loading: null,
      loading2: true,
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
      perVisible: false,
      permissions: {
        del: common.permCheck("role:del"),
        edit: common.permCheck("role:edit"),
        add: common.permCheck("role:add"),
        menu: common.permCheck("role:menu")
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
        name: "",
        remarks: ""
      },
      idx: -1,
      rules: {
        name: [{ required: true, message: "角色名称", trigger: "blur" }]
      },
      menuTree: [],
      myMenu: [],
      defaultProps: {
        children: "children",
        label: "label"
      }
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
  methods: {
    showLoading() {
      this.loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
    },
    // 分页导航
    handleCurrentChange(val) {
      this.tableData.page = val;
      this.getData();
    },
    // 获取 easy-mock 的模拟数据
    getData() {
      var _this = this;
      _this.showLoading();
      apiService.pageList(
        "/system/role/pageList",
        _this.tableData.page,
        10,
        _this.query,
        function(data) {
          if (_this.loading) {
            _this.loading.close();
          }
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
    initMyMenu(menuTree, myMenu){
      if(menuTree == null || menuTree.length == 0 || myMenu == null || myMenu.length == 0){
        return ;
      }
      var _this = this;
      menuTree.forEach(element => {
        if((element.children == null || element.children.length == 0) && myMenu.indexOf(element.id) > -1){//没有子节点切
          _this.myMenu.push(element.id);
        }else{
          _this.initMyMenu(element.children, myMenu);
        }
      });
    },
    handlePer(index, row) {
      var _this = this;
      _this.loading2 = true;
      _this.menuTree = [];
      _this.myMenu = [];
      this.perVisible = true;
      this.idx = index;
      apiService.post(
        "/system/menu/roleMenuAndAllMenu",
        { roleId: row.id },
        function(data) {
          _this.loading2 = false;
          if (apiService.isOK(data)) {
            //成功
            _this.menuTree = data.body.menuTree;
            _this.initMyMenu(_this.menuTree, data.body.myMenu);
          } else {
            _this.$message.error(apiService.getMsg(data));
          }
        }
      );
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
        remarks: row.remarks,
        name: row.name,
        title: "编辑"
      };
      if (this.$refs["form"]) {
        this.$refs["form"].clearValidate();
      }
      this.editVisible = true;
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
      this.$refs["form"].validate(valid => {
        if (valid) {
          _this.showLoading();
          apiService.post("/system/role/saveOrUpdate", _this.form, function(
            data
          ) {
            if (_this.loading) {
              _this.loading.close();
            }
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
      _this.showLoading();
      apiService.post("/system/role/del", { id: item.id }, function(data) {
        if (_this.loading) {
          _this.loading.close();
        }
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
    addRoleMenu() {
      var _this = this;
      var halfCheckedKeys = this.$refs.tree.getHalfCheckedKeys(); //半选中节点
      var currentKey = this.$refs.tree.getCheckedKeys(); //选中节点
      var noneKey = new Array();
      noneKey = common.concat(noneKey, halfCheckedKeys);
      noneKey = common.concat(noneKey, currentKey);
      const item = this.tableData.results[this.idx];
      _this.showLoading();
      apiService.post(
        "/system/menu/addRoleMenu",
        { roleId: item.id, ids: noneKey },
        function(data) {
          if (_this.loading) {
            _this.loading.close();
          }
          if (apiService.isOK(data)) {
            //成功
            _this.perVisible = false;
            _this.$message.success("操作成功");
          } else {
            _this.$message.error(apiService.getMsg(data));
          }
          _this.delVisible = false;
        }
      );
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
</style>
