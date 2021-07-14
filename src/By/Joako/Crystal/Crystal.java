package By.Joako.Crystal;

import By.Joako.Crystal.command.CrystalCommand;
import By.Joako.Crystal.listener.CrystalListener;
import By.Joako.Crystal.listener.JoinListener;
import By.Joako.Crystal.placeholder.PlaceHolderAPIExpansion;
import By.Joako.Crystal.sql.MySQL;
import By.Joako.Crystal.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Crystal extends JavaPlugin {

    private static Crystal instance;
    private Map<String, Integer> Crystals=new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.reloadConfig();
        if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("YML")) {
            this.getConfig().getConfigurationSection("data").getKeys(false).forEach(key ->{
                this.Crystals.put(key, (Integer) this.getConfig().get("data."+key));
            });
        }else if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("MySQL")) {
            MySQL.connect();
            MySQL.createDatabase();
        }else{
            Bukkit.getPluginManager().disablePlugins();
        }
        this.getCommand("crystal").setExecutor(new CrystalCommand());
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new CrystalListener(), this);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolderAPIExpansion().register();
        }
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m--------------------------------------"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&aLoading plugin..."));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&b"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("    &d★ &bCrystal plugin &d★"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&b"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&a ➥ &bAuthor: &dByJoako"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&a ➥ &bVesion: &d")+getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage(CC.translate("&a"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&ohttps://discord.gg/hely"));
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7&m--------------------------------------"));
    }

    @Override
    public void onDisable(){
        if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("YML")) {
            for(Map.Entry<String, Integer> entry: this.Crystals.entrySet()){
                this.getConfig().set("data."+entry.getKey(), entry.getValue());
            }
            this.saveConfig();
        }else if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("MySQL")) {
            MySQL.disconnect();
        }
        instance=null;
    }

    public static Crystal getPlugin(){
        return instance;
    }

    public Map<String, Integer> getCrystals() {
        return Crystals;
    }

    public void setCrystals(Map<String, Integer> crystals) {
        Crystals = crystals;
    }
}
