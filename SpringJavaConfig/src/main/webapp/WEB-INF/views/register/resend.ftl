<#include "../templates/body.ftl">
<@body title="Resend Verifiation Email">
<br><br>
    <#if tooManyResend??>
    <h2>Too many requests.</h2>
    <p>Bro, try again 5 minutes later.</p><br>
    <#else>
    <p>We sent a verification email again.</p>
    </#if>

<p>
    <a href="/register?resend_token">Resend Verification Email</a>
</p>
</@body>