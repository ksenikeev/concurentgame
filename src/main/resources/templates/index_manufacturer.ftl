<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Конкуренция</title>
</head>
<body>

<h1>Игра "Конкуренция"</h1>


<#if canJoin>
<div >
    <form action="/game/join" method="post">
        <label>Введите имя:</label>
        <input type="text" name="name"/>
        <button>Принять участие в игре</button>
    </form>
</div>
</#if>

<#if inActiveGame>
    Вы участник игры ${game.id}, <a href="/game/manufacturer/${manufacturerId}">перейти</a>
</#if>

</body>
</html>