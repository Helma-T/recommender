package cn.hesheng.recommender.lenskit;

import org.grouplens.lenskit.data.event.Event;

/**
 * 服务端数据操作接口
 */
public interface ServerDataAccessObject{
	
	public void addUser(long userId);
		
	public void deleteUser(long userId);
	
	public void addItem(long itemId, String name, String tags, String revisionId);
	
	public void deleteItem(long itemId);
	
	public void addEvent(Event evt);
	
	public void deleteEvent(long eventId);
	
	public String getUserRevId(long userId);
	
	public String getItemRevId(long itemId);

	public String getEventRevId(long eventId);
	
}
