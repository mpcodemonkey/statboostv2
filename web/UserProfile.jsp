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
            <li><b>Role:</b> ${user.usrRole}</li>
        </div>
    </div>


</div>



<jsp:include page="/include/Footer.jsp"/>