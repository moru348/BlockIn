package me.moru3.blockin;

import me.moru3.sqlow.Column;
import me.moru3.sqlow.DataType;
import me.moru3.sqlow.Table;
import me.moru3.sqlow.Upsert;
import me.moru3.sqlow.exceptions.NoPropertyException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerIDs implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Upsert upsert = new Upsert("players").addKey("uuid").addValue("uuid", event.getPlayer().getUniqueId().toString()).addValue("name", event.getPlayer().getName());
        try {
            upsert.send();
        } catch (NoPropertyException | SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerIDs() {
        Table table = new Table("players")
                .addColumn(new Column("id", DataType.INTEGER).setPrimaryKey(true).setAutoIncrement(true))
                .addColumn(new Column("uuid", DataType.VARCHAR).setNotNull(true).setUniqueIndex(true).setProperty(36))
                .addColumn(new Column("name", DataType.VARCHAR).setNotNull(true).setProperty(16));
        try {
            table.send(false);
        } catch (NoPropertyException | SQLException e) {
            e.printStackTrace();
        }
    }
}
