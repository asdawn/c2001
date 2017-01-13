package net.c2001.dm.ar.transaction.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A format converter for transaction data stored in CSV format.
 * This class can convert tabular files to transactional files,
 * if the input file is a valid CSV file, the first row is
 * table header and the first column is TID. For example,
 * table<br>
 * ____________<br>
 * TID, A, B, C<br>
 * 1, 1, 0, 1<br>
 * ____________<br>
 * will be converted to table<br>
 * __________<br>
 * TID, Item<br>
 * 1, A<br>
 * 1, C<br>
 * __________<br>
 * 
 * 
 * @author Lin Dong
 *
 */
public class TabToTransConverter {
	
	/**
	 * Convert the input tabular file to transactional file.
	 * @param in input tabular file.
	 * @param out output transactional file.
	 */
	public static void convert(File in, File out) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(in));
		BufferedWriter writer = new BufferedWriter(new FileWriter(out));
		//read the table header.
		String header = reader.readLine();
		if(header == null){
			System.out.println("Input file has no data.");
			reader.close();
			writer.close();
			return;
		}
		
		String[] fields = header.split(",");
		if(fields.length == 1){
			System.out.println("Input file has only 1 column.");
			reader.close();
			writer.close();
			return;
		}
		ArrayList<String> names = new ArrayList<>();
		for(int i=0;i<fields.length;i++){
			names.add(fields[i].trim());
		}
		String transaction = reader.readLine();
		if(transaction == null){
			System.out.println("Input file has only table header.");
			reader.close();
			writer.close();
			return;
		}
		//write table header
		writer.write("TID, event\n");
		while(transaction != null){
			String[] values = transaction.split(",");
			String tid = values[0].trim();
			for(int i=1;i<values.length;i++){
				if(values[i].trim().equals("1")){
					writer.append(tid);
					writer.append(","+names.get(i)+"\n");
				}
					
			}
			transaction = reader.readLine();
		}
		reader.close();
		writer.close();
	
	}
	
	/*
	public static void main(String[] args) throws IOException {
		convert(new File("c:/tmp/TabularFileData.csv"), new File("c:/tmp/TransactionalFileData.csv"));
	}
	*/
}
		
		