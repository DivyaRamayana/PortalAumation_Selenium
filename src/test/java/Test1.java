public class Test1 {
   private String Portal;
   private String Date;
   private String Time;
   private String Status;
   private String Customer_PO_Number;
   private String Directory_path;
   private String Processing_Status;
   private String SAP_Order_number;
   private String Order_confirm_Date;
   private String Comments;
   
   public Test1(String a, String b, String c,String d,String e, String f,String g,String h,String i,String j) {
	   Portal = a.trim();
	   Date = b.trim();
	   Time = c.trim();
	   Status = d.trim();
	   Customer_PO_Number = e.trim();
	   Directory_path = f.trim();	   
	   SAP_Order_number = g.trim();
	   Order_confirm_Date = h.trim();
	   Processing_Status = i.trim();
	   Comments =j.trim();
   }
   public String getPortal() {
      return Portal;
   }
   public String getDate() {
      return Date;
   }
   public String getTime() {
	      return Time;
	   }
   public String getStatus() {
	      return Status;
	   }
   public String getCustomer_PO_Number() {
	      return Customer_PO_Number;
	   }
   public String getDirectory_path() {
	      return Directory_path;
	   }
   public String getSAP_Order_number() {
	      return SAP_Order_number;
	   }
   public String getOrder_confirm_Date() {
	      return Order_confirm_Date;
	   }
  
   public String getProcessing() {
      return Processing_Status;
   }
      
   public String Comments() {
	      return Comments;
	   }
   
}