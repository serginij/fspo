<html>
<head>
<title>Проверка метода GET в PHP</title>
<link rel = 'stylesheet' href = 'index.css'>
</head>
<body>
<form>
<a href='/laba/create_user.php'>Create user</a>
<a href='/laba/arena.php'>Arena</a>
<a href='/laba/fight.php'>Fight</a>
<a href='/laba/save_load.php'>Save / Load</a>
<a href='/laba/level_up.php'>Level up</a>
</form>
<?php
echo ($_GET["num"]."<br>");
echo ($_GET["type"]."<br>");
echo ($_GET["v"]);
?>
</body>
</html>