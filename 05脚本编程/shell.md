# 脚本 编程
## 文件准备
```
[root@gmall ~]# cp /etc/profile ./
[root@gmall ~]# ll
total 8
-rw-------. 1 root root 1552 Jan 30 21:36 anaconda-ks.cfg
-rw-r--r--  1 root root 1795 Feb  4 10:57 profile
```
```
[root@gmall ~]# cat profile 
# /etc/profile

# System wide environment and startup programs, for login setup
# Functions and aliases go in /etc/bashrc

# It's NOT a good idea to change this file unless you know what you
# are doing. It's much better to create a custom.sh shell script in
# /etc/profile.d/ to make custom changes to your environment, as this
# will prevent the need for merging in future updates.

pathmunge () {
    case ":${PATH}:" in
        *:"$1":*)
            ;;
        *)
            if [ "$2" = "after" ] ; then
                PATH=$PATH:$1
            else
                PATH=$1:$PATH
            fi
    esac
}


if [ -x /usr/bin/id ]; then
    if [ -z "$EUID" ]; then
        # ksh workaround
        EUID=`/usr/bin/id -u`
        UID=`/usr/bin/id -ru`
    fi
    USER="`/usr/bin/id -un`"
    LOGNAME=$USER
    MAIL="/var/spool/mail/$USER"
fi

# Path manipulation
if [ "$EUID" = "0" ]; then
    pathmunge /usr/sbin
    pathmunge /usr/local/sbin
else
    pathmunge /usr/local/sbin after
    pathmunge /usr/sbin after
fi

HOSTNAME=`/usr/bin/hostname 2>/dev/null`
HISTSIZE=1000
if [ "$HISTCONTROL" = "ignorespace" ] ; then
    export HISTCONTROL=ignoreboth
else
    export HISTCONTROL=ignoredups
fi

export PATH USER LOGNAME MAIL HOSTNAME HISTSIZE HISTCONTROL

# By default, we want umask to get set. This sets it for login shell
# Current threshold for system reserved uid/gids is 200
# You could check uidgid reservation validity in
# /usr/share/doc/setup-*/uidgid file
if [ $UID -gt 199 ] && [ "`/usr/bin/id -gn`" = "`/usr/bin/id -un`" ]; then
    umask 002
else
    umask 022
fi

for i in /etc/profile.d/*.sh ; do
    if [ -r "$i" ]; then
        if [ "${-#*i}" != "$-" ]; then 
            . "$i"
        else
            . "$i" >/dev/null
        fi
    fi
done

unset i
unset -f pathmunge
```

# 文件描述符
+ ** 0 输入流 **
+ ** 1 正确输出 **
+ ** 2 错误输出 **
```
&> 或者 >& 可以合并正解输出或错误输出到一个文件
[root@gmall ~]# ls / /god &> ls01.txt
[root@gmall ~]# cat ls01.txt
ls: cannot access /god: No such file or directory
/:
bin
boot
dev
etc
home
lib
lib64
media
mnt
opt
proc
root
run
sbin
srv
sys
tmp
usr
var
[root@gmall ~]#
```

# 变量
+ 本地变量
	- 当前shell拥有
	- 生命周期随shell
	- name=good
+ 局部变量
	- 只能local用于函数
	- val=100
+ 位置变量
	- $1, $2, ${11}
	- 脚本
	- 函数
+ 特殊变量
	- $#:位置参数的个数
	- $*:参数列表，双引号引用为一个字符串
	- $@:参数列表，双引号引用为单独的字符串
	- $$:当前 shell 的 PID
	- $?:上一个命令的退出状态 0成功， other 失败
+ 环境变量
	- export 定义变量
	- unset:取消变量
	- set：查看shell变量

# 引用与命令替换
+ 双引号引用不能阻止变量替换，变量扩展
+ 单引号可以阻止变量的替换过程
+ {}扩展不能被引用
+ 命令执行前删除引用
+ 使用`（反引号）进行命令替换

# 退出状态
+ echo $?

# 算术表达式
+ let 算术运算表达式
	- let C=$A+$B
+ $[算术表达示]
	- C=$[$A+$B]
+ $((算术表达示))
	- C=$((A+B))
+ expr 算术表达示
	- 注意表达示中各操作数及运算符之间要有空格。而且要使用命令引用
	- C=`expr $A + $B`

# 条件表达示 test
```
[root@gmall ~]# test 3 -gt 8
[root@gmall ~]# echo $?
1

test 可以用 [] 替换
[root@gmall ~]# [ 3 -gt 8 ]
[root@gmall ~]# echo $?
1

```
### 小练习
要求
+ 添加用户
+ 用户密码同用户名
+ 静默运行脚本
+ 避免捕获用户接口
+ 程序自定义输出
```
[root@gmall ~]# vi userAdd.sh
#!/bin/bash
useradd $1
echo $1 | passwd --stdin $1 &> /dev/null
echo "user add ok"
[root@gmall ~]# chmod +x userAdd.sh 
[root@gmall ~]# ./userAdd.sh stanlong
user add ok
脚本优化：
[root@gmall ~]# vi userAdd.sh
#!/bin/bash
[ ! $# -eq 1 ] && echo "args error..." && exit 2
id $1 &> /dev/null && echo "user exist..." && exit 3
useradd $1 &> /dev/null && echo $1 | passwd --stdin $1 &> /dev/null && echo "user add ok" && exit 0
echo "No permission to add user" && exit 9
```










































