<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]/>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]/>
<#assign url = "${springMacroRequestContext.getRequestUri()}">
<#macro body title="Главная" styles=[] scripts=[]>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/resources/images/favicon.ico" rel="icon" type="image/x-icon"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <script type="application/javascript" src="/resources/js/jquery-3.1.1.min.js"></script>
    <script type="application/javascript" src="/resources/js/include_csrf.js"></script>
    <link href="/resources/ccs/style.css" rel="stylesheet" type="text/css"/>
    <link href='//fonts.googleapis.com/css?family=Viga' rel='stylesheet' type='text/css'>
    <script type="application/javascript">
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);
        function hideURLbar() {
            window.scrollTo(0, 1);
        } </script>
    <#list styles as style>
    ${style}
    </#list>
    <#list scripts as script>
    ${script}
    </#list>
    <title>${title}</title>
</head>
<body>
<div class="main">
    <div class="nav_w3l">
        <ul>
            <li <#if url?length ==1>class="active"</#if>><a class="hvr-sweep-to-bottom" href="/">Home</a></li>
            <@security.authorize access="isAnonymous()">
                <li <#if url?contains("login")>class="active"</#if>><a href="/login" class="hvr-sweep-to-bottom">Log
                    in</a>
                </li>
                <li <#if url?contains("register")>class="active"</#if>><a href="/register"
                                                                          class="hvr-sweep-to-bottom">Sign Up</a></li>
            </@security.authorize>
            <li <#if url?contains("about")>class="active"</#if>><a href="/about" class="hvr-sweep-to-bottom">About</a>
            </li>
            <@security.authorize access="isAuthenticated()">
                <li>
                    <@form.form action="/logout" method="post">
                        <button type="submit" class="hvr-sweep-to-bottom">Logout</button>
                    </@form.form>
                </li>
            </@security.authorize>
        </ul>
    </div>
    <#nested>
</div>
<footer class="copyright">
    <p>© 2016. All rights reserved | Design by <a href="http://w3layouts.com">W3layouts</a></p>
</footer>
</body>
</html>
</#macro>