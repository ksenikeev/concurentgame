<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Конкуренция</title>
    <link rel='stylesheet' href='/game/resources/css/game.css'>
</head>
<body>


<div class="mcnt">
    <h2>Игра "Конкуренция"</h2>

<#if canJoin>
    <form class="container" action="/game/join" method="post">
        <label>Введите имя:</label>
        <input type="text" name="name"/>
        <button>Принять участие в игре</button>
    </form>
</#if>

<#if inActiveGame>
    Вы участник игры ${game.id},
    <button onclick="window.location.href='/game/manufacturer/${manufacturerId}';">перейти</button>
</#if>

<br>
<button class="logout" onclick="window.location.href='/game/logout';">Выход</button>

</div>

</body>
</html>