<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Конкуренция-Администрирование</title>
</head>
<body>
<table>
<tr>
    <th>Дата начала</th>
    <th>Стартовый капитал производителя</th>
    <th>Базовая себестомость</th>
    <th>Бюджет потребителя</th>
    <th>Статус</th>
    <th></th>
</tr>
<tr>
    <td>${game.startDate!} </td>
    <td>${game.getStartUpCapital()!}</td>
    <td>${game.getCostPrice()!}</td>
    <td>${game.getBuyersBudget()!}</td>
    <td>${game.getGameStatus()!}</td>
</tr>
</table>
<form action="/game/${game.id}/start">
    <button>Запустить игру</button>
</form>
<form action="/game/${game.id}/finish">
    <button>Завершить игру</button>
</form>
<form action="/game">
    <button>Все игры</button>
</form>
</body>
</html>