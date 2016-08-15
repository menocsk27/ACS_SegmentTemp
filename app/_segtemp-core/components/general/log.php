<?php
    $logEntry = json_decode(file_get_contents('php://input'));
    $mysqli = new mysqli("localhost", "Employee", "12345", "FoodDB");
    if (mysqli_connect_errno()) {
        printf("Connect failed: %s\n", mysqli_connect_error());
        exit();
    }
    
    if ($logEntry->ID_Res != "null") {$logEntry->ID_Res = "'".$logEntry->ID_Res."'";}
    $sql = "CALL insert_log('".$logEntry->Username."','".$logEntry->Action."',".$logEntry->ID_Res.")";

    if ($stmt = $mysqli->prepare($sql)) {
        $stmt->execute();
        if ($stmt->error) { echo("ERROR"); }
        else { echo("DONE"); }   
        $stmt->close();
    }
    $mysqli->close();
?>
