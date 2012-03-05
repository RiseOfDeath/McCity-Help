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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijikokun.bukkit.Permissions.Permissions;

public class Help extends JavaPlugin{

	private Permissions permissions = null;
	private FileConfiguration cfg;
	
	
	public static Logger log;
    public static Server server;
    protected PluginManager pm;
    
    private Vector<HelpUser> Users;
    private HelpTopic Main;
    private Map<String, HelpTopic> Hide;
    
    
    @SuppressWarnings("unused")
	private HelpPlayerListener playerListener;
    
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		log.info("[McCity Help] Dasabled.");
		
	}

	@Override
	public void onEnable() {
		playerListener = new HelpPlayerListener(this);
		Users = new Vector<HelpUser>();
		Hide=new HashMap<String, HelpTopic>();
		Main=new HelpTopic();
		Main.setNoSub(false);
		Main.setName("Main");
		server=this.getServer();
		pm = server.getPluginManager();
		log=Logger.getLogger("Minecraft");
		permissions = (Permissions)checkPlugin("Permissions");
		if(permissions==null)
		{
			log.info("[McCity Help] No any permisions plugin found.");
		}
		
		if(server.getOnlinePlayers().length!=0)
		{
			for(int i=0;i<server.getOnlinePlayers().length;i++)
			{
				addUser(server.getOnlinePlayers()[i].getName());
			}
		}
		
		File file=new File(this.getDataFolder(), "main");
		if(!file.isDirectory())
		{
			file.delete();
			file.mkdir();
			file=new File(this.getDataFolder(), "main/about.yml");
			cfg = YamlConfiguration.loadConfiguration(file);
			cfg.set("Info.Title", "About");
			cfg.set("Info.Tags", "About, Info, Triva");
			cfg.set("Info.Permissions", "mccityhelp.user");
			cfg.set("Text", "This is help plugin by " + ChatColor.GREEN +"RiseOfDeath.<endl>Made special for " + ChatColor.GREEN+"McCity" + ChatColor.WHITE+" server.<endl>Visit us at " +ChatColor.GREEN+"http://minecraft-mccity.ru/plugins:eng<endl>§kWe have cookies");
			try {
				cfg.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		loadTitles();
		
		//getServer().getPluginManager().registerEvents(new HelpPlayerListener(), this);
		//pm.registerEvent(Event.Type.PLAYER_JOIN, this.playerListener, Event.Priority.Normal, this);
		log.info("[McCity Help] Enabled");
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 

	{
		String strBuf;
		if(label.equalsIgnoreCase("help"))
		{
			if(hasPerm((Player)sender,"mccityhelp.user", true))
			{
				if(args.length!=0)
				{		
					for(int i=0;i<Users.size();i++)
					{
						
						if(Users.get(i).getPlayer().equalsIgnoreCase(sender.getName()))
						{
							try
							{
								if(Users.get(i).getLastTipic().getSections().size()==0)
								{
									Users.get(i).setLastTipic(Main);
									strBuf="Help topics:";
									sender.sendMessage(strBuf);
									strBuf=ChatColor.RED + "-Empty-";
									sender.sendMessage(strBuf);
									return true;
								}
							}
							catch(Exception ex)
							{
								Users.get(i).setLastTipic(Main);
								strBuf="Help topics:";
								sender.sendMessage(strBuf);
								log.info("[McCity Help][Error]" + "No sub sections");
								return true;
							}
							for(int j=0;j<Users.get(i).getLastTipic().getSections().size();j++)
							{
								
								if(Users.get(i).getLastTipic().getSections().get(j).getName().equalsIgnoreCase(args[0]))
								{
									if(Users.get(i).getLastTipic().getSections().get(j).isNoSub())
									{
										if(hasPerm((Player)sender,Users.get(i).getLastTipic().getSections().get(j).getPermissions(), true))
										{
											if(args.length>1)
											{
												Users.get(i).getLastTipic().getSections().get(j).printSubject((Player)sender,8, Integer.parseInt(args[1]));
											}
											else
											{
												Users.get(i).getLastTipic().getSections().get(j).printSubject((Player)sender);
											}
										}
										else
										{
											sender.sendMessage(ChatColor.AQUA + "Subject:");
											sender.sendMessage(ChatColor.WHITE + "This subject is classified");
										}
										return true;
									}
									else
									{
										strBuf="Help topics:";
										sender.sendMessage(strBuf);
										try
										{
											if(Users.get(i).getLastTipic().getSections().get(j).getSections().size()==0)
											{
												strBuf="Empty folder :(";
												sender.sendMessage(strBuf);
												sender.sendMessage("Topic not found. Going to main page[1]");
												Users.get(i).setLastTipic(Main);
												return true;
											}
											for(int k=0;k < Users.get(i).getLastTipic().getSections().get(j).getSections().size();k++)
											{
												
												strBuf=new String();
												
												if(Users.get(i).getLastTipic().getSections().get(j).getSections().get(k).isNoSub())
												{
													if(hasPerm((Player)sender,Users.get(i).getLastTipic().getSections().get(j).getSections().get(k).getPermissions(), true))
													{
														strBuf=ChatColor.GREEN + Users.get(i).getLastTipic().getSections().get(j).getSections().get(k).getName() + " [TOPIC]";
													}
													else
													{
														strBuf=ChatColor.RED + Users.get(i).getLastTipic().getSections().get(j).getSections().get(k).getName() + " [TOPIC][CLASSIFIED]";
													}
												}
												else
												{
													strBuf=ChatColor.YELLOW + Users.get(i).getLastTipic().getSections().get(j).getSections().get(k).getName() + " [SUBFOLDER]";
												}											
												sender.sendMessage(strBuf);
											}
										}
										catch(Exception ex)
										{
											ex.printStackTrace();
											return true;
										}
										Users.get(i).setLastTipic(Users.get(i).getLastTipic().getSections().get(j));
										//Users.get(i).addToHistroy(Users.get(i).getLastTipic().getSections().get(j));
										return true;
									}
								}
								else
								{
								}
								
							}
							sender.sendMessage("Topic not found. Going to main page");
							Users.get(i).setLastTipic(Main);
							return true;	
						}
						else
						{
						}
						
					}
					
					log.info("[McCity Help][Error]" + "This is not " + sender.getName() + "...");
					return true;
					
				}
				else
				{
					strBuf="Help topics:";
					sender.sendMessage(strBuf);
					for(int i=0;i < Main.getSections().size();i++)
					{
						strBuf=new String();
						if(Main.getSections().get(i).isNoSub())
						{
							strBuf=ChatColor.GREEN + Main.getSections().get(i).getName()  + " [TOPIC]";
						}
						else
						{
							strBuf=ChatColor.YELLOW + Main.getSections().get(i).getName() + " [SUBFOLDER]";
						}
						sender.sendMessage(strBuf);
						
					}
					if(Main.getSections().size()>0)
					{
						strBuf=ChatColor.GREEN + "For example, try to write " + ChatColor.GOLD + "/help " + Main.getSections().get(0).getName();
						sender.sendMessage(strBuf);
						strBuf="Not case sensitive";
						sender.sendMessage(strBuf);
					}
					else
					{
						strBuf="Help is empty. Possibly the server owner have forgotten to fill it.";
						sender.sendMessage(strBuf);
					}
					for(int i=0;i<Users.size();i++)
					{
						if(Users.get(i).getPlayer().equalsIgnoreCase(sender.getName()))
						{
							Users.get(i).clearHistory();
							Users.get(i).addToHistroy(Main);
						}
					}
					return true;
				}
			}
			else
			{
				sender.sendMessage("[McCity Help]: Sorry, but you haven't got permissions");
				return true;
			}
		}
		if(label.equalsIgnoreCase("adminhelp"))
		{
			if(hasPerm((Player)sender,"mccityhelp.admin", sender.isOp()))
			{
				if(args.length>0)
				{
					if(args[0].equalsIgnoreCase("new"))
					{
						if(args.length>1)
						{
							HelpTopic buf;
							buf=new HelpTopic();
							buf.setName(args[1]);
							buf.setNoSub(true);
							//buf.setSubjectString("No subject");
							Hide.put(sender.getName(), buf);
							sender.sendMessage("[McCity Help] Created empty topic with name "+ args[1]);
							return true;
						}
						else
						{
							sender.sendMessage("[McCity Help] I should guess the title?");
							return true;
						}
					}
					if(args[0].equalsIgnoreCase("subject"))
					{
						sender.sendMessage("[McCity Help] This metod is outdated");
						sender.sendMessage("[McCity Help] Use '/adminhelp addstring' to add a new line");
						return true;
					}
					if(args[0].equalsIgnoreCase("reload"))
					{
						Main=new HelpTopic();
						Main.setNoSub(false);
						Main.setName("Main");
						loadTitles();
						return true;
					}
					
					if(args[0].equalsIgnoreCase("addstring"))
					{
						if(args.length>1)
						{
							String str=new String();
							for(int i=1; i< args.length;i++)
							{
								str +=args[i] + " ";
							}
							
							Hide.get(sender.getName()).addSubjectString(str);
							sender.sendMessage("[McCity Help] Subject was assigned");
							return true;
						}
						else
						{
							return false;
						}	
					}
					
					if(args[0].equalsIgnoreCase("permission"))
					{
						if(args.length>1)
						{
							Hide.get(sender.getName()).setPermissions(args[1]);
							sender.sendMessage("[McCity Help] Set permission "+args[1]);
							return true;
						}
						else
						{
							return false;
						}	
					}
					
					if(args[0].equalsIgnoreCase("save"))
					{
						if(args.length>1)
						{
							saveTitle(Hide.get(sender.getName()), args[1]);
							sender.sendMessage("[McCity Help] Saved into folder 'main/" + args[1] + "/'");
							return true;
						}
						else
						{
							saveTitle(Hide.get(sender.getName()));
							sender.sendMessage("[McCity Help] Saved into folder 'main/'");
							return true;
						}
					}
					
					if(args[0].equalsIgnoreCase("color"))
					{
						sender.sendMessage(ChatColor.AQUA + "Text Colors:");
						sender.sendMessage("§0This is '0' color");
						sender.sendMessage("§1This is '1' color");
						sender.sendMessage("§2This is '2' color");
						sender.sendMessage("§3This is '3' color");
						sender.sendMessage("§4This is '4' color");
						sender.sendMessage("§5This is '5' color");
						sender.sendMessage("§6This is '6' color");
						sender.sendMessage("§7This is '7' color");
						sender.sendMessage("§8This is '8' color");
						sender.sendMessage("§9This is '9' color");
						sender.sendMessage("§aThis is 'a' color");
						sender.sendMessage("§bThis is 'b' color");
						sender.sendMessage("§cThis is 'c' color");
						sender.sendMessage("§dThis is 'd' color");
						sender.sendMessage("§eThis is 'e' color");
						sender.sendMessage("§fThis is 'f' color");
						//sender.sendMessage("§kThis is 'k' color");
						return true;
					}
				}
			}
			else
			{
				sender.sendMessage("[McCity Help] Sorry, but you haven't got permissions");
				return true;
			}
		}
		return false;
	}
	
	
	private void saveTitle(HelpTopic Title, String path)
	{
		FileConfiguration cfg;	
		File fileBuf;
		fileBuf=new File(getDataFolder(), "main/" + path + "/" + Title.getName().toLowerCase() + ".yml");
		cfg = YamlConfiguration.loadConfiguration(fileBuf);
		cfg.set("Info.Title", Title.getName());
		cfg.set("info.Tags", "NoTags");
		cfg.set("info.Permissions", Title.getPermissions());
		cfg.set("Text", Title.getSubjectString());
		try {
			cfg.save(fileBuf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveTitle(HelpTopic Title)
	{
		FileConfiguration cfg;	
		File fileBuf;
		fileBuf=new File(getDataFolder(),"main/"+ Title.getName().toLowerCase() + ".yml");
		cfg = YamlConfiguration.loadConfiguration(fileBuf);
		cfg.set("Info.Title", Title.getName());
		cfg.set("info.Tags", "NoTags");
		cfg.set("info.Permissions", Title.getPermissions());
		cfg.set("Text", Title.getSubjectString());
		try {
			cfg.save(fileBuf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Main.addSection(Title);
		
	}
	
	private void loadTitles()
	{
		HelpTopic buf,buf1;
		
		File fileBuf;
		fileBuf=new File(getDataFolder(),"main");
		if(fileBuf.listFiles()==null)
		{
			return;
		}
		
		for(int i=0;i<fileBuf.listFiles().length;i++)
		{
			
			if(fileBuf.listFiles()[i].isDirectory())
			{
				buf=new HelpTopic();
				buf.setNoSub(false);
				buf.setName(fileBuf.listFiles()[i].getName());
				if(fileBuf.listFiles()[i].listFiles().length==0)
				{
					Main.addSection(buf);
				}
				else
				{
					//buf.setSubjectString(getSubjectString(fileBuf));
					for(int j=0;j< fileBuf.listFiles()[i].listFiles().length;j++)
					{
						buf1=loadTitles(fileBuf.listFiles()[i].listFiles()[j]);
						if(buf1!=null)
						{
							buf.addSection(buf1);
						}
					}
					Main.addSection(buf);
				}
				
				
			}
			else
			{
				buf=new HelpTopic();
				buf.setNoSub(true);
				buf.setSubject(getSubject(fileBuf.listFiles()[i]));
				buf.setName(getName(fileBuf.listFiles()[i]));
				buf.setPermissions(getPermissions(fileBuf.listFiles()[i]));
				Main.addSection(buf);
			}
		}
	}
	
	private HelpTopic loadTitles(File fileBuf)
	{
		HelpTopic buf,buf1;
			if(fileBuf.isDirectory())
			{
				buf=new HelpTopic();
				buf.setNoSub(false);
				buf.setSubject(getSubject(fileBuf));
				buf.setName(fileBuf.getName());
				for(int j=0;j< fileBuf.listFiles().length;j++)
				{
					buf1=loadTitles(fileBuf.listFiles()[j]);
					if(buf1!=null)
					{
						buf.addSection(buf1);						
					}
				}
				return buf;
			}
			else
			{
				buf=new HelpTopic();
				buf.setNoSub(true);
				//buf.setSubjectString(getSubjectString(fileBuf));
				buf.setSubject(getSubject(fileBuf));
				buf.setName(getName(fileBuf));
				buf.setPermissions(getPermissions(fileBuf));
				return buf;
			}
	}
	
	private String getSubjectString(File textFile)
	{
		FileConfiguration cfg;
		String strBuf;
		
		cfg = YamlConfiguration.loadConfiguration(textFile);
		strBuf=cfg.getString("Text");
		if(strBuf==null)
		{
			log.info("[McCity Help] No any content in " + textFile.getName());
			strBuf=ChatColor.RED + "No any text or wrong text.";
		}
		strBuf=strBuf.replaceAll("&0", "§0");
		strBuf=strBuf.replaceAll("&1", "§1");
		strBuf=strBuf.replaceAll("&2", "§2");
		strBuf=strBuf.replaceAll("&3", "§3");
		strBuf=strBuf.replaceAll("&4", "§4");
		strBuf=strBuf.replaceAll("&5", "§5");
		strBuf=strBuf.replaceAll("&6", "§6");
		strBuf=strBuf.replaceAll("&7", "§7");
		strBuf=strBuf.replaceAll("&8", "§8");
		strBuf=strBuf.replaceAll("&9", "§9");
		strBuf=strBuf.replaceAll("&a", "§a");
		strBuf=strBuf.replaceAll("&b", "§b");
		strBuf=strBuf.replaceAll("&c", "§c");
		strBuf=strBuf.replaceAll("&d", "§d");
		strBuf=strBuf.replaceAll("&e", "§e");
		strBuf=strBuf.replaceAll("&f", "§f");
		strBuf=strBuf.replaceAll("&k", "§k");
		return strBuf;
	}
	
	private List<String> getSubject(File textFile)
	{
		List<String> listBuf=new ArrayList<String>();
		String strBuf=getSubjectString(textFile);
		listBuf= getListFromString(strBuf, "<endl>");
		return listBuf;
	}
	
	private List<String> getListFromString(String str, String regex)
	{
		
		List<String> listBuf=new ArrayList<String>();
		String [] strBuf;
		if(str==null)
		{
			return listBuf;
		}
		strBuf=str.split(regex);
		for(int i=0; i < strBuf.length;i++)
		{
			listBuf.add(strBuf[i]);
		}
		return listBuf;
	}
	
	private String getName(File textFile)
	{

		FileConfiguration cfg;
		String strBuf;
		
		cfg = YamlConfiguration.loadConfiguration(textFile);
		strBuf=cfg.getString("Info.Title");
		if(strBuf==null)
		{
			strBuf=ChatColor.RED + "Untitled";
		}
		return strBuf;
	}
	
	private String getPermissions(File textFile)
	{
		FileConfiguration cfg;
		String strBuf;
		
		cfg = YamlConfiguration.loadConfiguration(textFile);
		strBuf=cfg.getString("Info.Permissions");
		if(strBuf==null)
		{
			strBuf="mccityhelp.user";
		}
		return strBuf;
	}
	
	
	public void addUser(String PlayerName)
	{
		HelpUser buf = new HelpUser();
		buf.setPlayer(PlayerName);
		buf.setLastTipic(this.Main);
		Users.add(buf);
	}
	
	public void deleteUser(String PlayerName)
	{
		for(int i=0;i<Users.size();i++)
		{
			if(Users.get(i).getPlayer().equalsIgnoreCase(PlayerName))
			{
				Users.remove(i);
			}
		}
	}	
	
	private Plugin checkPlugin(String p) {
		Plugin plugin;
		try{
		log.info(pm.getPlugin(p).toString());
		plugin = pm.getPlugin(p);
		}
		catch(NullPointerException ex)
		{
			return null;
		}
		return checkPlugin(plugin);
	}
	
	private Plugin checkPlugin(Plugin plugin) {
		if (plugin != null/* && plugin.isEnabled()*/) {
			log.info("[McCity Help] Found " + plugin.getDescription().getName() + " (v" + plugin.getDescription().getVersion() + ")");
			return plugin;
		}
		return null;
	}
	
	/*
	 * Check whether the player has the given permissions.
	 */
	public boolean hasPerm(Player player, String perm, boolean def) {
		if (permissions != null) {
			return permissions.getHandler().has(player, perm);
		} else {
			return def;
		}
	}

}
