<#include "../templates/body.ftl"/>
<@body title="404 Not Found">
<h1>404</h1>
<#if SPRING_SECURITY_403_EXCEPTION??>

</#if>
<h2>ooops, something goes wrong</h2>
<h2>Page not found</h2>
<div class="more_w3ls">
    <a href="/">Return to home page</a>
</div>
<div class="wthree_social_icons">
    <div>
        <a href="#"><span>Facebook</span></a>
        <a href="#"><span>Twitter</span></a>
        <a href="#"><span>Tumblr</span></a>
        <a href="#"><span>LinkedIn</span></a>
        <a href="#"><span>Vimeo</span></a>
    </div>
</div>
</@body>