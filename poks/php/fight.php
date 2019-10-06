<html>
  <head>
    <title>Fight</title>
    <link rel = 'stylesheet' href = 'index.css'>
  </head>
  <body>
    <h2>Fight</h2>
    <form>
      <a href='/laba/arena.php'>Arena</a>
    </form>
  </body>
</html>

<?php
session_start();

if($_SESSION['power'] == '') {
  header("Refresh:0; url=/laba/create_user.php");
}

$enemyPower = rand(1, 9);
$enemyHp = rand(1, 9);
echo 'ENEMY Power: ' . $enemyPower . '<br />';
echo 'ENEMY HP: ' . $enemyHp . '<br />';

$power = $_SESSION['power'];
$hp = $_SESSION['hp'];
$level = $_SESSION['level'];
$points = $_SESSION['points'];
$name = $_SESSION['name'];
$exp = $_SESSION['exp'];

echo $name . ' Power: ' . $power . '<br />';
echo $name . ' HP: ' . $hp . '<br /><br />';

if (($power > $enemyHp && $enemyPower > $hp) || (!($power > $enemyHp) && !($enemyPower > $hp))) {
  echo 'RESULT OF FIGHT: <b>Draw</b> <br />';
}
else if ($power > $enemyHp) {
  echo 'RESULT OF FIGHT: <b>' . $name . ' wins</b> <br />';
  $_SESSION['exp'] = ++$exp;
  if ($exp % 3 == 0) {
    $_SESSION['points'] += 3;
    $_SESSION['level'] += 1;
  }
}
else if ($enemyPower > $hp) {
  echo 'RESULT OF FIGHT: <b>Enemy wins </b><br />';
  $_SESSION['exp'] = $exp - $exp % 3;
}  
?>