package com.stanlong.api;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 *
 */
public class TestAPI {

    // 判断表是否存在
    public static boolean isTableExists(String tableName) throws IOException{
        // 1. 获取配置文件信息
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "node02, node03, node04");

        // 2. 获取管理员对象
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        // 3. 判断表是否存在
        boolean result = admin.tableExists(TableName.valueOf(tableName));

        // 4. 关闭连接
        admin.close();

        // 5. 返回结果
        return result;
    }

    public static void main(String[] args) throws IOException{
        // 测试表是否存在
        System.out.println(isTableExists("stu"));
    }
}
