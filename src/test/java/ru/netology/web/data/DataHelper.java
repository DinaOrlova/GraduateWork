package ru.netology.web.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;

public class DataHelper {
    private DataHelper() {}

    public static String selectCurrentMonthOrMoreThanCurrent(int months) {
        LocalDate validity = LocalDate.now().plusMonths(months);
        String validityMonth = validity.toString().substring(5, 7);
        return validityMonth;
    }

    public static String selectMonthLessThanCurrent(int months) {
        LocalDate validity = LocalDate.now().plusMonths(months);
        String validityMonth = validity.toString().substring(5, 7);
        return validityMonth;
    }

    public static String selectCurrentYearOrMoreThanCurrent(int years) {
        LocalDate validity = LocalDate.now().plusYears(years);
        String validityYear = validity.toString().substring(2, 4);
        return validityYear;
    }

    public static String selectPastYear(int years) {
        LocalDate validity = LocalDate.now().minusYears(years);
        String invalidYear = validity.toString().substring(2, 4);
        return invalidYear;
    }

    public static String cardNumber() {
        Faker faker = new Faker();
        String cardNumber = faker.business().creditCardNumber();
        return  cardNumber;
    }

    public static String incompleteCardNumber() {
        Faker faker = new Faker();
        String cardNumber = faker.number().digits(10);
        return  cardNumber;
    }

    public static String owner() {
        Faker faker = new Faker();
        String owner = faker.name().firstName() + " " + faker.name().lastName();
        return owner;
    }

    public static String codeCVV() {
        Faker faker = new Faker();
        String codeCVV = faker.number().digits(3);
        return codeCVV;
    }

    public static Card getApprovedCard() {
        String month = selectCurrentMonthOrMoreThanCurrent(0);
        String year = selectCurrentYearOrMoreThanCurrent(3);
        return new Card("4444 4444 4444 4441", month, year, "Petr Kulikov", "456");
    }

    public static Card getDeclinedCard() {
        String month = selectCurrentMonthOrMoreThanCurrent(0);
        String year = selectCurrentYearOrMoreThanCurrent(3);
        return new Card("4444 4444 4444 4442", month, year, "Petr Kulikov", "456");
    }

    public static Card getInvalidCardNumber() {
        return new Card(cardNumber(), selectCurrentMonthOrMoreThanCurrent(2),
                selectCurrentYearOrMoreThanCurrent(1), owner(), codeCVV());
    }

    public static Card getIncompleteCardNumber() {
        return new Card(incompleteCardNumber(), selectCurrentMonthOrMoreThanCurrent(6),
                selectCurrentYearOrMoreThanCurrent(2), owner(), codeCVV());
    }

    public static Card getCardWithoutNumber() {
        return new Card("", selectCurrentMonthOrMoreThanCurrent(5),
                selectCurrentYearOrMoreThanCurrent(3), owner(), codeCVV());
    }

    public static Card getCardWithoutMonth() {
        return new Card("4444 4444 4444 4441", "", selectCurrentYearOrMoreThanCurrent(4),
                owner(), codeCVV());
    }

    public static Card getCardWith00Month() {
        return new Card("4444 4444 4444 4441", "00", selectCurrentYearOrMoreThanCurrent(2),
                owner(), codeCVV());
    }

    public static Card getCardWith1DigitMonth() {
        return new Card("4444 4444 4444 4441", "4", selectCurrentYearOrMoreThanCurrent(1),
                owner(), codeCVV());
    }

    public static Card getCardWithMonthMoreThan12() {
        return new Card("4444 4444 4444 4441", "15", selectCurrentYearOrMoreThanCurrent(5),
                owner(), codeCVV());
    }

    public static Card getCardWithoutYear() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(8), "",
                owner(), codeCVV());
    }

    public static Card getCardWith00Year() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(12), "00",
                owner(), codeCVV());
    }

    public static Card getCardWith1DigitYear() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(2), "2",
                owner(), codeCVV());
    }

    public static Card getCardWithPastYear() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(0),
                selectPastYear(1), owner(), codeCVV());
    }

    public static Card getCardWithYearOverCurrentYearOn6() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(0),
                selectCurrentYearOrMoreThanCurrent(6), owner(), codeCVV());
    }

    public static Card getCardWithoutOwner() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(1),
                selectCurrentYearOrMoreThanCurrent(3), "", codeCVV());
    }

    public static Card getCardWithOwnerWithLargeNumberOfLetters() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(7),
                selectCurrentYearOrMoreThanCurrent(2), "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaa", codeCVV());
    }

    public static Card getCardWithOwnerNameInCyrillic() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(4),
                selectCurrentYearOrMoreThanCurrent(1), "Екатерина Токарева", codeCVV());
    }

    public static Card getCardWithOwnerInNumbers() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(9),
                selectCurrentYearOrMoreThanCurrent(3), "1234567890", codeCVV());
    }

    public static Card getCardWithOwnerInSpecialCharacters() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(11),
                selectCurrentYearOrMoreThanCurrent(4), "®©§€∞≤≥β", codeCVV());
    }

    public static Card getCardWithOwnerWithLink() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(5),
                selectCurrentYearOrMoreThanCurrent(5), "https://google.ru/", codeCVV());
    }

    public static Card getCardWithoutCVV() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(3),
                selectCurrentYearOrMoreThanCurrent(3), owner(), "");
    }

    public static Card getCardWith1DigitCVV() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(10),
                selectCurrentYearOrMoreThanCurrent(1), owner(), "9");
    }

    public static Card getCardWith000CVV() {
        return new Card("4444 4444 4444 4441", selectCurrentMonthOrMoreThanCurrent(2),
                selectCurrentYearOrMoreThanCurrent(2), owner(), "000");
    }
}
