<#include "../templates/body.ftl"/>

<@body title="Reset password" styles=["<link href=\"/resources/ccs/style1.css\" rel=\"stylesheet\" type=\"text/css\"/>"]>
<h2>Reset password page</h2>
    <#if tooManyResend??>
    <p>Too many requests to reset password, try again 10 minutes later</p>
    </#if>
<div class="agilesign-form">
    <div class="agileits-top">
        <@form.form id="reg" action="/login?reset_password" method="post">
            <div class="styled-input w3ls-text">
                <input type="text" name="email" required="required" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$">
                <label>Enter your email</label>
                <span></span>
            </div>
            <input id="submit" type="submit" value="Send reset password email">
        </@form.form>
    </div>
</div>
</@body>