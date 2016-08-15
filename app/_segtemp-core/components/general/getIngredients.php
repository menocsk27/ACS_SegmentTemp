<?php
    $dataBaseConnection = new mysqli("localhost", "Client", "12345", "FoodDB");

	if (mysqli_connect_errno()) {
	    printf("Connect failed: %s\n", mysqli_connect_error());
	    exit();
	}

    $sql = "CALL get_ingredients()";
    $result = $dataBaseConnection->query($sql);

    if ($result->num_rows > 0) {
        $outp = "[";

        while($row = $result->fetch_assoc()) {
            if ($outp != "[") {$outp .= ",";}
            $outp .= '{"ID_Ingredient":"'  . $row["idIngredient"].'",';
            $outp .= '"Name":"'  . $row["name"].'",';
            $outp .= '"Price":"'  . $row["price"].'",';
            $outp .= '"Picture":"'  . base64_encode($row["picture"]) .'",';
            $outp .= '"ID_Type":"'  . $row["idType"].'",';
            $outp .= '"Type_Name":"'   . $row["typeName"].'"}';
        }
        $outp .="]";
        echo($outp);
    } else {
        echo "La base no tiene ingredientes";
    }
    $dataBaseConnection->close();
?>
