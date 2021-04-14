import java.awt.AWTException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LoadXML {

	static DateFormat df = new SimpleDateFormat("ddMMyyyy");
	static String datefolder = df.format(new Date());
	static DateFormat tf = new SimpleDateFormat("HHmm");
	static String timefolder = tf.format(new Date());

	public static ArrayList csvadd = new ArrayList();
	public static ArrayList ordernum = new ArrayList();

	public static String result;

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		File configfile = new File(args[0]);
		// File configfile = new File ("./Config/Config_xmlfile.xml");

		System.setProperty("webdriver.chrome.driver", args[1]);
		// System.setProperty("webdriver.chrome.driver",
		// "./src/main/resources/driverBindings/chromedriver.exe");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(configfile);
		doc.getDocumentElement().normalize();
		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		NodeList nodeList = doc.getElementsByTagName("Portal");

		for (int itr = 0; itr < nodeList.getLength(); itr++) {
			Node node = nodeList.item(itr);
			System.out.println("\nNode Name :" + node.getNodeName());

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				System.out.println("Name: " + eElement.getElementsByTagName("Name").item(0).getTextContent());
				System.out.println("Status: " + eElement.getElementsByTagName("Status").item(0).getTextContent());
				System.out
						.println("Runjobpath: " + eElement.getElementsByTagName("Runjobpath").item(0).getTextContent());
				System.out.println("Destination_Dir: "
						+ eElement.getElementsByTagName("Destination_Dir").item(0).getTextContent());

				String Status = eElement.getElementsByTagName("Status").item(0).getTextContent();
				String Dir1 = eElement.getElementsByTagName("Destination_Dir").item(0).getTextContent();
				String Dir = eElement.getElementsByTagName("Destination_Dir").item(0).getTextContent() + "\\" + "Run"
						+ "\\" + datefolder + "\\" + "Data";
				String LogsDir = eElement.getElementsByTagName("Destination_Dir").item(0).getTextContent() + "\\"
						+ "Run" + "\\" + datefolder + "\\" + "Logs" + "\\" + "Logging.log";

				new File(Dir).mkdirs();

				Thread.sleep(1000);

				System.setProperty("logfile.name", LogsDir);

				String logcsv = Dir + "\\" + datefolder + "_Dailylogs" + ".csv";

				if (Status.contentEquals("Active")) {

					File file = new File(logcsv);

					FileWriter csvfile = null;

					if (!file.exists()) {
						file.createNewFile();

						csvfile = new FileWriter(logcsv, false);

						Thread.sleep(3000);
						csvfile.append("Portal");
						csvfile.append(",");
						csvfile.append("Date");
						csvfile.append(",");
						csvfile.append("Time");
						csvfile.append(",");
						csvfile.append("Download Status");
						csvfile.append(",");
						csvfile.append("Customer PO Number");
						csvfile.append(",");
						csvfile.append("Directory path");
						csvfile.append(",");
						csvfile.append("SAP Order number");
						csvfile.append(",");
						csvfile.append("Order confirm Date");
						csvfile.append(",");
						csvfile.append("Processing Status");
						csvfile.append(",");
						csvfile.append("Comments");
						csvfile.append("\n");
						Thread.sleep(3000);
					}

					else {
						csvfile = new FileWriter(logcsv, true);
						csvfile.append("\n");

					}

					if (eElement.getElementsByTagName("Name").item(0).getTextContent().contentEquals("KHS")) {
		
						KHSPortal_Automation.main(eElement.getElementsByTagName("Name").item(0).getTextContent(), Dir,
								datefolder, timefolder, LogsDir);
						Thread.sleep(1000);
						FilesRename(Dir, eElement.getElementsByTagName("Name").item(0).getTextContent());
						Thread.sleep(1000);

						/*
						 * int count = KHSPortal_Automation.ct; int ordernum_size = ordernum.size();
						 * if(count==ordernum_size) {
						 * sendmail(eElement.getElementsByTagName("Name").item(0).getTextContent()); }
						 */

						writetocsv(Dir, eElement.getElementsByTagName("Name").item(0).getTextContent(), csvfile);
					}

					
					 /* if
					  (eElement.getElementsByTagName("Name").item(0).getTextContent().contentEquals
					  ("Krones")) {
					  
					  //System.out.println("Dir===>"+Dir);
					  //System.out.println("Logs===>"+LogsDir);
					  
					  KronesOrderMain.main(Dir, LogsDir,datefolder,timefolder,"Krones");
					  Thread.sleep(1000); FilesRename(Dir,
					  eElement.getElementsByTagName("Name").item(0).getTextContent());
					  Thread.sleep(1000); writetocsv(Dir,
					  eElement.getElementsByTagName("Name").item(0).getTextContent(), csvfile);
					  
					  
					  }*/
					 

					csvfile.flush();
					csvadd.clear();
					ordernum.clear();

				}

			}

		}

	}

	public static void sendmail(String portal, Exception Exception, String Exceptionname) {

		final String username = "SAP.UAR@Rexnord.com";

		// change accordingly
		final String password = "YQhAXzAKGoRiA2orKG98";

		// or IP address
		final String host = "mail.rexnord.com";

		// Get system properties
		Properties props = new Properties();

		// enable authentication
		props.put("mail.smtp.auth", host);

		// enable STARTTLS
		props.put("mail.smtp.starttls.enable", "false");

		// Setup mail server
		props.put("mail.smtp.host", host);

		// TLS Port
		props.put("mail.smtp.port", "587");
		//props.put("mail.smtp.port", "25");

		// creating Session instance referenced to
		// Authenticator object to pass in
		// Session.getInstance argument
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {

			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(username, password);
			}
		});

		try {

			// compose the message
			// javax.mail.internet.MimeMessage class is
			// mostly used for abstraction.
			Message message = new MimeMessage(session);

			// header field of the header.
			message.setFrom(new InternetAddress(username));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("divya.r@rexnord.com"));

			
			//message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("divya.r@rexnord.com,siddharthjawale612@gmail.com,akash.patil@rexnord.com"));
			 

			message.setSubject("order download failed - " + portal);

			String result = KHSPortal_Automation.OrderfailedList.stream().collect(Collectors.joining(", "));

			System.out.println(result);
			if (Exceptionname == "ordersfailed") {
				message.setText("Please be informed that order download for the portal " + portal + " on date: "
						+ datefolder + " at time: " + timefolder + " having orders " + result
						+ " was failed, Please contact Automation team to resolve this issue. ");

			} else {
				message.setText("Please be informed that order download for the portal " + portal + " on date: "
						+ datefolder + " at time: " + timefolder
						+ " was failed due to the exception, Please contact Automation team to resolve this issue. " + "Exception:- "
						+ Exception);
			}

			Transport.send(message); // send Message

			System.out.println("Message sent");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	private static void writetocsv(String dir, String Name, FileWriter csvfile) throws IOException {

		for (int i = 0, k = 0; (i < csvadd.size() && k < ordernum.size()); i++, k++) {
			String load[] = new String[] { Name, datefolder, timefolder, "Success", ordernum.get(k).toString(),
					csvadd.get(i).toString(), "", "", "Pending", "NA" };

			java.util.List<String> listcsv = Arrays.asList(load);

			for (int j = 0; j < listcsv.size(); j++) {
				csvfile.append(listcsv.get(j));
				// System.out.println(listcsv.get(j));
				csvfile.append(",");
			}

			if (i < csvadd.size() - 1) {

				csvfile.append("\n");
			}
		}
	}

	private static void FilesRename(String dir, String portal) throws InterruptedException {

		File folder = new File(dir);
		File[] filesList = folder.listFiles();
		for (int i = 0; i < filesList.length; i++) {

			// if (filesList[i].getName().contains(".pdf") ||
			// filesList[i].getName().contains(".xlsx")) {
			if ((filesList[i].getName().contains(".pdf") && (!filesList[i].getName().contains(portal)))) {
				File sourceFile = new File(filesList[i].getName());
				// System.out.println(filesList[i]);

				// System.out.println(sourceFile.getName());
				String newname = dir + "\\" + portal + "_" + datefolder + "_" + timefolder + "_"
						+ filesList[i].getName();

				csvadd.add(newname);

				int index1 = newname.lastIndexOf('_');
				int index2 = newname.lastIndexOf('.');

				// System.out.println(index1 + ":" + index2);

				ordernum.add(newname.subSequence(index1 + 1, index2));

				File destFile = new File(newname);

				// System.out.println(destFile.getName());
				filesList[i].renameTo(destFile);

			} else if (filesList[i].getName().contains(".xlsx")) {
				if (filesList[i].getName().contains(timefolder)) {
					int index1 = filesList[i].getName().lastIndexOf('_');
					int index2 = filesList[i].getName().lastIndexOf('.');

					csvadd.add(filesList[i].getName());
					ordernum.add(filesList[i].getName().subSequence(index1 + 1, index2));
				}
			}

			// }

			//

		}

	}

}
