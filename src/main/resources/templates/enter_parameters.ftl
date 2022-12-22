<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Конкуренция-Администрирование</title>
</head>
<body>

<form method="post" action="/game/${gameId}/manufacturer-status/${statusId}">
    <label>Количество продукции</label><br>
    <input type="number" name="productCount"><br>
    <label>Цена единицы продукции</label><br>
    <input type="number" name="price"><br>
    <label>Затраты на рекламу</label><br>
    <input type="number" name="advertisement"><br>
    <label>Ассортимент</label><br>
    <input type="number" name="assortment"><br>
    <input type="submit" value="Отправить">
</form>

</body>
</html>