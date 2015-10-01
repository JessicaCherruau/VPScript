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

	private String url;
	private WebDriver driver;
	
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
			WebElement popup = driver.findElement(By.xpath("//*[@id=\"expressProductSheet\"]"));
			List<WebElement> models = null;

			models = popup.findElements(By.xpath("//*[@id=\"model\"]/option"));
			if(models.isEmpty()){
				popup.findElement(By.xpath("//*[@id=\"addToCartLink\"]")).click();
			}
			else{
				Iterator<WebElement> itModel = models.listIterator();
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
						if(Main.validModel(option.getAttribute("availablequantity"), option.getText())){
							option.click();
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							popup.findElement(By.xpath("//*[@id=\"addToCartLink\"]")).click();
							
							//declaration of a JsExecutor to execute js script
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
		driver.close();
	}

}
