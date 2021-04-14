import java.util.ArrayList;
import java.io.*; //File,FileReader,FileNotFoundException,BufferedReader,IOException
public class Test2 {
   private ArrayList<Test1> List;
   private ArrayList<Test1> ProcessedList;
   private ArrayList<Test1> FullList;
   public Test2() {
      List = new ArrayList<Test1>();
      ProcessedList = new ArrayList<Test1>();
      FullList = new ArrayList<Test1>();
   }
   public void add(Test1 sb) {
      List.add(sb);
      ProcessedList.add(sb);
      FullList.add(sb);
   }
   public void readFromCSV(String filename) {
      File file = new File(filename);
      
    
      
      FileReader reader = null;
      try {
         reader = new FileReader(file);
      }
      catch (FileNotFoundException e) {
         e.printStackTrace();
         System.exit(1);
      }
      BufferedReader infile = new BufferedReader(reader);
      String line = "";
      try {
         boolean done = false;
         while (!done) {
            line = infile.readLine();
            if (line == null) {
               done = true;
            }
            else {
            	
               
               if(line.contains("Customer") || line.isEmpty() )
               {
            	   
               }
               else
               {
            	   
               String[] tokens = line.trim().split(",");
               String Portal = tokens[0].trim();
               String Date = tokens[1].trim();
               String Time = tokens[2].trim();
               String Status = tokens[3].trim();
               String Customer_PO_Number = tokens[4].trim();
               String Directory_path = tokens[5].trim();  
               String SAP_Order_number = tokens[6].trim();
               String Order_confirm_Date = tokens[7].trim();
              // boolean Processing_Status = Boolean.parseBoolean(tokens[6].trim());
               String Processing_Status = tokens[8].trim();
               String Comments = tokens[9].trim();
               System.out.println("Success");
               //System.out.println("Processing status:"+Processing_Status);
               //System.out.println("Token:"+tokens[6].trim());
               
               Test1 sb = new Test1(Portal,Date,Time,Status,Customer_PO_Number,Directory_path,SAP_Order_number,Order_confirm_Date,Processing_Status,Comments);
               FullList.add(sb);
               if(line.contains("UnProcessed"))
        	   {
            	   List.add(sb);   
        	   }
               else
               {
            	   ProcessedList.add(sb); 
               }
               
               
               
               }
            }
         }
      }
      catch (IOException e) {
         e.printStackTrace();
         System.exit(1);
      }
   }
   public Object[][] convert2Data() {
       Object[][] data = new Object[List.size()][10];
       for (int i = 0; i < List.size(); i++) {
    	   
    	  
          data[i][0] = List.get(i).getPortal();
          data[i][1] = List.get(i).getDate();
          data[i][2] = List.get(i).getTime();
          data[i][3] = List.get(i).getStatus();
          data[i][4] = List.get(i).getCustomer_PO_Number();
          data[i][5] = List.get(i).getDirectory_path();
          data[i][6] = List.get(i).getSAP_Order_number();
          data[i][7] = List.get(i).getOrder_confirm_Date();
          data[i][8] = List.get(i).getProcessing();
          data[i][9] = List.get(i).Comments();
        
       }
       return data;
   }
   
   public Object[][] convert2DataProcessed() {
       Object[][] Processeddata = new Object[ProcessedList.size()][10];
       for (int i = 0; i < ProcessedList.size(); i++) {
    	   
    	  
    	   Processeddata[i][0] = ProcessedList.get(i).getPortal();
    	   Processeddata[i][1] = ProcessedList.get(i).getDate();
    	   Processeddata[i][2] = ProcessedList.get(i).getTime();
    	   Processeddata[i][3] = ProcessedList.get(i).getStatus();
    	   Processeddata[i][4] = ProcessedList.get(i).getCustomer_PO_Number();
    	   Processeddata[i][5] = ProcessedList.get(i).getDirectory_path();
    	   Processeddata[i][6] = ProcessedList.get(i).getSAP_Order_number();
    	   Processeddata[i][7] = ProcessedList.get(i).getOrder_confirm_Date();
    	   Processeddata[i][8] = ProcessedList.get(i).getProcessing();
    	   Processeddata[i][9] = ProcessedList.get(i).Comments();
        
       }
       return Processeddata;
   }
   
   public Object[][] convert2DataFull() {
       Object[][] fulldata = new Object[FullList.size()][10];
       for (int i = 0; i < FullList.size(); i++) {
    	   
    	  
    	   fulldata[i][0] = FullList.get(i).getPortal();
    	   fulldata[i][1] = FullList.get(i).getDate();
    	   fulldata[i][2] = FullList.get(i).getTime();
    	   fulldata[i][3] = FullList.get(i).getStatus();
    	   fulldata[i][4] = FullList.get(i).getCustomer_PO_Number();
    	   fulldata[i][5] = FullList.get(i).getDirectory_path();
    	   fulldata[i][6] = FullList.get(i).getSAP_Order_number();
    	   fulldata[i][7] = FullList.get(i).getOrder_confirm_Date();
    	   fulldata[i][8] = FullList.get(i).getProcessing();
    	   fulldata[i][9] = FullList.get(i).Comments();
        
       }
       return fulldata;
   }
}