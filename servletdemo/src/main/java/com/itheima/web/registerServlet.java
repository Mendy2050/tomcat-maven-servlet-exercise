package com.itheima.web; /**
 * @author Mendy
 * @create 2023-05-26 14:03
 */

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、接受用户数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        //2、封装User对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);


        //3、调用Mapper，根据用户名查询用户对象
        // 调用MyBatis完成查询
        //3.1、获取SqlSessionFactory对象
        /*String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);*/
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();

        //3.2、获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //3.3、互殴Mapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //3.4 调用方法
        User u = userMapper.selectByUsername(username);

        //4、判断 “欲注册对象username” 即 u是否为null
        if(u == null){
            //在数据库中不存在这个用户名，表示“可以”注册这个新的用户名
            userMapper.add(user);

            //因为是增删改的操作，所以这里要提交事务
            sqlSession.commit();

            //在数据库中存在这个用户名，表示“不可以”注册这个新的用户名
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("用户名已成功注册");

//            //释放资源
//            sqlSession.close();

        }else{
            //在数据库中存在这个用户名，表示“不可以”注册这个新的用户名
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("用户名已存在");

        }


    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
