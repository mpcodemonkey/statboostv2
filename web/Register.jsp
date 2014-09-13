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
<script src="/include/javascripts/jquery.maskedinput.min.js"></script>
<script>
    $(document).ready(function () {
        //init input masks
        $('#phone').mask('(999)-999-9999',{placeholder:" "});
        $('#zip').mask('99999?-9999',{placeholder:" "});
        $('#dcinumber').mask('9999999999',{placeholder:" "});




        //init password strength checker
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
        if(document.getElementById('pswd').value != document.getElementById('pswdConf').value) {
            alert('Passwords must match!');
            document.getElementById('pswd').value = '';
            document.getElementById('pswdConf').value = '';
            return false;
        }
        return true;
    }
</script>



    <div class="container">
        <div class="well well-lg">
            <h2 align="center">Register</h2>

            <c:if test="${requestScope.alert != null && requestScope.alertType != null}">
                <div class="alert alert-${requestScope.alertType} fade in">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Oops!</strong> <c:out value="${requestScope.alert}" />
                </div>
            </c:if>


            <form id="form" method="post" onsubmit="return validate()">
                <div style="max-width: 600px;margin-left: auto; margin-right: auto;">
                    <div class="form-group">
                        <div class="form-inline">
                            <div class="form-group">
                                <input id="firstname" name="firstname" type="firstname" class="form-control" placeholder="First Name" value="" required>
                            </div>
                            <div class="form-group">
                                <input id="lastname" name="lastname" type="lastname" class="form-control" placeholder="Last Name" value="" required>
                            </div>
                            <div class="form-group">
                                <input id="username" name="email" type="email" class="form-control" placeholder="Email" value="" required>
                            </div>
                        </div>
                        <br>
                        <div class="form-inline">
                            <div id="pwd-container">

                                <div class="form-group">
                                    <input id="pswd" name="password" type="password" class="form-control" placeholder="Password" required>
                                </div>
                                <div class="form-group">
                                    <input id="pswdConf" name="passwordConf" type="password" class="form-control" placeholder="Confirm Password" required>

                                </div>
                                <div style="width: 200px;display: inline-block;vertical-align: text-top;">
                                    <div class="pwstrength_viewport_progress"></div>
                                </div>

                            </div>
                        </div>
                        <br>
                        <hr>
                        <h3>Optional</h3>
                        <div id="optional" class="form-inline">
                            <div class="form-group">
                                <input id="address1" name="address1" type="text" class="form-control" placeholder="Address 1">
                            </div>
                            <br><br>
                            <div class="form-group">
                                <input id="address2" name="address2" type="text" class="form-control" placeholder="Address 2">
                            </div>
                            <br><br>
                            <div class="form-group">
                                <input id="city" name="city" type="text" class="form-control" placeholder="City">
                                <select id="state" name="state" class="form-control">
                                    <option value="AL">Alabama</option>
                                    <option value="AK">Alaska</option>
                                    <option value="AZ">Arizona</option>
                                    <option value="AR">Arkansas</option>
                                    <option value="CA" selected>California</option>
                                    <option value="CO">Colorado</option>
                                    <option value="CT">Connecticut</option>
                                    <option value="DE">Delaware</option>
                                    <option value="DC">District Of Columbia</option>
                                    <option value="FL">Florida</option>
                                    <option value="GA">Georgia</option>
                                    <option value="HI">Hawaii</option>
                                    <option value="ID">Idaho</option>
                                    <option value="IL">Illinois</option>
                                    <option value="IN">Indiana</option>
                                    <option value="IA">Iowa</option>
                                    <option value="KS">Kansas</option>
                                    <option value="KY">Kentucky</option>
                                    <option value="LA">Louisiana</option>
                                    <option value="ME">Maine</option>
                                    <option value="MD">Maryland</option>
                                    <option value="MA">Massachusetts</option>
                                    <option value="MI">Michigan</option>
                                    <option value="MN">Minnesota</option>
                                    <option value="MS">Mississippi</option>
                                    <option value="MO">Missouri</option>
                                    <option value="MT">Montana</option>
                                    <option value="NE">Nebraska</option>
                                    <option value="NV">Nevada</option>
                                    <option value="NH">New Hampshire</option>
                                    <option value="NJ">New Jersey</option>
                                    <option value="NM">New Mexico</option>
                                    <option value="NY">New York</option>
                                    <option value="NC">North Carolina</option>
                                    <option value="ND">North Dakota</option>
                                    <option value="OH">Ohio</option>
                                    <option value="OK">Oklahoma</option>
                                    <option value="OR">Oregon</option>
                                    <option value="PA">Pennsylvania</option>
                                    <option value="RI">Rhode Island</option>
                                    <option value="SC">South Carolina</option>
                                    <option value="SD">South Dakota</option>
                                    <option value="TN">Tennessee</option>
                                    <option value="TX">Texas</option>
                                    <option value="UT">Utah</option>
                                    <option value="VT">Vermont</option>
                                    <option value="VA">Virginia</option>
                                    <option value="WA">Washington</option>
                                    <option value="WV">West Virginia</option>
                                    <option value="WI">Wisconsin</option>
                                    <option value="WY">Wyoming</option>
                                </select>
                                <input id="zip" name="zip" type="text" class="form-control" placeholder="Zip">
                            </div>
                            <br><br>
                            <div class="form-group">
                                <input id="phone" name="phone" type="text" class="form-control" placeholder="Phone">
                            </div>
                            <br><br><br>
                            <div class="form-group">
                                <label class="checkbox-inline">Receive newsletter?</label>
                                <input id="newsletter" name="newsletter" type="checkbox" value="true" class="checkbox-inline" ><br><br>
                            </div>
                            <br>
                            <div class="form-group">
                                <label><i>Magic The Gathering Players:</i></label>
                                <input id="dcinumber" name="dcinumber" type="text" class="form-control" placeholder="DCI Number">
                            </div>
                        </div>
                        <br>
                        <div class="form-inline" align="center">
                            <button type="submit" class="btn btn-primary btn-lg">Register User</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>



<jsp:include page="/include/Footer.jsp"/>
