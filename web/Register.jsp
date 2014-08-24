<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 8/6/2014
  Time: 3:35 PM
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>
<script src="/include/javascripts/pwstrength-1.2.0.min.js"></script>
<script>
    $(document).ready(function () {
        "use strict";
        var options = {};
        options.ui = {
            container: "#pwd-container",
            showVerdictsInsideProgressBar: true,
            viewports: {
                progress: ".pwstrength_viewport_progress"
            }
        };
        options.common = {
            minChar: 1,
            debug: true,
            onLoad: function () {
                $('#messages').text('Start typing password');
            }
        };
        $('#pswd').pwstrength(options);
    });



    /**
    * Do last resort form validation here
    * @returns {boolean}
    */
    function validate() {

        if(document.getElementById('firstname').value.length < 2) {
            alert('First name must be at least 2 characters.');
            return false;
        }
        if(document.getElementById('lastname').value.length < 2) {
            alert('Last name must be at least 2 characters.');
            return false;
        }
        if(document.getElementById('pswd').value.length < 8) {
            alert('Password must be at least 8 characters.');
            return false;
        }
        return true;
    }
</script>



    <div class="container">
        <div class="well well-lg">
            <h2>Register</h2>

            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Oops!</strong> <c:out value="${requestScope.alert}" />
                </div>
            </c:if>


            <form id="form" method="post" onsubmit="return validate()">
                <div class="form-group">
                    <div class="form-inline">
                        <div class="form-group">
                            <input id="firstname" name="firstname" type="firstname" class="form-control" placeholder="First Name" value="">
                        </div>
                        <div class="form-group">
                            <input id="lastname" name="lastname" type="lastname" class="form-control" placeholder="Last Name" value="">
                        </div>
                    </div>
                    <br>
                    <div class="form-inline">
                        <div id="pwd-container">
                            <div class="form-group">
                                <input id="username" name="email" type="email" class="form-control" placeholder="Email" value="">
                            </div>
                            <div class="form-group">
                                <input id="pswd" name="password" type="password" class="form-control" placeholder="Password">
                            </div>
                            <div class="form-group">
                                <input name="passwordConf" type="password" class="form-control" placeholder="Confirm Password">
                            </div>
                            <br><br>
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="pwstrength_viewport_progress"></div>
                                </div>
                            </div>

                        </div>




                    </div>
                    <br>
                    <div class="form-inline">
                        <button type="submit" class="btn btn-primary">Register User</button>
                    </div>
                </div>
            </form>
        </div>
    </div>



<jsp:include page="/include/Footer.jsp"/>
