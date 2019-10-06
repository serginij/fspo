<html>
  <head>
    <title>Save / Load</title>
    <link rel = 'stylesheet' href = 'index.css'>
  </head>
  <body>
    <h2>Save / Load</h2>
    <form>
      <a href='/laba/create_user.php'>Create user</a>
      <a href='/laba/arena.php'>Arena</a>
    </form>
      <form action="save_load.php" method="post">
        Slot 1  <input type='radio' name='slot' value="0" /><br/>
        Slot 2  <input type='radio' name='slot' value="1" /><br/>
        Slot 3  <input type='radio' name='slot' value="2" /><br/><br />
        <input type='submit' name='save' value="Save" />
        <input type='submit' name='load' value="Load" />
      </form>
  </body>
</html>

<?php
session_start();

$power = $_SESSION['power'];
$hp = $_SESSION['hp'];
$level = $_SESSION['level'];
$points = $_SESSION['points'];
$exp = $_SESSION['exp'];
$name = $_SESSION['name'];

echo 'Name: ' . $name . '<br />';

$conn_str= mysqli_connect('localhost', 'root', 'root', 'users');
 if ($conn_str== false)
  {
    echo "Ошибка " . mysqli_connect_error();
  }
  else
  {
    $sql = 'SELECT * FROM user';

    function updateSlot ($id, $name, $power, $hp, $points, $exp, $level) {
      return 'UPDATE user SET name = '. "'" . $name . "'" .', power = '. $power .', hp = '. $hp .', points='. $points .', exp = '. $exp .', level = '. $level .' WHERE id = ' . $id;
    }

    if (isset($_POST['save'])) {
      echo updateSlot($_POST['slot'], $name, $power, $hp, $points, $exp, $level);
      $result = mysqli_query($conn_str, updateSlot($_POST['slot'], $name, $power, $hp, $points, $exp, $level));

      if ($result == false) {
        print("Произошла ошибка при выполнении запроса" . mysqli_error($conn_str));
      } else {
        header("Refresh:0; url=/laba/arena.php");
      }
    }

    if (isset($_POST['load'])) {

      $sql = 'SELECT * FROM user WHERE id = ' . $_POST['slot'];
      $result = mysqli_query($conn_str, $sql);

      if ($result == false) {
        print("Произошла ошибка при выполнении запроса" . mysqli_error($conn_str));
      } else {
          while ($row = mysqli_fetch_array($result)) {
            $_SESSION['name'] = $row['name'];
            $_SESSION['power'] = $row['power'];
            $_SESSION['hp'] = $row['hp'];
            $_SESSION['level'] = $row['level'];
            $_SESSION['points'] = $row['points'];
            $_SESSION['exp'] = $row['exp'];
          }
          header("Refresh:0; url=/laba/arena.php");
    }
    }

  }
mysqli_close($conn_str);
?>