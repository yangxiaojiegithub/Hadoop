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


## 容器命令

