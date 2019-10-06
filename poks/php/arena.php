<html>
  <head>
    <title>Arena</title>
    <link rel = 'stylesheet' href = 'index.css'>
  </head>
  <body>
    <h2>Arena</h2>
    <form>
      <a href='/laba/fight.php'>Fight</a>
      <a href='/laba/level_up.php'>Level up</a>
      <a href='/laba/save_load.php'>Save / Load</a>
    </form>
  </body>
</html>

<?php
session_start();

if($_SESSION['power'] == '') {
  header("Refresh:0; url=/laba/create_user.php");
}

echo '<h3>' . $_SESSION['name'] . '</h3>';
echo 'Power:  ' . $_SESSION['power'] . '<br />';
echo 'HP: ' . $_SESSION['hp'] . '<br />';
echo 'Level:  ' . $_SESSION['level'] . '<br />';
echo 'Experience: ' . $_SESSION['exp'] . '<br />';

?>