```shell
# 从 tablename 里读取表名后批量更改
# 并将错误日志输出到 total.log

#!/bin/bash
date_list=`date +"%Y%m%d%H%M%S"` 
mkdir $date_list
while read line
do
    beeline -e "alter table $line rename to $line_off20211116" > ./$date_list/$line.log 2&>1
    grep -i error ./$date_list/$line.log > ./$date_list/$line.check.log
    if[-s ./$date_list/$line.check.log]; then
        echo $line error
        cat ./$date_list/$line.check.log >> ./$date_list/total.log
        rm ./$date_list/$line.check.log ./$date_list/$line.check.log
    else
        echo $line ok
        rm ./$date_list/$line.check.log ./$date_list/$line.check.log
    fi
done < ./tablename
```

