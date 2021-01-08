# LVS

LVS是Linux Virtual Server 的简写， 意即Linux虚拟服务器，是一个虚拟的服务器集群系统

ipvs： 嵌入到linux的内核

ipvsadm：ipvs的客户端管理程序

**缺点：容易出现单点故障**

## LVS调度方法

### 四种静态

- rr：轮询
- wrr：
- dh：
- sh：

### 动态调度方法

- lc：最少连接
- wlc：加权最少连接
- sed：最短期望延迟
- nq：never queue
- LBLC：基于本地的最少连接
- DH：
- LBLCR：基于本地的带复制功能的最少连接

### 默认调度方法

默认调度方法**wlc**

## LVS命令

### 监控包

- 添加： -A

  ```
  命令格式 -A -t|u|f service-address [-s scheduler]
  参数说明:
  -t: TCP协议的集群
  -u: UDP协议的集群
  -f: FWM 防火墙标记
  如： ipvsadm -A -t 192.168.235.12:80 -s rr
  追加监控 TCP协议 目标地址192.168.238.12:80 调度算法 rr轮询
  ```

- 修改：-E

- 删除：-D

  ```
  命令格式 -D -t|u|f service-address
  ```

### 负载包

- 添加：-a

  ```
  命令格式 -a -t|u|f service-address -r server-address [-g|i|m][-w weight]
  -t|u|f service-address: 事先定义好的某集群服务
  -r server-address： 某RS的地址， 在NAT模型中， 可使用IP：PORT实现端口映射
  [-g|i|m]：LVS模型 g是DR模型，i是TUN模型， m是NAT模型
  [-w weight]：定义服务器权重
  ```

- 修改：-e

- 删除：-d

- 查看：

  - -L|l
  - -n：数字格式显示主机地址和端口
  - --stats：统计数据
  - --rate：速率
  - --timeout：显示tcp、tcpfin和udp的回话超时时长
  - -c：显示当前的ipvs连接状况

- 删除所有集群服务： -C： 清空ipvs规则

- 保存规则：-S

- 载入此前规则： -R

## 实操

**浏览器作为客户端，node01作为VS， node02和node03作为RS**

1. 集群规划

   准备三台虚拟机

   |        |      |          | eth0 |            |                           |             |
   | ------ | ---- | -------- | ---- | ---------- | ------------------------- | ----------- |
   | node01 | LVS  | ipvsadm  | DIP  | eth0:0 VIP |                           | 配置ipvsadm |
   | node02 | RS01 | httpd 80 | RIP  | lo:0 VIP   | 调整内核ARP通告和响应级别 | 启动httpd   |
   | node03 | RS02 | httpd 80 | RIP  | lo:0 VIP   | 调整内核ARP通告和响应级别 | 启动httpd   |

2. 配置三台虚拟机的网络

3. 配置LVS的VIP

   ```shell
   临时配置
   [root@node01 ~]# ifconfig ens33:2 192.168.235.100/24
   [root@node01 ~]# ifconfig
   ens33: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
           inet 192.168.235.11  netmask 255.255.255.0  broadcast 192.168.235.255
           inet6 fe80::4776:cded:346a:2363  prefixlen 64  scopeid 0x20<link>
           ether 00:0c:29:fd:d4:e0  txqueuelen 1000  (Ethernet)
           RX packets 1921  bytes 125928 (122.9 KiB)
           RX errors 0  dropped 0  overruns 0  frame 0
           TX packets 322  bytes 29535 (28.8 KiB)
           TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
   
   ens33:2: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
           inet 192.168.235.100  netmask 255.255.255.0  broadcast 192.168.235.255
           ether 00:0c:29:fd:d4:e0  txqueuelen 1000  (Ethernet)
   
   lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
           inet 127.0.0.1  netmask 255.0.0.0
           inet6 ::1  prefixlen 128  scopeid 0x10<host>
           loop  txqueuelen 1  (Local Loopback)
           RX packets 0  bytes 0 (0.0 B)
           RX errors 0  dropped 0  overruns 0  frame 0
           TX packets 0  bytes 0 (0.0 B)
           TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
   [root@node01 ~]# echo 1 > /proc/sys/net/ipv4/ip_forward # 配置地址转发功能        
   ```

