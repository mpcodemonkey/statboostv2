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
            events: [
                {
                    title: 'All Day Event',
                    start: '2014-09-01',
                    allDay: true
                },
                {
                    title: 'Cheddar Cheese',
                    start: '2014-09-03T08:32:00',
                    url: 'https://www.google.com/search?q=cheese&tbm=isch',
                    allDay: true
                },
                {
                    title: 'Long Event',
                    start: '2014-09-07',
                    end: '2014-09-10'
                },
                {
                    id: 999,
                    title: 'Repeating Event',
                    start: '2014-09-09T16:00:00'
                },
                {
                    id: 999,
                    title: 'Repeating Event',
                    start: '2014-09-16T16:00:00'
                },
                {
                    title: 'Conference',
                    start: '2014-09-11',
                    end: '2014-09-13'
                },
                {
                    title: 'Meeting',
                    start: '2014-09-12T10:30:00',
                    end: '2014-09-12T12:30:00'
                },
                {
                    title: 'Lunch',
                    start: '2014-09-12T12:00:00'
                },
                {
                    title: 'Meeting',
                    start: '2014-09-12T14:30:00'
                },
                {
                    title: 'Happy Hour',
                    start: '2014-09-12T17:30:00'
                },
                {
                    title: 'Dinner',
                    start: '2014-09-12T20:00:00'
                },
                {
                    title: 'Birthday Party',
                    start: '2014-09-13T07:00:00'
                },
                {
                    title: 'Click for Google',
                    url: 'http://google.com/',
                    start: '2014-09-28'
                },
                {
                    title: 'Magic Tournament',
                    url: '/magicSearch?cardName=water',
                    start: '2014-09-24'
                }
            ]
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