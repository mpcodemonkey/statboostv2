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
            <li>
                <b>Name:</b> ${user.usrFirstName} ${user.usrLastName}
            </li>
            <li><b>Email:</b> ${user.usrEmail}</li>
            <li><b>Phone:</b> ${user.usrPhone}</li>
            <li><b>Role:</b> ${user.usrRole}</li>
            <hr>
            <div align="right">
                <button class="btn btn-primary">Edit</button>
            </div>
            <li><b>Address 1:</b> ${user.usrAddress1}</li>
            <li><b>Address 2:</b> ${user.usrAddress2}</li>
            <li><b>City:</b> ${user.usrCity}</li>
            <li><b>State:</b> ${user.usrState}</li>
            <li><b>Zip:</b> ${user.usrZip}</li>
            <br>
            <li><b>Recieve Newsletter? </b> <c:choose><c:when test="${user.usrNewsletter == '-128'}">No</c:when><c:otherwise>Yes</c:otherwise></c:choose></li>
            <li><b>DCI Number:</b> ${user.usrDciNumber}</li>
        </div>
    </div>


</div>



<jsp:include page="/include/Footer.jsp"/>