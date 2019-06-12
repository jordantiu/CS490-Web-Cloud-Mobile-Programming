function getGithubInfo(user) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it. The function should finally return the object(it now contains the response!)
    // $.ajax({
    //     url: "https://api.github.com/users/" + user + "/events/public",
    //     datatype: JSON
    // }).done(function(data) {
    //
    // });
    // $.ajax({url: "https://api.github.com/users/" + user + "/events/public", success: function(result){
    //         return result;
    //     }});

    // $.ajax({
    //     url: "test.html",
    //     context: document.body
    // }).done(function() {
    //     $( this ).addClass( "done" );
    // });

    $('.information').text("Searching");
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {

        // const json = $(this.data).filter("#content")
        // $('.information').text(json.message);
        // // if (json.message == "Not Found") {
        // //     noSuchUser(user)
        // // } else {
        // //     //showUser(JSON.parse(this.response.responseText))
        // // }
        // return this.response;

        if (this.readyState == 4 && this.status == 200) {
            showUser(JSON.parse(this.responseText)[0])
        } else {
            noSuchUser(user)
        }
    };
    xhttp.open("GET", "https://api.github.com/users/" + user + "/events/public", true);

    xhttp.send();
}

function showUser(user) {
    // $('<img src="'+ user.avatar_url +'">').load(function() {
    //     $(this).width(100).height(100).appendTo('.avatar');
    // });<img class="avatar" style="width: 100px; height: 100px;" alt="Avatar">

    $('.avatar').html("<img style='width: 100px; height: 100px;' src='"+user.actor.avatar_url+"' alt='Avatar'>")
    const link = "https://github.com/" + user.actor.login
    // $('.information').html("<a href='"+link+"'>"+user.actor.login+"</a><span>"+user.payload.commits.author.name+" ("+ user.payload.commits.author.email+")</span>")
    $('.information').html("<a href='"+link+"'>"+user.actor.login+"</a>")
}

function noSuchUser(username) {
    $('.information').text(username + " is not a Github user");
}

$(document).ready(function(){
    $(document).on('keypress', '#username', function(e){
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            const username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
            getGithubInfo(username);
            // const response = getGithubInfo(username);
            // //if the response is successful show the user's details
            // if (response.status == 200) {
            //     showUser(JSON.parse(response.responseText));
            //     //else display suitable message
            // } else {
            //     noSuchUser(username);
            // }
        }
    })
});
