# docker
## docker的基本组成
+ 镜像
+ 容器
+ 仓库

## docker安装(操作系统CentOS7)
官网地址： https://docs.docker.com/install/linux/docker-ce/centos/

### 使用存储库安装

+ 设置存储库
	- yum install -y yum-utils device-mapper-persistent-data lvm2
	- yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

+ 安装最新版本的Docker Engine-Community和containerd
	- yum install docker-ce docker-ce-cli containerd.io

+ 配置docker阿里云加速器
	- https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors
	```
	针对Docker客户端版本大于 1.10.0 的用户
	您可以通过修改daemon配置文件/etc/docker/daemon.json来使用加速器
    sudo mkdir -p /etc/docker
    sudo tee /etc/docker/daemon.json <<-'EOF'
    {
      "registry-mirrors": ["https://ekzpnbca.mirror.aliyuncs.com"] -- 这个要先注册账号之后才会有
    }
    EOF
    sudo systemctl daemon-reload
    sudo systemctl restart docker
	```
    ```
    [root@changgou etc]# mkdir docker
    [root@changgou etc]# cd docker
    [root@changgou docker]# tee /etc/docker/daemon.json <<-'EOF'
    > {
    >   "registry-mirrors": ["https://ekzpnbca.mirror.aliyuncs.com"]
    > }
    > EOF
    {
      "registry-mirrors": ["https://ekzpnbca.mirror.aliyuncs.com"]
    }
    [root@changgou docker]# systemctl daemon-reload
    [root@changgou docker]# systemctl restart docker
    [root@changgou docker]# ps -ef | grep docker
	root       5484      1  0 16:11 ?        00:00:00 /usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
	root       5638   3564  0 16:15 pts/0    00:00:00 grep --color=auto docker
	```

+ 启动Docker
	- sudo systemctl start docker

+ 通过运行hello-world 映像来验证是否正确安装了Docker Engine-Community
	- docker run hello-world
	```
    [root@changgou docker]# docker run hello-world
    Unable to find image 'hello-world:latest' locally
    latest: Pulling from library/hello-world
    1b930d010525: Pull complete 
    Digest: sha256:9572f7cdcee8591948c2963463447a53466950b3fc15a247fcad1917ca215a2f
    Status: Downloaded newer image for hello-world:latest

    Hello from Docker!
    This message shows that your installation appears to be working correctly.
	```

# docker 常用命令

## 帮助命令
+ docker version
+ docker info
+ docker help

## 镜像命令
+ docker images :列出本地主机镜像
```
[root@changgou ~]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
hello-world         latest              fce289e99eb9        13 months ago       1.84kB
REPOSITORY : 表示镜像仓库
TAG ： 镜像标签
IMAGE ID ： 镜像ID
CREATED ：镜像创建时间
SIZE ： 镜像大小
```
	- -a:列出本地所有镜像（含中间映像层）
	- -q:只显示镜像id
	- --digests：只显示镜像的摘要信息
	- --no-trunc:显示完整的镜像信息
+ docker search 某个镜像的名字 :查找镜像
	- -s：列出收藏数不小于指定值的镜像
	- --no-trunc:显示完整的镜像描述
	- --automated:只列出automated build类型的镜像
+ docker pull 某个镜像的名字 ：下载镜像
+ docker rmi 某个镜像的名字 ：删除镜像

## 容器命令
+ 新建并启动容器
```
[root@changgou ~]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
centos              latest              470671670cac        2 weeks ago         237MB
hello-world         latest              fce289e99eb9        13 months ago       1.84kB
[root@changgou ~]# docker run -it 470671670cac
--name="窗口名字":为容器指定一个名称
-d: 后台运行容器，并返回容器id,也即启动一个守护进程
-i: 以交互模式运行容器，通常与-t同时使用
-t: 为容器分配一个伪输入终端，通常与-i同时使用
-P: 随机端口映射
-p: 指定端口映射，有以下四种形式
	ip:hostPort:containerPort
    ip::containerPort
    hostPort:containerPort
    containerPort
```
+ docker ps ：列出当前所有正在运行的容器
```
[root@changgou ~]# docker ps
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
800d4a6c6eb7        470671670cac        "/bin/bash"         2 minutes ago       Up 2 minutes                            funny_carson
```
	- -a:列出当前所有正在运行的容器+历史上运行过的
	- -l:显示最近创建的容器
	- -n:显示最近n个创建的容器
	- -q:静默模式，只显示容器编号
	- --no-trunc:不截断输出
+ exit:退出容器
```
[root@800d4a6c6eb7 /]# exit
exit
[root@changgou ~]#
```
+ docker start 容器id或容器名: 启动容器
+ docker restart 容器id或容器名: 重启容器
+ docker stop 容器id或容器名: 停止容器
+ docker kill 容器id或容器名: 强制停止容器
+ docker rm 容器id： 删除已停止的容器





