package com.headwire.automatedscreenrecorder.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public abstract class TypeCondition {
	
	public WebElement typeCondition(String var, String type) {
		Driver.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (type.equals("xpath")) {
			return Driver.driver.findElement(By.xpath(var));
		} else if (type.equals("id")) {
			return Driver.driver.findElement(By.id(var));
		} else if (type.equals("name")) {
			return Driver.driver.findElement(By.name(var));
		} else if (type.equals("class")) {
			return Driver.driver.findElement(By.className(var));
		} else {
			return null;
		}
	}
}
