<template>
  <div class="table" style="height:100%;">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 图书管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container" style="min-height:100%;">
      <div class="handle-box">
        <!-- <el-input v-model="query.id" placeholder="图书编号" class="handle-input mr10"></el-input> -->
        <el-input v-model="query.name" placeholder="图书名称" class="handle-input mr10"></el-input>
        <el-input v-model="query.index_code" placeholder="图书索引" class="handle-input mr10"></el-input>
        <el-select v-model="query.category" class="handle-select mr10" placeholder="请选择图书类别">
          <el-option
            v-for="item in categoryList"
            :key="item.code"
            :label="item.name"
            :value="item.code"
          ></el-option>
        </el-select>

        <el-button type="primary" icon="search" @click="search">搜索</el-button>
        <el-button v-show="permissions.add" type="success" icon="add" @click="handleAdd">新增</el-button>
      </div>
      <el-table
        :data="tableData.results"
        border
        class="table"
        ref="multipleTable"
         highlight-current-row
        
      >
        <el-table-column type="index" width="50"></el-table-column>
        <!-- <el-table-column prop="id" label="图书ID" width="120" :show-overflow-tooltip="true"></el-table-column> -->
        <el-table-column prop="name" label="图书名称" width="120" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column
          prop="category"
          label="图书类别"
          width="120"
          :show-overflow-tooltip="true"
          :formatter="categoryFormatter"
        ></el-table-column>
        <el-table-column prop="indexCode" label="图书索引" width="120" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="author" label="作者" width="120" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="location" label="存放位置" :show-overflow-tooltip="true" width="120"></el-table-column>
        <el-table-column prop="operator" label="操作人" width="120" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column
          prop="updateTime"
          label="变更时间"
          :formatter="formatter"
          :show-overflow-tooltip="true"
          @current-change="handleCurrentChange"
        ></el-table-column>

        <el-table-column label="操作" width="240" align="center">
          <template slot-scope="scope">
            <el-button
              v-show="permissions.edit && scope.row.isSys != '1'"
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button>
            <el-button
              v-show="permissions.edit && scope.row.isSys != '1'"
              type="text"
              icon="el-icon-delete"
              class="red"
              @click="handleDel(scope.$index, scope.row)"
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
    <el-dialog :title="form.title" :visible.sync="editVisible" width="80%">
      <el-form :inline="true" ref="form" :model="form" :rules="formRules" label-width="100px">
        <el-form-item label="图书名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入图书名称"></el-input>
        </el-form-item>

        <el-form-item label="图书类别" prop="category">
          <!-- <el-input v-model="form.category" placeholder="请选择图书类别"></el-input> -->
          <el-select v-model="form.category" filterable placeholder="请选择图书类别">
            <el-option
              v-for="item in categoryList"
              :key="item.code"
              :label="item.name"
              :value="item.code"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图书索引" prop="indexCode">
          <el-input v-model="form.indexCode" placeholder="请输入图书索引"></el-input>
        </el-form-item>

        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者名"></el-input>
        </el-form-item>
        <el-form-item label="存放位置" prop="location">
          <el-input v-model="form.location" placeholder="请输入存放位置"></el-input>
        </el-form-item>

        <el-form-item label="图书摘要" prop="introduction">
          <el-input type="textarea" v-model="form.introduction"></el-input>
        </el-form-item>
        <el-form-item label="简介图片" prop="imgUrl">
          <el-upload
            accept="image/*"
            class="idcard"
            id="idcard1"
            :action="uploadAction"
            list-type="picture-card"
            :before-upload="beforeUpload1"
            :on-remove="onRemove1"
            :on-error="onError1"
            :data="uploadData"
            :on-success="onSuccess1"
            ref="upload1"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <!-- <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt />
          </el-dialog>-->
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
      <div class="del-dialog-cnt">是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import apiService from "../../../service/common/apiService";
