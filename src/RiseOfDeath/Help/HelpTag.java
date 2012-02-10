package RiseOfDeath.Help;

import java.util.ArrayList;
import java.util.List;

/*
 * Не используется
 * В будущем это будет применятся для тегов и поиска по тегам.
 */
public class HelpTag {
	private List<HelpTopic> Subjects;
	private String Tag;
	
	public HelpTag()
	{
		Tag=null;
		Subjects = new ArrayList<HelpTopic>();
	}

}
