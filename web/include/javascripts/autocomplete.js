/**
 * Created by Jon on 3/8/14.
 */

$(function() {
    /*
     * By Jon
     * Autocomplete for populating an input field with a value from the server.
     */

    $('input.autocomplete').each( function() {
        var $input = $(this);
        var serverUrl = $input.data('url');
        
        $(this).autocomplete({
            source:serverUrl,
            minLength: 3,
            delay: 100,
            open: function() {
                $(this).autocomplete("widget").css({
                    "width": 400
                });
            },
            focus: function( event, ui ) {
                $( "input.autocomplete" ).val( ui.item.name );
                return false;
            },
            select: function(event, ui) {
                $( "input.autocomplete" ).val(ui.item.name);
                $(this).closest("form").submit();
            }
        });
    })
    .data("ui-autocomplete")._renderItem = function(ul, item){
        return $("<li>")
            .append( "<a href=magicSearch?cardName="+encodeURIComponent(item.name)+">" + item.name + "<br>" + item.text + "</a>" )
            .appendTo(ul);
    }

});
