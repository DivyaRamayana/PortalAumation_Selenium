import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;
public class Test4 extends JFrame {
   private static JTable table;
private JTable tableprocessed;
private JTable fulltable;
   private DefaultTableModel model,Processedmodel,fullmodel;
   private Object[][] data,Processeddata,Fulldata;
   private JButton button;
   public String tempordernum;
   public static String filename;
   
   String[] arr = new String[7]; 
   
   
   
  
   private String[] columnNames = {"Portal","Date","Time","Status","Order_Number","Directory_path","Processing_Status",};
     
   private Test2 myList;
   
   public Test4() {
      setTitle("Un-Processed Order details");
     
      //System.out.println("E:\\Automation\\KHS\\Daily_Run_Report\\"+filename+"_Dailylogs.csv");
      
      myList = new Test2();
      myList.readFromCSV("E:\\Automation\\KHS\\Daily_Run_Report\\"+filename+"_Dailylogs.csv");
      //myList.readFromCSV("E:\\Automation\\KHS\\Daily_Run_Report\\04062020_Dailylogs.csv");
      
      data = myList.convert2Data();      
      Processeddata = myList.convert2DataProcessed();
      Fulldata = myList.convert2DataFull();
            
      model = new DefaultTableModel(data, columnNames);
      Processedmodel = new DefaultTableModel(Processeddata, columnNames);
      fullmodel = new DefaultTableModel(Fulldata, columnNames);
     
      
      table = new JTable(model);
      tableprocessed = new JTable(Processedmodel);
      fulltable = new JTable(fullmodel);
      
      
      
      table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
      tableprocessed.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
      fulltable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
      
      
      button = new JButton("Process");     
      
      button.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent ae) {
            // check for selected row first
        	/* System.out.println("Processing status: "+fulltable.getValueAt(fulltable.getSelectedRow(),6).toString());
        	 
        	 System.out.println("Order number:"+ fulltable.getValueAt(fulltable.getSelectedRow(),4).toString());
        	 
        	 tempordernum = fulltable.getValueAt(fulltable.getSelectedRow(),4).toString();
             
        	 //fulltable.setValueAt("Processed", fulltable.getSelectedRow(), 6);
             
             System.out.println("Processing status_changed: "+fulltable.getValueAt(fulltable.getSelectedRow(),6).toString());*/
             
            // updatetable();
             
            
        	 
            if(fulltable.getSelectedRow() != -1) {
               // remove selected row from the model
            	//model.removeRow(table.getSelectedRow());
            	if(fulltable.getValueAt(fulltable.getSelectedRow(), 6).toString().contentEquals("Processed"))
            	{
            		JOptionPane.showMessageDialog(null, "Order Processed already, please check..!!");
            	}
            	else
            	{
            	
            	
            	
            	
               
               /*try {
   				
   				toCSV(fulltable,"E:\\\\Automation\\\\KHS\\\\Daily_Run_Report\\\\04062020_Dailylogs.csv" );
   			} catch (IOException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}*/
               
            	int n = JOptionPane.showConfirmDialog(
               		    null,
               		    "Are you sure,Do you want to process?",
               		    "An Inane Question",
               		    JOptionPane.YES_NO_OPTION);
                  
                  if(n == JOptionPane.YES_OPTION) 
                  {
               
                	  JOptionPane.showMessageDialog(null, "Order Processed successfully");
                	  fulltable.setValueAt("Processed", fulltable.getSelectedRow(), 6);
                	  updatetable();
                  }
            	}
               
            }
         }

		private void updatetable() {
			int l = fulltable.getRowCount();
		      System.out.println(l);
		      
		     /* for(int i =0;i<l;i++)
		      {
		      
		    	  if(fulltable.getValueAt(i,4).toString().contains(tempordernum))
		    	  {
		     	
		    		  System.out.println(fulltable.getValueAt(i,4).toString());
		    		  fulltable.setValueAt("Processed", i, 6);
		    	  }
		      
		      
		      }*/
		      
		       try {
 				
 				toCSV(fulltable,"E:\\Automation\\KHS\\Daily_Run_Report\\"+filename+"_Dailylogs.csv" );
 			} catch (IOException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
			
		}
      });
      add(new JScrollPane(fulltable), BorderLayout.CENTER);
      
      fulltable.setAutoCreateRowSorter(true);
      
      fullmodel.fireTableDataChanged();
     // add(new JScrollPane(tableprocessed), BorderLayout.CENTER);
      add(button, BorderLayout.SOUTH);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(400, 300);
      setLocationRelativeTo(null);
      setVisible(true);
      
     
      
      
   }
   
   public void toCSV(JTable table, String string) throws IOException{
	   TableModel model = table.getModel();
	   File file = new File(string);
       FileWriter out = new FileWriter(file);

       for(int i=0; i < model.getColumnCount(); i++) {
           out.write(model.getColumnName(i) + ",");
       }
       out.write("\n");
       for(int i=0; i< model.getRowCount(); i++) {
           for(int j=0; j < model.getColumnCount(); j++) {
               out.write(model.getValueAt(i,j).toString()+",");
           }
           out.write("\n");
       }
       out.close();
       System.out.println("write out to: " + file);
	}
   //public static void main(String[] args) {
	   public static void main(String str) {
	   filename = str;
      new Test4();
     // return table;
   }
}