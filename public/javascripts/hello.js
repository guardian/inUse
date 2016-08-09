
$(function(){

  console.log("Herro, Javascript");

  var filterServices = function(service) {

    $(".servicebox").each(function() {

      if($(this).data("service").includes(service) || service === ""){
        $(this).show();
      } else {
        $(this).hide();
      }

    });

    $()


  }

  $("#filter-input").keydown(function(){
    filterServices($("#filter-input").val().trim())
  })

})