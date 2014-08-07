<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 8/3/2014
  Time: 9:25 PM
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>


<h1>This is a test.. poop.</h1>

<c:forEach items="${words}" var="word" >
    <c:out value="${word}" />&nbsp;
</c:forEach>

<c:if test="${testBoolean}">
    <span class="glyphicon glyphicon-search"></span>
</c:if>




<jsp:include page="/include/Footer.jsp"/>
