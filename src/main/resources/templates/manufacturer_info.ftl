<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>Конкуренция-Администрирование</title>
    <link rel='stylesheet' href='/game/resources/css/game.css'>
</head>
<body>

<div class="mcnt container">

    <button onclick="window.location.href='/game/manufacturer/${statusId}';">
        Перейти к отправке данных производства
    </button>

    <#list statusInfoLst as info>
        <fieldset class="container">
            <legend>${info.startDate!}</legend>
            <label>Начальный остаток: ${info.balance!}</label>
            <label>Количество: ${info.productCount!}</label>
            <label>Ассортимент: ${info.assortment!}</label>
            <label>Производственные расходы:</label>
            <label>${info.expenses!}</label>
            <label>Реклама: ${info.advertisement!}</label>
            <label>Цена: ${info.price!}</label>
            <label>Выручка: ${info.income!}</label>
        </fieldset>
    </#list>

</div>
</body>
</html>