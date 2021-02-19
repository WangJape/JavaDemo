import dao.UserMapper;
import entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class AppMain {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //创建能执行映射文件中sql的sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        /*
         * 映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        User user = sqlSession.selectOne("dao.UserMapper.getById", 1);
        System.err.println(user);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        user = userMapper.getById(2);
        System.err.println(user);
    }
}
