<#include "../templates/body.ftl">
<@body title="Success Registration">
<br><br>

<h2>Success registration</h2>
<p>We have sent you a confirmation email to ${user.getEmail()}</p>
<br>
<p>Didn't get the email? <a href="/register?resend_token">Resend verification email</a> </p>
</@body>