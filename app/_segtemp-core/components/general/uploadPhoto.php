<?php
  $filename = $_FILES['file']['name'];
  $destination = '../../../assets/temp/' . $filename;
  move_uploaded_file( $_FILES['file']['tmp_name'],$destination);

  echo(realpath($destination));
?>
