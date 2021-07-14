package By.Joako.Crystal.listener;

import By.Joako.Crystal.Crystal;
import By.Joako.Crystal.sql.MySQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("YML")) {
            if(!Crystal.getPlugin().getCrystals().containsKey(event.getPlayer().getName())){
                Crystal.getPlugin().getCrystals().put(event.getPlayer().getName(), 0);
            }
        }else if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("MySQL")) {
            if(!MySQL.userExists(event.getPlayer())){
                MySQL.createUser(event.getPlayer());
            }
        }
    }
}
