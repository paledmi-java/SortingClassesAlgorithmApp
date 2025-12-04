package interfaces;

import java.util.List;

public interface Sortable {

    default int[] bubbleIntegerSorting(int[] numbers) {
        boolean isSwaped = true;
        int lastIndex = numbers.length - 1;

        while (isSwaped) {
            isSwaped = false;
            int range = lastIndex;

            for (int i = 0; i < range; i++) {
                if (numbers[i] > numbers[i + 1]) {
                    int number1 = numbers[i];
                    numbers[i] = numbers[i + 1];
                    numbers[i + 1] = number1;
                    isSwaped = true;
                    lastIndex = i + 1;
                }
            }
        }
        return numbers;
    }
}
