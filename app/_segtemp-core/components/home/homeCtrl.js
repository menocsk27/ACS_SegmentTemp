app.controller('homeCtrl', ['$scope', '$http', 'Upload', '$timeout', '$location', function($scope, $http, Upload, $timeout, $location) {

  $scope.currentVideo = '/app/assets/temp/aw ye.jpg';
  $scope.videoToUpload = '';

  $scope.uploadVideo = function(file){
    if (file) {
      file.upload = Upload.upload({ url: '_segtemp-core/components/general/upload.php', data: { file: file } });

      if ("WebSocket" in window){
               console.log("Se puede intentar la conexión con el software.")
               
               // Let us open a web socket

               var ws = new WebSocket("ws://localhost:9090");   //Si se pone algo despues del puerto/ se manda como mensaje que recibe java, aun no dice que la conexion esta hecha bien
        
               ws.onopen = function(){
                  console.log("Se logro la conexión con el servidor.");
                  ws.send("direccion al video");
               };
               ws.onmessage = function (evt){ 
                  var msg_recibido = evt.data;
                  alert("Mensaje recibido: " + msg_recibido);
               };       
               ws.onclose = function(){ 
                  alert("Se cerró la conexión al software."); 
               };
            } else {
               alert("No se puede hacer la conexión con el software.");
            }
    }
  }
  
  window.onhashchange = function() {
    history.go(0);
  }
  
  $("#uploadVideoDiv").on("click", function() {
    $('#selectVideoInput').click(); 
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

  $('.uploadIcon').popup({
    position: 'left center',
    title: 'Seleccionar video',
    target: '.ui.icon.infoIcon',
    content: 'Seleccione el archivo y comienza la subida'
  });

  $("#uploadVideoInput").click( function() {
    if($('#selectVideoInput').val() == ""){
      sweetAlert(
        'Nope',
        'Debe especificar un archivo de video para analizar.',
        'error')    
    }
    else{
      sweetAlert(
        'Por favor espere',
        'Su archivo está siendo subido',
        'warning')
    }
  });

}]);
