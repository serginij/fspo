<html>
  <head>
    <title>Create user</title>
    <link rel = 'stylesheet' href = 'index.css'>
  </head>
  <body>
    <h2>Create user</h2>
    <form>
      <a href='/laba/save_load.php'>Save / Load</a>
    </form>
    <form action="create_user.php" method="post" class='form' style='width: 10%;
      display: flex;
      flex-direction: column;
      text-align: left;
      justify-content: space-evenly;'>
      Name <input type="text" name="name" value="" required /><br/>
      Power <input type="text" name="power" required pattern="^(?:[1-9]|0[1-9])$"/><br/>
      Hp <input type="text" name="hp" value="" required pattern="^(?:[1-9]|0[1-9])$"/><br/>
      <input type="submit" name="create_user" value="Create" style='padding: 20px; margin: 20px; font-size: 30px'/>
    </form>

    <script>
    console.log('create_user.php -- mounted');
    </script>
  </body>
</html>

<?php
session_start();

$_SESSION['name'] = $_POST['name'];
$_SESSION['power'] = $_POST['power'];
$_SESSION['hp'] = $_POST['hp'];
$_SESSION['level'] = 1;
$_SESSION['points'] = 0;
$_SESSION['exp'] = 0;

if(isset($_POST['create_user'])) {
  header("Refresh:0; url=/laba/arena.php");
}
?>