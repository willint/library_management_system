<template>
  <div class="table" style="height:100%;">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 模块配置
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container" style="min-height:100%;">
      <div class="handle-box">
        <el-input v-model="query.TITLE" placeholder="标题" class="handle-input mr10"></el-input>
        <el-select v-model="query.CLIENT_TYPE" placeholder="请选择客户端" class="handle-select mr10">
          <el-option key label="全部" value></el-option>
          <el-option key="iOS" label="iOS" value="iOS"></el-option>
          <el-option key="Android" label="Android" value="Android"></el-option>
        </el-select>
        <el-select v-model="query.STATE" placeholder="请选择状态" class="handle-select mr10">
          <el-option key label="全部" value></el-option>
          <el-option
            v-for="item in status"
            :key="item.value"
            :label="item.name"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-select v-model="query.MODULE_KEY" placeholder="请选择模块" class="handle-select mr10">
          <el-option key label="全部" value></el-option>
          <el-option
            v-for="item in moduleKeys"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button v-show="permissions.add" type="primary" icon="el-icon-refresh" @click="handleRefresh">立即生效</el-button>
        <el-button v-show="permissions.add" type="primary" icon="el-icon-circle-plus-outline" @click="handleAdd">新增</el-button>
      </div>
      <el-table
        :data="tableData.results"
        border
        class="table"
        ref="multipleTable"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="index" width="50"></el-table-column>
        <el-table-column prop="title" label="标题" width="120" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="clientType" label="客户端" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column
          prop="moduleKey"
          :formatter="moduleKeyFormatter"
          label="模块"
          width="120"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column
          prop="openMode"
          :formatter="openModeFormatter"
          label="打开方式"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column
          prop="minVersion"
          :formatter="minFormatter"
          label="最低版本"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column
          prop="maxVersion"
          :formatter="maxFormatter"
          label="最高版本"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column
          prop="state"
          :formatter="statusFormatter"
          label="状态"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column
          prop="createDate"
          label="创建时间"
          width="160"
          :formatter="formatter"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
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
    <el-dialog :title="form.dialogTitle" :visible.sync="editVisible" width="80%">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="登录标识" prop="flagLogin">
              <el-radio-group v-model="form.flagLogin">
                <el-radio-button v-for="item in flagLogins" :key="item.value" :label="item.name"></el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="标题" prop="title">
              <el-input v-model="form.title"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="模块" prop="moduleKeyObj">
              <el-cascader
                expand-trigger="hover"
                :options="moduleKeys"
                v-model="form.moduleKeyObj"
                @change="handleChange"
              ></el-cascader>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="打开方式" prop="openMode">
              <el-radio-group v-model="form.openMode">
                <el-radio-button v-for="item in openModes" :key="item.value" :label="item.name"></el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="打开目标" prop="openTarget">
              <el-input v-model="form.openTarget"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="排序" prop="sort">
              <el-input v-model="form.sort"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="客戶端类型" prop="clientType">
              <el-radio-group v-model="form.clientType">
                <el-radio-button v-for="item in clientTypes" :key="item.value" :label="item.name"></el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="最低版本" prop="minVersion">
              <el-input v-model="form.minVersion"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="最高版本" prop="maxVersion">
              <el-input v-model="form.maxVersion"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="状态" prop="state">
              <el-radio-group v-model="form.state">
                <el-radio-button v-for="item in status" :key="item.value" :label="item.name"></el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="170标识" prop="flag170">
              <el-radio-group v-model="form.flag170">
                <el-radio-button v-for="item in flag170s" :key="item.value" :label="item.name"></el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="上架日期" prop="startDate">
              <el-date-picker
                :picker-options="startDateOptions"
                v-model="form.startDate"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="图标标识" prop="iconFlag">
              <el-radio-group v-model="form.iconFlag">
                <el-radio-button label="小图标"></el-radio-button>
                <el-radio-button label="大图标"></el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="图标" prop="iconUrl">
              <el-upload
                :action="uploadAction"
                :show-file-list="false"
                list-type="picture-card"
                :on-success="handleAvatarSuccess"
                :on-progress="handleProgress"
                :before-upload="beforeAvatarUpload"
                :data="uploadData"
                v-show="!showProgress"
              >
                <img v-if="form.iconUrl" :src="form.iconUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
              <el-progress
                v-show="showProgress"
                style="margin-top: 5px;"
                :text-inside="true"
                :stroke-width="18"
                :percentage="imageUploadPercent"
              ></el-progress>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="高亮图标" prop="name">
              <el-upload
                :action="uploadAction"
                :show-file-list="false"
                list-type="picture-card"
                :on-success="handleAvatarSuccessH"
                :on-progress="handleProgressH"
                :before-upload="beforeAvatarUploadH"
                :data="uploadData"
                v-show="!showProgressH"
              >
                <img v-if="form.iconHUrl" :src="form.iconHUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
              <el-progress
                v-show="showProgressH"
                style="margin-top: 5px;"
                :text-inside="true"
                :stroke-width="18"
                :percentage="imageUploadPercent"
              ></el-progress>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="下架日期" prop="endDateOptions">
              <el-date-picker
                :picker-options="endDateOptions"
                v-model="form.endDate"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
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

    <!-- 查看大图 -->
    <el-dialog :visible.sync="dialogVisible" size="tiny">
      <img width="50%" :src="dialogImageUrl" alt>
    </el-dialog>
  </div>
