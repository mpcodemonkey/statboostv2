<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>


<div class="container">
    <div class="well well-lg">
        <div class="btn-toolbar">
            <div class="btn-group"><button type="button" class="btn btn-primary" onclick="">poop</button></div>
        </div>
        <div><br></div>
        <h1>Order History</h1>

        <table class="table table-responsive table-bordered">
            <thead>
                <tr>
                    <th>Order Number</th>
                    <th>Date Submitted</th>
                    <th>Date Completed</th>
                    <th>Order Total</th>
                </tr>
            </thead>
            <thead>
                <tr>
                    <td>test-001</td>
                    <td>11/03/14</td>
                    <td>11/05/14</td>
                    <td>$999.99</td>
                </tr>

            </thead>
        </table>

    </div>
</div>





<jsp:include page="/include/Footer.jsp"/>