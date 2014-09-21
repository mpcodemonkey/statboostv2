<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>


<div class="container">

    <div class="btn-toolbar">
        <c:if test="${user.usrRole == 'Admin'}">
            <div class="btn-group"><button type="button" class="btn btn-primary" onclick="location.href='/admin/adminCP'">Admin CP</button></div>
        </c:if>
        <c:if test="${user.usrRole == 'Customer'}">
            <div class="btn-group"><button type="button" class="btn btn-primary" onclick="location.href='#'">View Order History</button></div>
            <div class="btn-group"><button type="button" class="btn btn-primary" onclick="">Delete Account</button></div>
        </c:if>
        <div class="btn-group"><button type="button" class="btn btn-primary" onclick="location.href='logout'">Logout</button></div>
    </div>
    <div><br></div>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h1 class="panel-title">${user.usrEmail}'s Profile</h1>
        </div>
        <div class="panel-body">
            <div id="profileInfo">
                <li>
                    <b>Name:</b> ${user.usrFirstName} ${user.usrLastName}
                </li>
                <li><b>Email:</b> ${user.usrEmail}</li>
                <li><b>Phone:</b> ${user.usrPhone}</li>
                <li><b>Role:</b> ${user.usrRole}</li>
                <hr>
                <div align="right">
                    <button class="btn btn-primary" onclick="editProfile();">Edit</button>
                    <button class="btn btn-primary" onclick="changePswd();">Change Password</button>
                </div>
                <li><b>Address 1:</b> ${user.usrAddress1}</li>
                <li><b>Address 2:</b> ${user.usrAddress2}</li>
                <li><b>City:</b> ${user.usrCity}</li>
                <li><b>State:</b> ${user.usrState}</li>
                <li><b>Zip:</b> ${user.usrZip}</li>
                <br>
                <li><b>Recieve Newsletter? </b> <c:choose><c:when test="${user.usrNewsletter == '127'}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></li>
                <li><b>DCI Number:</b> ${user.usrDciNumber}</li>
            </div>
            <div id="editProfile" style="display: none;">
                <div style="max-width: 300px;" align="left">
                    <form method="post">
                        <div class="form-group-sm">
                            <div class="form-group">
                                <label>Address 1</label>
                                <input id="address1" name="address1" type="text" class="form-control" value="${user.usrAddress1}">
                            </div>
                            <div class="form-group">
                                <label>Address 2</label>
                                <input id="address2" name="address2" type="text" class="form-control" value="${user.usrAddress2}">
                            </div>
                            <div class="form-group">
                                <label>City</label>
                                <input id="city" name="city" type="text" class="form-control" value="${user.usrCity}">
                            </div>
                            <div class="form-group">
                                <label>State</label>
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
                            </div>
                            <div class="form-group">
                                <label>Zip</label>
                                <input id="zip" name="zip" type="text" class="form-control" value="${user.usrZip}">
                            </div>
                            <div class="form-group">
                                <label>Phone</label>
                                <input id="phone" name="phone" type="text" class="form-control" value="${user.usrPhone}">
                            </div>
                            <div class="form-group">
                                <label class="checkbox-inline">Receive newsletter?</label>
                                <input id="newsletter" name="newsletter" type="checkbox" value="true" class="checkbox-inline" <c:if test="${user.usrNewsletter == '127'}">checked</c:if> > <br><br>
                            </div>
                            <div class="form-group">
                                <label><i>DCI Number:</i></label>
                                <input id="dcinumber" name="dcinumber" type="text" class="form-control" value="${user.usrDciNumber}">
                            </div>
                            <div align="center">
                                <button class="btn btn-primary" type="SUBMIT">Save</button>
                                <button class="btn btn-primary" onclick="window.location.back()">Cancel</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div id="changePswd" style="display: none;">
                <div style="max-width: 300px;" align="left">
                    <div class="form-inline">
                        <div id="pwd-container">
                            <div class="form-group">
                                <label>Current Password</label>
                                <input id="oldPswd" name="oldPassword" type="password" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>New Password</label>
                                <input id="pswd" name="newPassword" type="password" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label>New Password</label>
                                <input id="pswdConf" name="passwordConf" type="password" class="form-control" placeholder="Confirm Password" required>
                            </div>
                            <div style="width: 200px;display: inline-block;vertical-align: text-top;">
                                <div class="pwstrength_viewport_progress"></div>
                            </div>
                        </div>
                    </div>
                    <div align="center">
                        <button class="btn btn-primary" onclick="">Change Password</button>
                    </div>
                </div>
            </div>
        </div>
    </div>


</div>

<script src="/include/javascripts/jquery.maskedinput.min.js"></script>
<script src="/include/javascripts/pwstrength-1.2.0.min.js"></script>
<script>


    $(document).ready(function () {
        //init input masks
        $('#phone').mask('(999)-999-9999', {placeholder: "_"});
        $('#zip').mask('99999?-9999', {placeholder: "_"});
        $('#dcinumber').mask('9999999999', {placeholder: "_"});


    });

    function editProfile() {
        $('#profileInfo').hide();
        $('#editProfile').show();
    }

    function changePswd() {
        $('#editProfile').hide();
        initPswdChecker();
        $('#changePswd').show();
    }

    function initPswdChecker() {
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
    }
</script>



<jsp:include page="/include/Footer.jsp"/>