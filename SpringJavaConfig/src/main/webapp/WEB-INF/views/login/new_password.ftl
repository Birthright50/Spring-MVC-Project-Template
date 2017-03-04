<#include "../templates/body.ftl"/>

<@body title="New Password" styles=["<link href=\"/resources/ccs/style1.css\" rel=\"stylesheet\" type=\"text/css\"/>"]>

<h2>New Password</h2>
<#if message??>
  <h2>${message}</h2>
</#if>
<div class="agilesign-form">
    <div class="agileits-top">
        <@form.form id="reg" action="/login/new_password" method="post">
            <div class="styled-input w3ls-text">
                <input type="password" name="password" required="required" title="UpperCase, LowerCase, Number/SpecialChar and min 8 Chars" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
                <label>Password</label>
                <span></span>
            </div>
            <div class="styled-input w3ls-text">
                <input type="password" name="matchingPassword" required="required" title="UpperCase, LowerCase, Number/SpecialChar and min 8 Chars" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
                <label>Matching Password</label>
                <span></span>
            </div>
            <input id="submit" type="submit" value="Send reset password email">
        </@form.form>
    </div>
</div>
</@body>