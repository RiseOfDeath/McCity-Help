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
		if(buf1==0)
		{
			return null;
		}
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
		if(buf1==0)
		{
			return null;
		}
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
