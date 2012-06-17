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

public class HelpFinder {
	
	private List<HelpTag> Tags;
	private List<HelpTopic> Subjects;
	private Help plugin;
	
	public HelpFinder()
	{
		Tags=new ArrayList<HelpTag>();
		Subjects=new ArrayList<HelpTopic>();
	}
	
	public HelpFinder(Help plugin)
	{
		Tags=new ArrayList<HelpTag>();
		Subjects=new ArrayList<HelpTopic>();
		this.plugin=plugin;
	}
	
	
	public HelpTopic findName(String Name)
	{
		for(HelpTopic i:Subjects)
		{
			if(i.getName().equalsIgnoreCase(Name))
			{
				return i;
			}
		}
		return null;
	}
		
	public List<HelpTopic> findTag(String Name)
	{
		for(HelpTag i: Tags)
		{
			if(i.getTag().equalsIgnoreCase(Name))
			{
				return i.getTopics();
			}
		}
		return null;
	}
	
	public void addTopic(HelpTopic Subject)
	{
		Subjects.add(Subject);
		List<String> sTags=Subject.getTags();
		HelpTag bufTag;
		for(String i: sTags)
		{
			bufTag=addTag(i);
			bufTag.addTopic(Subject);
		}
	}
	
	public HelpTag addTag(String Tag)
	{
		for(HelpTag i: Tags)
		{
			if(i.getTag().equalsIgnoreCase(Tag))
			{
				return i;
			}
		}
		HelpTag newTag=new HelpTag(Tag);
		Tags.add(newTag);
		return newTag;
	}
	
	public void addTag(HelpTag Tag)
	{
		addTag(Tag.getTag());
	}
	
	
	public List<HelpTag> getTags()
	{
		return Tags;
	}
	
	public void removeTopic(HelpTopic Subject)
	{
		
	}
}
