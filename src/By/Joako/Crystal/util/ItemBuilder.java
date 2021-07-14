package By.Joako.Crystal.util;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.*;
import com.google.common.base.*;

import java.util.*;

import org.bukkit.enchantments.*;

public class ItemBuilder {
    private ItemStack stack;
    private ItemMeta meta;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, (byte) 0);
    }

    public ItemBuilder(ItemStack stack) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");
        this.stack = stack;
    }

    public ItemBuilder(Material material, int amount, byte data) {
        Preconditions.checkNotNull(material, "Material cannot be null");
        Preconditions.checkArgument(amount > 0, "Amount must be positive");
        this.stack = new ItemStack(material, amount,  data);
    }

    public ItemBuilder displayName(String name) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }
        this.meta.setDisplayName(CC.translate(name));
        return this;
    }

    public ItemBuilder loreLine(String line) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }
        boolean hasLore = this.meta.hasLore();
        List<String> lore = hasLore ? this.meta.getLore() : new ArrayList<String>();
        lore.add(hasLore ? lore.size() : 0, line);
        this.lore(CC.translate(line));
        return this;
    }

    public ItemBuilder lore(String... lore) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }
        this.meta.setLore(CC.translate(Arrays.asList(lore)));
        return this;
    }

    /**
     * Changes the durability of the current {@link ItemStack}
     *
     * @param durability
     *              the new int amount to set the ItemStack's durability to.
     *
     * @return the current instance for chainable application.
     * @since 1.0
     */
    public ItemBuilder durability(final int durability){
        this.stack.setDurability((short) durability);
        return this;
    }


    public ItemBuilder lore(List<String> lore) {
        if (this.meta == null) {
            this.meta = this.stack.getItemMeta();
        }
        this.meta.setLore(CC.translate( lore));
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        return this.enchant(enchantment, level, true);
    }

    public ItemBuilder enchant(Enchantment enchantment, int level, boolean unsafe) {
        if (unsafe && level >= enchantment.getMaxLevel()) {
            this.stack.addUnsafeEnchantment(enchantment, level);
        } else {
            this.stack.addEnchantment(enchantment, level);
        }
        return this;
    }

    public ItemBuilder data(short data) {
        this.stack.setDurability(data);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.stack.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        if (this.meta != null) {
            this.stack.setItemMeta(this.meta);
        }
        return this.stack;
    }
}