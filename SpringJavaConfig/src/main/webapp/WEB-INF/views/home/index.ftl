<#include "../templates/body.ftl"/>

<@body title="Main Page"
styles=["<link href=\"/resources/ccs/style1.css\" rel=\"stylesheet\" type=\"text/css\"/>"]>
<h2>Hello,
    <@security.authorize access="isAnonymous()">
Anonymous
    </@security.authorize>

    <@security.authorize access="isAuthenticated()">
    Tupica
    </@security.authorize>
</h2>
</@body>