<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Конкуренция-Администрирование</title>
    <link rel='stylesheet' href='/game/resources/css/game.css'>
</head>
<body>

<div class="mcnt">
    <form  class="container" method="post" action="/game/create">
        <label for = "sc">Стартовый капитал производителя</label>
        <input id = "sc" type="number" name="startUpCapital">
        <label for = "cp">Базовая себестомость</label>
        <input id = "cp" type="number" name="costPrice">
        <label for = "bp">Бюджет потребителя</label>
        <input id = "bp" type="number" name="buyersBudget">
        <input type="submit" value="Сформировать новую игру">
    </form>
    <br>
    <button class="logout" onclick="window.location.href='/game/logout';">Выход</button>

</div>
</body>
</html>