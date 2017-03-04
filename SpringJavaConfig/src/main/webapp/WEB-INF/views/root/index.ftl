<#include "../templates/body.ftl"/>

<@body title="Home Page">
<h2>Hello,
    <@security.authorize access="isAnonymous()">
Anonymous
    </@security.authorize>

    <@security.authorize access="isAuthenticated()">
    <@security.authentication property="principal.username"/>
    </@security.authorize>
</h2>
</@body>