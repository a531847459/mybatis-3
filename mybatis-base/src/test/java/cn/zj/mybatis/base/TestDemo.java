package cn.zj.mybatis.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.zj.mybatis.vo.News;


public class TestDemo {
	@Test
	public void TestAdd() throws IOException {
		InputStream inputStream=Resources.getResourceAsStream("mybatis/mybatis.cfg.xml");
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession=sqlSessionFactory.openSession();//获取连接
		News vo=new News();
		vo.setTitle("好好学习");
		vo.setNote("天天向上");
		vo.setPubdate(new Date());
		System.out.println(sqlSession.insert("cn.zj.mapping.NewsNS.doCreate", vo));
		System.out.println(vo);
		sqlSession.commit();
		sqlSession.close();
		inputStream.close();
	}
	@Test
	public void testNewsEdit() throws IOException{
		InputStream inputStream=Resources.getResourceAsStream("mybatis/mybatis.cfg.xml");
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession=sqlSessionFactory.openSession();//获取连接
		News vo=new News();
		vo.setNid(8L);
		vo.setTitle("好好学习2");
		vo.setNote("天天向上2");
//		vo.setPubdate(new Date());
		sqlSession.update("cn.zj.mapping.NewsNS.doEdit", vo);
		System.out.println(vo);
		sqlSession.commit();
		sqlSession.close();
		inputStream.close();
	}
	
	@Test
	public void testDelete() throws IOException{
		InputStream inputStream=Resources.getResourceAsStream("mybatis/mybatis.cfg.xml");
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession=sqlSessionFactory.openSession();//获取连接
		sqlSession.delete("cn.zj.mapping.NewsNS.doRemove", 1L);
		sqlSession.commit();
		sqlSession.close();
		inputStream.close();
	}
	@Test
	public void testGet() throws IOException{
		InputStream inputStream=Resources.getResourceAsStream("mybatis/mybatis.cfg.xml");
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession=sqlSessionFactory.openSession();//获取连接
		News vo=sqlSession.selectOne("cn.zj.mapping.NewsNS.findById", 8L);
		System.out.println(vo);
		inputStream.close();
	}
	
	@Test
	public void testFindAll() throws IOException{
		InputStream inputStream=Resources.getResourceAsStream("mybatis/mybatis.cfg.xml");
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession=sqlSessionFactory.openSession();//获取连接
		List<News> all=sqlSession.selectList("cn.zj.mapping.NewsNS.findAll");
		Iterator<News> iter=all.iterator();
		while(iter.hasNext()) {
			News vo=iter.next();
			System.out.println(vo);
		}
	}
	
	@Test
	public void testFindMap()throws IOException{
		InputStream inputStream=Resources.getResourceAsStream("mybatis/mybatis.cfg.xml");
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession=sqlSessionFactory.openSession();//获取连接
		Map<Long,Map<String,Object>> mapf=sqlSession.selectMap("cn.zj.mapping.NewsNS.findMap", "nid");
		Iterator<Map.Entry<Long, Map<String,Object>>> iterf=mapf.entrySet().iterator();
		while(iterf.hasNext()) {
			Map.Entry<Long, Map<String,Object>> mef=iterf.next();
			Map<String,Object> m=mef.getValue();
			Iterator<Map.Entry<String, Object>> itern=m.entrySet().iterator();
			while(itern.hasNext()) {
				Map.Entry<String, Object> me=itern.next();
				System.out.println("\t|-"+me.getKey()+"=="+me.getValue());
			}
		} 
	}
}
