package By.Joako.Crystal.placeholder;

import By.Joako.Crystal.Crystal;
import By.Joako.Crystal.sql.MySQL;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceHolderAPIExpansion extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "crystal";
    }

    @Override
    public String getAuthor() {
        return "ByJoako";
    }

    @Override
    public String getPlugin() {
        return Crystal.getPlugin().getName();
    }

    @Override
    public String getVersion() {
        return Crystal.getPlugin().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equalsIgnoreCase("amount")) {
            if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("YML")) {
                return String.valueOf(Crystal.getPlugin().getCrystals().get(player.getName()));
            }else if(Crystal.getPlugin().getConfig().getString("sql.driver").equalsIgnoreCase("MySQL")) {
                return String.valueOf(MySQL.getCrystal(player));
            }
        }
        return null;
    }
}
