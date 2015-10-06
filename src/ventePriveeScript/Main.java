package ventePriveeScript;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Uses Selenium (external library)
 * 
 * This script is addressed to users of the Vente Privee website wishing to add to their cart the products
 * from the different categories of a sale. It can be useful in case of summer sales (the script is executed
 * at the opening hour and allows to add the maximum of products).
 * 
 * First, it authenticates on the website. Then open the sale. A thread is created for each category of the sale
 * The products are added to the cart according to their availability and the size (defined in the validModel
 * method).
 * 
 * @author Jessica CHERRUAU
 *
 */
public class Main {
	public static final String DOMAIN_NAME = "http://fr.vente-privee.com/";		// URL of the website
	private static final String[] UNWANTED_BRANDS = {  };		//the categories of a sale to ignore

	/**
	 * General mecanism described in the javadoc of the class
	 * @param email login of the user account on Vente Privee
	 * @param pwd password of the user account on Vente Privee
	 * @param saleNo sale number
	 */
	public static void startScript(String email, String pwd, String saleNo){
		// Create a new instance of the Firefox driver
		WebDriver driver = new FirefoxDriver();
		driver.get(DOMAIN_NAME+"home/fr/Operation/Access/"+saleNo);

		if(driver.getCurrentUrl().contains("authentication")){
			// input the arguments and valid the authentication form
			driver.findElement(By.xpath("//*[@id=\"mail\"]")).sendKeys(email);
			driver.findElement(By.xpath("//*[@id=\"mdp\"]")).sendKeys(pwd);
			driver.findElement(By.xpath("//*[@id=\"loginBtn\"]")).click();
		}

		//iterate in the different categories
		List<WebElement> list = driver.findElements(By.xpath("//*/ul[@class=\"menuEV_Container\"]/li/a"));
		ArrayList<String> linkList = new ArrayList<String>(); 	//contains the URL of the products lists
		ListIterator<WebElement> it = list.listIterator();
		while(it.hasNext()){
			WebElement elementA = it.next();
			// if the category is ok, we add the link of the product list of the category into the array
			if(!Main.isUnwantedBrand(elementA.getText().replace("<span>", "").replace("</span>",  "").trim()))
				linkList.add(elementA.getAttribute("href")+"/all");
		}
		Iterator<String> itUrl = linkList.iterator();
		while(itUrl.hasNext()){
			String url = itUrl.next();
			// start a new Thread for each product list
			new Thread(new ThreadSale(url, driver.manage().getCookies())).start();
		}
		System.out.println("Page title is: " + driver.getTitle());

	}

	/**
	 * Check if the provided category/brand is in the list of unwanted categories
	 * @param name provided category or brand
	 * @return true if unwanted, else true
	 */
	public static boolean isUnwantedBrand(String name){
		for(int i = 0 ; i < UNWANTED_BRANDS.length ; i++){
			if(UNWANTED_BRANDS[i].equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
}
