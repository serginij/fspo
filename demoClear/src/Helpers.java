import java.time.LocalDate;

/*
    Разработал: Сергей Котлицкий
    github: https://github.com/serginij
    email: kotlizkiy@gmail.com
*/

public class Helpers {
    // Функция для проверки строки на совпадение Кириллице
    public static boolean checkTextCyrillic(String text) {
        return text.matches("^[а-яА-Я]+$");
    }

    // Функция для проверки строки на совпадение Латинице
    public static boolean checkTextLatin(String text) {
        return text.matches("^[a-zA-Z]+$");
    }

    // Функция для проверки строки на совпадение Латинице + цифры
    public static boolean checkTextDigits(String text) {
        return text.matches("^[a-zA-Z0-9]+$");
    }

    // Функция для проверки строки на длину
    public static boolean checkLength(String text, int length) {
        return text.length() <= length && text.trim().length() > 0;
    }

    // Функция для проверки строки на строго заданную длину
    public static boolean checkLengthStrict(String text, int length) {
        return text.length() == length;
    }

    // Функция для проверки строки на возможность преобразования в дату
    public static boolean checkDate(String text) {
        try {
            LocalDate date = LocalDate.parse(text);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
