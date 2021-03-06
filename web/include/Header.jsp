<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>StatBoost</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Include favicon here -->
    <link rel="shortcut icon" href="/include/images/favicon.ico">

    <!-- Include css here -->
    <link rel="stylesheet" media="screen" href="/include/stylesheets/bootstrap.min.css">
    <link rel="stylesheet" href="/include/stylesheets/jquery-ui.css">
    <link rel="stylesheet" href="/include/stylesheets/stylesheet.css">
    <link rel="stylesheet" href="/include/stylesheets/bootstrapValidator.min.css">

    <!-- Include js here -->
    <script type="text/javascript" src="/include/javascripts/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/include/javascripts/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/include/javascripts/js-webshim/minified/polyfiller.js"></script>
    <script type="text/javascript" src="/include/javascripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="/include/javascripts/autocomplete.js"></script>
    <script src="/include/javascripts/bootstrapValidator.min.js"></script>




</head>
<body>
<!-- let there be html5 support for forms on old browsers -->
<script>
    webshim.polyfill('forms');
</script>

<!-- let there be bugmuncher! -->
<script type="text/javascript">
    var bugmuncher_options = {
        language: "en",
        position: "right",
        require_email: false,
        api_key: "050d8080e61b4d1cb5677c15b38233371608785f"
    };
    (function(){
        var node = document.createElement("script");
        node.setAttribute("type", "text/javascript");
        node.setAttribute("src", "//app.bugmuncher.com/js/bugMuncher.min.js");
        document.getElementsByTagName("head")[0].appendChild(node);
    })();
</script>