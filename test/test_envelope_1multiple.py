import pytest
import time

def test_create_multiple_envelopes(driver):
	for i in range (1,4):
		activity = driver.current_activity
		if activity == ".EnvelopeActivity":
			element = driver.find_element_by_id('add_envelope_fab')
		else:
			element = driver.find_element_by_id('main_new_envelope')
		assert element is not None
		element.click()
		element = driver.find_element_by_id('name_edit_text')
		assert element is not None
		envelope_name = "Envelope"+str(i)
		element.send_keys(envelope_name)
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
	
def test_edit_multiple_envelopes(driver):
	for i in range (1,4):
		activity = driver.current_activity
		assert activity == ".EnvelopeActivity"
		if i == 1:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.TextView[1]')
		elif i == 2:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.TextView[1]')
		elif i == 3:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.TextView[1]')
		elif i == 4:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.TextView[1]')
		else:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.LinearLayout[7]/android.widget.TextView[1]')
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
	for i in range (1,4):
		activity = driver.current_activity
		assert activity == ".EnvelopeActivity"
		if i == 1:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.TextView[2]')
		elif i == 2:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.TextView[2]')
		elif i == 3:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.TextView[2]')
		elif i == 4:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.TextView[2]')
		else:
			element = driver.find_element_by_xpath('/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ListView/android.widget.LinearLayout[7]/android.widget.TextView[2]')
		assert element is not None
		assert element.text == "475.00"
		
def test_delete_multiple_envelopes(driver):
	for i in range (1,4):
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