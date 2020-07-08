
# 图书管理系统 分为发布可运行和源码两部分

## deploy 目录下为发布可运行部分 pages为前端 ， server为后端服务

### 前后端运行通过nginx分发，nginx 关键配置如下：
   nginx.conf

   #上行服务端口为9999
   upstream lib-server {
        server 127.0.0.1:9999 max_fails=2 fail_timeout=30s;   
   }


   server {
        listen       7009; #监听端口
        server_name  localhost;

        #配置后端 
        location ~ ^/lib-server {
           proxy_set_header Host  $http_host;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header  X-Real-IP $remote_addr;
           proxy_pass http://lib-server;
        }
        #配置前端静态资源
        location /lib-mg {
            root   D:/project/workspace_libraray_system/deploy/pages;
        } 
   }

### 后端server启动命令如下：
windows cmd命令窗口 执行如下，

D:\project\workspace_libraray_system\deploy\server>java -jar libraryserver-0.0.1-SNAPSHOT.jar

### 后端服务配置了H2数据库，无需安装数据库，缓存用的redis ，需要在本地安装redis服务！！！！


## sources-code 为源码部分， 前端源码 pages， 后端源码 libraryserver

### pages 用vue快速开发，本地需要安装nodejs 服务！！
#### 这里用工具vscode直接打开目录，执行以下命令安装项目依赖包
>npm install
启动测试如下命令，前端启动默认8080端口 ，// 开启服务器，浏览器访问 http://localhost:8080
>npm run serve

打包输出如下命令
>npm run build


### libraryserver为java微服务项目，maven管理依赖
#### 这里用eclipse 引入项目 , 在eclipse 对项目进行依赖构建
#### spingboot微服务项目，启动主程序入口 LibraryserverApplication.java

如上 如有问题 随时沟通 微信 will_int


