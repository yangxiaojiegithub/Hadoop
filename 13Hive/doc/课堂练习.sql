悟空  A   男
大海  A   男
宋宋  B   男
凤姐  A   女
婷姐  B   女
婷婷  B   女

select 
  dept_id,
  sum(case sex when '男' then 1 else 0 end) male_count,
  sum(case sex when '女' then 1 else 0 end) female_count
from 
  emp_sex
group by
  dept_id;

select
   dept_id,
   sum(if(sex='男',1,0)) male_count,
   sum(if(sex='女',1,0)) female_count
from 
  emp_sex
group by
  dept_id;

孙悟空 白羊座 A
大海  射手座 A
宋宋  白羊座 B
猪八戒 白羊座 A
凤姐  射手座 A

射手座,A            大海|凤姐
白羊座,A            孙悟空|猪八戒
白羊座,B            宋宋

射手座,A            大海
射手座,A            凤姐
白羊座,A            孙悟空
白羊座,A            猪八戒
白羊座,B            宋宋

create table person_info(
name string, 
constellation string, 
blood_type string) 
row format delimited fields terminated by "\t";

select
   concat(constellation,',',blood_type) constellation_blood_type,
   name
from
   person_info;t1

select
   constellation_blood_type,
   concat_ws('|',collect_set(name))
from
   (select
   concat(constellation,',',blood_type) constellation_blood_type,
   name
from
   person_info)t1
group by constellation_blood_type;t2

select
    movie,
    category_name
from 
    movie_info lateral view explode(category) table_tmp as category_name;

select name,count(*) over()
from business 
where substring(orderdate,1,7) = '2017-04' 
group by name;

jack 5
mart 5
mart 5
mart 5
mart 5

select name,count(*) over()
from business 
where substring(orderdate,1,7) = '2017-04';

select *,sum(cost) over()
from business;

select 
name,
orderdate,
cost,
sum(cost) over(distribute by name sort by orderdate)
from business;

select
    name,
    orderdate,
    cost,
    lag(orderdate,1,'1970-01-01') over(distribute by name sort by orderdate)
from
    business;

select
    name,
    orderdate,
    cost,
    lead(orderdate,1,'9999-99-99') over(distribute by name sort by orderdate)
from
    business;

select
   name,
   orderdate,
   cost,
   sum(cost) over(distribute by name sort by orderdate rows between 1 preceding and 1 following)
from 
   business;

select
   name,
   orderdate,
   cost,
   sum(cost) over(distribute by name sort by orderdate rows between 2 preceding and current row)
from
   business;

select
   name,
   orderdate,
   cost,
   sum(cost) over(distribute by name sort by orderdate rows between unbounded preceding and current row)
from 
   business;

select
   name,
   orderdate,
   cost,
   sum(cost) over(distribute by name sort by orderdate rows between unbounded preceding and unbounded following)
from
   business;

select
   name,
   orderdate,
   cost,
   ntile(5) over(order by orderdate)
from
   business;


movie  category

select
   movie,
   category_name
from
   movie_info
lateral view explode(category) category_tmp_table as category_name;

hive (default)> select 
              >    e.empno,
              >    e.ename,
              >    d.deptno
              > from
              >    emp e 
              > join
              >    dept d 
              > on
              >    e.deptno=d.deptno or e.ename=d.dname;
FAILED: SemanticException [Error 10019]: Line 10:3 OR not supported in JOIN currently 'dname';



select ename, sal*2 twosal from emp order by twosal;


select
  count(*) ct,

from

join on

where

group by

order by

having

limit


from -> join on -> where -> group by -> select|having -> order by -> limit

select
   id,
   name
from
   A
join B
on a.id=B.id and a.id>100;

select
from
(select id,name from A where a.id > 100) t1
join B
on t1.id=B.id 


select
 count(*) ct
from
 A;t1

select 
   ct,
   ct+1
from 
   (select
 count(*) ct
from
 A)t1;


select
    movie,
    explode(category)
from
    movie_info;


select
    movie,
    category_name
from
    movie_info
lateral view explode(category) category_tmp as category_name;



business.name   business.orderdate      business.cost


select
    name,
    orderdate,
    cost,
    sum(cost) over(distribute by name sort by orderdate)
from
    business;

select
    name,
    orderdate,
    cost,
    sum(cost) over(partition by name order by orderdate)
from
    business;

jack    2017-01-01      10
tony    2017-01-02      15
jack    2017-02-03      23
tony    2017-01-04      29
jack    2017-01-05      46
jack    2017-04-06      42
tony    2017-01-07      50
jack    2017-01-08      55
mart    2017-04-08      62
mart    2017-04-09      68
neil    2017-05-10      12
mart    2017-04-11      75
neil    2017-06-12      80
mart    2017-04-13      94

select
    name,
    orderdate,
    cost,
    sum(cost) over(rows between 2 preceding and current row)
from
    business;

select
    name,
    orderdate,
    cost,
    sum(cost) over(partition by name rows between current row and 2 following)
