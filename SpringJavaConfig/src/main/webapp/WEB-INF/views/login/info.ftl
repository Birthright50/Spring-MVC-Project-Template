<#include "../templates/body.ftl"/>

<@body title="Information">
<#if tooManyResend??>
    <h2>Try again later, we send password reset email less than 10 minutes ago</h2>
<#else>
<h2>${message}</h2>
</#if>
</@body>