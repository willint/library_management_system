<template>
  <el-container style="height: 495px; border: 1px solid #eee">
    <el-aside width="200px" style="height: 495px; background-color: #FFF">
      <el-tree
        ref="tree"
        :data="menuTree"
        node-key="id"
        default-expand-all
        :expand-on-click-node="false"
        :props="defaultProps"
        v-loading="loading2"
        @node-click="handleNodeClick"
      ></el-tree>
    </el-aside>

    <el-container>
      <el-header style="text-align: left; font-size: 12px;margin-left: 30px;">
        <div>
          <el-button type="primary" round @click="handleAddSub">添加子节点</el-button>
          <el-button round v-show="disabled && menuForm.id != '0'" @click="handleEdit">编辑</el-button>
          <el-button
            type="success"
            round
            v-show="!disabled && menuForm.id != '0'"
            @click="saveEdit"
            v-loading.fullscreen.lock="fullscreenLoading"
          >保存</el-button>
          <el-button type="danger" v-show="menuForm.id != '0'" round @click="handleDel">删除</el-button>
        </div>
      </el-header>
      <el-main>
        <el-form
          :model="menuForm"
          status-icon
          :rules="rules"
          ref="menuForm"
          label-width="100px"
          style="width:500px;"
          class="demo-ruleForm"
        >
          <el-form-item label="标题" prop="title">
            <el-input v-model="menuForm.title" :disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="访问路由">
            <el-input v-model="menuForm.index" :disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="权限" prop="permission">
            <el-input v-model="menuForm.permission" :disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="图标" prop="icon">
          <el-input v-model="menuForm.icon" :disabled="disabled"></el-input>
        </el-form-item>
          <el-form-item label="排序" prop="seq">
            <el-input v-model="menuForm.seq" :disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="state">
            <el-radio-group v-model="menuForm.state" :disabled="disabled">
              <el-radio-button label="有效"></el-radio-button>
              <el-radio-button label="无效"></el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="菜单类型" prop="type">
            <el-radio-group v-model="menuForm.type" :disabled="disabled">
              <el-radio-button label="菜单"></el-radio-button>
              <el-radio-button label="按钮"></el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
    <el-dialog title="删除确认" :visible.sync="delVisible" width="30%" center>
      <span>是否确认删除该节点及该节点下的所有子节点？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="delMenu">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 新增子节点弹出框 -->
    <el-dialog title="新增" :visible.sync="editVisible" width="40%">
      <el-form
        :model="subForm"
        status-icon
        :rules="rules"
        ref="subForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="上级菜单" prop="parentName">
          <el-input v-model="subForm.parentName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="subForm.title"></el-input>
        </el-form-item>
        <el-form-item label="访问路由">
          <el-input v-model="subForm.index"></el-input>
        </el-form-item>
        <el-form-item label="权限" prop="permission">
          <el-input v-model="subForm.permission"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="subForm.icon"></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="seq">
          <el-input v-model="subForm.seq"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-radio-group v-model="subForm.state">
            <el-radio-button label="有效"></el-radio-button>
            <el-radio-button label="无效"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单类型" prop="type">
          <el-radio-group v-model="subForm.type">
            <el-radio-button label="菜单"></el-radio-button>
            <el-radio-button label="按钮"></el-radio-button>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveSub" v-loading.fullscreen.lock="fullscreenLoading">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>
