package ventePriveeScript;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ThreadSale implements Runnable {

	private String url;			//URL of the products list 
	private WebDriver driver;	// firefox browser 
	
	/**
	 * Contructor
	 * 
	 * Initializes the browser driver with the provided cookies (to keep the session)
	 * @param url URL of the product list
	 * @param cookies cookies of a browser driver (already opened, session ready)
	 */
	public ThreadSale(String url, Set<Cookie> cookies){
		this.url = url;
		this.driver = new FirefoxDriver();
		this.driver.get(Main.DOMAIN_NAME);
		Iterator<Cookie> cookie = cookies.iterator();
		while(cookie.hasNext()){
			this.driver.manage().addCookie(cookie.next());
		}
	}
	
	@Override
	/**
	 * For each product, click on the "Achat Express" (express buy) button, check the availability and
	 * the model/size. If theses are correct, click on the "add to cart" button.
	 */
	public void run() {
		driver.get(url);
		List<WebElement> items = driver.findElements(By.xpath("//*[@class=\"infoExpressBt\"]"));
		ListIterator<WebElement> itItems = items.listIterator();
		while(itItems.hasNext()){
			itItems.next().click();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// after clicking on the Achat express button, a pop up opens with detailed inforations of 
			// the product
			WebElement popup = driver.findElement(By.xpath("//*[@id=\"expressProductSheet\"]"));
			List<WebElement> models = null;

			models = popup.findElements(By.xpath("//*[@id=\"model\"]/option"));
			if(models.isEmpty()){
				// in the case there is no several sizes or models, the product is automatically added
				popup.findElement(By.xpath("//*[@id=\"addToCartLink\"]")).click();
			}
			else{
				Iterator<WebElement> itModel = models.listIterator();
				
				//for each model/size
				while(itModel.hasNext()){
					try{
						WebElement option = itModel.next();
						try {
							Thread.sleep(600);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// check if the model for the item is available
						if(ThreadSale.validModel(option.getAttribute("availablequantity"), option.getText())){
							option.click();
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							popup.findElement(By.xpath("//*[@id=\"addToCartLink\"]")).click();
							/*after clicking on the add to cart button, this button disappear
							  we need to execute javascript to make the button visible again
							 (in order to add to cart the next correct model */
							JavascriptExecutor js = (JavascriptExecutor) driver;
							// set the div containing the button
							js.executeScript("document.getElementById('addToCartLinkContainer').setAttribute('style', '')");
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		driver.close();		// all of the products has been examined, we close the window
	}
	
	/**
	 * Check the availability and the size of the model
	 * @param quantity available quantity of the model
	 * @param desc description of the model (contains generally the size)
	 * @return true if the model is correct, else false
	 */
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
}
