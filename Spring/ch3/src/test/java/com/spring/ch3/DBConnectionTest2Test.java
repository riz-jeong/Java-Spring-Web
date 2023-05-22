package com.spring.ch3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired
    DataSource ds;

    @Test
    public void insertUserTest() throws Exception {
        User user = new User("asdf", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
        deleteAll();
        int rowCnt = insertUser(user);

        assertTrue(rowCnt==1);
    }

    @Test
    public void selectUserTest() throws Exception {
        deleteAll();
        User user = new User("asdf", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
        int rowCnt = insertUser(user);
        User user2 = selectUser("asdf");

        assertTrue(user.getId().equals("asdf"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        deleteAll();
        int rowCnt = deleteUser("asdf");

        assertTrue(rowCnt==0);

        User user = new User("asdf", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());
        rowCnt = insertUser(user);
        assertTrue(rowCnt==1);

        rowCnt = deleteUser(user.getId());
        assertTrue(rowCnt==1);

        assertTrue(selectUser(user.getId())==null);
    }

    @Test
    public void updateUserTest() throws Exception {
        deleteAll();
        User user = new User("update", "1234", "abc", "aaaa@aaa.com", new Date(), "fb", new Date());

        int rowCnt = insertUser(user);
        assertTrue(rowCnt==1);

        assertTrue(selectUser(user.getId())!=null);

        rowCnt = updateUser(user);
        assertTrue(rowCnt==1);
    }

    // 매개변수로 받은 사용자 정보로 user_info테이블을 update하는 메서드
    public int updateUser(User user) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "UPDATE user_info SET pwd = ?, name = ?, email = ?, birth = ?, sns = ? WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql); // SQL Injection공격, 성능향상

        ps.setString(1, user.getPwd());
        ps.setString(2, user.getName());
        ps.setString(3, user.getEmail());
        ps.setDate(4, new java.sql.Date(user.getBirth().getTime()));
        ps.setString(5, user.getSns());
        ps.setString(6, user.getId());

        return ps.executeUpdate();
    }

    public int deleteUser(String id) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "DELETE FROM user_info WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
        ps.setString(1, id);
//        int rowCnt = ps.executeUpdate(); // insert, delete, update
//        return rowCnt;
        return ps.executeUpdate();
    }

    public User selectUser(String id) throws Exception {
        Connection conn = ds.getConnection();

        String sql = "SELECT * FROM user_info WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery(); // insert, delete, update

        if(rs.next()) {
            User user = new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(rs.getDate(5));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getTimestamp(7).getTime()));

            return user;
        }
        return null;
    }
    private void deleteAll() throws Exception {
        Connection conn = ds.getConnection();

        String sql = "DELETE FROM user_info";

        PreparedStatement ps = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
        ps.executeUpdate(); // insert, delete, update
    }

    @Test
    public void transactionTest() throws Exception {
        Connection conn = null;
        try {
            deleteAll();
            conn = ds.getConnection();
            conn.setAutoCommit(false); // conn.setAutoCommit(true);

//        INSERT INTO user_info (id, pwd, name, email, birth, sns, reg_date)
//        VALUES ('asdf22', '1234', 'smith', 'aaa@aaa.com', '2022-01-01', 'facebook', now());

            String sql = "INSERT INTO user_info VALUES (?, ?, ?, ?, ?, ?, now())";

            PreparedStatement ps = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
            ps.setString(1, "asdf");
            ps.setString(2, "1234");
            ps.setString(3, "abc");
            ps.setString(4, "aaa@aaa.com");
            ps.setDate(5, new java.sql.Date(new Date().getTime()));
            ps.setString(6, "fb");

            int rowCnt = ps.executeUpdate(); // insert, delete, update

            ps.setString(1, "asdf");
            rowCnt = ps.executeUpdate();

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
//            throw new RuntimeException(e);
        } finally {

        }

    }

    // 사용자 정보를 user_info 테이블에 저장하는 메서드
    public int insertUser(User user) throws Exception{
        Connection conn = ds.getConnection();

//        INSERT INTO user_info (id, pwd, name, email, birth, sns, reg_date)
//        VALUES ('asdf22', '1234', 'smith', 'aaa@aaa.com', '2022-01-01', 'facebook', now());

        String sql = "INSERT INTO user_info VALUES (?, ?, ?, ?, ?, ?, now())";

        PreparedStatement ps = conn.prepareStatement(sql); // SQL Injection공격, 성능향상
        ps.setString(1, user.getId());
        ps.setString(2, user.getPwd());
        ps.setString(3, user.getName());
        ps.setString(4, user.getEmail());
        ps.setDate(5, new java.sql.Date(user.getBirth().getTime()));
        ps.setString(6, user.getSns());

        int rowCnt = ps.executeUpdate(); // insert, delete, update

        return rowCnt;
    }

    @Test
    public void springJdbcConnectionTest() throws Exception {
//        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
//        DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn!=null); // 괄호 안의 조건식이 true면, 테스트 성공, 아니면 실패
    }
}