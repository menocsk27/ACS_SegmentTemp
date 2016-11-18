app.controller('homeCtrl', ['$scope', '$http', 'Upload', '$timeout', '$location', function($scope, $http, Upload, $timeout, $location) {
  var videoFile = "";
  var groundTruth = "";
  var videoUploaded = false;
  var groundTruthUploaded = false;

  var validVidFiles = ["mp4", "avi"]
  var validGTFiles = ["txt", "xml", "csv", "json", "yaml"]

  $scope.showResultsBoolean = false; 
  $scope.msg_recibido = "";

  $scope.validFile = function(validList, extToCheck) {
    for (i in validList) {
      if (validList[i] == extToCheck) {
        return true;
      }
    }
    return false;
  }

  $scope.uploadVideo = function(file){
    var filename = file.name
    var ext = filename.substring(filename.indexOf(".")+1,filename.length)
    var valid = $scope.validFile(validVidFiles, ext);

    if (file) {
      if (valid) {
        file.upload = Upload.upload({ url: '_segtemp-core/components/general/upload.php', data: { file: file } });
        var videoDirection = document.getElementById('selectVideoInput').value;
        var nameStart = (videoDirection.indexOf('\\') >= 0 ? videoDirection.lastIndexOf('\\') : videoDirection.lastIndexOf('/'));
        videoFile = videoDirection.substring(nameStart+1);
        sweetAlert(
            'Archivo enviado:',
            videoFile,
            'success');
        videoUploaded = true;
      }
      else {
        sweetAlert(
            'Archivo inválido',
            filename + " no es un archivo de video válido.",
            'error');
      }
    }


  };

  $scope.uploadGT = function(file){
    var filename = file.name
    var ext = filename.substring(filename.indexOf(".")+1,filename.length)
    var valid = $scope.validFile(validGTFiles, ext);

    if (file) {
      if (valid) {
        file.upload = Upload.upload({ url: '_segtemp-core/components/general/upload.php', data: { file: file } });
        var groundTruthDirection = document.getElementById('selectGroundTruth').value;
        var nameStart = (groundTruthDirection.indexOf('\\') >= 0 ? groundTruthDirection.lastIndexOf('\\') : groundTruthDirection.lastIndexOf('/'));
        groundTruth = groundTruthDirection.substring(nameStart+1);
        sweetAlert('Archivo enviado:', groundTruth, 'success');
        groundTruthUploaded = true;
      }
      else {
        sweetAlert(
            'Archivo inválido',
            filename + " no es un archivo de ground truth válido.",
            'error');
      }
    }
  };


  $scope.startTSA = function(){
    if(videoUploaded){
      if ("WebSocket" in window){
        var ws = new WebSocket("ws://localhost:9090/");   //Si se pone algo despues del 9090/ se manda como mensaje que recibe java, aun no dice que la conexion esta hecha bien
        document.getElementById("videosDiv").innerHTML = "";

        ws.onopen = function(){
          sweetAlert(
            'Conexión',
            'Se logró la conexión con el servidor.',
            'success');
          if(groundTruthUploaded){
            ws.send('C:/wamp64/www/app/assets/temp/' + groundTruth); 
          }
          ws.send('C:/wamp64/www/app/assets/temp/' + videoFile);
        };
        
        ws.onmessage = function (evt){ 
          $scope.msg_recibido = evt.data;

          var gtstr = $scope.msg_recibido;
          if (gtstr != "") {
            var res = gtstr.split(",");
            document.getElementById("pfr").innerHTML = res[0];
            document.getElementById("nfr").innerHTML = res[1];
      
            $scope.showResultsBoolean = true;
          }
          //Aqui se mandaria la direccion con los nuevos videos.
          alert("Mensaje recibido: " + msg_recibido);
          document.getElementById("videosDiv").innerHTML += '<video width="500" controls><source src="' + msg_recibido + '" type="video/mp4">Your browser does not support HTML5 video.</video>';
        };       
        
        ws.onclose = function(){
          sweetAlert(
            'Conexión cerrada',
            'Se cerró la conexión al software.',
            'warning');
        };
      } else {
        sweetAlert(
            'Error conectando',
            'No se puede hacer la conexión con el software.',
            'error');
        alert("No se puede hacer la conexión con el software.");
      }
    }else{
      sweetAlert(
        'Nope',
        'Debe especificar un archivo para mandar.',
        'error');
    }
  }
  
  window.onhashchange = function() {
    history.go(0);
  };
  
  $("#uploadVideoDiv").on("click", function() {
    $('#selectVideoInput').click(); 
  });

  $("#uploadGroundTruth").on("click", function() {
    $('#selectGroundTruth').click();
  });

  $('.dropdown').dropdown({
    transition: 'drop'
  });

  $('.dropdown.config').popup({
    position: 'left center',
    title: 'Documentación',
    target: '.ui.icon.infoIcon',
    content: 'Le presentamos la documentación y a los involucrados en el proyecto'
  });

  $('.uploadIcon1').popup({
    position: 'left center',
    title: 'Seleccionar Video',
    target: '.ui.icon.infoIcon',
    content: 'Seleccione el video que desea analizar.'
  });

  $('.uploadIcon2').popup({
    position: 'left center',
    title: 'Seleccionar Ground Truth',
    target: '.ui.icon.infoIcon',
    content: 'Seleccione el archivo de Ground Truth para comparación.'
  });

  $('#start').popup({
    position: 'left center',
    title: 'Iniciar Segmentación',
    target: '.ui.icon.infoIcon',
    content: 'Inicia el análisis de segmentación temporal.'
  });

}]);
