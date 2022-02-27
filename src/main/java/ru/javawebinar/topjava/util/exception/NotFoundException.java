package ru.javawebinar.topjava.util.exception;
//клас с нашими ексепшенами. наследуем от Рантайм.
public class NotFoundException extends RuntimeException {
    //будем выбрасывать это наше исключение в местах, где может что-то пойти не так
    //в этот конструктор надо подать Стрингу с сообщением об ошибке
    public NotFoundException(String message) {
        super(message);
    }
}