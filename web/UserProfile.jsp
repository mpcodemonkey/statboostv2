<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>


<div class="container">

    <div class="btn-toolbar">
        <c:if test="${user.role == 'Admin'}">
            <div class="btn-group"><button type="button" class="btn btn-primary" onclick="location.href='adminCP'">Admin CP</button></div>
        </c:if>
        <c:if test="${user.role == 'Customer'}">
            <div class="btn-group"><button type="button" class="btn btn-primary" onclick="location.href='#'">View Order History</button></div>
            <div class="btn-group"><button type="button" class="btn btn-primary" onclick="">Delete Account</button></div>
        </c:if>
        <div class="btn-group"><button type="button" class="btn btn-primary" onclick="location.href='logout'">Logout</button></div>
    </div>
    <div><br></div>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h1 class="panel-title">${user.email}'s Profile</h1>
        </div>
        <div class="panel-body">
            <li>
                <b>Name:</b> ${user.firstName} ${user.lastName}
            </li>
            <li><b>Email:</b> ${user.email}</li>
            <li><b>Role:</b> ${user.role}</li>
        </div>
    </div>


</div>



<jsp:include page="/include/Footer.jsp"/>