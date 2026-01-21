package dev.ravn.core.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Fluent API for creating and modifying ItemStacks.
 */
public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }

    public ItemBuilder name(String name) {
        return meta(meta -> meta.setDisplayName(ColorUtil.colorize(name)));
    }

    public ItemBuilder lore(String... lore) {
        return meta(meta -> {
            List<String> list = new ArrayList<>();
            for (String s : lore) {
                list.add(ColorUtil.colorize(s));
            }
            meta.setLore(list);
        });
    }

    public ItemBuilder lore(List<String> lore) {
        return meta(meta -> {
            List<String> list = new ArrayList<>();
            for (String s : lore) {
                list.add(ColorUtil.colorize(s));
            }
            meta.setLore(list);
        });
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        return meta(meta -> meta.addEnchant(enchantment, level, true));
    }

    public ItemBuilder flags(ItemFlag... flags) {
        return meta(meta -> meta.addItemFlags(flags));
    }

    public ItemBuilder glow() {
        return addEnchant(Enchantment.DURABILITY, 1).flags(ItemFlag.HIDE_ENCHANTS);
    }

    public ItemBuilder meta(Consumer<ItemMeta> metaConsumer) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            metaConsumer.accept(meta);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
