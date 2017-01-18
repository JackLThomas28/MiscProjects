;(function ($) {
    $(function() {

      var notes = [];

      $.getJSON('/api', function(data) {
        getValues(data);
      });

      function goToIndex(){
        location.href = "/";
      }

      function getValues(data){
        notes = [];
        $.each(data, function(key, value) {
          notes.push(value);
        });
      }

      function clearInputValues() {
        $("#note-key-box").val("");
        $("#note-title-box").val("");
        $("#note-body-box").val("");
      }

      $("#clear").click(function() {
        clearInputValues();
      });

      // $(document).on('click', document.getElementById("submit-button"), (function(e) {
      $('#submit-button').click(function() {
        if($('#note-key-box').val() !== "" || $('#note-title-box').val() !== "" || $('#note-body-box').val() !== ""){
          var inputValues = {
            id: 0,
            notekey: $('#note-key-box').val(),
            title: $('#note-title-box').val(),
            body: $('#note-body-box').val()
          }

          var isDuplicateKey = function() {
            for(var i = 0; i < notes.length; i++){
              if(notes[i].notekey === inputValues.notekey)return true;
            }
            return false;
          }
          if(!isDuplicateKey()){
            notes.push(inputValues);
            $.ajax({
              type: "POST",
              url: '/api/submit',
              data: inputValues,
              success: function() {
                updateNotes(notes);
              },
              error: function(err) {
                console.log('POST error', err)
              }
            });
            clearInputValues();
          }
          else {
            alert("There cannot be multiple notes with the same key");
          }
        }
        else{
          alert("Add new note information");
        }
      });

      $(document).on('click', '.delete-button', function(e) {
        var deleteBool = confirm("Are you sure you want to delete this note?");
        if(deleteBool === true){
          var idName = e.target.id;
          var tempKey = idName.split("-", 1);
          tempKey = tempKey[0];

          $.ajax({
            type: 'DELETE',
            url: '/api/delete/' + tempKey,
            success: function(data) {
              getValues(data);
              updateNotes(notes);
            },
            error: function(err) {
              console.log('DELETE error', err);
            }
          }); //ajax

          var URL = window.location.href;
          if(URL.search("/note/" + tempKey) !== -1){
            goToIndex();
          }
        }
      });

      $(document).on('click', '.submit-changes-button', function(e) {
        var editBool = confirm("Are you sure you want to submit these changes?");
        if(editBool === true){
          var idName = e.target.id;
          var tempKey = idName.split("-", 1);
          tempKey = tempKey[0];

          var changedNote = {
            id: 0,
            notekey: $('#note-key-box').val(),
            title: $('#note-title-box').val(),
            body: $('#note-body-box').val()
          }
          
          $.ajax({
            type: 'PUT',
            url: '/api/edit/' + tempKey,
            data: changedNote,
            success: function(updatedList) {
              console.log('here')
              getValues(updatedList);
              // updateNotes(notes);
              goToIndex();
            },
            error: function(err) {
              console.log('err', err);
              goToIndex();
            }
          });
        }
      });

      function updateNotes(data) {
         var output = '';
         $.each(data, function(key, value) {
           output += '<li>';
           output += '  <a class="noteLinks" href = "/note/' + value.notekey + '">';
           output += '    <h3>' + value.title + '</h3>';
           output += '    <h4>' + value.notekey   + '</h4>';
           output += '    <p>'  + value.body  + '</p>';
           output += '  </a>';
           output += '    <ul>';
           output += '      <li class="delete-edit"><p class="delete-button" id="' + value.notekey + '-delete">Delete</p></li>';
           output += '      <li class="delete-edit"><a id="' + value.notekey + '-edit" href="/edit/' + value.notekey + '" class="edit-button">Edit</a></li>';
           output += '    </ul>';
           output += '    <div class="separator"><p></p></div>';
           output += '</li>';
         });
         $('#notes-list').html(output);
       }
    });
})(jQuery);
