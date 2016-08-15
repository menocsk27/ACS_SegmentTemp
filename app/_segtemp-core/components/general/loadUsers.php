<?php
    $dataBaseConnection = new mysqli("localhost", "Client", "12345", "FoodDB");

	if (mysqli_connect_errno()) {
	    printf("Connect failed: %s\n", mysqli_connect_error());
	    exit();
	}

    $sql = "CALL get_user(null)";
    $result = $dataBaseConnection->query($sql);

    if ($result->num_rows > 0) {
        $outp = "[";

        while($row = $result->fetch_assoc()) {
            if ($outp != "[") {$outp .= ",";}
            $outp .= '{"ID":"'  . $row["idUsers"].'",';
            $outp .= '"Username":"'   . $row["nickname"].'",';
            $outp .= '"Password":"'   . $row["password"].'",';
            $outp .= '"ProfilePicture":"'   . base64_encode($row["picture"]) .'",';
            $outp .= '"IsAdmin":"'   . $row["admin"].'",';
            $outp .= '"Name":"'   . $row["name"].'",';
            $outp .= '"LastName":"'   . $row["lastName"].'",';
            $outp .= '"SecLastName":"'   . $row["secLastName"].'",';
            $outp .= '"IdCard":"'   . $row["idCard"].'",';
            $outp .= '"Email":"'   . $row["email"].'",';
            $outp .= '"PhoneNumber":"'   . $row["phoneNumber"].'"}';
        }
        $outp .="]";
        echo($outp);
    } else {
        echo "La base no tiene usuarios";
    }
    $dataBaseConnection->close();
?>
