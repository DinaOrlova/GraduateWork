package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement heading = $(withText("Оплата по карте"));
    private SelenideElement cardNumber = $(withText("Номер карты")).parent().$(".input__control");
    private SelenideElement month = $(withText("Месяц")).parent().$(".input__control");
    private SelenideElement year = $(withText("Год")).parent().$(".input__control");
    private SelenideElement owner = $(withText("Владелец")).parent().$(".input__control");
    private SelenideElement codeCVV = $(withText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement fieldError = $(".input_invalid .input__sub");
    private SelenideElement errorMessage = $(".notification_status_error");
    private SelenideElement successMessage = $(".notification_status_ok");
    private SelenideElement closeMessage = $(".notification__closer");

    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void fillData(Card card) {
        cardNumber.setValue(card.getCardNumber());
        month.setValue(card.getMonth());
        year.setValue(card.getYear());
        owner.setValue(card.getOwner());
        codeCVV.setValue(card.getCodeCVV());
        continueButton.click();
    }

    public void shouldGiveFieldErrorWhenIncorrectFormat() {
        fieldError.shouldHave(Condition.text("Неверный формат")).shouldBe(visible, Duration.ofMillis(15000));
    }

    public void shouldGiveFieldErrorWhenIncorrectCardExpirationDate() {
        fieldError.shouldHave(Condition.text("Неверно указан срок действия карты"))
                .shouldBe(visible, Duration.ofMillis(15000));
    }

    public void shouldGiveFieldErrorWhenCardExpired() {
        fieldError.shouldHave(Condition.text("Истёк срок действия карты")).shouldBe(visible, Duration.ofMillis(15000));
    }

    public void shouldGiveFieldErrorWhenRequiredField() {
        fieldError.shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe(visible, Duration.ofMillis(15000));
    }

    public void shouldGiveFieldErrorWhenValueCannotBeLong() {
        fieldError.shouldHave(Condition.text("Значение поля не может быть длиннее 70 символов"))
                .shouldBe(visible, Duration.ofMillis(15000));
    }

    public void shouldGiveFieldErrorWhenNameIsNotInLatin() {
        fieldError.shouldHave(Condition.text("Значение поля может содержать только латинские буквы и дефис"))
                .shouldBe(visible, Duration.ofMillis(15000));
    }

    public void shouldGiveFieldErrorWhenIncorrectCVV() {
        fieldError.shouldHave(Condition.text("Неверно указан CVC/CVV"))
                .shouldBe(visible, Duration.ofMillis(15000));
    }

    public void shouldGiveFieldError() {
        fieldError.shouldBe(visible, Duration.ofMillis(15000));
    }

    public void shouldNotGiveFieldError() {
        fieldError.shouldNotBe(visible, Duration.ofMillis(15000));
    }

    public void shouldGiveErrorMessage() {
        errorMessage.shouldBe(visible, Duration.ofMillis(15000));
    }

    public void shouldGiveSuccessMessage() {
        successMessage.shouldBe(visible, Duration.ofMillis(15000));
    }

    public void shouldNotBeVisibleSuccessMessage() {
        closeMessage.click();
        successMessage.shouldNotBe(visible, Duration.ofMillis(15000));
    }
}
