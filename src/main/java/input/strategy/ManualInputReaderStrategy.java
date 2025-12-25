package input.strategy;

import dto.Client;
import input.CustomCollection;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Стратегия для ручного ввода данных клиентов через консоль.
 * Реализует интерфейс {@link ClientInputStrategy} для интерактивного
 * ввода данных о клиентах с валидацией и подтверждением.
 *
 * <p>Обеспечивает:</p>
 * <ul>
 *   <li>Пошаговый ввод данных о клиентах</li>
 *   <li>Валидацию введенных данных</li>
 *   <li>Подтверждение корректности ввода</li>
 *   <li>Ограничение на уникальность ID клиентов</li>
 *   <li>Лимит на максимальное количество клиентов (1000)</li>
 * </ul>
 *
 * @see ClientInputStrategy
 * @see Client
 * @see CustomCollection
 */
public class ManualInputReaderStrategy implements ClientInputStrategy {
    private Scanner scanner;
    private Set<Integer> userIds;

    public ManualInputReaderStrategy() {
        this.scanner = new Scanner(System.in);
        this.userIds = new HashSet<>();
    }

    /**
     * Получает коллекцию клиентов через интерактивный ручной ввод.
     * Метод последовательно запрашивает данные о каждом клиенте,
     * выполняет валидацию и подтверждение, после чего добавляет
     * клиента в результирующую коллекцию.
     *
     * <p>Процесс ввода продолжается до тех пор, пока:</p>
     * <ul>
     *   <li>Пользователь не откажется от добавления нового клиента</li>
     *   <li>Не будет достигнут лимит в 1000 клиентов</li>
     * </ul>
     *
     * @return коллекция введенных клиентов
     * @throws IOException если возникает ошибка ввода-вывода
     */
    @Override
    public CustomCollection<Client> getData() throws IOException {
        CustomCollection<Client> clients = new CustomCollection<>();

        System.out.println("=== Ручной ввод клиентов ===");
        System.out.println("Вводите данные клиентов. Для завершения введите 'стоп'.\n");

        int clientNumber = 1;

        while (true) {
            if (userIds.size() == 1000) {
                System.out.println("Достигнут лимит клиентов");
                break;
            }

            System.out.println("Ввод клиент №" + clientNumber);

            try {
                if (!promptForConfirmation("Добавляем клиента?")) {
                    break;
                }

                Client client = inputClient(clientNumber);
                if (client != null) {
                    clients.add(client);
                    clientNumber++;
                }
            } catch (StopInputException e) {
                System.out.println("\nВвод прерван пользователем. Текущий клиент не добавлен.");
                break;
            }
        }
        System.out.println("\n Ввод завершен. Добавлено клиентов: " + clients.size());
        return clients;
    }

    /**
     * Выполняет ввод данных для одного клиента.
     * Запрашивает имя, телефонный номер и идентификатор,
     * затем отображает введенные данные для подтверждения.
     *
     * @param clientNumber порядковый номер клиента (для отображения в интерфейсе)
     * @return объект {@link Client} с введенными данными или {@code null},
     * если пользователь отменил ввод
     */
    private Client inputClient(int clientNumber) {
        try {
            String name = promptForName();
            String phone = promptForNumber();
            int id = promptForId();

            Client.ClientBuilder builder = new Client.ClientBuilder().name(name)
                    .phoneNumber(phone)
                    .idNumber(id);

            System.out.println("\nВы ввели:");
            System.out.println("  Имя: " + name);
            System.out.println("  Телефон: " + phone);
            System.out.println("  ID: " + id);

            if (promptForConfirmation("Все верно?")) {
                return builder.build();
            } else {
                System.out.println("Отмена ввода этого клиента");
                return null;
            }
        } catch (StopInputException e) {
            throw e;
        }
    }

    private String promptForName() {
        try {
            return promptForString("Введите имя", true);
        } catch (StopInputException e) {
            throw e;
        }
    }

    private String promptForNumber() {
        while (true) {
            try {
                String phone = promptForString("Введите номер телефона в формате +7XXXXXXXXXX", true);

                if (phone.matches("^\\+7\\d{10}$")) {
                    return phone;
                } else {
                    System.out.println("⚠️  Неверный формат телефона! Пример: +79991234567");
                    System.out.println("    Должно начинаться с +7 и содержать 11 цифр");
                }
            } catch (StopInputException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int promptForId() {
        while (true) {
            String input = promptForString("Введите ID в диапазоне от 0 до 1000", true);

            try {
                int id = Integer.parseInt(input);

                if (id < 0 || id > 1000) {
                    System.out.println("ID должен быть в диапазоне от 0 до 1000");
                    continue;
                }

                if (userIds.contains(id)) {
                    System.out.println("Этот ID уже занят");
                    continue;
                }

                userIds.add(id);
                return id;
            } catch (StopInputException e) {
                throw e;
            } catch (NumberFormatException e) {
                System.out.println("ID должен быть целым числом");
            }
        }
    }

    /**
     * Запрашивает у пользователя подтверждение действия.
     * Поддерживает ввод на русском и английском языках.
     *
     * <p>Принимаемые варианты подтверждения:</p>
     * <ul>
     *   <li>Положительные: "да", "д", "y", "yes"</li>
     *   <li>Отрицательные: "нет", "н", "n", "no"</li>
     * </ul>
     *
     * <p>Метод продолжает запрашивать ввод до получения корректного ответа.</p>
     *
     * @param message сообщение с вопросом для подтверждения
     * @return {@code true} если пользователь подтвердил действие,
     * {@code false} если отказался
     */
    private boolean promptForConfirmation(String message) {
        while (true) {
            System.out.print(message + " (да/д/yes/y  или нет/н/no/n): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equalsIgnoreCase("стоп")) {
                throw new StopInputException();
            }

            if (input.equals("да") || input.equals("д") || input.equals("y") || input.equals("yes")) {
                return true;
            }
            if (input.equals("нет") || input.equals("н") || input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("⚠️  Пожалуйста, введите 'да/д/yes/y' или 'нет/н/no/n'");
        }
    }

    /**
     * Универсальный метод для запроса строкового ввода от пользователя.
     * Обрабатывает обязательность поля и базовые ошибки ввода.
     *
     * <p>Если поле является обязательным ({@code required = true}),
     * метод будет продолжать запрашивать ввод до получения непустой строки.</p>
     *
     * @param message  сообщение с описанием запрашиваемых данных
     * @param required флаг, указывающий является ли поле обязательным для заполнения
     * @return введенная пользователем строка (без начальных и конечных пробелов)
     */
    private String promptForString(String message, boolean required) {
        while (true) {
            System.out.print(message + ": ");

            try {
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("стоп")) {
                    throw new StopInputException();
                }

                if (required && input.isEmpty()) {
                    System.out.println("⚠️  Это поле обязательно для заполнения!");
                    continue;
                }

                return input;
            } catch (Exception e) {
                if (e instanceof StopInputException) {
                    throw e; // Пробрасываем дальше
                }
                System.out.println("Ошибка ввода: " + e.getMessage());
                scanner.nextLine(); // Очистка буфера
            }
        }
    }

    static class StopInputException extends RuntimeException {
        public StopInputException() {
            super("Ввод прерван пользователем");
        }
    }
}