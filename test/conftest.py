import pytest
import time
from appium import webdriver

@pytest.fixture(scope="session")
def driver():
	desired_caps = {}
	desired_caps['platformName'] = 'Android'
	desired_caps['platformVersion'] = '7.0'
	desired_caps['deviceName'] = 'Nexus 5 with Nougat'
	desired_caps['appPackage'] = 'com.example.kitty.budgetenvelopes'
	desired_caps['appActivity'] = '.MainActivity'

	driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)
	yield driver
	driver.quit()
