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
    var theId = 1, cond = "NEW";
    $(document).ready(function () {

        //initialize calendar
        $('#calendar').fullCalendar({

            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },

            eventRender: function (event, element) {
                element.attr('href', 'javascript:void(0);');
                element.click(function() {
                    $('#modalTitle').html(event.title);
                    $('#eventDesc').html(event.description);
                    $('#eventStart').html(moment(event.start).format("h:mm a"));
                    $('#eventEnd').html(moment(event.end).format("h:mm a"));
                    $('#eventPrice').html(event.eventCost);
                    theId = event.invId;
                    cond = event.cond;
                    //$('#eventLimit').html(event.playerLimit);
                    $('#fullCalModal').modal();
                });
            },

            height: 800,
            //eventually populate with json payload
            events: "/eventFeed"

        })

    });
</script>

<div class="container">
    <div id="fullCalModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 id="modalTitle" class="modal-title">Event!</h4>
                </div>
                <div id="modalBody" class="modal-body">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="row">
                                <div class="col-md-4">
                                    <h4>Start:</h4>
                                    <h4>End:</h4>
                                </div>
                                <div class="col-md-8">
                                    <h4 id="eventStart">date</h4>
                                    <h4 id="eventEnd">date</h4>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <p id="eventDesc">This paragraph holds the description!</p>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <h4 class="pull-left">Price: </h4>
                    <h4 class="pull-left" id="eventPrice">$$$$</h4>
                    <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-default" onclick="addToCart(theId, cond, 1)">Register</button>
                </div>
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

<script>
    function addToCart(inv_uid, condition, quantity) {
        xmlhttp = new XMLHttpRequest();
        xmlhttp.open("POST", '/cart?addItem=true&inv_uid='+inv_uid+'&condition='+condition+'&quantity='+quantity, true);
        xmlhttp.send();
        $('#'+inv_uid).show();
        setTimeout(function() {location.reload(true)}, 500);
    }
</script>

<jsp:include page="/include/Footer.jsp"/>
