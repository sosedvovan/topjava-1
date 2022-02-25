package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

public class Util {

//   метод проверяет- находится ли переданный объект в заданном промежутке. Наверно параметризуется или LocalDate или LocalTime
//
//    объект,который на входе должен имплементить интерфейс Comparable<который параметризован родителем этого объекта>
//    (напр приходит объект LocalDate, класс которого имплементит Comparable<LocalDateTime> ?)
//
//    в return- выражение в первых скобках вернет True, если (start == null или start) >= 0)
//              выражение во вторых скобках вернет True, если (end == null или (end) <= 0)
//    общий True будет, если и в первых и во вторых скобках True одновремменно
    public static <T extends Comparable<? super T>> boolean isBetweenInclusive(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) <= 0);
    }
}
