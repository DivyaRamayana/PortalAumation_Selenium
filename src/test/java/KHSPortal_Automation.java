
import org.apache.log4j.ConsoleAppender;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class KHSPortal_Automation {
	public static int ct;
	public static int download_ct;

	public static ArrayList<String> orders = new ArrayList<String>();
	public static ArrayList<String> downloadedorders = new ArrayList<String>();
	public static ArrayList<String> OrderfailedList = new ArrayList<String>();

	ArrayList<String> csvdata = new ArrayList<String>();

	static Logger logger = Logger.getLogger(KHSPortal_Automation.class);

	public static void main(String Name, String dir, String datefolder, String Timefolder, String logsDir)
			throws InterruptedException, IOException {

		Logger rootLogger = Logger.getRootLogger();

		PatternLayout layout = new PatternLayout("%d{ISO8601} [%t] %-5p %c %x - %m%n");

		rootLogger.addAppender(new ConsoleAppender(layout));

		RollingFileAppender fileAppender;

		fileAppender = new RollingFileAppender(layout, logsDir);
		rootLogger.addAppender(fileAppender);

		logger.info("Job Started");

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();

		chromePrefs.put("profile.default_content_settings.popups", 0);

		chromePrefs.put("download.default_directory", dir);

		chromePrefs.put("plugins.always_open_pdf_externally", true);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");

		options.setExperimentalOption("prefs", chromePrefs);

		WebDriver driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		try {
			driver.get("https://sapportal.khs.com/lp_pa(bD1kZSZjPTAxMCZkPW1pbg==)/plogin.do?sap-unique=195250");
		} catch (Exception e) {
			logger.info("Exception: " + e);
			LoadXML.sendmail("KHS", e, "KHS URL");
		}

		driver.findElement(By.id("htmlb_image_2")).click();
		try {
			driver.findElement(By.id("partner")).sendKeys("LI00089339");
			driver.findElement(By.id("uname")).sendKeys("REXNO02");
			driver.findElement(By.id("pwd")).sendKeys("rex2016");
			driver.findElement(By.className("urBtnPaddingEmph")).click();
			logger.info("Login Successfull");
		} catch (Exception e) {
			logger.info("Login Failed");
			LoadXML.sendmail("KHS", e, "KHS login");
		}

		driver.manage().window().maximize();

		try {
			driver.findElement(By.linkText("Orders")).click();
		} catch (Exception e) {
			logger.info("Unable to navigate to orders page");
			LoadXML.sendmail("KHS", e, "Orders page navigate");
		}

		driver.switchTo().frame(driver.findElement(By.id("subpagecontent")));
		Select s = new Select(driver.findElement(By.id("NEUE-filter_6"))); // Filter set on 'Read'
		s.selectByValue("0");

		//s.selectByValue("N");

		WebElement table = driver.findElement(By.cssSelector("[class='urSAPTable']")); // table identified

		int rowcount = table.findElements(By.cssSelector("tr[rr]")).size(); // common css for all rows grabbed
		int count = table.findElements(By.cssSelector("tr[rr] [class='urTxtStd']")).size();

		ct = count;
		
		
		int size = driver.findElements(By.cssSelector("[class='urTbsCnt'] tr[rr] [ct='Link']")).size();
		
		//System.out.println(size);
		
        if(size>0)
        {
		try {
			
			logger.info("Order listing Successfull: Order count= " + ct);
			for (int i = 0; i < count; i++) {
				

				WebElement element = driver.findElements(By.cssSelector("[class='urTbsCnt'] tr[rr] [ct='Link']")).get(i);
				
				

				String url = element.getAttribute("href");

				((JavascriptExecutor) driver).executeScript("window.open(" + "'" + url + "'" + ")");

				orders.add(
						driver.findElements(By.cssSelector("[class='urTbsCnt'] tr[rr] [ct='Link']")).get(i).getText());

				sleep(2000);
				}

			

		} catch (Exception e) {
			logger.info("Exception: " + e);
			LoadXML.sendmail("KHS", e, "Order listing");
		}

		Set<String> set = driver.getWindowHandles();
		Iterator<String> it = set.iterator();
		String parent = it.next();
		
		try {
			String child = it.next();
			driver.switchTo().window(child);
		} catch (Exception e) {
			logger.info("Exception : " + e);
		}

		download_ct = 0;
		try {
			driver.findElement(By.partialLinkText("Order doc")).click();

			String getdownloads = (driver.findElement(By.partialLinkText("Order doc")).getText()).replaceAll("[^0-9]",
					"");

			logger.info("Order downloaded successfully : " + getdownloads);

			downloadedorders.add(getdownloads);

			download_ct++;

			Thread.sleep(3000);
		} catch (Exception e) {
			logger.info("Exception: " + e);
		}

		try {
			while (it.hasNext()) {

				driver.switchTo().window(it.next());

				driver.findElement(By.partialLinkText("Order doc")).click();

				String getdownload = (driver.findElement(By.partialLinkText("Order doc")).getText())
						.replaceAll("[^0-9]", "");

				logger.info("Order downloaded successfully : " + getdownload);

				downloadedorders.add(getdownload);
				download_ct++;

				Thread.sleep(3000);

				///////////////////////////////////////////////// added to test error
				///////////////////////////////////////////////// handling////////////////////////////////////////

				/*
				 * if(downloadedorders.size()<4) { downloadedorders.add(getdownload);
				 * download_ct++; }
				 */
				// driver.close();

			}
		} catch (Exception e) {
			logger.info("Exception: " + e);
		}

		System.out.println("Downloaded order count : " + download_ct);

		if (download_ct < ct) {
			System.out.println("Error");

			for (String item : orders) {
				if (downloadedorders.contains(item)) {

					System.out.println(item + "=========Downloaded successfully================");

				} else {
					OrderfailedList.add(item);
					logger.info("Failed to Download Order : " + item);
					System.out.println(item + "===========Read orders to be downloaded==============");
				}
			}

			/*
			 * driver.quit();
			 * 
			 * HashMap<String, Object> chromePrefs1 = new HashMap<String, Object>();
			 * 
			 * chromePrefs1.put("profile.default_content_settings.popups", 0);
			 * 
			 * chromePrefs1.put("download.default_directory", dir);
			 * 
			 * chromePrefs1.put("plugins.always_open_pdf_externally", true);
			 * 
			 * ChromeOptions options1 = new ChromeOptions();
			 * //options.addArguments("headless");
			 * 
			 * options1.setExperimentalOption("prefs", chromePrefs1);
			 * 
			 * WebDriver driver1 = new ChromeDriver(options1);
			 * 
			 * driver1.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			 * 
			 * driver1.get(
			 * "https://sapportal.khs.com/lp_pa(bD1kZSZjPTAxMCZkPW1pbg==)/plogin.do?sap-unique=195250"
			 * );
			 * 
			 * driver1.findElement(By.id("htmlb_image_2")).click();
			 * 
			 * driver1.findElement(By.id("partner")).sendKeys("LI00089339");
			 * driver1.findElement(By.id("uname")).sendKeys("REXNO02");
			 * driver1.findElement(By.id("pwd")).sendKeys("rex2016");
			 * driver1.findElement(By.className("urBtnPaddingEmph")).click();
			 * 
			 * driver1.manage().window().maximize();
			 * 
			 * driver1.findElement(By.linkText("Orders")).click();
			 * 
			 * //driver1.switchTo().window(parent);
			 * //driver1.findElement(By.linkText("Orders")).click();
			 * 
			 * driver1.switchTo().frame(driver1.findElement(By.id("subpagecontent")));
			 * Select failed = new Select(driver1.findElement(By.id("NEUE-filter_6"))); //
			 * Filter set on 'Read' failed.selectByValue("0");
			 * 
			 * 
			 * for (int i = 0; i < OrderfailedList.size(); i++) {
			 * 
			 * WebElement element1 =
			 * driver1.findElement(By.partialLinkText(OrderfailedList.get(i)));
			 * 
			 * System.out.println(driver1.findElement(By.partialLinkText(OrderfailedList.get
			 * (i))).getText());
			 * 
			 * String url1 = element1.getAttribute("href");
			 * 
			 * ((JavascriptExecutor)
			 * driver1).executeScript("window.open("+"'"+url1+"'"+")");
			 * 
			 * sleep(2000);
			 * 
			 * }
			 * 
			 * Set<String> set1 = driver1.getWindowHandles();
			 * 
			 * Iterator<String> it1 = set1.iterator(); String parent1 = it1.next();
			 * 
			 * String child1 = it1.next();
			 * 
			 * driver1.switchTo().window(child1);
			 * 
			 * 
			 * driver1.findElement(By.partialLinkText("Order doc")).click(); String
			 * getdownloadsss =
			 * (driver1.findElement(By.partialLinkText("Order doc")).getText()).replaceAll(
			 * "[^0-9]", "");
			 * 
			 * logger.info("Order downloaded successfully : "+ getdownloadsss);
			 * 
			 * 
			 * while (it1.hasNext()) {
			 * 
			 * driver1.switchTo().window(it1.next());
			 * 
			 * driver1.findElement(By.partialLinkText("Order doc")).click();
			 * 
			 * String getdownloadss =
			 * (driver1.findElement(By.partialLinkText("Order doc")).getText()).replaceAll(
			 * "[^0-9]", "");
			 * 
			 * logger.info("Order downloaded successfully : "+ getdownloadss); }
			 * 
			 * Thread.sleep(5000); driver1.quit();
			 * 
			 * logger.info("Job Ended");
			 * 
			 * Thread.sleep(5000); driver.quit();
			 */

			LoadXML.sendmail("KHS", null, "ordersfailed");

			logger.info("Job Ended");

			driver.quit();

		}
		

		else {

			///////////////////////////////////////////////// added to test error
			///////////////////////////////////////////////// handling////////////////////////////////////////
			logger.info("Job Ended");
			driver.quit();

		}
        }
        else {
        	logger.info("No New Orders found");
        	logger.info("Job Ended");
			driver.quit();
        }

	}

}
