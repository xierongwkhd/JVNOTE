package cn.moke.mybatis.first;

import cn.moke.mybatis.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by MOKE on 2019/2/10.
 */
public class MybatisFirst {
    //根据id查询用户信息
    public void findUserByIdTest() throws IOException {
        //加载配置文件
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //通过工厂得到SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //通过SqlSession操作数据库
        User user = sqlSession.selectOne("test.fingUserById",1);//selectOne
        //第一个参数为映射文件statement的id（namespqce+"."+id）
        // 第二个指定映射文件中所匹配parameterType类型的参数
        //结果为映射文件中的resultType类型的对象
        System.out.print(user);
        sqlSession.close();
    }

    //根据用户名称模糊查询
    public void findUserByNameTest() throws IOException {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> userList = sqlSession.selectList("test.findUserByUsername","小明");//selectList
        System.out.print(userList);
        sqlSession.close();
    }

    //添加用户
    public void insertUserTest() throws IOException {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUsername("张小明");
        user.setAddress("河南郑州");
        user.setSex("1");
        sqlSession.insert("test.insertUser", user);
        sqlSession.commit();//提交事务
        sqlSession.close();
    }

}
