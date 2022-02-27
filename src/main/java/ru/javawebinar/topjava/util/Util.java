package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

public class Util {

//   метод проверяет- находится ли переданный объект LocalTime в заданном промежутке (между start LocalTime и end LocalTime).
//
//   исходя из выражения:<T extends Comparable<? super T>>  - метод параметризуется объектом LocalTime тк,
//   класс LocalTime имплементит интерфейс Comparable<LocalTime>  и сам Comparable в этом случае параметризован
//   с учетом требования из выражения:<? super T>   - те любой тип T или его родитель  (те T это нижняя граница)
    //учитывая, что у LocalTime родитель только Object, то можно только сказать: Comparable<LocalTime> или Comparable<Object>
    //значит фраза <? super T> написанна только для протокола и получается, что так надо писать всегда, когда хотим сказать,
    // что класс должен имплеменить Comparable:  <T extends Comparable<? super T>>.

//    (напр приходит объект LocalDate, класс которого имплементит Comparable<LocalTime> ?)

    //учитывая, что LocalDate, LocalDateTime не реализуют интерфейса Comparable, то в этот метод можно подать только LocalTime
    //Так зачем его надо было параметризовывать? Универсального метода не получильсь же.
//
//    в return- выражение в первых скобках вернет True, если (start == null или start) >= 0)
//              выражение во вторых скобках вернет True, если (end == null или (end) <= 0)
//    общий True будет, если и в первых и во вторых скобках True одновремменно
    public static <T extends Comparable<? super T>> boolean isBetweenInclusive(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) <= 0);
    }
}

//<? super T>  - означает ограничение по нижней границе включительно- можно подать класс T или любой из родителей T.
//T extends Comparable  - означает ограничение по верхней границе НЕвключительно - можно подать только детёныша Comparable,
//но сам Comparable подать нельзя (на месте Comparable может быть любой интерфейс или класс, от которого екстендятся).