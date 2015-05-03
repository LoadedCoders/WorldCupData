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
	
	/**
	 * @param args
	 * @throws IOException
	 * @throws ZooKeeperConnectionException
	 * @throws MasterNotRunningException
	 */
	public static void main(String[] args) throws MasterNotRunningException,
			ZooKeeperConnectionException, IOException {
		
		configuration = HBaseConfiguration.create();
		sAdmin = new HBaseAdmin(configuration);

		if(sAdmin.tableExists(WCSchema.TABLE_NAME)) {
			listTables(sAdmin);
		} else {
			setup(WCSchema.TABLE_NAME, WCSchema.ColumnFamilies, sAdmin);
		}
	}
	
	public static void setup(String tableName, HashMap<String, String[]> columnFamilies, HBaseAdmin admin) throws IOException {
		HTableDescriptor descriptor = new HTableDescriptor(
				TableName.valueOf(tableName));
		Set<String>keysSet = columnFamilies.keySet();
		
		for (Iterator iterator = keysSet.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			descriptor.addFamily(new HColumnDescriptor(string));
//			String[] columns = columnFamilies.get(string);
//			for (int i = 0; i < columns.length; i++) {
//				String col = columns[i];
//				descriptor.addFamily(new HColumnDescriptor(col));
//			}
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
	
	public static void put(HBaseAdmin admin, JSONObject tweet) throws IOException, JSONException {
		// Instantiating HTable class
	      HTable hTable = new HTable(configuration, WCSchema.TABLE_NAME);

	      // Instantiating Put class
	      // accepts a row name.
	      Put p = new Put(Bytes.toBytes(tweet.getInt("id"))); 
	      
	      p.add(Bytes.toBytes("personal data"),
	      Bytes.toBytes("name"),Bytes.toBytes("pradyumna"));

	      p.add(Bytes.toBytes("personal data"),
	      Bytes.toBytes("city"),Bytes.toBytes("Hyderabad"));

	      p.add(Bytes.toBytes("professional data"),Bytes.toBytes("designation"),
	      Bytes.toBytes("manager"));

	      p.add(Bytes.toBytes("professional data"),Bytes.toBytes("salary"),
	      Bytes.toBytes("50000"));
	      
	      // Saving the put Instance to the HTable.
	      hTable.put(p);
	      System.out.println("data inserted");
	      
	      // closing HTable
	      hTable.close();
	}
	
	public void scan(String tableName, HBaseAdmin admin) {
		
	}
	
	public void drop(String tableName, HBaseAdmin admin) {
		
	}
}
