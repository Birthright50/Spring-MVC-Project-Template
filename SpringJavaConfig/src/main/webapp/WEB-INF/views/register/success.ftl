<#include "../templates/body.ftl">
<@body title="Success Registration">
<br><br>

<h2>Success registration</h2>
<h3>We have sent you a confirmation email to ${user.getEmail()}</h3>
<br>
<h4>Didn't get the email? <a href="/register?resend_token">Resend verification email</a> </h4>
</@body>