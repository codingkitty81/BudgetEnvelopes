import pytest
import time

def test_create_multiple_debit_transactions(driver):
	activity = driver.current_activity
	if activity != ".MainActivity":
		element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]')
		element.click()
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
	for i in range (1,5):
		activity = driver.current_activity
		if activity != ".TransactionActivity":
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[3]')
			element.click()
		time.sleep(2)
		element = driver.find_element_by_id('add_transaction_fab')
		assert element is not None
		element.click()
		element = driver.find_element_by_id('payee_edit_text')
		assert element is not None
		payee = "Payee"+str(i)
		element.send_keys(payee)
		element = driver.find_element_by_id('amount_edit_text')
		assert element is not None
		element.send_keys("5")
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
		try:
			driver.hide_keyboard()
		except:
			pass
		element = driver.find_element_by_id('accept_transaction_button')
		assert element is not None
		element.click()
		
	for i in range (1,5):
		activity = driver.current_activity
		assert activity == ".TransactionActivity"
		if i == 1:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[1]/android.widget.TextView[3]')
		elif i == 2:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[2]/android.widget.TextView[3]')
		elif i == 3:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[3]/android.widget.TextView[3]')
		elif i == 4:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[4]/android.widget.TextView[3]')
		else:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[4]/android.widget.TextView[3]')
		assert element is not None
		assert element.text == "5.00"
		
def test_edit_multiple_transactions(driver):
	for i in range (1,5):
		activity = driver.current_activity
		assert activity == ".TransactionActivity"
		if i == 1:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[1]/android.widget.TextView[3]')
		elif i == 2:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[2]/android.widget.TextView[3]')
		elif i == 3:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[3]/android.widget.TextView[3]')
		elif i == 4:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[4]/android.widget.TextView[3]')
		else:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[4]/android.widget.TextView[3]')
		assert element is not None
		element.click()
		element = driver.find_element_by_id('edit_transaction_button')
		assert element is not None
		element.click()
		element = driver.find_element_by_id('edit_transaction_amount_edit')
		assert element is not None
		element.send_keys("13.75")
		try:
			driver.hide_keyboard()
		except:
			pass
		element = driver.find_element_by_id('edit_transaction_accept')
		assert element is not None
		element.click()
		
	for i in range (1,5):
		activity = driver.current_activity
		assert activity == ".TransactionActivity"
		if i == 1:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[1]/android.widget.TextView[3]')
		elif i == 2:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[2]/android.widget.TextView[3]')
		elif i == 3:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[3]/android.widget.TextView[3]')
		elif i == 4:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[4]/android.widget.TextView[3]')
		else:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.view.ViewGroup[4]/android.widget.TextView[3]')
		assert element is not None
		assert element.text == "13.75"
		
def test_delete_multiple_transactions(driver):
	activity = driver.current_activity
	assert activity == ".TransactionActivity"
	for i in range (1,5):
		element = driver.find_element_by_id('payee_view')
		assert element is not None
		element.click()
		element = driver.find_element_by_id('delete_transaction_button')
		assert element is not None
		element.click()
		activity = driver.current_activity
		assert activity == ".TransactionActivity"
	element = driver.find_element_by_id('trans_balance_view')
	assert element is not None
	assert element.text == "250.00"
	element = driver.find_element_by_id('navigation_envelopes')
	assert element is not None
	element.click()
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
		
		
