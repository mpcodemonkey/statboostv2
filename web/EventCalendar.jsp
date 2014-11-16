<%--
  User: Sam
  Date: 9/12/2014
  Time: 7:33 PM
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<link rel='stylesheet' href='/include/stylesheets/fullcalendar.min.css' />
<script src='/include/javascripts/moment.min.js'></script>
<script src='/include/javascripts/fullcalendar.min.js'></script>

<script>
    $(document).ready(function () {

        //initialize calendar
        $('#calendar').fullCalendar({

            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },

            eventClick:  function(event, jsEvent, view) {
                $('#modalTitle').html(event.title);
                $('#modalBody').html(event.description);
                //$('#eventStart').html(event.start);
                //$('#eventEnd').html(event.end);
                //$('#eventLimit').html(event.playerLimit);
                $('#eventUrl').attr('href', event.url);
                $('#fullCalModal').modal();
            },

            height: 800,
            //eventually populate with json payload
            events: "/eventFeed"

        })

    });
</script>

<div id="fullCalModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span>Close</span></button>
                <h4 id="modalTitle" class="modal-title"></h4>
                <h4 id="eventStart" class="modal-title"></h4>

            </div>
            <div id="modalBody" class="modal-body"></div>
            <div class="modal-footer">

                <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-default"><a id="eventUrl" target="_blank">Check out More Details!</a></button>
            </div>
        </div>
    </div>
</div>


<div class="container-fluid">
    <div class="well well-lg">
        <h2 align="center"><img src="/include/images/logo.png" height="75px" width="100px"> Event Calendar</h2>
        <div id="calendar"></div>
    </div>
</div>


<jsp:include page="/include/Footer.jsp"/>
