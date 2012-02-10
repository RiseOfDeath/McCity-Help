 package RiseOfDeath.Help;
 

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
 
 public class HelpPlayerListener implements Listener
 {
   private Help plugin;
 
   
   public HelpPlayerListener(Help plugin) {
	   this.plugin=plugin;
       plugin.getServer().getPluginManager().registerEvents(this, plugin);
   }
   
   @EventHandler(priority = EventPriority.LOW)
   public void onPlayerJoin(PlayerJoinEvent event) 
   {
	   plugin.deleteUser(event.getPlayer().getName());
	   plugin.addUser(event.getPlayer().getName());
	   plugin.log.info("[McCity Help][Info]" + event.getPlayer().getName() + " added");
	   
   }
   
   @EventHandler(priority = EventPriority.LOW)
   public void onPlayerQuit(PlayerQuitEvent event) 
   {
	   plugin.deleteUser(event.getPlayer().getName());
	   plugin.log.info("[McCity Help][Info]" + event.getPlayer().getName() + " removed");
   }
 }