import pytest
import time
from appium.webdriver.common.touch_action import TouchAction

def test_create_envelope(driver):
	element = driver.find_element_by_id('main_new_envelope')
	element.click()
	element = driver.find_element_by_id('name_edit_text')
	assert element is not None
	element.send_keys("Cash")
	element = driver.find_element_by_id('open_balance_edit_text')
	assert element is not None
	element.send_keys("250")
	try:
		driver.hide_keyboard()
	except:
		pass
	time.sleep(2)
	element = driver.find_element_by_id('accept_envelope_button')
	assert element is not None
	element.click()
	time.sleep(2)
	activity = driver.current_activity
	assert activity == ".EnvelopeActivity"

def test_edit_envelope(driver):
	activity = driver.current_activity
	assert activity == ".EnvelopeActivity"
	element = driver.find_element_by_id('env_name')
	assert element is not None
	element.click()
	element = driver.find_element_by_id('edit_envelope_button')
	assert element is not None
	element.click()
	element = driver.find_element_by_id('edit_envelope_opening_balance_edit')
	assert element is not None
	element.send_keys("475")
	try:
		driver.hide_keyboard()
	except:
		pass
	time.sleep(2)
	element = driver.find_element_by_id('edit_envelope_accept_button')
	assert element is not None
	element.click()
	time.sleep(2)
	activity = driver.current_activity
	assert activity == ".EnvelopeActivity"
	element = driver.find_element_by_id('env_balance')
	assert element is not None
	assert element.text == "475.00"
	
def test_delete_envelope(driver):
	activity = driver.current_activity
	assert activity == ".EnvelopeActivity"
	element = driver.find_element_by_id('env_name')
	assert element is not None
	element.click()
	element = driver.find_element_by_id('delete_envelope_button')
	assert element is not None
	element.click()
	activity = driver.current_activity
	assert activity == ".EnvelopeActivity"