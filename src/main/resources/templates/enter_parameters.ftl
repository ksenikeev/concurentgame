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
<#if showParam>
<form class="container" method="post" action="/game/manufacturer-status/${statusId}">
    Вы располагаете суммой: <span style="color:red;font-weight: bold">${balance!}</span>

    ваши текущие затраты: <span id="expenses" style="color:red;font-weight: bold">0</span>

    <h3>Укажите параметры предприятия:</h3>

    <label for="productCount">Количество продукции: </label>
    <input type="number" name="productCount" id="productCount" value="${productCount!}">

    <label for="price">Цена единицы продукции: </label>
    <input type="number" name="price" id="price" value="${price!}">

    <label for="advertisement">Затраты на рекламу:</label>
    <input type="number" name="advertisement" id="advertisement" value="${advertisement!}">

    <label for="assortment">Ассортимент:</label>
    <input type="number" name="assortment" id="assortment" value="${assortment!}">

    <input type="submit" value="Отправить">

</form>

    <div>
        <h3>Расчет себестоимости</h3>
        <table>
            <tr><td width="60%">[1 - 10)</td><td>${cost!}</td></tr>
            <tr><td width="60%">[10 - 100)</td><td>${cost95!}</td></tr>
            <tr><td width="60%">[100 - 500)</td><td>${cost90!} </td></tr>
            <tr><td width="60%">[500 - 1000)</td><td>${cost85!}</td></tr>
            <tr><td width="60%">[1000 - 5000)</td><td>${cost80!}</td></tr>
            <tr><td width="60%">[5000 - )</td><td>${cost75!}</td></tr>
        </table>
    </div>

    <br>
    <button class="logout" onclick="window.location.href='/game/logout';">Выход</button>

    <script type="text/javascript">
     document.getElementById('productCount').addEventListener('input', calculate_expenses);
     document.getElementById('advertisement').addEventListener('input', calculate_expenses);
     document.getElementById('assortment').addEventListener('input',  calculate_expenses);
     document.getElementById('price').addEventListener('input', calculate_expenses);

     function calculate_expenses() {
        if (Number(advertisement.value) < 0) advertisement.value = 0;
        if (Number(assortment.value) > Number(productCount.value)) assortment.value = productCount.value;
        if (Number(assortment.value) < 1) assortment.value = 1;

        expenses.innerHTML = Number(advertisement.value) + calculate_cost(productCount.value / assortment.value) * Number(productCount.value);
        console.log(Number(assortment.value) + ", " + (calculate_cost(productCount.value / assortment.value)) + ", " + productCount.value);
     }

     function calculate_cost(count) {
         if (count < 10) return ${cost!};
        else if (count < 100) return  ${cost95!};
        else if (count < 500) return  ${cost90!};
        else if (count < 1000) return  ${cost85!};
        else if (count < 5000) return  ${cost80!};
        else return ${cost75!};
     }

     window.addEventListener('load', calculate_expenses);
</script>

<#else>
<h2>Ожидайте начала очередного кона игры</h2>
</#if>
<br>
<button onclick="window.location.href='/game/manufacturer-status/info/${statusId}';">Ваша статистика</button>

</body>
</html>