</template>

<script>
import apiService from "../../../service/common/apiService";
import common from "../../../service/common/common";
import configInfo from "../../../service/common/configInfo";
export default {
  name: "basetable",
  data() {
    var validateIconUrl = (rule, value, callback) => {
      if (this.form.iconUrl === "" || this.form.iconUrl == undefined) {
        callback(new Error("请选择图标"));
      } else {
        callback();
      }
    };
    return {
      imageUploadPercent: 0,
      imageUploadPercentH: 0,
      showProgress: false,
      showProgressH: false,
      iconImageUrl: "",
      uploadAction: configInfo.serverUrl + "/images/upload",
      uploadData: { type: "icon" },
      openModes: [{ value: "1", name: "原生" }, { value: "2", name: "网页" }],
      status: [{ value: "0", name: "有效" }, { value: "1", name: "无效" }],
      flagLogins: [{ value: "1", name: "是" }, { value: "0", name: "否" }],
      clientTypes: [
        { value: "iOS", name: "苹果" },
        { value: "Android", name: "安卓" }
      ],
      iconFlags: [
        { value: "0", name: "小图标" },
        { value: "1", name: "大图标" }
      ],
      flag170s: [
        { value: "0", name: "全部" },
        { value: "2", name: "非170" },
        { value: "1", name: "170" }
      ],
      moduleKeys: [
        {
          value: "MY",
          label: "我的",
          children: [
            { value: "0|海航通信合伙人", label: "海航通信合伙人" },
            { value: "1|我的订单", label: "我的订单" },
            { value: "2|我的业务", label: "我的业务" },
            { value: "3|为您推荐", label: "为您推荐" }
          ]
        },
        {
          value: "BUSI_HALL",
          label: "营业厅",
          children: [
            { value: "1|业务查询", label: "业务查询" },
            { value: "2|业务办理", label: "业务办理" },
            { value: "3|产品订购", label: "产品订购" },
            { value: "4|全新产品", label: "全新产品" }
          ]
        },
        {
          value: "AD",
          label: "广告",
          children: [
            { value: "1|QMC_AD", label: "QMC_AD" },
            { value: "2|SUCC_AD", label: "SUCC_AD" },
            { value: "3|FDJH_AD", label: "FDJH_AD" }
          ]
        },
        {
          value: "BOTTOM_MENU",
          label: "底部菜单",
          children: [{ value: "0|底部菜单", label: "底部菜单" }]
        },
        {
          value: "INDEX",
          label: "首页",
          children: [
            { value: "1|首页广告|1", label: "首页广告" },
            { value: "2|首页产品|2", label: "首页产品" },
            { value: "3|消息通知|3", label: "消息通知" },
            { value: "5|号卡专区|5", label: "号卡专区" },
            { value: "6|话费宝|6", label: "话费宝" }
          ]
        }
      ],
      loading: null,
      loading2: true,
      tableData: {
        total: 0,
        page: 1,
        results: []
      },
      multipleSelection: [],
      is_search: false,
      editVisible: false,
      delVisible: false,
      perVisible: false,
      permissions: {
        del: common.permCheck("module:del"),
        edit: common.permCheck("module:edit"),
        add: common.permCheck("module:add")
      },
      query: {
        phone: "",
        user_name: "",
        user: ""
      },
      fullscreenLoading: false,
      form: {
        name: "",
        remarks: ""
      },
      startDateOptions: {
        disabledDate: time => {
          if (this.form.endDate == null) {
            return false;
          }
          return time.getTime() > new Date(this.form.endDate).getTime();
        }
      },
      endDateOptions: {
        disabledDate: time => {
          if (this.form.startDate == null) {
            return false;
          }
          return time.getTime() < new Date(this.form.startDate).getTime();
        }
      },
      idx: -1,
      rules: {
        flagLogin: [
          { required: true, message: "请选择登录标识", trigger: "blur" }
        ],
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        moduleKeyObj: [
          { required: true, message: "请选择模块", trigger: "blur" }
        ],
        openMode: [
          { required: true, message: "请选择打开方式", trigger: "blur" }
        ],
        openTarget: [
          { required: true, message: "请输入打开目标", trigger: "blur" }
        ],
        sort: [{ required: true, message: "请输入排序", trigger: "blur" }],
        clientType: [
          { required: true, message: "请选择客户端类型", trigger: "blur" }
        ],
        minVersion: [
          { required: true, message: "请输入最低版本号", trigger: "blur" }
        ],
        maxVersion: [
          { required: true, message: "请输入最高版本号", trigger: "blur" }
        ],
        state: [{ required: true, message: "请选择状态", trigger: "blur" }],
        flag170: [
          { required: true, message: "请选择170标识", trigger: "blur" }
        ],
        iconFlag: [
          { required: true, message: "请选择图标标识", trigger: "blur" }
        ],
        iconUrl: [{ validator: validateIconUrl, trigger: "blur" }]
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
    //打开方式格式化
    openModeFormatter(row, column) {
      var name = "未知";
      this.openModes.forEach(function(data) {
        if (data.value === row.openMode + "") {
          name = data.name;
          return;
        }
      });
      return name;
    },
    //状态格式化
    statusFormatter(row, column) {
      var name = "未知";
      this.status.forEach(function(data) {
        if (data.value === row.state + "") {
          name = data.name;
          return;
        }
      });
      return name;
    },
    minFormatter(row, column) {
      if (row.minVersion == null || row.minVersion == "") {
        return "5.0.0";
      } else {
        return row.minVersion;
      }
    },
    maxFormatter(row, column) {
      if (row.maxVersion == null || row.maxVersion == "") {
        return "9.9.9";
      } else {
        return row.maxVersion;
      }
    },
    //模块格式化
    moduleKeyFormatter(row, column) {
      var name = row.moduleKey;
      this.moduleKeys.forEach(function(data) {
        if (data.value === row.moduleKey + "") {
          name = data.label + "/" + row.groupTitle;
          return;
        }
      });
      return name;
    },
    handleChange(value) {
      this.$refs["form"].validateField("moduleKeyObj");
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
        "/config/module/pageList",
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
    handleAdd() {
      this.form = {
        dialogTitle: "新增",
        flagLogin: "是",
        title: "",
        moduleKeyObj: [],
        openMode: "原生",
        openTarget: "",
        sort: "1",
        clientType: "苹果",
        minVersion: "5.0.0",
        maxVersion: "9.9.9",
        state: "有效",
        flag170: "全部",
        iconFlag: "小图标"
      };
      if (this.$refs["form"]) {
        this.$refs["form"].clearValidate();
        this.$refs["form"].resetFields();
      }
      this.editVisible = true;
    },
    handleEdit(index, row) {
      this.idx = index;
      this.form = JSON.parse(JSON.stringify(row));
      this.form.dialogTitle = "修改";
      this.form.openMode = common.valuetoname(
        this.openModes,
        this.form.openMode,
        1
      );
      this.form.clientType = common.valuetoname(
        this.clientTypes,
        this.form.clientType,
        1
      );
      this.form.state = common.valuetoname(this.status, this.form.state, 1);
      this.form.flag170 = common.valuetoname(
        this.flag170s,
        this.form.flag170,
        1
      );
      this.form.iconFlag = common.valuetoname(
        this.iconFlags,
        this.form.iconFlag,
        1
      );
      this.form.flagLogin = common.valuetoname(
        this.flagLogins,
        this.form.flagLogin,
        1
      );
      if (this.form.minVersion == null || this.form.minVersion == "") {
        this.form.minVersion = "5.0.0";
      }
      if (this.form.maxVersion == null || this.form.maxVersion == "") {
        this.form.maxVersion = "9.9.9";
      }
      var groupKey = "";
      if (row.groupKey != null) {
        groupKey = "|" + row.groupKey;
      }
      this.form.moduleKeyObj = [
        row.moduleKey,
        row.group + "|" + row.groupTitle + groupKey
      ];
      if (this.$refs["form"]) {
        this.$refs["form"].clearValidate();
      }
      this.editVisible = true;
    },
    handleDelete(index, row) {
      this.idx = index;
      this.delVisible = true;
    },
    handleRefresh(){
      var _this = this;
      this.$confirm('是否刷新模块列表', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          apiService.post("/config/module/refresh", {}, function(data) {
            if (apiService.isOK(data)) {
              _this.$message.success("操作成功");
            } else {
              _this.$message.error(apiService.getMsg(data));
            }
          });
        })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 保存编辑
    saveEdit() {
      var _this = this;
      this.$refs["form"].validate(valid => {
        if (valid) {
          var obj = JSON.parse(JSON.stringify(_this.form));
          obj.moduleKey = _this.form.moduleKeyObj[0];
          var moduleKeyArr = _this.form.moduleKeyObj[1].split("|");
          obj.group = moduleKeyArr[0];
          obj.groupTitle = moduleKeyArr[1];
          if (moduleKeyArr.length > 2) {
            obj.groupKey = moduleKeyArr[2];
          }
          obj.openMode = common.valuetoname(_this.openModes, obj.openMode, 0);
          obj.clientType = common.valuetoname(
            _this.clientTypes,
            obj.clientType,
            0
          );
          obj.state = common.valuetoname(this.status, obj.state, 0);

          obj.flag170 = common.valuetoname(_this.flag170s, obj.flag170, 0);
          obj.iconFlag = common.valuetoname(_this.iconFlags, obj.iconFlag, 0);
          obj.flagLogin = common.valuetoname(
            _this.flagLogins,
            obj.flagLogin,
            0
          );
          apiService.post("/config/module/saveOrUpdate", obj, function(data) {
            if (apiService.isOK(data)) {
              //成功
              _this.handleCurrentChange(1);
              _this.editVisible = false;
              _this.$message.success("操作成功");
            } else {
              _this.$message.error(apiService.getMsg(data));
            }
          });
        }
      });
    },
    // 确定删除
    deleteRow() {
      var _this = this;
      const item = this.tableData.results[this.idx];
      _this.showLoading();
      apiService.post("/config/module/del", { id: item.id }, function(data) {
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
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 1;
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 1MB!");
      }
      this.imageUploadPercent = 0;
      return isLt2M;
    },
    handleAvatarSuccess(response, file, fileList) {
      this.goProgress(100);
      this.showProgress = false;
      if (response.state === "0") {
        this.form.iconUrl = configInfo.imagesUrl + response.body.imageUrl;
      } else {
        this.$message.error(apiService.getMsg(response.msg));
      }
      this.$refs["form"].validateField("iconUrl");
    },
    beforeAvatarUploadH(file) {
      const isLt2M = file.size / 1024 / 1024 < 1;
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 1MB!");
      }
      this.imageUploadPercentH = 0;
      return isLt2M;
    },
    handleAvatarSuccessH(response, file, fileList) {
      this.goProgressH(100);
      this.showProgressH = false;
      if (response.state === "0") {
        this.form.iconHUrl = configInfo.imagesUrl + response.body.imageUrl;
      } else {
        this.$message.error(apiService.getMsg(response.msg));
      }
    },
    goProgressH(p) {
      this.showProgressH = true;
      if (p > this.imageUploadPercent) {
        this.imageUploadPercentH = p;
      }
    },
    handleProgress(event, file, fileList) {
      this.goProgress(file.percentage.toFixed(0));
    },
    handleProgressH(event, file, fileList) {
      this.goProgressH(file.percentage.toFixed(0));
    },
    goProgress(p) {
      this.showProgress = true;
      if (p > this.imageUploadPercent) {
        this.imageUploadPercent = p;
      }
    }
  }
};
</script>
<style>
.el-upload--picture-card,
.avatar,
.el-upload-list--picture-card .el-upload-list__item {
  width: 36px !important;
  height: 36px !important;
  line-height: 45px !important;
}
</style>
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
