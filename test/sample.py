import os
from time import sleep

import unittest

from appium import webdriver

# Returns abs path relative to this file and not cwd
PATH = lambda p: os.path.abspath(
    os.path.join(os.path.dirname(__file__), p)
)

class SimpleAndroidTests(unittest.TestCase):
    def setUp(self):
        desired_caps = {}
        desired_caps['platformName'] = 'Android'
        desired_caps['platformVersion'] = '7.0'
        desired_caps['deviceName'] = 'Nexus 5 with Nougat'
        desired_caps['appPackage'] = 'com.example.kitty.budgetenvelopes'
        desired_caps['appActivity'] = '.MainActivity'

        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

    def tearDown(self):
        # end the session
        self.driver.quit()

    def test_find_elements(self):
        el = self.driver.find_element_by_id('main_new_envelope')
        el.click()
        el = self.driver.find_element_by_id('name_edit_text')
        self.assertIsNotNone(el)
        el.send_keys("Cash")
        
        el = self.driver.find_element_by_id('open_balance_edit_text')
        self.assertIsNotNone(el)
        el.send_keys("250")
        
        el = self.driver.find_element_by_id('accept_envelope_button')
        self.assertIsNotNone(el)
        el.click()

        activity = self.driver.current_activity
        self.assertEquals('.EnvelopeActivity', activity)


if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(SimpleAndroidTests)
    unittest.TextTestRunner(verbosity=2).run(suite)