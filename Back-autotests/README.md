# Проект WordPress для автоматизации тестирования API и БД

## Общая информация
Для использования проекта нужен Docker и Docker-compose.

Более подробно документацию к API WordPress можно получить на [офф сайте](http://v2.wp-api.org/)

После развертывания проекта для доступа к API WordPress можно воспользоваться ссылкой:
[http://localhost:8000/index.php?rest_route=/](http://localhost:8000/index.php?rest_route=/)

## Тест-кейсы для тестирования API и БД которые будут автоматизированы в данном проекте: 
https://docs.google.com/spreadsheets/d/1EKZtW9pT6DV10WF0yfvRN0bYiQLSgoJL9jPaykVgHO4/edit?usp=sharing
## Инструкция по развертыванию проекта

1. Установить docker - [ссылка на скачивание docker для Windows](https://www.docker.com/docker-windows)
2. Распаковать архив и перейти в дирректорию скачанного проекта, где лежит docker-compose файл
3. Открыть командную строку в дирректории из шага 3
4. В командной строке выполнить команду – `docker-compose up`.
   Это приведёт к запуску двух контейнеров Docker, база данных MySQL и Apache сервер с предустановленным WordPress. Порты можно поправить при необходимости в docker-compose.yml. По умолчанию Compose пробросит два порта на Ваш localhost:
    + порт 8000 для доступа к wordpress по http;
    + порт 3306 для доступа к mysql; 
5. В браузере открыть ссылку [http://localhost:8000/](http://localhost:8000/). Должно отобразиться окно начальной настройки WordPress
6. Выбрать русский язык и нажать продолжить
7. Заполнить появившуюся форму данными в соответствии с требованиями в программе обучения, например:
    + Название сайта – lastname-site-probation
    + Имя пользователя – Firstname.LastName
    + Пароль - 123-Test
    + e-mail – firstname.lastname@simbirsoft.com
8. Нажать кнопку "Установить WordPress"
9. После установки нажать кнопку "Войти" и ввести введенные ранее учетные данные.
10. Данные для подключения к базе данных:  
- порт 3306
- БД - wordpress
- user – wordpress
- password – wordpress.
11. Перейти в директорию проекта и открыть командную строку
12. Выполнить команду `docker ps` и для перехода в контейнер выполнить `docker exec -it <container> bash`
13. Выполнить команды `apt-get update` и `apt-get install git`
14. Перейти в каталог `/var/www/html/wp-content/plugins#`
15. Установить плагин для базовой аутентификации `git clone https://github.com/WP-API/Basic-Auth.git`
16. Если все прошло успешно должны появиться папка Basic-Auth
17. Открыть в браузере ссылку [http://localhost:8000/wp-admin/plugins.php](http://localhost:8000/wp-admin/plugins.php) и перейти в раздел ‘Плагины’
18. Активировать плагин JSON Basic Authentification
19. Открыть Postman и посмотреть информацию о пользователе (использовать базовую аутентификацию)
20. Если все шаги выполнены верно, то в запросе отобразится информация о текущем пользователе
