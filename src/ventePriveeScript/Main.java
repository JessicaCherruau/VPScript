package ventePriveeScript;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {
	private static final String SALE_NO = "47413"; // last number in url
	public static final String DOMAIN_NAME = "http://fr.vente-privee.com/";
	private static final String[] UNWANTED_BRANDS = {  };

	public static void startScript(String email, String pwd){
		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface, 
		// not the implementation.
		WebDriver driver = new FirefoxDriver();
		// And now use this to visit Google
		driver.get(DOMAIN_NAME+"home/fr/Operation/Access/"+SALE_NO);

		if(driver.getCurrentUrl().contains("authentication")){
			// Find the text input element by its name
			driver.findElement(By.xpath("//*[@id=\"mail\"]")).sendKeys(email);
			driver.findElement(By.xpath("//*[@id=\"mdp\"]")).sendKeys(pwd);
			driver.findElement(By.xpath("//*[@id=\"loginBtn\"]")).click();
		}

		//iterate in the different brands
		List<WebElement> list = driver.findElements(By.xpath("//*/ul[@class=\"menuEV_Container\"]/li/a"));
		ArrayList<String> linkList = new ArrayList<String>();
		ListIterator<WebElement> it = list.listIterator();
		while(it.hasNext()){
			WebElement elementA = it.next();
			if(!Main.isUnwantedBrand(elementA.getText().replace("<span>", "").replace("</span>",  "").trim()))
				linkList.add(elementA.getAttribute("href")+"/all");
		}
		Iterator<String> itUrl = linkList.iterator();
		while(itUrl.hasNext()){
			String url = itUrl.next();
			new Thread(new ThreadSale(url, driver.manage().getCookies())).start();
		}
		System.out.println("Page title is: " + driver.getTitle());

	}
	
	public static boolean validModel(String quantity, String desc){
		if(quantity != null && !quantity.equals("0")){
			if(desc.contains("(FR)")){
				if(desc.contains("38 (FR)") || desc.contains("40 (FR)"))
					return true;
			}
			else{
				if(desc.contains("38") || desc.contains("40"))
					return true;
			}
			if(desc.contains("TU") || desc.contains("T.U") || desc.contains("26") ||
					desc.contains("M") || desc.contains("27") || desc.contains("90E"))
				return true;
			else
				return false;
		}
		else{
			return false;
		}
	}

	public static boolean isUnwantedBrand(String name){
		for(int i = 0 ; i < UNWANTED_BRANDS.length ; i++){
			if(UNWANTED_BRANDS[i].equalsIgnoreCase(name))
				return true;
		}
		return false;
	}
}