<script>
import apiService from "../../../service/common/apiService";
import common from "../../../service/common/common";
export default {
  name: "basetable",
  inject: ["reload"],
  data() {
    return {
      fullscreenLoading: false,
      disabled: true,
      delVisible: false,
      editVisible: false,
      loading: null,
      menuForm: { id: "0", title: "顶级菜单" },
      subForm: {},
      statusMap: { "0": "无效", "1": "有效" },
      statusMap1: { "无效": "0", "有效": "1" },
      typeMap: { "0": "菜单", "1": "按钮" },
      typeMap1: { "菜单": "0", "按钮": "1" },
      loading2: true,
      menuTree: [{ id: "0", label: "顶级菜单", children: [] }],
      menuMap: { "0": {} },
      defaultProps: {
        children: "children",
        label: "label"
      },
      rules: {
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        permission: [
          { required: true, message: "请输入权限", trigger: "blur" }
        ],
        state: [{ required: true, message: "请选择状态", trigger: "blur" }],
        type: [{ required: true, message: "请选择类型", trigger: "blur" }],
        seq: [{ required: true, message: "请输入排序", trigger: "blur" }]
      },
      treeIds: []
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    // 获取 easy-mock 的模拟数据
    init() {
      var _this = this;
      _this.menuTree[0].children = [];
      apiService.post("/system/menu/allMenu", {}, function(data) {
        _this.loading2 = false;
        if (apiService.isOK(data)) {
          //成功
          _this.menuTree[0].children = data.body.lists;
          _this.menuMap = data.body.menuMap;
        } else {
          _this.$message.error(apiService.getMsg(data));
        }
      });
    },
    handleNodeClick(data) {
      if (this.$refs["menuForm"]) {
        this.$refs["menuForm"].clearValidate();
      }
      this.disabled = true;
      if (data.id == "0") {
        this.menuForm = { id: "0", title: "顶级菜单" };
        return;
      }
      var map = this.menuMap[data.id];
      if (map != null && map.state != null && this.statusMap[map.state] != undefined) {
        map.state = this.statusMap[map.state];
      }
      if (map != null && map.type != null && this.typeMap[map.type] != undefined) {
        map.type = this.typeMap[map.type];
      }

      this.menuForm = map;
    },
    handleEdit() {
      this.disabled = false;
    },
    handleDel() {
      this.delVisible = true;
    },
    handleAddSub() {
      if (this.$refs["subForm"]) {
        this.$refs["subForm"].clearValidate();
      }
      this.subForm = { state: "有效", type: "菜单" };
      this.subForm.parentName = this.menuForm.title;
      this.subForm.parent = this.menuForm.id;
      this.editVisible = true;
    },
    showLoading() {
      this.loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
    },
    saveEdit() {
      var _this = this;

      _this.$refs["menuForm"].validate(valid => {
        if (valid) {
          _this.showLoading();
          _this.menuForm.state = _this.statusMap1[_this.menuForm.state];
          _this.menuForm.type = _this.typeMap1[_this.menuForm.type];
          apiService.post("/system/menu/addMenu", _this.menuForm, function(
            data
          ) {
            if (_this.loading) {
              _this.loading.close();
            }

            if (apiService.isOK(data)) {
              //成功
              _this.editVisible = false;
              _this.$message.success("操作成功");
              _this.reload();
            } else {
              _this.$message.error(apiService.getMsg(data));
            }
          });
        }
      });
    },
    saveSub() {
      var _this = this;
      this.$refs["subForm"].validate(valid => {
        if (valid) {
          _this.showLoading();
          _this.subForm.state = _this.statusMap1[_this.subForm.state];
          _this.subForm.type = _this.typeMap1[_this.subForm.type];
          apiService.post("/system/menu/addMenu", _this.subForm, function(
            data
          ) {
            if (_this.loading) {
              _this.loading.close();
            }

            if (apiService.isOK(data)) {
              //成功
              _this.editVisible = false;
              _this.$message.success("操作成功");
              _this.reload();
            } else {
              _this.$message.error(apiService.getMsg(data));
            }
          });
        }
      });
    },
    findTreeId(id, list) {
      var _this = this;
      list.forEach(function(element) {
        var myId = id;
        if (id === element.parent) {
          _this.treeIds.push(element.id);
          myId = element.id;
        }
        if (element.children != null && element.children.length > 0) {
          _this.findTreeId(myId, element.children);
        }
      });
    },
    delMenu() {
      var _this = this;
      _this.treeIds = [_this.menuForm.id];
      _this.findTreeId(_this.menuForm.id, _this.menuTree);
      _this.showLoading();
      apiService.post("/system/menu/delMenu", { ids: _this.treeIds }, function(
        data
      ) {
        if (_this.loading) {
          _this.loading.close();
        }
        if (apiService.isOK(data)) {
          //成功
          _this.editVisible = false;
          _this.$message.success("操作成功");
          _this.reload();
        } else {
          _this.$message.error(apiService.getMsg(data));
        }
      });
    }
  }
};
</script>