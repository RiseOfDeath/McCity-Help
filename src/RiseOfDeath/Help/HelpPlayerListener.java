/*
	This file is part of McCity Help.

 	Copyright © 2011, 2012 Anton Belousov
 	
    McCity Help is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    McCity Help is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with McCity Help.  If not, see <http://www.gnu.org/licenses/>.
*/

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