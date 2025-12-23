package userInterface;

public interface Printable {

    default void printMainMenu(){
        System.out.println(
                "1) Наполнить клиентскую базу\n" +
                        "2) Показать параметры сортировки\n" +
                            "0) Выход"
        );
    }

    default void printFillingDatabaseMenu(){

        System.out.println("""
                Заполнить базу данных:
                1) Ручной ввод
                2) Файлом
                3) Случайный набор\
                0) Возврат в главное меню"""
        );
    }

    default void printSortingMenu(){
        System.out.println("""
                Выберите вариант:
                1) Отображать клиентов в порядке по умолчанию
                2) Сортировать по имени
                3) Сортировать по ID
                4) Сортировать по номеру телефона
                0) Возврат в главное меню"""
        );
    }

    default void printNameSortingOptions(){
        System.out.println(
                "Клиенты отсортированы по умолчанию\n"
        );
    }

    default void printIdSortingOptions(){
        System.out.println("""
                Сортировать клиентов:
                1) По возрастанию ID
                2) По четным ID
                0) Назад"""

        );
    }

    default void printPhoneSortingOptions(){
        System.out.println(
                "Клиенты отсортированы по умолчанию\n"
        );
    }
}
