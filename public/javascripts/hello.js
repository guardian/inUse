
$(function(){

  console.log("Herro, Javascript");

  var filterServices = function(service) {

    $(".servicebox").each(function() {

      if($(this).data("service").includes(service) || service == ""){
        $(this).show();
      } else {
        $(this).hide();
      }

    });

  };

  $(".servicebox>.title-area").click(function(){
    $(this).parent().find(".service-calls").toggle();
  });

  $("#filter-input").on("keyup", function(e){
    filterServices($("#filter-input").val().trim())
  })

})