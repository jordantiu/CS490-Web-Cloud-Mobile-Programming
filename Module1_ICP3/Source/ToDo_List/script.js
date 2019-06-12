
var wishes = [ "learn how to use JQuery", "build a website", "Become a Web Developer" ]

function addToList(item) {
  const deleteBtn = "'deleteItem('" + item + "')'";
  $('#items').append("<li id="+item+">" + item + "<span class='label pending'>Pending</span><button onclick=" + deleteBtn +">Delete</button></li>");
}

function updateTotal() {
  completed = $('.success').length;
  pending = $('.pending').length;

  if (completed > 0 || pending > 0) {
    $('.total').text(" Pending: " + pending + " Completed: " + completed);
  }
}

function deleteItem(item) {
  const name = "#" + item
  $(name).remove()
  updateTotal()
}

$(document).ready(function(){
  wishes.forEach(function(element) {
    addToList(element);
  });
  updateTotal();

  $(document).on('click','#add-to-list',function(){
    item = $("#item").val();

    $("#item").val(""); /* clear value */
    $("#item").focus();

    addToList(item);
    updateTotal();
  });

  $(document).on('click','#hint',function(){
    item = $("#item").val();

    $("#item").val(""); /* clear value */
    $("#item").focus();

    addToList(item);
    updateTotal();
  });

  $(document).on('click','.pending',function(){
    $(this).parent().append("<span class='label success'>Done!</span>");
    $(this).parent().attr("class", 'completed');
    $(this).remove();
    updateTotal();
  });
});
