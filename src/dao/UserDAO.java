package dao;

import model.User;

import javax.sound.midi.MidiDevice;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDAO implements IUserDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
    private String username = "root";
    private String password = "dan224159";


    private static  final String INSERT_USER_SQL = "insert into users (name, email, country) values (?,?,?);";
    private static final String SELECT_USER_BY_ID = "select * from users where id = ? ;";
    private static  final String SELECT_ALL_USERS = "select * from users;";
    private static final String DELETE_USER_SQL = "delete from users where id =?;";
    private static final String UPDATE_USER_SQL = "update users set name=?, email=?, country=? where id=?;";
    private static final String SELECT_USER_BY_COUNTRY = "select * from users where country like ?";
    private static final String SELECT_USER_ORDERBY_NAME = "select * from users order by name";

    public UserDAO(){
        getConnection();
    };


    //gọi kết nối với Database
    protected Connection getConnection(){
        if (connection == null ) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(jdbcURL, username, password);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void insertUser(User user) {
        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
            String insert_into = "{call insert_user (?,?,?)}";
            CallableStatement statement = connection.prepareCall(insert_into);

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());

            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User selectUser(int id) {
        User user = null;
        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
//            preparedStatement.setInt(1, id);
            String select_user_by_id = "{call select_user_by_id(?)}";
            CallableStatement callableStatement = connection.prepareCall(select_user_by_id);
            callableStatement.setInt(1, id);

            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");

                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();

        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            String selectAll = "call selectAllUser()";
            CallableStatement statement = connection.prepareCall(selectAll);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDelete = false;
        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL);

            String deleteUser = "call deleteUser(?)";
            CallableStatement statement = connection.prepareCall(deleteUser);
            statement.setInt(1, id);
            rowDelete = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDelete;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdate = false;
        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL);
            String updateUser = "{call updateUser(?,?,?,?)}";
            CallableStatement statement = connection.prepareCall(updateUser);

            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getCountry());

            rowUpdate = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdate;
    }

    public List<User> selectUserByCountry(String countryName) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_COUNTRY);
            preparedStatement.setString(1, "%" + countryName + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");

                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users ;
    }

    public List<User> selectUserOrderByName() {
        List<User> users = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ORDERBY_NAME);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String country = resultSet.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
