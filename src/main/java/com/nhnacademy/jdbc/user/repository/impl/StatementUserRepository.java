package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementUserRepository implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#1 아이디, 비밀번호가 일치하는 User 조회
        String sql = "SELECT * FROM jdbc_users WHERE user_id = '" + userId + "' AND user_password = '"
                + userPassword + "'";
        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            if(resultSet.next()) {
                User user = new User(resultSet.getString("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_password"));
                return Optional.of(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //#todo#2-아이디로 User 조회
        String sql = "SELECT * FROM jdbc_users WHERE user_id = '" + userId + "'";

        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            if(resultSet.next()) {
                User user = new User(resultSet.getString("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_password"));
                return Optional.of(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3- User 저장
        String sql = "INSERT INTO jdbc_users (user_id, user_name, user_password) VALUES( '"
                + user.getUserId() + "', '"
                + user.getUserName() + "', '"
                + user.getUserPassword() + "')";
        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        //todo#4-User 비밀번호 변경
        String sql = "UPDATE jdbc_users SET user_password = '" + userPassword + "' WHERE user_id = '" + userId + "'";
        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#5 - User 삭제
        String sql = "DELETE FROM jdbc_users WHERE user_id = '" + userId + "'";
        try (Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}
