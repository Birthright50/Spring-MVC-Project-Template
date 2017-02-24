<#include "../templates/body.ftl">
<@body title="Resend Verifiation Email">
<br><br>
    <#if tooManyResend??>

    <h2>Too many requests, bro, try again 5 minutes later.</h2><br>
    <#else>
    We sent a verification email again.
    </#if>
<h2><a href="/register?resend_token">Resend Verification Email</a></h2>
</@body>