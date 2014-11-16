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
            height: 800,
            //eventually populate with json payload
            events: "/eventFeed"
        })

    });
</script>

<div class="container-fluid">
    <div class="well well-lg">
        <h2 align="center"><img src="/include/images/logo.png" height="75px" width="100px"> Event Calendar</h2>
        <div id="calendar"></div>
    </div>
</div>



<jsp:include page="/include/Footer.jsp"/>