import common from "../../../service/common/common";
import configInfo from "../../../service/common/configInfo";
import stateInfo from "../../../service/common/state";
import draggable from "vuedraggable";
export default {
  name: "basetable",
  data() {
    return {
      dragOptions: {
        animation: 120,
        scroll: true,
        group: "sortlist",
        ghostClass: "ghost-style"
      },
      tableData: {
        total: 0,
        page: 1,
        results: []
      },
      
      categoryList: stateInfo.categoryList,
      uploadData: { type: "book" },
      
      loginUserId: "",
      loginInfo: {},
      
      multipleSelection: [],
      
      del_list: [],
      showAgentName: "",
      is_search: false,
      editVisible: false,
      delVisible: false,
      stateVisible: false,
      chargeVisible: false,

      uploadAction: configInfo.serverUrl + "/images/upload",
      permissions: {
        del: common.permCheck("book:del"),
        edit: common.permCheck("book:edit"),
        add: common.permCheck("book:add")
      },
      query: {
        id: "",
        name: "",
        index_code: ""
      },
      fullscreenLoading: false,
      stateMap: {
        "1": "有效",
        "0": "无效"
      },
     
      form: {
        title: "",
        id: "",
        name: "",
        introduction: "",
        category: "",
        author: "",
        location: "",
        imgUrl: "",
        indexCode: "",
        createTime: ""       
      },
      
      chargeBillBalanceForm: {
        agentId: "",
        operatorId: "",
        agentName: "",
        chargeThreshold: 0,
        chargeAmount: 0,
        remark: ""
      },
      idx: -1,
      formRules: {
        name: [{ required: true, message: "图书名称必填", trigger: "blur" }],
        category: [
          { required: true, message: "图书类别必选", trigger: "change" }
        ],

        author: [{ required: true, message: "作者信息必填", trigger: "blur" }],
        indexCode: [
          { required: true, message: "图书索引必填", trigger: "blur" }
        ],
        location: [
          { required: true, message: "存放位置必填", trigger: "blur" }
        ],
        introduction: [{ required: true, message: "摘要必填", trigger: "blur" }]
      },

      pwdform: {},
      restPwdVisible: false,
      subAgentVisible: false,
      subData: [],
      subValue: [],
      selectId: null,
      filterMethod(query, item) {
        return item.label.indexOf(query) > -1;
      },
      loading: null
    };
  },
  created() {
    this.loginInfo = common.loginInfo();
    this.loginUserId = this.loginInfo.user.id;
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
        "/book/pageList",
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
      return common.formatter(row.updateTime, "yyyy-MM-dd hh:mm:ss");
    },
  
   
    handleAdd() {
      this.form = {
        title: "新增",
        operType: "1"
      };
      if (this.$refs["form"]) {
        this.$refs["form"].clearValidate();
      }
      this.editVisible = true;
    },
    handleDel(index, row) {
      this.idx = index;
      this.delVisible = true;
    },
    handleEdit(index, row) {
      this.idx = index;
      this.form = {
        operType: "2",
        id: row.id,
        name: row.name,
        introduction: row.introduction,
        category: row.category,
        author: row.author,
        location: row.location,
        imgUrl: row.imgUrl,
        indexCode: row.indexCode,
        operType: "2",
        // createTime: row.id,
        title: "编辑"
      };
      if (this.$refs["form"]) {
        this.$refs["form"].clearValidate();
      }
      this.editVisible = true;
    },

    // 查看代理商下登录账户

    categoryFormatter(row, column) {
      console.log(+stateInfo.category[row.category]);
      return stateInfo.category[row.category];
    },

    showLoading() {
      this.loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
    },
    hideLoading() {
      if (this.loading) {
        this.loading.close();
      }
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
          console.log(_this.form);
          apiService.post("/book/saveOrUpdate", _this.form, function(data) {
            loading.close();
            if (apiService.isOK(data)) {
              //成功
              _this.handleCurrentChange(1);
              _this.editVisible = false;
              _this.$message.success("操作成功");
              _this.getData();
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
      apiService.post(
        "/book/saveOrUpdate",
        { id: item.id, operType: "3" },
        function(data) {
          loading.close();
          if (apiService.isOK(data)) {
            //成功
            _this.handleCurrentChange(1);
            _this.$message.success("删除成功");
          } else {
            _this.$message.error(apiService.getMsg(data));
          }
          _this.delVisible = false;
        }
      );
    },

    beforeUpload1(file) {
      var idcard1 = document
        .getElementById("idcard1")
        .getElementsByClassName("el-upload--picture-card");
      for (var i = 0; i < idcard1.length; i++) {
        idcard1[i].style.display = "none";
      }
    },
    onRemove1() {
      var idcard1 = document
        .getElementById("idcard1")
        .getElementsByClassName("el-upload--picture-card");
      setTimeout(() => {
        for (var i = 0; i < idcard1.length; i++) {
          idcard1[i].style.display = "block";
        }
      }, 500);
    },
    onError1() {
      var idcard1 = document
        .getElementById("idcard1")
        .getElementsByClassName("el-upload--picture-card");
      setTimeout(() => {
        for (var i = 0; i < idcard1.length; i++) {
          idcard1[i].style.display = "block";
        }
      }, 500);
    },
    onSuccess1(response, file, fileList) {
      var _this = this;
      if (apiService.isOK(response)) {
        _this.form.imgUrl = response.body.imageUrl;
        // console.log("pic:"+_this.$refs["form"].imgUrl)
      } else {
        _this.$message.error(apiService.getMsg(response));
      }
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 180px;
}

.handle-input {
  width: 200px;
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
<style>
.el-transfer-panel__body .el-transfer-panel__list::-webkit-scrollbar {
  display: none;
}
.idcard {
  width: 148px;
  float: left;
  margin-right: 10px;
}
.red {
  color: #ff0000;
}
</style>

