package com.umkc.worldcupdata;
import java.io.IOException;

import org.apache.derby.iapi.sql.dictionary.TableDescriptor;
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

public class HBaseHelper {

	
	private static Configuration configuration;

	/**
	 * @param args
	 * @throws IOException
	 * @throws ZooKeeperConnectionException
	 * @throws MasterNotRunningException
	 */
	public static void main(String[] args) throws MasterNotRunningException,
			ZooKeeperConnectionException, IOException {
		// TODO Auto-generated method stub

		configuration = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(configuration);

		if(admin.tableExists("employee")) {
			// Getting all the list of tables using HBaseAdmin object
			listTables(admin);
			
			put(null, admin);
			return;
		}
		
		HTableDescriptor descriptor = new HTableDescriptor(
				TableName.valueOf("employee"));
		descriptor.addFamily(new HColumnDescriptor("personal data"));
		descriptor.addFamily(new HColumnDescriptor("professional data"));

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
	
	public static void put(Put put, HBaseAdmin admin) throws IOException {
		// Instantiating HTable class
	      HTable hTable = new HTable(configuration, "employee");

	      // Instantiating Put class
	      // accepts a row name.
	      Put p = new Put(Bytes.toBytes("111")); 

	      // adding values using add() method
	      // accepts column family name, qualifier/row name ,value
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
}
