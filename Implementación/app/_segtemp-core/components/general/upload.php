<?php
    $allowedExts = array("jpg", "jpeg", "gif", "png", "mp3", "mp4", "wma");
  
    echo "Type: " . $_FILES["file"]["type"] . "<br />";
    echo "Error: " . $_FILES["file"]["error"] . "<br />";
    echo "Size: " . $_FILES["file"]["size"] . "<br />";
    echo "Tmp Name: " . $_FILES["file"]["tmp_name"] . "<br />";
    echo "Name: " . $_FILES["file"]["name"] . "<br />";

    
    if ($_FILES["file"]["error"] > 0){
        echo "Return Code: " . $_FILES["file"]["error"] . "<br />";
    }else{
        echo "Upload: " . $_FILES["file"]["name"] . "<br />";
        echo "Type: " . $_FILES["file"]["type"] . "<br />";
        echo "Size: " . ($_FILES["file"]["size"] / 1024) . " Kb<br />";
        echo "Temp file: " . $_FILES["file"]["tmp_name"] . "<br />";
        
        if (file_exists("upload/" . $_FILES["file"]["name"])){
            echo $_FILES["file"]["name"] . " already exists. ";
        }else{
            $destination = '../../../assets/temp/' . $_FILES["file"]["name"];
            move_uploaded_file($_FILES["file"]["tmp_name"], $destination);
            echo "Stored in: " . $destination;
        }
    }
?>
