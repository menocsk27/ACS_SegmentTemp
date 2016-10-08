app.controller('homeCtrl', ['$scope', '$http', 'Upload', '$timeout', '$location', function($scope, $http, Upload, $timeout, $location) {
  var videoFile = "";
  var groundTruth = "";
  var videoUploaded = false;
  var groundTruthUploaded = false;

  $scope.uploadVideo = function(file){
    if (file) {
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
  };

  $scope.uploadGT = function(file){
    if (file) {
      file.upload = Upload.upload({ url: '_segtemp-core/components/general/upload.php', data: { file: file } });
      var groundTruthDirection = document.getElementById('selectGroundTruth').value;
      var nameStart = (groundTruthDirection.indexOf('\\') >= 0 ? groundTruthDirection.lastIndexOf('\\') : groundTruthDirection.lastIndexOf('/'));
      groundTruth = groundTruthDirection.substring(nameStart+1);
      sweetAlert(
            'Archivo enviado:',
            groundTruth,
            'success');
      groundTruthUploaded = true;
    }
  };

  function startTSA(){
    if(videoUploaded){
      if ("WebSocket" in window){
        var ws = new WebSocket("ws://localhost:9090/");   //Si se pone algo despues del 9090/ se manda como mensaje que recibe java, aun no dice que la conexion esta hecha bien
        ws.onopen = function(){
          sweetAlert(
            'Conexión',
            'Se logró la conexión con el servidor.',
            'success');
          ws.send('C:/wamp64/www/app/assets/temp/' + videoFile);
          if(groundTruthUploaded){
            ws.send('C:/wamp64/www/app/assets/temp/' + groundTruth); 
          }
        };
        ws.onmessage = function (evt){ 
          var msg_recibido = evt.data;
          //Aqui se mandaria la direccion con los nuevos videos.
          alert("Mensaje recibido: " + msg_recibido);
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
    console.log("1");
    $('#selectVideoInput').click(); 
  });

  $("#uploadGroundTruth").on("click", function() {
    console.log("2");
    $('#selectGroundTruth').click();
  });

  $("#start").on("click", function() {
    console.log("3");
    startTSA();
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
    title: 'Seleccionar video',
    target: '.ui.icon.infoIcon',
    content: 'Seleccione el archivo y comienza la subida.'
  });

  $('.uploadIcon2').popup({
    position: 'left center',
    title: 'Seleccionar Ground Truth',
    target: '.ui.icon.infoIcon',
    content: 'Seleccione el archivo de Ground Truth.'
  });

  $('#start').popup({
    position: 'left center',
    title: 'Iniciar',
    target: '.ui.icon.infoIcon',
    content: 'Iniciar análisis de segmentación temporal.'
  });

  $("#selectVideoInpu").click( function() {
    if($('#selectVideoInput').val() === ""){
      sweetAlert(
        'Nope',
        'Debe especificar un archivo de video para analizar.',
        'error');    
    }
    else{
      sweetAlert(
        'Por favor espere',
        'Su archivo está siendo subido',
        'warning');
    }
  });

  $("#selectGroundTrut").click( function() {
    if($('#selectGroundTruth').val() === ""){
      sweetAlert(
        'Nope',
        'Debe especificar un archivo para mandar.',
        'error');
    }else{
      sweetAlert(
        'Por favor espere',
        'Su archivo está siendo subido',
        'warning');
    }
  });

}]);
