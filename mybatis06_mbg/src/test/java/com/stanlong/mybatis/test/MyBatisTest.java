package com.stanlong.mybatis.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;

/**
 * 测试动态sql
 * @author 矢量
 *
 */
public class MyBatisTest {
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException{
		String resource = "mybatis/SqlSessionConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	@Test
	public void test01() throws Exception{
		SqlSessionFactory sessionFactory = getSqlSessionFactory();
		SqlSession openSession = sessionFactory.openSession();
		try{
			TbItemParamExample example = new TbItemParamExample();
			Criteria criteria = example.createCriteria();
			criteria.andItemCatIdEqualTo(3L);
			TbItemParamMapper mapper = openSession.getMapper(TbItemParamMapper.class);
			List<TbItemParam> itemParamList = mapper.selectByExampleWithBLOBs(example);
			for (TbItemParam tbItemParam : itemParamList) {
				System.out.println(tbItemParam.getParamData());
			}
		}finally{
			openSession.close();
		}
	}
	
}
