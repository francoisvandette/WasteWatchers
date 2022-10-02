package WasteWatchers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AppDao {
    Connection conn;

    // default constructor creates a db connection
    public AppDao() throws ClassNotFoundException {
        try {
            conn = DBconnect.getConnectionToDatabase();
            if (conn != null) {
                System.out.println("connected");
            } else {
                System.out.println("conection failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String pass) throws SQLException {
        String sql = "SELECT verified FROM users WHERE username =? and pswd=?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, pass);
        ResultSet result = stmt.executeQuery();
        result.next();

        if (result.getRow() > 0) {
            System.out.println("Login is good");
            if (result.getBoolean(1) == true) {
                return true;
            } else {
                System.out.println("account not verified");
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean checkUser(String email, String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE email=? or username=?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, username);
        ResultSet result = stmt.executeQuery();
        result.next();
        if (result.getRow() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUser(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email=?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet result = stmt.executeQuery();
        result.next();
        if (result.getRow() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet result = stmt.executeQuery();
        result.next();
        if (result.getRow() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean createUser(String email, String username, String password, String hash) throws SQLException {
        String sql = "INSERT INTO users (username, email, pswd, hash) VALUES (?,?,?,?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.setString(4, hash);
        stmt.executeUpdate();
        // CREATE TABEL can not be a prepared statement
        sql = "CREATE TABLE " + username + " (itemID int PRIMARY KEY auto_increment, itemName varchar(250), expiry date)"; // date format is YYYY-MM-DD
        stmt.executeUpdate(sql);
        return true;
    }

    public void insertFoodItem(String username, String itemname, java.sql.Date expiry) throws SQLException {
        String sql = "INSERT INTO " + username + " (itemName, expiry) VALUES (?,?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, itemname);
        stmt.setDate(2, expiry);
        stmt.executeUpdate();
    }

    public String getPassword(String email) throws SQLException {
        String sql = "SELECT pswd FROM users WHERE email =?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet result = stmt.executeQuery();
        result.next();

        if (result.getRow() > 0) {
            System.out.println("password is good" + result.getString(1));
            return result.getString(1);
        } else {
            return null;
        }
    }

    public ArrayList<FoodItem> getFoodTable(String username) throws SQLException, IOException {
        PreparedStatement stmt = conn.prepareStatement("select * from " + username + " ORDER BY expiry ASC");
        ArrayList<FoodItem> inventory = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                inventory.add(new FoodItem(rs.getInt(1), rs.getString(2), rs.getDate(3).toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public void deleteItem(String num, String username) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + username + " WHERE itemID = ?;");
            stmt.setString(1, num);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean activateUser(String hash) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE users SET verified='1' where hash = ?;");
        stmt.setString(1, hash);
        if (stmt.executeUpdate() == 1) {
            return true;
        } else {
            return false;
        }

    }

    public void setUsername(String oldUsername, String newUsername) {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("UPDATE users SET username=? where username=?;");
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
            stmt.executeUpdate();
            stmt.close();

            // Table rename can not be a prepared statement
            Statement stmt2 = conn.createStatement();
            String sql = "rename table " + oldUsername + " to " + newUsername + ";";
            stmt2.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPassword(String username, String newPassword) {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("UPDATE users SET pswd=? WHERE username=?;");
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<User> getUsers () {
        String sql = "SELECT email, username FROM users WHERE verified = '1';";
        PreparedStatement stmt;
        ArrayList<User> users = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                users.add(new User(result.getString(1), result.getString(2)));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<Item> getItems(String username) {
        String sql = "SELECT itemName, expiry FROM "+ username+";";
        ArrayList<Item> items = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                items.add(new Item(result.getString(1), result.getDate(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

}