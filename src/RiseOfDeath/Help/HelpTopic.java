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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpTopic {
	
	private Help plugin;
	private Vector <HelpTopic> Sections;
	private boolean noSub;
	private String Name;
	private String Permissions;
	private List<String> Subject;
	
	public HelpTopic()
	{
		noSub=true;
		Sections=new  Vector<HelpTopic>();
		Permissions = "mccityhelp.user";
		Subject=new ArrayList<String>();
	}
	public HelpTopic(Help plugin)
	{
		noSub=true;
		Sections=new  Vector<HelpTopic>();
		Permissions = "mccityhelp.user";
		Subject=new ArrayList<String>();
		this.plugin=plugin;
	}
	
	
	public boolean isNoSub()
	{
		return noSub;
	}
	
	public void setPermissions(String Permissions)
	{
		this.Permissions=Permissions;
	}
	
	public String getPermissions()
	{
		return Permissions;
	}
	
	public void setSubject(List<String> Subject)
	{
		this.Subject = Subject;
	}
	
	public List<String> getSubject()
	{
		return Subject;
	}
	
	@Deprecated
	public void printSubject(Player Player)
	{
		printSubject(Player, 8, 1);
	}
	
	public void printSubject(Player Player, int strPerPage, int Page)
	{

		if(this.noSub)
		{
			printTopicText(Player,strPerPage,Page);
		}
		else
		{
			printTopicList(Player,strPerPage,Page);
		}
	}
	
	private void printTopicText(Player Player, int strPerPage, int Page)
	{
		if(plugin.hasPerm(Player,this.getPermissions(), true))
		{
			Player.sendMessage(ChatColor.AQUA + "Subject:");
			for(int i=(Page-1)*strPerPage;(i<Page*strPerPage)&(i<Subject.size());i++)
			{
				Player.sendMessage(Subject.get(i));
			}
			if(Page*strPerPage>=Subject.size())
			{
				Player.sendMessage(ChatColor.AQUA + "End subject");
			}
			else
			{
				Player.sendMessage(ChatColor.AQUA + "End page.");
			}
		}
		else
		{
			Player.sendMessage(ChatColor.AQUA + "Subject:");
			Player.sendMessage(ChatColor.WHITE + "This subject is classified");
			Player.sendMessage(ChatColor.AQUA + "End subject");
		}
		
	}
	
	private void printTopicList(Player Player, int strPerPage, int Page)
	{
		Player.sendMessage(ChatColor.AQUA + "Subjects list:");
		String str;
		for(int i=(Page-1)*strPerPage;(i<Page*strPerPage)&(i<Sections.size());i++)
		{
			str="";
			
			if(Sections.get(i).noSub)
			{
				str=ChatColor.GREEN + Sections.get(i).getName();
			}
			else
			{
				str=ChatColor.YELLOW + Sections.get(i).getName();
			}
			
			if(!plugin.hasPerm(Player,this.getPermissions(), true))
			{
				str+=" [CLASSIFIED]";
			}
			str+=ChatColor.WHITE;
			Player.sendMessage(str);
			str=null;
		}
		if(Page*strPerPage>=Sections.size())
		{
			Player.sendMessage(ChatColor.AQUA + "End Subjects list");
		}
		else
		{
			Player.sendMessage(ChatColor.AQUA + "End page.");
		}
	}
	
	
	public String getSubjectString()
	{
		return this.toString();
	}
	
	public void addSubjectString(String SubjectString)
	{
		this.Subject.add(SubjectString);
	}
	
	public void setName(String Name)
	{
		this.Name = Name;
	}
	
	public String getName()
	{
		return Name;
	}

	public void setNoSub(boolean noSub)
	{
		this.noSub=noSub;
	}
	

	public boolean addSection(HelpTopic Section)
	{
		if(this.isNoSub())
		{
			return false;
		}
		else
		{
			this.Sections.addElement(Section);
			return true;
		}
	}
	
	public Vector<HelpTopic> getSections()
	{
		return Sections;
		
	}
	
	public String toString()
	{
		String strBuf=new String();
		for(String i : Subject)
		{
			strBuf+=i;
			strBuf+="<endl>";
		}
		return strBuf;
	}

}
