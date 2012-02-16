package RiseOfDeath.Help;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpTopic {
	
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
	
	public void printSubject(Player Player)
	{
		Player.sendMessage(ChatColor.AQUA + "Subject:");
		for(String str : Subject)
		{
			Player.sendMessage(str);
		}
		Player.sendMessage(ChatColor.AQUA + "End subject.");
	}
	
	public void printSubject(Player Player, int strPerPage, int Page)
	{
		Player.sendMessage(ChatColor.AQUA + "Subject:");
		for(int i=(Page-1)*strPerPage;i<Page*strPerPage;i++)
		{
			Player.sendMessage(Subject.get(i));
		}
		Player.sendMessage(ChatColor.AQUA + "End subject.");
	}
	
	
	public String getSubjectString()
	{
		/*String strBuf=new String();
		for(String i : Subject)
		{
			strBuf+=i;
			strBuf+="<endl>";
		}*/
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
	
	/*
	 * А что делать.. надо такие вещи делать, надо...
	 * Только даже не знаю, тчо тут вернуть можно.. не выдавать же все в  одной строке...
	 */
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
