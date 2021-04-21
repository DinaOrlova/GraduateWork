package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SqlHelper;
import ru.netology.web.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentGateTest {
    public static String sutUrl = System.getProperty("sut.url");

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide()); }

    @BeforeEach
    void setup() {
        open(sutUrl);
    }

    @AfterEach
    void clearDB() {
        SqlHelper.deleteData();
    }

    @AfterAll
    static void tearDownAll() { SelenideLogger.removeListener("allure"); }

    @Test
    void shouldPayFromApprovedCard() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getApprovedCard());
        paymentPage.shouldGiveSuccessMessage();
        assertEquals("APPROVED", SqlHelper.getPaymentStatus());
    }

    @Test
    void shouldNotPayFromDeclinedCard() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getDeclinedCard());
        paymentPage.shouldGiveErrorMessage();
        assertEquals("DECLINED", SqlHelper.getPaymentStatus());
    }

    @Test
    void shouldNotPayFromInvalidCardNumber() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getInvalidCardNumber());
        paymentPage.shouldGiveErrorMessage();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromIncompleteCardNumber() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getIncompleteCardNumber());
        paymentPage.shouldGiveFieldErrorWhenIncorrectFormat();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithoutNumber() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithoutNumber());
        paymentPage.shouldGiveFieldErrorWhenRequiredField();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithoutMonth() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithoutMonth());
        paymentPage.shouldGiveFieldErrorWhenRequiredField();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWith00Month() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWith00Month());
        paymentPage.shouldGiveFieldErrorWhenIncorrectCardExpirationDate();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWith1DigitMonth() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWith1DigitMonth());
        paymentPage.shouldGiveFieldErrorWhenIncorrectFormat();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithMonthMoreThan12() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithMonthMoreThan12());
        paymentPage.shouldGiveFieldErrorWhenIncorrectCardExpirationDate();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithoutYear() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithoutYear());
        paymentPage.shouldGiveFieldErrorWhenRequiredField();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWith00Year() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWith00Year());
        paymentPage.shouldGiveFieldErrorWhenCardExpired();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWith1DigitYear() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWith1DigitYear());
        paymentPage.shouldGiveFieldErrorWhenIncorrectFormat();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithPastYear() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithPastYear());
        paymentPage.shouldGiveFieldErrorWhenCardExpired();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithYearOverCurrentYearOn6() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithYearOverCurrentYearOn6());
        paymentPage.shouldGiveFieldErrorWhenIncorrectCardExpirationDate();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithoutOwner() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithoutOwner());
        paymentPage.shouldGiveFieldErrorWhenRequiredField();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithOwnerWithLargeNumberOfLetters() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithOwnerWithLargeNumberOfLetters());
        paymentPage.shouldGiveFieldErrorWhenValueCannotBeLong();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithOwnerInCyrillic() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithOwnerNameInCyrillic());
        paymentPage.shouldGiveFieldErrorWhenNameIsNotInLatin();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithOwnerInNumbers() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithOwnerInNumbers());
        paymentPage.shouldGiveFieldErrorWhenNameIsNotInLatin();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithOwnerInSpecialCharacters() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithOwnerInSpecialCharacters());
        paymentPage.shouldGiveFieldErrorWhenNameIsNotInLatin();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithOwnerWithLink() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithOwnerWithLink());
        paymentPage.shouldGiveFieldErrorWhenNameIsNotInLatin();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithoutCVV() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithoutCVV());
        paymentPage.shouldGiveFieldErrorWhenRequiredField();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWith1DigitCVV() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWith1DigitCVV());
        paymentPage.shouldGiveFieldErrorWhenIncorrectFormat();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWith000CVV() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWith000CVV());
        paymentPage.shouldGiveFieldErrorWhenIncorrectCVV();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithLastMonthCurrentYear() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithLastMonthCurrentYear());
        paymentPage.shouldGiveFieldErrorWhenCardExpired();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotPayFromCardWithPlus1MonthPlus5Year() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getCardWithPlus1MonthPlus5Year());
        paymentPage.shouldGiveFieldErrorWhenIncorrectCardExpirationDate();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotBeVisibleFieldError() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getEmptyCard());
        paymentPage.shouldGiveFieldError();
        paymentPage.fillData(DataHelper.getApprovedCard());
        paymentPage.shouldNotGiveFieldError();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotBeVisibleSuccessMessage() {
        val startPage = new StartPage();
        val paymentPage = startPage.selectPayment();
        paymentPage.fillData(DataHelper.getInvalidCardNumber());
        paymentPage.shouldGiveErrorMessage();
        paymentPage.shouldNotBeVisibleSuccessMessage();
        assertEquals(0, SqlHelper.getOrderCount());
    }
}
