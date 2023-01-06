<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Конкуренция-Администрирование</title>
    <link rel='stylesheet' href='/game/resources/css/game.css'>
</head>
<body>

<div>

<a href="/game/${gameId}">&gt;Перейти к управлению игрой&gt;</a>
</div>
<br>

<div>
    <table>
        <tr>
            <th>Производитель</th>
            <th>Время старта</th>
            <th>Количество</th>
            <th>Цена</th>
            <th>Реклама</th>
            <th>Ассортимент</th>
            <th>Выручка</th>
            <th>Начальный остаток</th>
        </tr>
        <#list statusInfoLst as info>
        <tr>
            <td>${info.name!} </td>
            <td>${info.startDate!} </td>
            <td>${info.productCount!} </td>
            <td>${info.price!} </td>
            <td>${info.advertisement!} </td>
            <td>${info.assortment!} </td>
            <td>${info.income!} </td>
            <td>${info.balance!} </td>
        </tr>
    </#list>
    </table>

</div>
</body>
</html>