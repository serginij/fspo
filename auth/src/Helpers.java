/* Информация о разработчике:
    Котлицкий Сергей
    Контакты:
        email: kotlizkiy@gmail.com
        github: https://github.com/serginij
*/
import java.time.LocalDate;

public class Helpers {
    public static boolean isText(String text) {
        return text.matches("[a-zA-Zа-яА-я]+");
    }

    public static boolean isDate(String text) {
        try {
            LocalDate date = LocalDate.parse(text);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean checkLength(String text, int length) {
        return text.length() <= length && text.trim().length() > 0;
    }

    public static boolean checkLengthStrict(String text, int length) {
        return text.length() == length;
    }
}
