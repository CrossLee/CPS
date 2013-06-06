package com.cemso.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <b>User</b>: Jazze Wang<br>
 * <b>Date</b>: 2012-04-15<br>
 * <b>Note</b>: Java实现的Sequence工具
 */
public class SequenceUtil {
	
	/**
	 * 单例模式
	 */
	private static SequenceUtil instance = new SequenceUtil();
	
	/**
	 * 存放序列的MAP
	 */
	private Map<String, KeyInfo> keyMap = new HashMap<String, KeyInfo>(20);
	private static final int POOL_SIZE = 1;

	/**
	 * 防止外部实例化
	 */
	private SequenceUtil() {
	}

	/**
	 * 单例模式,获取单例
	 * 
	 * @return SequenceUtils的单例对象
	 */
	public static SequenceUtil getInstance() {
		return instance;
	}

	/**
	 * 获取下一个sequence值
	 * 
	 * @param keyName
	 *            Sequence名称
	 * @return 下一个Sequence键值
	 */
	public synchronized int getNextKeyValue(String keyName) {
		KeyInfo keyInfo = null;
		Integer keyObject = null;
		try {
			if (keyMap.containsKey(keyName)) {
				keyInfo = keyMap.get(keyName);
			} else {
				keyInfo = new KeyInfo(keyName, POOL_SIZE);
				keyMap.put(keyName, keyInfo);
			}
			keyObject = keyInfo.getNextKey();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return keyObject;
	}
}

class KeyInfo {
	
	/**
	 * 当前Sequence载体的最大值
	 */
	private int maxKey;
	
	/**
	 * 当前Sequence的最小值
	 */
	private int minKey;
	
	/**
	 * 下一个Sequence值
	 */
	private int nextKey;
	
	/**
	 * Sequence缓存值
	 */
	private int poolSize;
	
	/**
	 * Sequence的名称
	 */
	private String keyName;
	
	/**
	 * 更新存放Sequence表的语句
	 */
	private static final String sql_update = "UPDATE sequence SET KEYVALUE = KEYVALUE + ? WHERE KEYNAME = ?";
	
	/**
	 * 查询Sequence表中的当前值
	 */
	private static final String sql_query = "SELECT KEYVALUE FROM sequence WHERE KEYNAME = ?";

	public KeyInfo(String keyName, int poolSize) throws SQLException {
		this.poolSize = poolSize;
		this.keyName = keyName;
		retrieveFromDB();
	}

	public String getKeyName() {
		return keyName;
	}

	public int getMaxKey() {
		return maxKey;
	}

	public int getMinKey() {
		return minKey;
	}

	public int getPoolSize() {
		return poolSize;
	}

	/**
	 * 获取下一个Sequence值
	 * 
	 * @return 下一个Sequence值
	 * @throws SQLException
	 */
	public synchronized int getNextKey() throws SQLException {
		if (nextKey > maxKey) {
			retrieveFromDB();
		}
		return nextKey++;
	}

	/**
	 * 执行Sequence表初始化和更新工作
	 * 
	 * @throws SQLException
	 */
	private void retrieveFromDB() throws SQLException {

		Connection conn = ConnectionPool.getInstance().getConnection();
		// 查询数据库
		PreparedStatement pstmt_query = conn.prepareStatement(sql_query);
		pstmt_query.setString(1, keyName);
		ResultSet rs = pstmt_query.executeQuery();
		if (rs.next()) {
			maxKey = rs.getInt(1) + poolSize;
			minKey = maxKey - poolSize + 1;
			nextKey = minKey;
			rs.close();
			pstmt_query.close();
		} else {
			String init_sql = "INSERT INTO sequence(KEYNAME,KEYVALUE) VALUES('"
					+ keyName + "',10000 + " + poolSize + ")";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(init_sql);
			maxKey = 10000 + poolSize;
			minKey = maxKey - poolSize + 1;
			nextKey = minKey;
			stmt.close();
			return;
		}

		// 更新数据库
		conn.setAutoCommit(false);
		PreparedStatement pstmt_up = conn.prepareStatement(sql_update);
		pstmt_up.setLong(1, poolSize);
		pstmt_up.setString(2, keyName);
		pstmt_up.executeUpdate();
		pstmt_up.close();
		conn.commit();

		rs.close();
		pstmt_query.close();
		conn.close();
	}

}