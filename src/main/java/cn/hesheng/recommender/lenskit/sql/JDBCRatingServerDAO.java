package cn.hesheng.recommender.lenskit.sql;

import cn.hesheng.recommender.lenskit.ServerDataAccessObject;
import org.grouplens.lenskit.data.event.Event;
import org.grouplens.lenskit.data.event.Rating;
import org.grouplens.lenskit.data.sql.CachedPreparedStatement;
import org.grouplens.lenskit.data.sql.JDBCRatingDAO;

import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class JDBCRatingServerDAO extends JDBCRatingDAO implements ServerDataAccessObject {
	
/*	private final CachedPreparedStatement prepareTableInitStatement;
	private final CachedPreparedStatement addItemStatement;
	private final CachedPreparedStatement deleteItemStatement;
	private final CachedPreparedStatement addEventStatement;
	private final CachedPreparedStatement deleteEventStatement;
	private final CachedPreparedStatement getEventRevIdStatement;*/
	

	public JDBCRatingServerDAO(Connection dbc, ServerSQLStatementFactory fac, boolean closeConnection) {
	    super(dbc,fac,closeConnection);
	}

	@Override
	public void addUser(long userId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteUser(long userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addItem(long itemId, String name, String tags, String revisionId) {
		try {
			PreparedStatement ps = null;//TODO 框架的原本是获取缓存的ItemPreparedStatement但是现在JDBCRatingDAO里面是私有的
			ps.setLong(BasicServerSQLStatementFactory.ITEM_TABLE_ID_COLUMN_INDEX, itemId);
			ps.setString(BasicServerSQLStatementFactory.ITEM_TABLE_NAME_COLUMN_INDEX, name);
			ps.setString(BasicServerSQLStatementFactory.ITEM_TABLE_TAG_COLUMN_INDEX, tags);
			ps.setString(BasicServerSQLStatementFactory.ITEM_TABLE_REVISION_COLUMN_INDEX, revisionId);
			
			if (ps.executeUpdate() != 1) {
				throw new SQLException("Error adding new item to database");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void deleteItem(long itemId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addEvent(Event evt) {
		if (evt instanceof Rating) {
			Rating r = (Rating)evt;
			try {
				PreparedStatement ps = null;
//				ps.setLong(BasicServerSQLStatementFactory.EVENT_TABLE_ID_COLUMN_INDEX, r.getId());
				ps.setLong(BasicServerSQLStatementFactory.EVENT_TABLE_USER_COLUMN_INDEX, r.getUserId());
				ps.setLong(BasicServerSQLStatementFactory.EVENT_TABLE_ITEM_COLUMN_INDEX, r.getItemId());
				if (r.getPreference() == null) {
					ps.setNull(BasicServerSQLStatementFactory.EVENT_TABLE_RATING_COLUMN_INDEX, Types.DOUBLE);
				} else {
					ps.setDouble(BasicServerSQLStatementFactory.EVENT_TABLE_RATING_COLUMN_INDEX, r.getPreference().getValue());
				}
				ps.setLong(BasicServerSQLStatementFactory.EVENT_TABLE_TIMESTAMP_COLUMN_INDEX, r.getTimestamp());
				ps.setString(BasicServerSQLStatementFactory.EVENT_TABLE_REVISION_COLUMN_INDEX, generateRevisionId());
				if (ps.executeUpdate() != 1) {
					throw new SQLException("Error Adding New Event to SQL Database");
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}		
	}

	@Override
	public void deleteEvent(long eventId) {
		try {
//			PreparedStatement ps = session.deleteEventStatement();
			PreparedStatement ps = null;
			ps.setLong(1, eventId);
			if (ps.executeUpdate() != 1) {
				throw new SQLException("Error Updating SQL Database");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public String getUserRevId(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getItemRevId(long itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEventRevId(long eventId) {
		try {
//			PreparedStatement ps = session.getEventRevIdStatement();
			PreparedStatement ps = null;
			ps.setLong(BasicServerSQLStatementFactory.EVENT_TABLE_ID_COLUMN_INDEX, eventId);
			ResultSet results = ps.executeQuery();
			results.next();
			String revId = results.getString(1);
			if (!results.isLast()) {
				throw new SQLException("Query Returned Multiple Events");
			}
			return revId;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public String generateRevisionId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public void close() {
		/*try {
			session.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}*/
	}
}