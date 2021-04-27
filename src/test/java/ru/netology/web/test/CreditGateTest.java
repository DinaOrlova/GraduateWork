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

public class CreditGateTest {
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
    void shouldCreditFromApprovedCard() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getApprovedCard());
        creditPage.shouldGiveSuccessMessage();
        assertEquals("APPROVED", SqlHelper.getCreditStatus());
    }

    @Test
    void shouldNotCreditFromDeclinedCard() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getDeclinedCard());
        creditPage.shouldGiveErrorMessage();
        assertEquals("DECLINED", SqlHelper.getCreditStatus());
    }

    @Test
    void shouldNotCreditFromInvalidCardNumber() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getInvalidCardNumber());
        creditPage.shouldGiveErrorMessage();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromIncompleteCardNumber() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getIncompleteCardNumber());
        creditPage.shouldGiveFieldErrorWhenIncorrectFormat();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithoutNumber() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithoutNumber());
        creditPage.shouldGiveFieldErrorWhenRequiredField();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithoutMonth() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithoutMonth());
        creditPage.shouldGiveFieldErrorWhenRequiredField();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWith00Month() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWith00Month());
        creditPage.shouldGiveFieldErrorWhenIncorrectCardExpirationDate();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWith1DigitMonth() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWith1DigitMonth());
        creditPage.shouldGiveFieldErrorWhenIncorrectFormat();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithMonthMoreThan12() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithMonthMoreThan12());
        creditPage.shouldGiveFieldErrorWhenIncorrectCardExpirationDate();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithoutYear() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithoutYear());
        creditPage.shouldGiveFieldErrorWhenRequiredField();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWith00Year() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWith00Year());
        creditPage.shouldGiveFieldErrorWhenCardExpired();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWith1DigitYear() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWith1DigitYear());
        creditPage.shouldGiveFieldErrorWhenIncorrectFormat();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithPastYear() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithPastYear());
        creditPage.shouldGiveFieldErrorWhenCardExpired();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithYearOverCurrentYearOn6() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithYearOverCurrentYearOn6());
        creditPage.shouldGiveFieldErrorWhenIncorrectCardExpirationDate();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithoutOwner() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithoutOwner());
        creditPage.shouldGiveFieldErrorWhenRequiredField();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithOwnerWithLargeNumberOfLetters() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithOwnerWithLargeNumberOfLetters());
        creditPage.shouldGiveFieldErrorWhenValueCannotBeLong();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithOwnerInCyrillic() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithOwnerNameInCyrillic());
        creditPage.shouldGiveFieldErrorWhenNameIsNotInLatin();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithOwnerInNumbers() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithOwnerInNumbers());
        creditPage.shouldGiveFieldErrorWhenNameIsNotInLatin();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithOwnerInSpecialCharacters() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithOwnerInSpecialCharacters());
        creditPage.shouldGiveFieldErrorWhenNameIsNotInLatin();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithOwnerWithLink() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithOwnerWithLink());
        creditPage.shouldGiveFieldErrorWhenNameIsNotInLatin();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithoutCVV() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithoutCVV());
        creditPage.shouldGiveFieldErrorForCVVWhenRequiredField();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWith1DigitCVV() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWith1DigitCVV());
        creditPage.shouldGiveFieldErrorWhenIncorrectFormat();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWith000CVV() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWith000CVV());
        creditPage.shouldGiveFieldErrorWhenIncorrectCVV();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithLastMonthCurrentYear() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithLastMonthCurrentYear());
        creditPage.shouldGiveFieldErrorWhenCardExpired();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotCreditFromCardWithPlus1MonthPlus5Year() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getCardWithPlus1MonthPlus5Year());
        creditPage.shouldGiveFieldErrorWhenIncorrectCardExpirationDate();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotBeVisibleFieldError() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getEmptyCard());
        creditPage.shouldGiveFieldError();
        creditPage.fillData(DataHelper.getApprovedCard());
        creditPage.shouldNotGiveFieldError();
        assertEquals(0, SqlHelper.getOrderCount());
    }

    @Test
    void shouldNotBeVisibleSuccessMessage() {
        val startPage = new StartPage();
        val creditPage = startPage.selectCredit();
        creditPage.fillData(DataHelper.getInvalidCardNumber());
        creditPage.shouldGiveErrorMessage();
        creditPage.shouldNotBeVisibleSuccessMessage();
        assertEquals(0, SqlHelper.getOrderCount());
    }
}
