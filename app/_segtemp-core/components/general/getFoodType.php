<?php
    $dataBaseConnection = new mysqli("localhost", "Client", "12345", "FoodDB");

	if (mysqli_connect_errno()) {
	    printf("Connect failed: %s\n", mysqli_connect_error());
	    exit();
	}

    $sql = "CALL get_foodtype()";
    $result = $dataBaseConnection->query($sql);

    if ($result->num_rows > 0) {
        $outp = "[";

        while($row = $result->fetch_assoc()) {
            if ($outp != "[") {$outp .= ",";}
            $outp .= '{"ID_Type":"'  . $row["idType"].'",';
            $outp .= '"Name_Type":"'   . $row["name"].'"}';
        }
        $outp .="]";
        echo($outp);
    } else {
        echo "La base no tiene categorías";
    }
    $dataBaseConnection->close();
?>
