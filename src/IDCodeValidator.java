public class IDCodeValidator {

    /**
     * Проверяет, является ли данный ID code допустимым.
     * Использует все остальные методы для проверки
     *
     * @param idCode строка, представляющая ID code
     * @return {@code true}, если ID code допустим, иначе {@code false}
     */
    public static boolean isCorrect(String idCode) {
        return checkControlNumber(idCode);
    }

    /**
     * Проверяет корректность кода пола в ID code.
     *
     * @param genderNumber целое число, представляющее код пола
     * @return {@code true}, если код пола корректен, иначе {@code false}
     */
    public static boolean isGenderNumberCorrect(int genderNumber) {
        if (genderNumber < 1 || genderNumber > 6) {
            return false;
        }
        return true;
    }


    /**
     * Проверяет корректность номера месяца рождения в ID code.
     *
     * @param dayNumber целое число, представляющее номер месяца
     * @return {@code true}, если номер дня корректен, иначе {@code false}
     */
    public static boolean isDayNumberCorrect(int dayNumber) {
        if (dayNumber < 1 || dayNumber > 31) {
            return false;
        }
        return true;
    }

    /**
     * Проверяет корректность номера месяца рождения в ID code.
     *
     * @param monthNumber целое число, представляющее номер месяца
     * @return {@code true}, если номер месяца корректен, иначе {@code false}
     */
    public static boolean isMonthNumberCorrect(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            return false;
        }
        return true;
    }


    /**
     * Проверяет корректность даты рождения в ID code.
     *
     * @param yearNumber  целое число, представляющее год рождения (1955, 1999, 2013)
     * @param monthNumber целое число, представляющее номер месяца (1, 8)
     * @param dayNumber   целое число, представляющее номер дня (15, 31)
     * @return {@code true}, если номер дня корректен, иначе {@code false}
     */
    public static boolean isBirthDateCorrect(int yearNumber, int monthNumber, int dayNumber) {
        if (isMonthNumberCorrect(monthNumber) && isDayNumberCorrect(dayNumber) && yearNumber > 0) {
            return true;
        }
        return false;
    }

    /**
     * Проверяет, является ли данный год високосным.
     *
     * @param yearNumber целое число, представляющее год
     * @return {@code true}, если год високосный, иначе {@code false}
     */
    public static boolean isLeapYear(int yearNumber) {

        if (yearNumber % 4 == 0 && yearNumber % 100 == 0 && yearNumber % 400 == 0) {
            return true;
        }
        if (yearNumber % 4 == 0 && yearNumber % 100 == 0) {
            return false;
        }


        if (yearNumber % 4 == 0) {
            return true;
        }
        return false;
    }

    /**
     * Проверяет корректность контрольной суммы в ID code.
     *
     * @param idCode строка, представляющая ID code
     * @return {@code true}, если контрольная сумма корректна, иначе {@code false}
     */
    public static boolean checkControlNumber(String idCode) {
        int[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            char digitChar = idCode.charAt(i);
            if (!Character.isDigit(digitChar)) {
                return false;
            }

            int digit = Character.getNumericValue(digitChar);
            sum += digit * weights[i];
        }
        int remainder = sum % 11;
        int controlDigit = Integer.parseInt(idCode.substring(10));

        if (remainder == 10) {
            int[] weights2 = {3, 4, 5, 6, 7, 8, 9, 1, 2, 3};
            sum = 0;

            for (int i = 0; i < 10; i++) {
                int digit = Character.getNumericValue(idCode.charAt(i));
                sum += digit * weights2[i];
            }

            remainder = sum % 11;
        }
        if (remainder == controlDigit || (remainder == 10 && controlDigit == 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Метод, для получения информаций из ИД кода
     * Используйте данный формат - This is a (gender) born on (DD.MM.YYYY).
     * Gender в качестве ENUM
     *
     * @param idCode строка, представляющая ID code
     * @return Строка, в которой написан пол и дата рождения человека
     */
    public static String getInformation(String idCode) throws IllegalArgumentException {
        int genderNumber = Integer.parseInt(idCode.substring(0, 1));
        if (!isGenderNumberCorrect(genderNumber)) {
            throw new IllegalArgumentException("Gender is wrong");
        }
        Gender gender = getGender(genderNumber);
        int year = getFullYear(genderNumber, Integer.parseInt(idCode.substring(1, 3)));
        int month = Integer.parseInt(idCode.substring(3, 5));
        int day = Integer.parseInt(idCode.substring(5, 7));
        if (!isDayNumberCorrect(day) && isMonthNumberCorrect(month)) {
            throw new IllegalArgumentException("Incorrect day or month of birth");
        }
        if (!isBirthDateCorrect(year, month, day)) {
            throw new IllegalArgumentException("Incorrect birth date");
        }
        return "This is a " + gender.toString() + " born on " + idCode.substring(5, 7) + "." + idCode.substring(3, 5) + "." + year;
    }


    /**
     * Верните пол человека
     *
     * @param genderNumber The gender number from the ID code.
     * @return The gender as a string ("male" or "female").
     */
    public static Gender getGender(int genderNumber) {
        if (genderNumber == 1 || genderNumber == 3 || genderNumber == 5) {
            return Gender.MALE;
        }
        if (genderNumber == 2 || genderNumber == 4 || genderNumber == 6) {
            return Gender.FEMALE;
        }
        return Gender.UNKNOWN;
    }

    /**
     * Верните год, когда человек родился (1955, 2014)
     *
     * @param genderNumber The gender number from the ID code.
     * @param year         The two last digits of the birth year from the ID code.
     * @return The full 4-digit birth year.
     */
    public static int getFullYear(int genderNumber, int year) {
        String fullYearString = "";
        if (genderNumber == 1 || genderNumber == 2) {
            fullYearString = "18" + year;

        }
        if (genderNumber == 3 || genderNumber == 4) {
            fullYearString = "19" + year;

        }
        if (genderNumber == 5 || genderNumber == 6) {
            fullYearString = "20" + year;
        }
        return Integer.parseInt(fullYearString);
    }

}
