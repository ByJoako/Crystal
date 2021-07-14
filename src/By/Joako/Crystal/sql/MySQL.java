package By.Joako.Crystal.sql;

import By.Joako.Crystal.Crystal;
import By.Joako.Crystal.util.CC;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQL {
    public static Connection connection;

    public static void connect(){
        String host = Crystal.getPlugin().getConfig().getString("sql.address");
        int port = Crystal.getPlugin().getConfig().getInt("sql.port");
        String user = Crystal.getPlugin().getConfig().getString("sql.username");
        String database = Crystal.getPlugin().getConfig().getString("sql.database");
        String password = Crystal.getPlugin().getConfig().getString("sql.password");
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&autoReconnectForPools=true&interactiveClient=true&characterEncoding=UTF-8", user, password);
            Bukkit.getConsoleSender().sendMessage(CC.translate("&aMySQL connection success"));
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cMySQL connection failed"));
            e.printStackTrace();
        }
    }

    private static boolean isConnected(){
        return connection != null;
    }

    public static void disconnect(){
        if(!isConnected()){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void createDatabase() {
        try {
            if (MySQL.connection.isClosed()) {
                MySQL.connect();
            }
            PreparedStatement ps = MySQL.connection.prepareStatement("CREATE TABLE IF NOT EXISTS crystal(Player VARCHAR(100), Crystals INTEGER)");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean userExists(Player player) {
        try {
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM crystal WHERE Player =?");
            ps.setString(1, player.getName());

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void createUser(Player player) {
        try {
            if (MySQL.connection.isClosed()) {
                MySQL.connect();
            }
            PreparedStatement ps = MySQL.connection.prepareStatement("INSERT INTO crystal(`Player`,`Crystals`) VALUES (?, ?)");
            ps.setString(1, player.getName());
            ps.setInt(2, 0);
            ps.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public static Integer getCrystal(Player player) {
        try {
            PreparedStatement ps = MySQL.connection.prepareStatement("SELECT Crystals FROM crystal WHERE Player =?");
            ps.setString(1, player.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Crystals");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setCrystal(Player player, Integer crystal) {
        try {
            PreparedStatement ps = MySQL.connection.prepareStatement("UPDATE crystal SET Crystals =? WHERE Player=?");
            ps.setInt(1, crystal);
            ps.setString(2, player.getName());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
