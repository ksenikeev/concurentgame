<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Конкуренция-Администрирование</title>
    <link rel='stylesheet' href='/game/resources/css/game.css'>
</head>
<body>
<h1>Игра "Конкуренция"</h1>

<form action="/game/create" method="get">
    <button>Сформировать новую игру</button>
</form>
<h2>История игр:</h2>
<table>
    <tr>
        <th>Начало</th>
        <th>Конец</th>
        <th>Статус</th>
        <th></th>
    </tr>
    <#list games as game>
        <tr>
            <td>${game.startDate!} </td>
            <td>${game.endDate!}</td>
            <td>${game.gameStatus!}</td>
            <td>
                <form action="/game/${game.id}">
                    <button>Перейти</button>
                </form>
            </td>
        </tr>
    </#list>
</table>
<br>
<button class="logout" onclick="window.location.href='/game/logout';">Выход</button>

</body>
</html>