package RiseOfDeath.Help;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/*
 *  Not used now.
 *  Will be used in future.
 */

public class HelpHelpCommand implements Listener {
    private Help plugin;

    public HelpHelpCommand(Help plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
        return false;
    }
}
