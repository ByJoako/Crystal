package By.Joako.Crystal.command;

import By.Joako.Crystal.Crystal;
import By.Joako.Crystal.listener.CrystalListener;
import By.Joako.Crystal.sql.MySQL;
import By.Joako.Crystal.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrystalCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length==0){
            Player player= (Player) sender;
            if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("YML")) {
                sender.sendMessage(CC.translate(Crystal.getPlugin().getConfig().getString("cristal.your")
                        .replace("%crystal%", String.valueOf(Crystal.getPlugin().getCrystals().get(player.getName())))));
            }else if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("MySQL")) {
                sender.sendMessage(CC.translate(Crystal.getPlugin().getConfig().getString("cristal.your")
                        .replace("%crystal%", String.valueOf(MySQL.getCrystal(player)))));
            }
        }else if(args.length==1){
            if(args[0].equalsIgnoreCase("shop")){
                Player player= (Player) sender;
                player.openInventory(CrystalListener.getCrystalInv());
            }else{
                Player player= Bukkit.getServer().getPlayer(args[0]);
                if(player==null){
                    sender.sendMessage(CC.translate("&6Player '&f" + args[0] + "&6' not found."));
                    return true;
                }
                if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("YML")) {
                    sender.sendMessage(CC.translate(Crystal.getPlugin().getConfig().getString("cristal.other")
                            .replace("%player%", player.getName())
                            .replace("%crystal%", String.valueOf(Crystal.getPlugin().getCrystals().get(player.getName())))));
                }else if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("MySQL")) {
                    sender.sendMessage(CC.translate(Crystal.getPlugin().getConfig().getString("cristal.other")
                            .replace("%player%", player.getName())
                            .replace("%crystal%", String.valueOf(MySQL.getCrystal(player)))));
                }
            }
        }else if(sender.hasPermission("crystal.manager")){
            if(args.length==3){
                Player player= Bukkit.getServer().getPlayer(args[0]);
                if(player==null){
                    sender.sendMessage(CC.translate("&6Player '&f" + args[0] + "&6' not found."));
                    return true;
                }
                if(args[1].equalsIgnoreCase("add")){
                    if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("YML")) {
                        int crystal=Crystal.getPlugin().getCrystals().get(player.getName())+Integer.parseInt(args[2]);
                        Crystal.getPlugin().getCrystals().put(player.getName(), crystal);
                    }else if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("MySQL")) {
                    MySQL.setCrystal(player, MySQL.getCrystal(player)+Integer.parseInt(args[2]));
                    }
                    sender.sendMessage(CC.translate("&e"+args[2]+" crystal was added"));
                }else if(args[1].equalsIgnoreCase("remove")){
                    if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("YML")) {
                        int crystal=Crystal.getPlugin().getCrystals().get(player.getName())-Integer.parseInt(args[2]);
                        Crystal.getPlugin().getCrystals().put(player.getName(), crystal);
                    }else if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("MySQL")) {
                        MySQL.setCrystal(player, MySQL.getCrystal(player) - Integer.parseInt(args[2]));
                    }
                    sender.sendMessage(CC.translate("&e"+args[2]+" crystal was removed"));
                }
            }else{
                sender.sendMessage(CC.translate("&cUse: /"+command.getLabel()+ " <player> add <amount>"));
                sender.sendMessage(CC.translate("&cUse: /"+command.getLabel()+ " <player> remove <amount>"));
            }
        }else{
            sender.sendMessage(CC.translate("&cUse: /"+command.getLabel()));
        }
        return false;
    }
}
