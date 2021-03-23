import dao.UserMapper;
import entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppMain {

    static SqlSessionFactory sqlSessionFactory;

    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //构建sqlSession的工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //创建能执行映射文件中sql的sqlSession
        //SqlSession sqlSession = sqlSessionFactory.openSession();
        //sqlSession.close();

        // TODO:调用方法
        insert2();


    }

    /**
     * 映射sql的标识字符串，
     * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
     * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
     */
    private static void select1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("dao.UserMapper.getById", 1);
        System.err.println(user);
        sqlSession.close();
    }

    /**
     * 从sqlSession获取Mapper，直接调用接口
     */
    private static void select2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getById(2);
        System.err.println(user);
        sqlSession.close();
    }

    /**
     * 大数据量插入测试1  逐一insert,批量提交,避免List过大而内存溢出
     * 用时：14250毫秒
     */
    private static void insert1() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        long start = System.currentTimeMillis();

        int total = 10000;
        try {
            for (int i = 1; i <= total; i++) {
                User user = new User(null, "Jape" + i, "1", "123456", new Date());
                sqlSession.insert("dao.UserMapper.insertOne", user);
                if (i == total || i % 1000 == 0) {
                    sqlSession.commit();
                    //清理缓存，防止溢出
                    sqlSession.clearCache();
                    System.err.printf("提交到：%d\n", i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

        long end = System.currentTimeMillis();
        System.err.printf("用时：%d毫秒\n", (end - start));
    }

    /**
     * 大数据量插入测试1  生成动态sql，一次提交一个大sql
     * 用时：3473毫秒
     */
    private static void insert2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        long start = System.currentTimeMillis();

        int page = 10;
        int pageSize = 1000;
        try {
            for (int i = 0; i < page; i++) {
                List<User> userList = new ArrayList<>();
                for (int j = 0; j < pageSize; j++) {
                    User user = new User(null, "Jape" + i, "1", "123456", new Date());
                    userList.add(user);
                }
                userMapper.batchInsert(userList);
                sqlSession.commit();
                sqlSession.clearCache();
                System.err.printf("提交到：%d\n", i * pageSize);
            }
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }

        long end = System.currentTimeMillis();
        System.err.printf("用时：%d毫秒\n", (end - start));

    }
}
