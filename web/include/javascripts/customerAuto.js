/**
 * Created by Sam on 9/27/2014.
 */
$(function() {
    /*
     * Autocomplete for populating an customer search input field
     */
    $('input.cust-autocomplete').each( function() {
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
                $( "input.cust-autocomplete" ).val( ui.item.name );
                return false;
            },
            select: function(event, ui) {
                $( "input.cust-autocomplete" ).val(ui.item.id);
                $(this).closest("form").submit();
            }
        });
    }).data("ui-autocomplete")._renderItem = function(ul, item){
        return $("<li>")
            .append( "<a href=/admin/setUserStatus?customerID="+item.id+"><b><u>" + item.name + "</u></b><br><i>" + item.email + "</i></a>" )
            .appendTo(ul);
    }

});