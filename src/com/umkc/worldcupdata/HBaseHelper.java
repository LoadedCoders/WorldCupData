package com.umkc.worldcupdata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import twitter4j.JSONException;
import twitter4j.JSONObject;

public class HBaseHelper {

	private static Configuration configuration;
	private static HBaseAdmin sAdmin;

	static {
		configuration = HBaseConfiguration.create();
		try {
			sAdmin = new HBaseAdmin(configuration);
		} catch (MasterNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 * @throws IOException
	 * @throws ZooKeeperConnectionException
	 * @throws MasterNotRunningException
	 */
	public static void main(String[] args) throws IOException {

		if (sAdmin.tableExists(WCSchema.TABLE_NAME)) {
			listTables(sAdmin);
		} else {
			setup(WCSchema.TABLE_NAME, WCSchema.ColumnFamilies, sAdmin);
		}
	}

	public static void setup(String tableName,
			HashMap<String, String[]> columnFamilies, HBaseAdmin admin)
			throws IOException {
		HTableDescriptor descriptor = new HTableDescriptor(
				TableName.valueOf(tableName));
		Set<String> keysSet = columnFamilies.keySet();

		for (Iterator iterator = keysSet.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			descriptor.addFamily(new HColumnDescriptor(string));
			// String[] columns = columnFamilies.get(string);
			// for (int i = 0; i < columns.length; i++) {
			// String col = columns[i];
			// descriptor.addFamily(new HColumnDescriptor(col));
			// }
		}

		admin.createTable(descriptor);
		admin.close();
	}

	public static void listTables(HBaseAdmin admin) throws IOException {
		HTableDescriptor[] tableDescriptor = admin.listTables();

		// printing all the table names.
		for (int i = 0; i < tableDescriptor.length; i++) {
			System.out.println(tableDescriptor[i].getNameAsString());
		}
	}

	public static void testPut() throws IOException {
		HTable hTable = new HTable(configuration, WCSchema.TABLE_NAME);

		Put p = new Put(Bytes.toBytes("111"));

		p.add(Bytes.toBytes("Team"), Bytes.toBytes("name"),
				Bytes.toBytes("West Indies"));
		p.add(Bytes.toBytes("Team"), Bytes.toBytes("match"),
				Bytes.toBytes("WIvsAUS"));

		// Saving the put Instance to the HTable.
		hTable.put(p);
		System.out.println("data inserted");

		// closing HTable
		hTable.close();
	}

	public static void put(Tweet t)
			throws IOException, JSONException {
		// Instantiating HTable class
		
		HTable hTable = new HTable(configuration, WCSchema.TABLE_NAME);

		String rowKey = t.id+"";
		System.out.println(rowKey);
		Put p = new Put(Bytes.toBytes(rowKey));

		JSONObject tweet = t.getJSONObject();
		Iterator<String>iterator = tweet.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
//			System.out.println(key);

			JSONObject object = (JSONObject) tweet.get(key);
			Iterator<String>columnIterator = object.keys();
			
			while (columnIterator.hasNext()) {
				String string = (String) columnIterator.next();
//				System.out.println(string);

				p.add(Bytes.toBytes(key), Bytes.toBytes(string),
						Bytes.toBytes(object.getString(string)));
			}
		}
		// Saving the put Instance to the HTable.
		hTable.put(p);
		
		// closing HTable
		hTable.close();
	}

	public void scan(String tableName, HBaseAdmin admin) {

	}

	public void drop(String tableName, HBaseAdmin admin) {
		
	}
}
