package userInterface;

public interface Printable {

    default void printMainMenu(){
        System.out.println(
                "1) Fill the client database\n" +
                        "2) Show sorting options" +
                            "3) Exit"
        );
    }

    default void printFillingDatabaseMenu(){
        System.out.println(
                "Fill database: \n" +
                        "1) Manual input\n" +
                        "2) File\n" +
                        "3) Random"
        );
    }

    default void printSortingMenu(){
        System.out.println(
                "Chose an option:\n" +
                        "1) Show clients in default order\n" +
                        "2) Sort by name\n" +
                        "3) Sort by ID\n" +
                        "4) Sort by phone number"
        );
    }

    default void printNameSortingOptions(){
        System.out.println(
                "Sort clients by:\n" +
                        "1) Names alphabet order\n" +
                        "2) Name length\n" +
                        "3) Amount of vowels in name\n" +
                        "4) Amount of consonants in name\n" +
                        "5) Name unicode\n" +
                        "6) Combine name criteria\n" + // Придумать что-то комбинированное
                        "0) Go back"
        );
    }

    default void printIdSortingOptions(){
        System.out.println(
                "Sort clients by:\n" +
                        "1) ID Ascending \n" +
                        "2) ID Descending \n" +
                        "3) Amount of numbers in ID\n" +
                        "4) ID even number\n" + // ЭТО ДОП ЗАДАНИЕ
                        "5) ID odd number\n" + // ЭТО ДОП ЗАДАНИЕ
                        "6) Unicode\n" +
                        "7) Combine criteria\n" +
                        "9) ID creation date" // если сделать ID включающим дату

        );
    }

    default void printPhoneSortingOptions(){
        System.out.println(
                "Sort clients by phone:\n" +
                        "1) Normalized number\n" + // приведенный к международному формату
                        "2) Country code\n" +
                        "3) Operator code\n" +
                        "4) Last numbers\n" +
                        "5) Number length\n" +
                        "6) Clean number\n" + // без знаков
                        "7) Combine criteria\n"

        );
    }
}
