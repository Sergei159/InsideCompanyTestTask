# test task for company "INSIDE"

Задание смотри в файле Task.  

cURL команды смотри в файле CurlCommands.



### Функции

**1. Регистрация пользователя**  

POST запрос
```
http://localhost:9092/person/sign-up 
```

```
{
"name" : "user",
"password" : "123456"
}
```

![ScreenShot](images/1.JPG)

**2. Вход в систему**  

POST запрос

```
http://localhost:9092/login 
```

```
{
"name" : "user",
"password" : "123456"
}
```

![ScreenShot](images/2.JPG)

**3. Скопировать токен из заголовка Authorization ответа**  

![ScreenShot](images/3.JPG)

**4. Во всех запросах должен быть заголовок Authorization со значением токена**  

![ScreenShot](images/4.JPG)

**5. Опубликовать сообщение**  

POST запрос

```
http://localhost:9092/message/
```
```

{
"body" : "msg1"
}
```

![ScreenShot](images/5.JPG)

**5. Посмотреть историю сообщений пользователя**  

POST запрос

```
http://localhost:9092/message/
```
```

{
"body" : "history"
}
```

![ScreenShot](images/6.JPG)

**5. Вывести n последних сообщений пользователя**  

POST запрос

```
http://localhost:9092/message/
```

```
{
"body" : "history 3"
}
```

![ScreenShot](images/7.JPG)

