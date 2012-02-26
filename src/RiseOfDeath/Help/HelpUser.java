package RiseOfDeath.Help;

import java.util.ArrayList;
import java.util.List;

public class HelpUser {
	private String PlayerName;
	//private HelpTopic LastTopic;
	private List<HelpTopic> History;
	
	public HelpUser()
	{
		History=new ArrayList<HelpTopic>();
	}
	
	public String getPlayer()
	{
		return PlayerName;
	}
	
	public void setPlayer(String PlayerName)
	{
		this.PlayerName = PlayerName;
	}
	
	@Deprecated
	public HelpTopic getLastTipic()
	{
		//return LastTopic;
		return this.readFromHistory();
	}
	
	@Deprecated
	public void setLastTipic(HelpTopic LastTopic)
	{
		//this.LastTopic = LastTopic;
		this.addToHistroy(LastTopic);
	}
	
	/*
	 * Добавляет в историю топик
	 */
	public void addToHistroy(HelpTopic LastTopic)
	{
		History.add(LastTopic);
	}
	
	/*
	 * Читает из истории, не удаляя ее последний элемент
	 */
	public HelpTopic readFromHistory()
	{
		HelpTopic buf;
		int buf1;
		buf1 = History.size();
		buf = History.get(buf1-1);
		return buf;
	}
	
	
	/*
	 * Извлекает из истории последний элемент, удаляя его из истории.
	 */
	public HelpTopic getFromHistory()
	{
		HelpTopic buf;
		int buf1;
		buf1 = History.size();
		buf = History.get(buf1-1);
		History.remove(buf1-1);
		return buf;
	}
	
	/*
	 * Очищает нахрен историю
	 */
	public void clearHistory()
	{
		History.clear();
	}
	
	/*
	 * Вы не поверите...
	 */
	public int Size()
	{
		return History.size();
	}
}
