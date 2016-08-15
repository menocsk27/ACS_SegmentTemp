<?php
    $dataBaseConnection = new mysqli("localhost", "Client", "12345", "FoodDB");

	if (mysqli_connect_errno()) {
	    printf("Connect failed: %s\n", mysqli_connect_error());
	    exit();
	} 

    $sql = "CALL get_restaurants";
    $result = $dataBaseConnection->query($sql);

    if ($result->num_rows > 0) {
        $outp = "[";
    
        while($row = $result->fetch_assoc()) {
            if ($outp != "[") {$outp .= ",";}
            $outp .= '{"ID_Res":"'  . $row["idRestaurant"].'",';
            $outp .= '"Name":"'   . $row["name"].'",';
            $outp .= '"PhoneNumber":"'   . $row["phoneNumber"].'",';
            $outp .= '"idSchedule":"'   . $row["idSchedule"].'",';   
            $outp .= '"WeekOpens":"'   . $row["weekOpens"].'",';
            $outp .= '"WeekCloses":"'   . $row["weekCloses"].'",';
            $outp .= '"WeekendOpens":"'   . $row["weekendOpens"].'",';
            $outp .= '"WeekendCloses":"'   . $row["weekendCloses"].'",';
            $outp .= '"BlockName":"'   . $row["blockName"].'",';
            $outp .= '"FoodType":"'   . $row["type"].'"}';
        }
        $outp .="]";
        echo($outp);
    } else {
        echo "La base no tiene restaurantes";
    }
    $dataBaseConnection->close();
?>