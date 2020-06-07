import java.time.LocalDate;

/*  Разработал: Сергей Котлицкий
    github: https://github.com/serginij
    email: kotlizkiy@gmail.com
*/

public class  Helpers {
    public static boolean checkText(String text) {
        return text.matches("[a-zA-Zа-яА-Я]+");
    }

    public static boolean checkLength(String text, int length) {
        return text.length() <= length && text.trim().length() > 0;
    }

    public static boolean checkLengthStrict(String text, int length) {
        return text.length() == length ;
    }

    public static boolean checkDate(String text) {
        try {
            LocalDate date = LocalDate.parse(text);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
}
