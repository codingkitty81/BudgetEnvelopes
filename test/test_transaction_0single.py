import pytest
import time
from appium.webdriver.common.touch_action import TouchAction

def test_create_transaction(driver):
	activity = driver.current_activity
	if activity != ".MainActivity":
		element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]')
		element.click()
		#TouchAction.tap(driver, None, 185, 1680, 1).perform()
	time.sleep(2)
	element = driver.find_element_by_id('main_new_envelope')
	assert element is not None
	element.click()
	element = driver.find_element_by_id('name_edit_text')
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
	element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[3]')
	element.click()
	#TouchAction.tap(driver, element, 890, 1650, 1).perform()
	activity = driver.current_activity
	assert activity == ".TransactionActivity"
	element = driver.find_element_by_id('add_transaction_fab')
	assert element is not None
	element.click()
	activity = driver.current_activity
	assert activity == ".AddTransactionActivity"
	element = driver.find_element_by_id('payee_edit_text')
	assert element is not None
	element.send_keys("Target")
	element = driver.find_element_by_id('amount_edit_text')
	assert element is not None
	element.send_keys("25.00")
	try:
		driver.hide_keyboard()
	except:
		pass
	element = driver.find_element_by_id('debit_credit_spinner')
	assert element is not None
	element.click()
	element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[1]')
	assert element is not None
	element.click()
	element = driver.find_element_by_id('envelope_spinner')
	assert element is not None
	element.click()
	element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView')
	assert element is not None
	element.click()
	element = driver.find_element_by_id('accept_transaction_button')
	assert element is not None
	element.click()
	activity = driver.current_activity
	assert activity == ".TransactionActivity"
	
def test_edit_transaction(driver):
	activity = driver.current_activity
	assert activity == ".TransactionActivity"
	element = driver.find_element_by_id('payee_view')
	assert element is not None
	element.click()
	element = driver.find_element_by_id('edit_transaction_button')
	assert element is not None
	element.click()
	element = driver.find_element_by_id('edit_transaction_payee_edit')
	assert element is not None
	element.send_keys("Bookstore")
	element = driver.find_element_by_id('edit_transaction_amount_edit')
	assert element is not None
	element.send_keys("53.18")
	try:
		driver.hide_keyboard()
	except:
		pass
	element = driver.find_element_by_id('edit_transaction_accept')
	assert element is not None
	element.click()
	activity = driver.current_activity
	assert activity == ".TransactionActivity"
	element = driver.find_element_by_id('amount_view')
	assert element is not None
	assert element.text == "53.18"
	