<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Конкуренция</title>

    <link rel='stylesheet' href='/game/resources/css/game.css'>
    <script src='/game/resources/js/game.js'></script>
</head>
<body>

<div class="mcnt">
    <h2>Игра "Конкуренция"</h2>
    <form class="container" action="/game/login" method="post" onsubmit="validate()">
        <input class="field" name="username" placeholder="имя пользователя"/>
        <input type="password" class="field" name="password" placeholder="пароль"/>
        <br>
        <input class="field" type="submit" value="Вход"/>
    </form>
</div>

</body>
</html>