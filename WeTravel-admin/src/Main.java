import com.wetravel.mapper.UserMapper;
import com.wetravel.model.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        String resource = "resources/mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);

        //上面是固定写法，通过xml配置文件创建SqlSessionFactory对象


        SqlSession sqlSession =sqlSessionFactory .openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User user=new User();
        user.setUserId(1);
        List<User> list=mapper.queryUser(user);
        System.out.println(list);
        sqlSession.commit();
        sqlSession.close();
        }
    }
