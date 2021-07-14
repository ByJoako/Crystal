package By.Joako.Crystal.listener;

import By.Joako.Crystal.Crystal;
import By.Joako.Crystal.sql.MySQL;
import By.Joako.Crystal.util.CC;
import By.Joako.Crystal.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CrystalListener implements Listener {

    public static Inventory getCrystalInv(){
        Inventory inv= Bukkit.createInventory(null, Crystal.getPlugin().getConfig().getInt("menu.rows")*9,
                CC.translate(Crystal.getPlugin().getConfig().getString("menu.title")));
        for(String items:Crystal.getPlugin().getConfig().getConfigurationSection("menu.items").getKeys(false)){
            ItemStack item=new ItemBuilder(Material.getMaterial(Crystal.getPlugin().getConfig().getString("menu.items."+items+".material")))
                    .amount(Crystal.getPlugin().getConfig().getInt("menu.items."+items+".amount"))
                    .displayName(Crystal.getPlugin().getConfig().getString("menu.items."+items+".displayname"))
                    .lore(Crystal.getPlugin().getConfig().getStringList("menu.items."+items+".lore")).build();
            inv.setItem(Crystal.getPlugin().getConfig().getInt("menu.items."+items+".slot")-1, item);
        }
        return inv;
    }

    @EventHandler
    public void onClickInv(InventoryClickEvent event){
        if(event.getInventory().getTitle().equalsIgnoreCase(CC.translate(Crystal.getPlugin().getConfig().getString("menu.title")))){
            event.setCancelled(true);
            for(String items:Crystal.getPlugin().getConfig().getConfigurationSection("menu.items").getKeys(false)){
                if(event.getSlot()==Crystal.getPlugin().getConfig().getInt("menu.items."+items+".slot")-1){
                    Player player= (Player) event.getWhoClicked();

                    if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("YML")) {
                        if(Crystal.getPlugin().getConfig().getInt("menu.items."+items+".price")<= Crystal.getPlugin().getCrystals().get(player.getName())){
                            int crystal=Crystal.getPlugin().getCrystals().get(player.getName())-Crystal.getPlugin().getConfig().getInt("menu.items."+items+".price");
                            Crystal.getPlugin().getCrystals().put(player.getName(), crystal);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Crystal.getPlugin().getConfig().getString("menu.items."+items+".command").replace("%player%", player.getName()));
                            player.sendMessage(CC.translate(Crystal.getPlugin().getConfig().getString("menu.buy")
                                    .replace("%item%", Crystal.getPlugin().getConfig().getString("menu.items."+items+".displayname"))
                                    .replace("%amount%", String.valueOf(Crystal.getPlugin().getConfig().getInt("menu.items."+items+".amount")))));
                            return;
                        }else{
                            player.sendMessage(CC.translate(Crystal.getPlugin().getConfig().getString("menu.enough_money")));
                        }
                    }else if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("MySQL")) {
                        if(Crystal.getPlugin().getConfig().getInt("menu.items."+items+".price")<= MySQL.getCrystal(player)){
                            MySQL.setCrystal(player, MySQL.getCrystal(player)-Crystal.getPlugin().getConfig().getInt("menu.items."+items+".price"));
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Crystal.getPlugin().getConfig().getString("menu.items."+items+".command").replace("%player%", player.getName()));
                            player.sendMessage(CC.translate(Crystal.getPlugin().getConfig().getString("menu.buy")
                                    .replace("%item%", Crystal.getPlugin().getConfig().getString("menu.items."+items+".displayname"))
                                    .replace("%amount%", String.valueOf(Crystal.getPlugin().getConfig().getInt("menu.items."+items+".amount")))));
                            return;
                        }else{
                            player.sendMessage(CC.translate(Crystal.getPlugin().getConfig().getString("menu.enough_money")));
                        }
                    }
                }
            }
        }
    }

}

