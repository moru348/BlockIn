package me.moru3.blockin;

import me.moru3.sqlow.DataType;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;

public class DataTypeMc {
    public static DataType<?> ITEMSTACK = new DataType<>("LONGTEXT", false, false, false, false, false, false, 2147483647, DataTypeMc::fromItemStack, DataTypeMc::toItemStack, ItemStack.class, 1);
    public static DataType<?> OFFLINEPLAYER = new DataType<>("LONGTEXT", false, false, false, false, false, false,2147483647, DataTypeMc::fromOfflinePlayer, DataTypeMc::toOfflinePlayer, OfflinePlayer.class, 1);

    public static String fromOfflinePlayer(Object offlinePlayer) {
        YamlConfiguration config = new YamlConfiguration();
        OfflinePlayer player = (OfflinePlayer) offlinePlayer;
        config.set("player", player);
        return DatatypeConverter.printBase64Binary(config.saveToString().getBytes(StandardCharsets.UTF_8));
    }

    public static OfflinePlayer toOfflinePlayer(Object obj) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(new String(DatatypeConverter.parseBase64Binary((String) obj), StandardCharsets.UTF_8));
        } catch (IllegalArgumentException | InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config.getOfflinePlayer("player", null);
    }

    public static String fromItemStack(Object itemStack) {
        YamlConfiguration config = new YamlConfiguration();
        ItemStack item = (ItemStack) itemStack;
        config.set("item", item);
        return DatatypeConverter.printBase64Binary(config.saveToString().getBytes(StandardCharsets.UTF_8));
    }

    public static ItemStack toItemStack(Object obj) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(new String(DatatypeConverter.parseBase64Binary((String) obj), StandardCharsets.UTF_8));
        } catch (IllegalArgumentException | InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config.getItemStack("item", null);
    }
}
