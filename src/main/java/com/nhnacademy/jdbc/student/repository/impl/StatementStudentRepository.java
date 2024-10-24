package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 insert student
        String sql = "INSERT INTO jdbc_students (id, name, gender, age) VALUES ('"
                + student.getId() + "', '"
                + student.getName() + "', '"
                + student.getGender() + "', "
                + student.getAge() + ")";
        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            log.error("Error while saving student: ",e);
        }
        return 0;
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 student 조회
        String sql = "SELECT * FROM jdbc_students WHERE id = '" + id + "'";
        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            if(resultSet.next()) {
                String name = resultSet.getString("name");
                Student.GENDER gender = Student.GENDER.valueOf(resultSet.getString("gender"));
                int age = resultSet.getInt("age");
                Student student = new Student(id, name, gender, age);
                return Optional.of(student);
            }
        } catch (SQLException e) {
            log.error("Error while finding student: ", e);
        }
        return Optional.empty();
    }

    @Override
    public int update(Student student){
        //todo#3 student 수정, name <- 수정합니다.
        String sql = "UPDATE jdbc_students SET name = '"
                + student.getName() + "', gender = '"
                + student.getGender().name() + "', age = "
                + student.getAge() + " WHERE id = '" + student.getId() + "'";
        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            log.error("Error while updating student: ", e);
        }
        return 0;
    }

    @Override
    public int deleteById(String id){
       //todo#4 student 삭제
        String sql = "DELETE FROM jdbc_students WHERE id = '" + id +"'";
        try(Connection connection = DbUtils.getConnection();
            Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            log.error("Error while deleting student: ", e);
        }
        return 0;
    }

}
