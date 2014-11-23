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

<link rel="stylesheet" href="/include/stylesheets/bootstrap-multiselect.css">
<link rel="stylesheet" href="/include/stylesheets/bootstrap-select.min.css">

<script src="/include/javascripts/bootstrap-multiselect.js"></script>
<script src="/include/javascripts/bootstrap-select.min.js"></script>

<div class="container">
    <div id="well">
        <div class="row">
            <div class="container">
                <div class="jumbotron">
                <h2 align="center">Store Search</h2>
                <form action="" method="post">
                    <div class="form-group">
                        <label class="control-label" for="nameField">Item Name:</label>
                        <input type="text" class="form-control" id="nameField" name="nameField" placeholder="Keywords or phrases">
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label" for="condPicker">Condition:</label>
                            <div>
                                <select name="condPicker" id="condPicker" class="selectpicker col-sm-offset-1" data-width="auto">
                                    <option value="New">New</option>
                                    <option value="Near Mint">Near Mint</option>
                                    <option value="Lightly Played">Lightly Played</option>
                                    <option value="Moderately Played">Moderately Played</option>
                                    <option value="Heavily Played">Heavily Played</option>
                                    <option value="Damaged">Damaged</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label" for="catPicker">Category:</label>
                            <div>
                                <select class="form-control multiselect" name="catPicker" id="catPicker" multiple="multiple">
                                    <option value="1">Accessories</option>
                                    <option value="2">Board Games</option>
                                    <option value="3">Magic</option>
                                    <option value="4">Sealed</option>
                                    <option value="5">Singles</option>
                                    <option value="6">Yu-Gi-Oh</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <label class="control-label col-sm-3">Price Range:</label>
                        <div class="form-group container col-sm-12" style="display: flex; align-items: center;">
                            <div class="col-sm-2">
                                <input type="text" name="minPrice" id="minPrice" class="sliderValue col-sm-12" data-index="0" value="0.01" />
                            </div>
                            <div class="col-sm-8">
                                <div id="slider"></div>
                            </div>
                            <div class="col-sm-2">
                                <input type="text" name="maxPrice" id="maxPrice" class="sliderValue col-sm-12" data-index="1" value="1000.00" />
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary" style="float:right;">Search</button>
                </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container center-block">
    <div class="row">
        <div class="span12" style="text-align: center;">
            <div class="col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <div class="caption">
                        <h3>Magic</h3>
                        <p>
                        <h3>Store:</h3>
                        <div>All Singles</div>
                        <div>All Sealed Product</div>
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <div class="caption">
                        <h3>Yu Gi Oh</h3>
                        <p>
                        <h3>Store:</h3>
                        <div>All Singles</div>
                        <div>All Sealed Product</div>
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 col-xs-12">
                <div class="thumbnail">
                    <div class="caption">
                        <h3>Accessories</h3>
                        <p>
                        <h3>Store:</h3>
                        <div>All Game Accessories</div>
                        <div></div>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        $("#slider").slider({
            min: 0.01,
            max: 1000.00,
            range: true,
            step: .01,
            values: [0.01, 99.00],
            animate: "fast",
            slide: function(event, ui) {
                for (var i = 0; i < ui.values.length; ++i) {
                    $("input.sliderValue[data-index=" + i + "]").val(ui.values[i].toFixed(2));
                }
            }
        });

        $('.multiselect').multiselect({
            maxHeight: 200
        });

        $("input.sliderValue").change(function() {
            var $this = $(this);
            $("#slider").slider("values", $this.data("index"), $this.val());
        });
    });
</script>


<style>
    #slider .ui-slider-range{
        background: #25db00;
    }
    #slider {
        background: #ffffff;
    }
</style>

<jsp:include page="/include/Footer.jsp"/>
