package interfaces;

import java.lang.reflect.Array;

public interface Printable {

    default void printMainMenu(){
        System.out.println(
                "Chose an option:\n" +
                        "1) Show clients in default order\n" +
                        "2) Sort by name\n" +
                        "3) Sort by ID\n" +
                        "4) Sort by credentials"
        );
    }

    default void printNameSortingOptions(){
        System.out.println(
                "Sort clients by:\n" +
                        "1) Names alphabet order\n" +
                        "2) Name length\n" +
                        "3) Amount of a's in name\n" +
                        "4) Amount of vowels in name\n" +
                        "5) Amount of consonants in name\n" +
                        "6) Name unicode\n" +
                        "7) Name locale\n" + // Тут все сложно
                        "8) Combine name criteria" // Придумать что-то комбинированное
        );
    }

    default void printLettersCaseOption(){
        System.out.println(
                "Chose an option:\n" +
                        "1) Case-sensitive sorting\n" +
                        "2) Case-insensitive sorting\n"
        );
    }

    default void printIdSortingOptions(){
        System.out.println(
                "Sort clients by:\n" +
                        "1) ID Ascending \n" +
                        "2) ID Descending \n" +
                        "3) Amount of numbers in ID\n" +
                        "4) ID even number\n" + // ЭТО ДОП ЗАДАНИЕ
                        "5) ID odd number\n" +
                        "6) Unicode\n" +
                        "7) Combine criteria\n" +
                        "8) ID parts\n" + // если сделать ID составной
                        "9) ID creation date" // если сделать ID включающим дату

        );
    }

    default void printCredentialsMenu(){
        System.out.println(
                "Sort clients by:\n" +
                        "1) Phone numbers\n" +
                        "2) Addresses\n" +
                        "3) Emails\n"
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

    default void printAddressesSortingOption(){
        System.out.println(
                "Sort clients by address:\n" + // так как в одну строку то меньше опций
                        "1) In alphabetic order\n" +
                        "2) Postal code\n"
        );
    }

    default void printEmailSortingOptions(){
        System.out.println(
                "Sort clients by email:\n" +
                        "1) Domain name\n" +
                        "2) Local part\n" +
                        "3) Domain type\n" +
                        "4) Length\n"
        );
    }

}
