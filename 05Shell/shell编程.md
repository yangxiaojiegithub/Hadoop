# Shell编程

## 介绍

shell是一个程序，采用C语言编写，是用户和Linux内核沟通的桥梁，它既是一种命令语言，又是一种解释性的编程语言。如下图所示

![](./doc/01.png)

## 功能

- 命令行解释功能
- 启动程序
- 输入输出重定向
- 管道连接
- 文件名替换(echo /*)
- 变量维护
- 环境控制

## 特殊符号

| 符号            | 含义                                                         |
| --------------- | ------------------------------------------------------------ |
| #               | 注释作用, #! 除外                                            |
| ;               | 命令行分隔符, 可以在一行中写多个命令. 例如p1;p2表示先执行p1,再执行p2 |
| ;;              | 连续分号 ,终止 case 选项                                     |
| ""              | 双引号，软转义，解释变量的内容                               |
| ''              | 单引号，硬转义，内容原样输出，不解释变量                     |
| \|              | 管道, 分析前边命令的输出, 并将输出作为后边命令的输入.        |
| >\|             | 强制重定向                                                   |
| \|\|            | 逻辑或，前一个命令执行失败后，才继续执行下一个命令。         |
| ()              | 指令数组 ,用括号将一串连续指令括起来,如 ``` (cd ~ ; vcgh=`pwd` ;echo $vcgh)``` |
| (())            | 计算数学表达式                                               |
| [[]]            | 字符串匹配                                                   |
| &               | 后台运行命令                                                 |
| &&              | 逻辑与，前一个命令执行成功后，才继续执行下一个命令。         |
| !               | 执行历史记录中的命令，"!!"执行上一条命令                     |
| >/dev/null 2>&1 | 标准输出和标准错误都重定向到了/dev/null                      |
| 2>&1 >/dev/null | 意思是把标准错误输出重定向到标准输出后重定向到/dev/null      |
| 1>&2 >/dev/null | 意思是把标准输出重定向到标准错误后重定向到/dev/null          |
| &> /dev/null    | 不管你是啥玩意儿文件描述符，通通重定向到/dev/null            |

## 重定向

```shell
>    重定向输入 覆盖原数据
>>   重定向追加输入，在原数据的末尾添加
<    重定向输出  例如： wc -l < /etc/paawd
<<   重定向追加输出  例如: fdisk /dev/sdb <<EOF ... EOF # 追加输入的内容为 fdisk 需要的参数
```

## 数学运算

```shell
[root@node01 ~]# expr 1 + 1 expr 命令: 只能做整数运算， 注意空格
2
[root@node01 ~]# expr 5 \* 2  # 乘法运算需要转义
10
[root@node01 ~]# echo $((100+3)) # (()) 也可以做数学运算
103
```

## 退出脚本

```shell
exit num 退出脚本，num 代表一个返回值范围是1-255
```

## 格式化输入输出

**echo** 

-n 不要自动换行

```shell
[root@node01 ~]# echo -n "date: ";date +%F
date:2021-11-02
```

-e 若字符串中出现转义字符，则需要特别处理，不会将转义字符当成一般文字输出

转义字符： 

\a 发出告警声

\b 删除前一个字符

```shell
# 倒计时脚本
# time.sh

#!/bin/bash
for time in `seq 9 -1 0`;do
	echo -n -e "\b$time"
	sleep 1
done

echo
```

**颜色代码**

```shell
[root@node01 ~]# echo -e "\033[背景色;字体颜色 字符串 \033[属性效果"
```

## 基本输入

**read**

-p 打印信息

-t 限定时间

-s 不显示输入的内容

-n 输入字符个数

```shell
#!/bin/bash

clear
# echo -n -e "Login: "
# read acc
# 上面两行可简写成
read -p "Login: " acc
echo -n -e "Password: "
read -s -t5 -n pw # 不显示输入的密码，5秒钟不输入密码就退出，密码长度只能有6位

echo "account: $ass password: $pw"
```

```shell
# 模拟登陆界面

#!/bin/bash

clear

echo "Centos Linux 7 (Core)"
echo "kernel `uname -r` an `uname -m` \n"
echo -n -e "$HOSTNAME login: "
read acc
read -s -p "password: "
read pw
```

## 变量

本地变量：只有本用户可以使用，保存在家目录下的 .bash_profile、.bashrc 文件中

全局变量：所有用户都可以使用，保存在 /etc/profile、/etc/bashrc文件中

用户自定义变量：比如脚本中的变量

**命名规则**

- 只能用英文字母，数字和下划线，不能以数字开头
- 中间不能有空格
- 不能使用bash里的关键字

## 数组

**语法**

```
数组名称=(元素1, 元素2， 元素3)
```

**检索数组元素**

```shell
# 格式
${数组名称[索引]}
默认索引从0开始

# 根据下标访问元素
array=(1 3 4 5 6)
echo "访问第二个元素 ${array[1]}"

# 统计数组元素的个数
echo ${#array[@]}
echo ${#array[*]}

# 访问数组中的所有元素
echo ${array[@]}
echo ${array[*]}

# 获取数组元素的索引
echo ${!array[@]}

# 切片获取部分数组元素
# ${array[@]:起始位置：终止位置：}或${array[*]:起始位置：终止位置}
program=(c c++ c# java python PHP perl go .net js shell)
echo "第三到第六的语言为：${program[*]:2:5}"
echo "第七到第十一的语言为：${program[@]:6:10}"
```

## 关联数组

可以自定义索引

```
赋值方式1
	  先声明再初始化，例如：
      declare -A mydict    #声明
      mydict["name"]=guess
      mydict["old"]=18
      mydict["favourite"]=coconut

赋值方式2：
	以索引数组格式定义，只不过元素变成了键值对，这种方式不需要先声明，例如：
　　mydict=(["name"]=guess ["old"]=18 ["favourite"]=coconut] ["my description"]="I am a student")

    也可以声明并同时赋值： 
　　declare -A mydict=(["name"]=guess ["old"]=18 ["favourite"]=coconut ["my description"]="I am a student")

    方式2中，和索引数组定义方式一样，数组名称=(元素1 元素2 元素3)，但括号内部元素格式不同。元素格式为：["键"]=值，元素键值对之间以空格分隔。
```

## IF判断

```shell
文件比较与检查

    -d 检查file是否存在并是一个目录
    -e 检查file是否存在
    -f 检查file是否存在并是一个文件
    -r 检查file是否存在并可读
    -s 检查file是否存在并非空
    -w 检查file是否存在并可写
    -x 检查file是否存在并可执行
    -O 检查file是否存在并属当前用户所有
    -G 检查file是否存在并且默认组与当前用户相同
    file1 -nt file2 检查file1是否比file2新
    file1 -ot file2 检查file1是否比file2旧
    
字符串比较运算

	== 等于
	!= 不等于
	-n 检查字符串长度是否大于0
	-z 检查字符串长度是否等于0
```

```shell
#!/bin/bash
# 如果目录 /tmp/abc 不存在，就创建一个
if[! -d /tmp/abc]
	then
		mkdir -v /tmp/abc
		echo "create /tmp/abc ok"
fi
```

```shell
#!/bin/bash
# 登陆人员身份认证

if[$USER == 'root']
	then
		echo "管理员， 你好"
else
	echo "guest, 你好"
fi
```

```shell
#!/bin/bash
# 判断两个整数的关系
if [$1 -gt $2]
	then
		echo "$1>$2"
elif [$1 -eq $2]
	then
		echo "$1=$2"
else
	echo "$1<$2"
fi
# 或者
if [$1 -eq $2]
	then
		echo "$1>$2"
else
	if[$1 -gt $2]
		then
			echo "$1>$2"
	else
		echo echo "$1<$2"
	fi
fi
```

## FOR循环

```shell
# 语法1
for var in value1 value2 ...
	do
		commands
done

# C格式语法
for ((变量; 条件; 自增减运算))
	do
		代码块
done
```

```shell
#!/bin/bash
# 直接赋值
for var in 1 2 3 4 5
	do
		echo $var
		sleep 1
done

# 使用命令赋值
for var in `var 1 9`
	do echo $var
	sleep 1
done

# C格式语法
for ((i=1; i<10; i++))
	do echo $i
done

for ((n=10, m=0; n>0, m<10; n--, m++))
	do
		echo -e "$n\t$m"
done
```





