<html>
  <head>
    <title>Level up</title>
    <link rel = 'stylesheet' href = 'index.css'>
  </head>
  <body>
    <h2>Level up</h2>
    <form>
      <a href='/laba/arena.php'>Arena</a>
    </form>
    <form action="level_up.php" method="post">
      <input type="submit" name="up_power" value="Upgrade power" />
      <input type="submit" name="up_hp" value="Upgrade hp" />
    </form>
  </body>
</html>

<?php
session_start();

if($_SESSION['power'] == '') {
  header("Refresh:0; url=/laba/create_user.php");
}

$power = $_SESSION['power'];
$hp = $_SESSION['hp'];
$level = $_SESSION['level'];
$points = $_SESSION['points'];
$exp = $_SESSION['exp'];

echo 'Points: ' . $points . '<br /><br />';
echo 'Power: ' . $power . '<br />';
echo 'HP: ' . $hp . '<br />';

if (isset($_POST['up_power']) && $points > 0) {
  $_SESSION['power'] = ++$power;
  $_SESSION['points'] = --$points;
  header("Refresh:0");
}
if (isset($_POST['up_hp']) && $points > 0) {
  $_SESSION['hp'] = ++$hp;
  $_SESSION['points'] = --$points;
  header("Refresh:0");
}
?>