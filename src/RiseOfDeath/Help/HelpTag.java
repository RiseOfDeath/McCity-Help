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
