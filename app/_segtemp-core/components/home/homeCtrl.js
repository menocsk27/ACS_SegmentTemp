app.controller('homeCtrl', function($scope, $http, $location) {

  window.onhashchange = function() {
    history.go(0);
  }
    
  $('.dropdown').dropdown({
    transition: 'drop'
  });

  $('.dropdown.config').popup({
    position: 'bottom left',
    title: 'Documentación',
    target: '.ui.icon.infoIcon',
    content: 'Le presentamos la documentación y a los involucrados en el proyecto'
  });

});
