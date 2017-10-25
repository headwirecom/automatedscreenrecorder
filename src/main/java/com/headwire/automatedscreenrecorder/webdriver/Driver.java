package com.headwire.automatedscreenrecorder.webdriver;

import com.headwire.automatedscreenrecorder.helpers.Context;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Driver extends TypeCondition {

	private static Driver instance = null;
	public static WebDriver driver;
	public static double multiplicator;
	
	private Driver() {
	}
	
	public static Driver getInstance() {
		if(instance == null) {
			instance = new Driver();
		}
		return instance;
	}

	private void initChromeDriver(Context ctx) {
		String exePath = ctx.getDriverPath();
		System.setProperty("webdriver.chrome.driver", exePath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		options.addArguments("--start-fullscreen");
		driver = new ChromeDriver(options);
	}

	private void initFirefoxDriver(Context ctx) {
		String exePath = ctx.getDriverPath();
		System.setProperty("webdriver.gecko.driver", exePath);
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--start-fullscreen");
		driver = new FirefoxDriver(options);
	}

	public void use(Context ctx, String browser) {
		if(browser.equals("chrome")) {
			initChromeDriver(ctx);
		} else if (browser.equals("firefox")) {
			initFirefoxDriver(ctx);
		}
		setMultiplicator();
	}

	public void open(String var) {
		driver.get(var);
	}

	public void click(String var, String type) {
		super.typeCondition(var, type).click();	
	}

	public void enter() {
		getActiveElement().submit();		
	}

	public void input(String var) {
		getActiveElement().sendKeys(var);
	}
	
	public void returnKey() {
		getActiveElement().sendKeys(Keys.RETURN);
	}

	public WebElement getActiveElement() {
		return driver.switchTo().activeElement();
	}

	public void quit() {
		driver.quit();
	}

	public void rightclick(String var, String type) {
		Actions action = new Actions(driver);
		action.moveToElement(super.typeCondition(var, type));
		action.contextClick(super.typeCondition(var, type)).build().perform();
	}

	public void doubleclick(String var, String type) {
		Actions action = new Actions(driver);
		action.moveToElement(super.typeCondition(var, type));
		action.doubleClick(super.typeCondition(var, type)).build().perform();
	}

	public void setMultiplicator() {
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = g.getDefaultScreenDevice();
		long lDeviceResolution = device.getDisplayMode().getWidth();
		double deviceResolution = (double) lDeviceResolution;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long lBrowserResolution = (long) js.executeScript("return screen.width;");
		double browserResolution = (double) lBrowserResolution;
		multiplicator = deviceResolution/browserResolution;
	}

	public double getMultiplicator() {
		return multiplicator;
	}

	public void scrolldown (String var) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + var + ")", "");
		Thread.sleep(500);
	}

	public void scrollup (String var) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, -" + var + ")", "");
		Thread.sleep(500);
	}

	public void highlight(String var, String type) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", super.typeCondition(var, type));
	}

	public void goToAndClick(String var, String type) throws Exception {
		goTo(var, type);
		click(var, type);
	}

	private static Robot initRobot() throws Exception {
		Robot robot = new Robot();
		return robot;
	}

	public void getItem(String var) throws Exception {
		int number = Integer.parseInt(var);

		for(int i = 1; i <= number; i++) {
			initRobot().keyPress(KeyEvent.VK_DOWN);
			initRobot().keyRelease(KeyEvent.VK_DOWN);
			Thread.sleep(500);
		}

		initRobot().keyPress(KeyEvent.VK_ENTER);
		initRobot().keyRelease(KeyEvent.VK_ENTER);
	}

	public void goTo(String var, String type) throws Exception {
		double[] coordinates;
		coordinates = prepareCoordinates(var, type);
		mouseMovement(coordinates);
	}
	
	public void dragAndDrop(String var, String type) throws Exception {
		double[] coordinates;
		coordinates = prepareCoordinates(var, type);
		initRobot().mousePress(InputEvent.BUTTON1_MASK);
		mouseMovement(coordinates);
		initRobot().mouseRelease(InputEvent.BUTTON1_MASK);
	}

	private double[] prepareCoordinates(String var, String type) {
		Point pTo = super.typeCondition(var, type).getLocation();

		java.awt.Point mouse = MouseInfo.getPointerInfo().getLocation();

		double pFromX = mouse.x;
		double pFromY = mouse.y;

		double pToX = pTo.x* Driver.multiplicator;
		double pToY = pTo.y* Driver.multiplicator;

		Dimension toSize = super.typeCondition(var, type).getSize();

		int xCentreTo = toSize.width / 2;
		int yCentreTo = toSize.height / 2;

		pToX += xCentreTo;
		pToY += yCentreTo;
		
		double[] coordinates = {pFromX, pFromY, pToX, pToY};
		return coordinates;
	}
	
	private void mouseMovement(double[] coordinates) throws Exception {
		double pFromX = coordinates[0];
		double pFromY = coordinates[1];
		double pToX = coordinates[2];
		double pToY = coordinates[3];

		double distance = Math.sqrt(Math.pow(pToX - pFromX, 2) + Math.pow(pToY - pFromY, 2));
		double step = distance/100.;

		for(double i = 0; i < distance; i+= step) {
			double x = pFromX + (pToX-pFromX)/distance * (i * (Math.pow(Math.sin(Math.PI/2 * (i/distance)),2.0)));
			double y = pFromY + (pToY-pFromY)/distance * (i * (Math.pow(Math.sin(Math.PI/2 * (i/distance)),2.0)));
			initRobot().mouseMove((int)x, (int)y);
			Thread.sleep(5);
		}
		if(true) return;
	}

	public void acceptAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
}
