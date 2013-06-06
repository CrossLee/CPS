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
 * <b>Note</b>: Javaʵ�ֵ�Sequence����
 */
public class SequenceUtil {
	
	/**
	 * ����ģʽ
	 */
	private static SequenceUtil instance = new SequenceUtil();
	
	/**
	 * ������е�MAP
	 */
	private Map<String, KeyInfo> keyMap = new HashMap<String, KeyInfo>(20);
	private static final int POOL_SIZE = 1;

	/**
	 * ��ֹ�ⲿʵ����
	 */
	private SequenceUtil() {
	}

	/**
	 * ����ģʽ,��ȡ����
	 * 
	 * @return SequenceUtils�ĵ�������
	 */
	public static SequenceUtil getInstance() {
		return instance;
	}

	/**
	 * ��ȡ��һ��sequenceֵ
	 * 
	 * @param keyName
	 *            Sequence����
	 * @return ��һ��Sequence��ֵ
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
	 * ��ǰSequence��������ֵ
	 */
	private int maxKey;
	
	/**
	 * ��ǰSequence����Сֵ
	 */
	private int minKey;
	
	/**
	 * ��һ��Sequenceֵ
	 */
	private int nextKey;
	
	/**
	 * Sequence����ֵ
	 */
	private int poolSize;
	
	/**
	 * Sequence������
	 */
	private String keyName;
	
	/**
	 * ���´��Sequence������
	 */
	private static final String sql_update = "UPDATE sequence SET KEYVALUE = KEYVALUE + ? WHERE KEYNAME = ?";
	
	/**
	 * ��ѯSequence���еĵ�ǰֵ
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
	 * ��ȡ��һ��Sequenceֵ
	 * 
	 * @return ��һ��Sequenceֵ
	 * @throws SQLException
	 */
	public synchronized int getNextKey() throws SQLException {
		if (nextKey > maxKey) {
			retrieveFromDB();
		}
		return nextKey++;
	}

	/**
	 * ִ��Sequence���ʼ���͸��¹���
	 * 
	 * @throws SQLException
	 */
	private void retrieveFromDB() throws SQLException {

		Connection conn = ConnectionPool.getInstance().getConnection();
		// ��ѯ���ݿ�
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

		// �������ݿ�
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