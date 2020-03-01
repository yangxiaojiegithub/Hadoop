# Kibana的使用

1. 查询所有索引

   ```java
   GET _cat/indices?v
   ```

   ![](./doc/01.png)

2. 新增索引

   ```
   PUT /索引名称
   ```

   ![](./doc/02.png)

3. 删除索引

   ```
   DELETE /索引名称
   ```

   ![](./doc/03.png)

4. 创建映射

   ```
   PUT /user/userinfo/_mapping 
   {
     "properties":{
       "name":{
         "type":"text",
         "analyzer": "ik_smart",
         "search_analyzer": "ik_smart",
         "store": false
       },
       "city":{
         "type":"text",
         "analyzer": "ik_smart",
         "search_analyzer": "ik_smart",
         "store": false
       },
       "age":{
         "type":"long",
         "store": false
       },
       "description":{
         "type":"text",
         "analyzer": "ik_smart",
         "search_analyzer": "ik_smart",
         "store": false
       }
     }
   }
   ```

   ![](./doc/04.png)

5. 新增文档数据

   ```
   PUT /user/userinfo/1
   {
     "name":"张三",
     "age":22,
     "city":"深圳",
     "description":"高级开发工程师"
   }
   
   # 测试用，多新增几条
   PUT /user/userinfo/2
   {
     "name":"李四",
     "age":30,
     "city":"深圳",
     "description":"初级工程师"
   }
   
   PUT /user/userinfo/3
   {
     "name":"王五",
     "age":35,
     "city":"深圳",
     "description":"产品经理"
   }
   
   PUT /user/userinfo/4
   {
     "name":"赵六",
     "age":37,
     "city":"深圳",
     "description":"项目经理"
   }
   
   PUT /user/userinfo/5
   {
     "name":"陈七",
     "age":39,
     "city":"深圳",
     "description":"项目经理",
     "address":"南京市江宁区"
   }
   
   PUT /user/userinfo/6
   {
     "name":"朱八",
     "age":42,
     "city":"深圳",
     "description":"销售经理",
     "address":"南京市江宁区"
   }
   ```

   ![](./doc/05.png)

6. 根据id查询数据

   ![](./doc/06.png)

7. 覆盖更新

   ![](./doc/07.png)

   再次查询"_id"为1的数据，会发现数据已经被覆盖了

   ![](./doc/08.png)

8. 更新数据(POST)

   ![](./doc/09.png)

9. 删除数据

   ![](./doc/10.png)

10. 查询user索引库下的所有数据

    ![](./doc/11.png)

11. Sort 排序

    ```
    # 按age降序排列
    GET /user/_search
    {
      "query":{
        "match_all": {
          
        }
      },
      "sort": [
        {
          "age": {
            "order": "desc"
          }
        }
      ]
    }
    ```

    ![](./doc/12.png)

12. 分页

from: 从N的记录开始查询

size:每页显示的条数

```
GET /user/_search
{
  "query":{
    "match_all": {
      
    }
  },
  "sort": [
    {
      "age": {
        "order": "desc"
      }
    }
  ],
  "from": 0,
  "size": 2
}
```

![](./doc/13.png)

13. 词项搜索

```
GET /user/userinfo/_search
{
  "query": {
    "term": {
      "name": {
        "value": "陈七"
      }
    }
  }
}
```

![](./doc/14.png)

14. 多个词项搜索

    ```
    GET /user/userinfo/_search
    {
      "query": {
        "terms": {
          "name": [
            "陈七",
            "张三丰"
          ]
        }
      }
    }
    ```

    ![](./doc/15.png)

15. 范围过滤搜索

    ```
    GET /user/userinfo/_search
    {
      "query": {
        "range": {
          "age": {
            "gte": 35,
            "lte": 40
          }
        }
      }
    }
    ```

    ![](./doc/16.png)

16. exits 过滤

    exists 过滤可以用于查找包含某个域的数据

    