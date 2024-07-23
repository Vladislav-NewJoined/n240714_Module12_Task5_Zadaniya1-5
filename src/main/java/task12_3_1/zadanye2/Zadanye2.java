package task12_3_1.zadanye2;

import java.sql.*;

// Команда в терминале для создания базы данных MMariaDB: docker run --name mysql -d -p 3306:3306 -e MYSQL_DATABASE=somedb -e MYSQL_USER=someuser -e MYSQL_PASSWORD=123 yobasystems/alpine-mariadb
// Значения параметров для настройки соединения в DBeaver (пришли в ответ на команду в терминале: docker inspect mysql:
// Сервер (Хост): 172.17.0.2 (нужно писать localhost вместо этого)
// База данных: somedb (значение переменной окружения MYSQL_DATABASE)
// Пользователь: someuser (значение переменной окружения MYSQL_USER)
// Пароль: 123 (значение переменной окружения MYSQL_PASSWORD)
// Драйвер: MySQL

public class Zadanye2 {
    public static void main(String[] args) throws SQLException {
        System.out.println("""
            Задание:\s
            Модуль 12. Базы данных и Git. Задание №4:\s
                2. Что такое партиционирование?

                Решение:
            \s""");

        System.out.println("""
                Партиционирование в базах данных - это процесс разделения большой таблицы на более
                мелкие (подтаблицы), называемые разделениями или партициями, с целью улучшения производительности
                и управления данными.\s
            
                Партиционирование позволяет лучше организовать данные в базе данных, увеличивая скорость
                выполнения запросов и снижая нагрузку на сервер. Оно также упрощает управление данными,
                позволяя легко добавлять, удалять и перемещать разделения внутри таблицы.
            
                Существует несколько способов реализации партиционирования, такие как разделение по
                диапазону значений столбца, хэш-функции или списку значений. Каждый из них подходит
                для определенных сценариев использования и позволяет оптимизировать работу с данными
                в базе данных.\s""");
    }
}
