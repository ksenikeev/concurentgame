<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Конкуренция-Администрирование</title>
</head>
<body>

<div>
<#if showParam>
<form method="post" action="/game/manufacturer-status/${statusId}">
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
<#else>
<h2>Ожидайте начала очередного кона игры</h2>
</#if>


<div>
    <h3>Расчет себестоимости</h3>
    <table>
        <tr><td>[1 - 100)</td><td>базовая себестоимость * 0.95 </td></tr>
        <tr><td>[100 - 500)</td><td>базовая себестоимость * 0.9 </td></tr>
        <tr><td>[500 - 1000)</td><td>базовая себестоимость * 0.85</td></tr>
        <tr><td>[1000 - 5000)</td><td>базовая себестоимость * 0.8</td></tr>
        <tr><td>[5000 - )</td><td>базовая себестоимость * 0.75</td></tr>
    </table>
</div>


</body>
</html>