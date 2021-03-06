package Color_yr.AllMusic;

import Color_yr.AllMusic.Command.CommandBukkit;
import Color_yr.AllMusic.Event.EventBukkit;
import Color_yr.AllMusic.Side.SideBukkit.PAPI;
import Color_yr.AllMusic.Side.SideBukkit.SideBukkit;
import Color_yr.AllMusic.bStats.MetricsBukkit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class AllMusicBukkit extends JavaPlugin {
    public static Plugin plugin;
    public static PAPI PAPI;

    @Override
    public void onEnable() {
        plugin = this;
        AllMusic.log = getLogger();
        AllMusic.Side = new SideBukkit();

        new AllMusic().init(plugin.getDataFolder());
        if (!AllMusic.isRun) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PAPI = new PAPI(this);
            if (!PAPI.register()) {
                AllMusic.log.info("§2PAPI支持已启动");
            }
        } else {
            AllMusic.log.info("§2PAPI未挂钩");
        }

        getServer().getMessenger().registerOutgoingPluginChannel(this, AllMusic.channel);
        Bukkit.getPluginCommand("music").setExecutor(new CommandBukkit());
        Bukkit.getPluginCommand("music").setTabCompleter(new CommandBukkit());
        Bukkit.getPluginManager().registerEvents(new EventBukkit(), this);
        new MetricsBukkit(this, 6720);

        AllMusic.start();
    }

    @Override
    public void onDisable() {
        AllMusic.isRun = false;
        AllMusic.stop();
    }
}
