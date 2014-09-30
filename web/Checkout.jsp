<%--
  Created by IntelliJ IDEA.
  User: Sam
  Date: 9/29/2014
  Time: 8:39 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>
<script src="/include/javascripts/jquery.maskedinput.min.js"></script>
<script>
    $(document).ready(function () {
        //init input masks
        $('#cc').mask('9999-9999-9999-9999', {placeholder: "_"});
        $('#exp').mask('99/99', {placeholder: "_"});

    });

</script>

<div class="container">
    <div class="well well-lg">
        <div style="max-width:400px;margin-left: auto; margin-right: auto;">
            <h1 align="center">Checkout</h1>
            <br>
            <form method="post">
                <div class="form-group">
                    <label class="">Credit Card Number</label>
                    <input class="form-control" id="cc" type='text' name='x_card_num'/>
                    <label class="">Exp.</label>
                    <input class="form-control" id="exp" type='text' class='text' name='x_exp_date' />
                </div>
                <div class="form-group">
                </div>
                <div class="form-group">
                    <label class="">Amount</label>
                    <input type='text' class='form-control' name='x_amount' readonly value='102.23' />
                </div>
                <br><br><br>
                <div align="center">
                    <button class="btn btn-lg btn-success" type='submit' name='buy_button' value='BUY'>Purchase</button>
                    <span class="btn btn-lg btn-default" type='button' onclick="window.location='/';">Run Away</span>
                </div>
            </form>
        </div>
    </div>
</div>


<jsp:include page="/include/Footer.jsp"/>