4. 调整RS的响应，通告级别（每一台RS都配）

   这里记录了node02上的操作，在node03上做同样的操作即可

   ```shell
   [root@node02 ~]# cd /proc/sys/net/ipv4/conf/
   [root@node02 conf]# ll
   total 0
   dr-xr-xr-x 1 root root 0 Jan  9 06:14 all
   dr-xr-xr-x 1 root root 0 Jan  9 06:14 default
   dr-xr-xr-x 1 root root 0 Jan  9 06:14 ens33
   dr-xr-xr-x 1 root root 0 Jan  9 07:54 lo
   [root@node02 conf]# cd ens33/
   [root@node02 ens33]# ls
   accept_local         arp_accept    arp_ignore   disable_policy      forwarding     medium_id            proxy_arp_pvlan  secure_redirects  src_valid_mark
   accept_redirects     arp_announce  arp_notify   disable_xfrm        log_martians   promote_secondaries  route_localnet   send_redirects    tag
   accept_source_route  arp_filter    bootp_relay  force_igmp_version  mc_forwarding  proxy_arp            rp_filter        shared_media
   [root@node02 ens33]# 
   [root@node02 ens33]# echo 1 > arp_ignore
   [root@node02 ens33]# echo 2 > arp_announce
   [root@node02 ens33]# cd ../
   [root@node02 conf]# cd all/
   [root@node02 all]# ls
   accept_local         arp_accept    arp_ignore   disable_policy      forwarding     medium_id            proxy_arp_pvlan  secure_redirects  src_valid_mark
   accept_redirects     arp_announce  arp_notify   disable_xfrm        log_martians   promote_secondaries  route_localnet   send_redirects    tag
   accept_source_route  arp_filter    bootp_relay  force_igmp_version  mc_forwarding  proxy_arp            rp_filter        shared_media
   [root@node02 all]# echo 1 > arp_ignore 
   [root@node02 all]# echo 2 > arp_announce 
   ```

5. 配置RS的VIP（每一台RS都配）

   这里记录了node02上的操作，在node03上做同样的操作即可

   ```shell
   [root@node02 all]# ifconfig lo:8 192.168.235.100 netmask 255.255.255.255
   [root@node02 all]# ifconfig
   ens33: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
           inet 192.168.235.12  netmask 255.255.255.0  broadcast 192.168.235.255
           inet6 fe80::4776:cded:346a:2363  prefixlen 64  scopeid 0x20<link>
           inet6 fe80::5e46:3279:9c35:eaf3  prefixlen 64  scopeid 0x20<link>
           ether 00:0c:29:fa:0d:89  txqueuelen 1000  (Ethernet)
           RX packets 2316  bytes 153244 (149.6 KiB)
           RX errors 0  dropped 0  overruns 0  frame 0
           TX packets 463  bytes 41403 (40.4 KiB)
           TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
   
   lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
           inet 127.0.0.1  netmask 255.0.0.0
           inet6 ::1  prefixlen 128  scopeid 0x10<host>
           loop  txqueuelen 1  (Local Loopback)
           RX packets 0  bytes 0 (0.0 B)
           RX errors 0  dropped 0  overruns 0  frame 0
           TX packets 0  bytes 0 (0.0 B)
           TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
   
   lo:8: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
           inet 192.168.235.100  netmask 255.255.255.255
           loop  txqueuelen 1  (Local Loopback)
   
   ```

6. 启动两台RS上的httpd

   ```shell
   # httpd 静态的webserver 服务
   [root@node02 ~]# yum install httpd -y  # 安装httpd
   [root@node02 ~]# cd /var/www/html/
   [root@node02 html]# vi index.html
   from 192.168.235.12
   [root@node02 html]# service httpd start
   # 启动成功后在浏览器里访问 192.168.235.12 默认端口80
   ```

   ![](./doc/01.png)

7. 通过客户端 ipvsadm 调用LVS的命令

   ```shell
   [root@node01 ~]# yum install ipvsadm -y 
   [root@node01 ~]# ipvsadm -A -t 192.168.235.100:80 -s rr # 设置监控的包
   [root@node01 ~]# ipvsadm -ln
   IP Virtual Server version 1.2.1 (size=4096)
   Prot LocalAddress:Port Scheduler Flags
     -> RemoteAddress:Port           Forward Weight ActiveConn InActConn
   TCP  192.168.235.100:80 rr
   [root@node01 ~]# ipvsadm -a -t 192.168.235.100:80 -r 192.168.235.12:80 -g -w 1
   [root@node01 ~]# ipvsadm -a -t 192.168.235.100:80 -r 192.168.235.13:80 -g -w 2
   [root@node01 ~]# ipvsadm -ln
   IP Virtual Server version 1.2.1 (size=4096)
   Prot LocalAddress:Port Scheduler Flags
     -> RemoteAddress:Port           Forward Weight ActiveConn InActConn
   TCP  192.168.235.100:80 rr
     -> 192.168.235.12:80            Route   1      0          0         
     -> 192.168.235.13:80            Route   2      0          0      
   ```

   配置完成后即时生效， 在浏览器里访问 192.168.235.100. 不断刷新可看到 node02和node03返回的页面

   ![](./doc/02.png)

