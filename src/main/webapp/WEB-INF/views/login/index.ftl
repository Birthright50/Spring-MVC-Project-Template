<#include "../templates/body.ftl"/>
<@body
title = "Login"
styles=["<link href=\"/resources/ccs/style1.css\" rel=\"stylesheet\" type=\"text/css\"/>"]>
<#if message??>
<h2>${message}</h2>
</#if>
<div class="agilesign-form">
    <div class="agileits-top">
    <@form.form id="reg" action="/login_processing" method="post">
        <div class="styled-input w3ls-text">
            <input type="text" name="name" required="required" pattern=".{3,}">
            <label>Username or Email</label>
            <span></span>
        </div>
        <div class="styled-input w3ls-text">
            <input type="password" name="password" required="required" title="UpperCase, LowerCase, Number/SpecialChar and min 8 Chars" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
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
            <input id="submit" type="submit" value="Log In">
        </div>
        <div class="w3agile-btm">
            <p>Not a member? <a href="/register">Sign in</a></p>
            <p>Forgot password? <a href="/login?reset_password">Reset it</a></p>
        </div>
    </@form.form>
    </div>
</div>
</@body>