from
    business;

select
    name,
    orderdate,
    cost,
    ntile(5) over(order by orderdate) ntile_5
from
    business;t1

select
    name,
    orderdate,
    cost
from
    (select
    name,
    orderdate,
    cost,
    ntile(5) over(order by orderdate) ntile_5
from
    business)t1
where
    ntile_5=1;

select
    name,
    lag(orderdate,1)
from
    business;

create table score(
name string,
subject string, 
score int) 
row format delimited fields terminated by "\t";

score.name      score.subject   score.score

select
    name,
    subject,
    score,
    rank() over(partition by subject order by score desc) rank1,
    dense_rank() over(partition by subject order by score desc) rank2,
    row_number() over(partition by subject order by score desc) rank3
from
    score;

select
    name,
    subject,
    score,
    rank()
from
    score;




userId  visitDate   visitCount

action

select
    userId,
    date_format(regexp_replace(visitDate,'/','-'),'yyyy-MM') mn,
    visitCount
from
    action;t1
u01     2017-01 5
u02     2017-01 6
u03     2017-01 8
u04     2017-01 3
u01     2017-01 6
u01     2017-02 8
u02     2017-01 6
u01     2017-02 4

select
    userId,
    mn,
    sum(visitCount) sum_visitCount,
from
    (select
    userId,
    date_format(regexp_replace(visitDate,'/','-'),'yyyy-MM') mn,
    visitCount
from
    action)t1
group by
    userId,mn;t2


select
    userId,
    mn,
    sum(visitCount) over(partition by userId,mn) sum_visitCount,
from
    (select
    userId,
    date_format(regexp_replace(visitDate,'/','-'),'yyyy-MM') mn,
    visitCount
from
    action)t1;t2

u01     2017-01 11
u01     2017-02 12
u02     2017-01 12
u03     2017-01 8
u04     2017-01 3

select
    userId,
    mn,
    sum_visitCount,
    sum(sum_visitCount) over(partition by userId order by mn)
from
    (select
    userId,
    mn,
    sum(visitCount) sum_visitCount
from
    (select
    userId,
    date_format(regexp_replace(visitDate,'/','-'),'yyyy-MM') mn,
    visitCount
from
    action)t1
group by
    userId,mn)t2;


visit.user_id   visit.shop


select
    shop,
    count(distinct user_id) uv
from
    visit
group by
    shop;

1.去重
select
    shop,
    user_id
from
    visit
group by
    shop,user_id;t1

2.计数
select
    shop,
    count(*) uv
from
    (select
    shop,
    user_id
from
    visit
group by
    shop,user_id)t1
group by
    shop;

二：
1.计算每个人访问每个店铺的总次数
select
    shop,
    user_id,
    count(*) ct
from
    visit
group by
    shop,user_id;t1

2.针对同一店铺，对访问次数进行逆序排序，并添加rank值
select
    shop,
    user_id,
    ct,
    row_number() over(partition by shop order by ct desc) rk
from
    t1;


select
    shop,
    user_id,
    ct,
    row_number() over(partition by shop order by ct desc) rk
from
    (select
    shop,
    user_id,
    count(*) ct
from
    visit
group by
    shop,user_id)t1;t2

3.去店铺访问前三名的用户
select
    shop,
    user_id,
    ct
from
    (select
    shop,
    user_id,
    ct,
    row_number() over(partition by shop order by ct desc) rk
from
    (select
    shop,
    user_id,
    count(*) ct
from
    visit
group by
    shop,user_id)t1)t2
where
    rk<=3;



假如只写group by user_id,data_dt 结果集为：
u01 2019-02-01
u01 2019-02-02
u01 2019-02-03

u02 2019-02-01
u02 2019-02-02
u02 2019-02-03

加上over(partition by user_id order by data_dt)之后就是对上述6条数据重新开窗
且是u01,u02属于不同的两个窗口
如果加上的是over() 也是对上述6条数据重新开窗
但是u01,u02属于同一个窗口，但是over里面没写排序规则，所有数据相等，结果都是1

insert overwrite table jointable
select n.* from 
nullidtable n full join 
ori o 
on 
case when n.id is null then concat('hive', rand()) else n.id end = o.id;





dept.deptno     dept.dname      dept.loc


create table dept_par(dname string,loc int)
partitioned by (deptno int)
row format delimited fields terminated by '\t';


insert into table dept_par partition(deptno)
select deptno,dname,loc from dept;

create temporary function default.mylower as "com.atguigu.udf.MyUDF2";

OK
dept.deptno     dept.dname      dept.loc

emp.empno       emp.ename       emp.job emp.mgr emp.hiredate    emp.sal emp.comm

select
    e.empno,
    d.dname
from
    emp e,dept d;

select * from emp order by deptno;



bin/flume-ng agent --conf conf --conf-file example.conf --name a1 -Dflume.root.logger=INFO,console

bin/flume-ng agent -n $agent_name -c conf -f conf/flume-conf.properties.template