<#include "../templates/body.ftl"/>

<@body
title = "Login"
styles=["<link href=\"/resources/ccs/style1.css\" rel=\"stylesheet\" type=\"text/css\"/>"]>
<div class="agilesign-form">
    <div class="agileits-top">

    <@form.form id="reg" action="/login_processing" method="post">
        <div class="styled-input w3ls-text">
            <input type="text" name="name" required="required" pattern=".{3,}">
            <label>Username or Email</label>
            <span></span>
        </div>
        <div class="styled-input w3ls-text">
            <input type="password" name="password" required="required" pattern=".{8,}">
            <label>Password</label>
            <span></span>
        </div>
        <div class="wthree-text">
            <p>
                <input type="checkbox" name="remember-me" id="remember-me">
                <label for="remember-me">
                    <span></span>
                   Remember Me
                </label>
            </p>
        </div>
        <div class="agileits-bottom">
            <input id="submit" type="submit" value="Sign Up">
        </div>
    </@form.form>
    </div>
    <div class="w3agile-btm">
        <p>Already a member? <a href="/login">Log in</a></p>
    </div>
    <div class="w3agile-btm">
        <p>Forgot password? <a href="/login">Reset it</a></p>
    </div>
</div>
</@body>
