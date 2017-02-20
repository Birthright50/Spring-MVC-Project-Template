<#include "../templates/body.ftl"/>

<@body title="Main Page">
<h2>Hello,
    <@security.authorize access="isAnonymous()">
Anonymous
    </@security.authorize>

    <@security.authorize access="isAuthenticated()">
    Tupica
    </@security.authorize>
</h2>
</@body>