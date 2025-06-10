@SmokeTests
Feature: Open XE Property Website

  @SmokeTests_01
  Scenario: <SmokeTests_01> Verify the prices are correct
    Given I open the XE Property website
    Then I search properties for "Ενοικίαση"
    Then I set the location as "Παγκράτι"
    Then I click all search results
    When I click search button
    Then I set the price from "200" to "700"
    Then I set the square meters from "55" to "100"
    Then I verify that the price is in the range of 200 to 700

  @SmokeTests_02
  Scenario: <SmokeTests_02> Verify the sizes are correct
    Given I open the XE Property website
    Then I search properties for "Ενοικίαση"
    Then I set the location as "Παγκράτι"
    Then I click all search results
    When I click search button
    Then I set the price from "200" to "700"
    Then I set the square meters from "55" to "100"
    Then I verify that the square meters are in the range of 55 to 150

  @SmokeTests_03
  Scenario: <SmokeTests_03> Verify that ads have no more than 30 photos
    Given I open the XE Property website
    When I search properties for "Ενοικίαση"
    When I set the location as "Παγκράτι"
    When I click all search results
    When I click search button
    When I set the price from "200" to "700"
    When I set the square meters from "55" to "150"
    Then I verify that the ads images are not greater than 30

  @SmokeTests_04
  Scenario: <SmokeTests_04> Verify that ads are correct when we set descending price filter
    Given I open the XE Property website
    When I search properties for "Ενοικίαση"
    When I set the location as "Παγκράτι"
    When I click all search results
    When I click search button
    When I set the price from "200" to "700"
    When I set the square meters from "55" to "150"
    When I click the sorting method "Τιμή (φθίνουσα)"
    Then I verify that price sorting method is descending

  @SmokeTests_05
  Scenario: <SmokeTests_05> Verify that the contact phone in each ad is a clickable button is and reveals the contact phone in a pop-up if pressed. (Bonus)
    Given I open the XE Property website
    When I search properties for "Ενοικίαση"
    When I set the location as "Παγκράτι"
    When I click all search results
    When I click search button
    When I set the price from "200" to "700"
    When I set the square meters from "75" to "150"
    Then I verify phone is not visible when clicking the ad but visible when clicking the phone button

