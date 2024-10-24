package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class PreparedStatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student) {
        //todo#1 insert student
        String sql = "INSERT INTO jdbc_students (id, name, gender, age) VALUES (?, ?, ?, ?)";
        try(Connection connection = DbUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getGender().name());
            preparedStatement.setInt(4, student.getAge());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while saving student: ", e);
        }
        return 0;

    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 student 조회
        String sql = "SELECT * FROM jdbc_students WHERE id = ?";
        try(Connection connection = DbUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

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
        //todo#3 student 수정, name 수정.
        String sql = "UPDATE jdbc_students SET name = ?, gender = ? , age = ? WHERE id = ?";
        try(Connection connection = DbUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getGender().name());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setString(4, student.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while updating student: ", e);
        }
        return 0;
    }
    @Override
    public int deleteById(String id){
        //todo#4 student 삭제
        String sql = "DELETE FROM jdbc_students WHERE id = ?";
        try(Connection connection = DbUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, id);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while deleting student: ", e);
        }
        return 0;
    }

}

