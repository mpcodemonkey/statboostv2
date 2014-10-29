<%--
  Created by IntelliJ IDEA.
  User: Jon
  Date: 10/28/2014
  Time: 4:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/include/Header.jsp"/>
<jsp:include page="/include/Navbar.jsp"/>

<div class="container">
    <div id="well">
        <div class="row">
            <div class="col-md-12">
                <form action="" method="post">
                    <div class="form-group">
                        <label class="control-label" for="nameField">Name</label>
                        <input type="text" class="form-control" id="nameField" name="nameField" placeholder="Placeholder text">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="condPicker">Condition</label>
                    </div>
                    <div class="form-group">
                        <select name="condPicker" id="condPicker" class="selectpicker" data-width="auto">
                            <option value="=">New</option>
                            <option value="<">Near Mint</option>
                            <option value=">">Lightly Played</option>
                            <option value="<=">Moderately Played</option>
                            <option value=">=">Heavily Played</option>
                            <option value="!=">Damaged</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="pricePicker">Price</label>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <div class="form-group">
                                <select name="pricePicker" id="pricePicker" class="selectpicker" data-width="auto">
                                    <option value="=">=</option>
                                    <option value="<">&lt;</option>
                                    <option value=">">&gt;</option>
                                    <option value="<=">≤</option>
                                    <option value=">=">≥</option>
                                    <option value="!=">!=</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <input type="text" class="form-control" id="priceField" name="priceField" placeholder="#">
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary" style="float:right;">Search</button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/include/Footer.jsp"/>
