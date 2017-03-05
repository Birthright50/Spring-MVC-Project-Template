<#include "../templates/body.ftl">
<@body
title = "Registration"
styles=["<link href=\"/resources/ccs/style1.css\" rel=\"stylesheet\" type=\"text/css\"/>"]>
<div class="agilesign-form">
    <div class="agileits-top">

        <@form.form modelAttribute="user" id="reg" action="/register" method="post">
            <div class="styled-input w3ls-text">
                <@form.input path="username" title="The length from 3 to 15, with no special characters" pattern="^[a-zA-Z0-9_-]{3,15}$" type="text" required="required"/>

                <label>Username</label>
                <span></span>
            </div>
            <div id="username_error" class="error">
                <p>This Username already exists</p>
                <hr>
            </div>
            <div class="styled-input w3ls-text">
                <@form.input path="email" type="text" required="required" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$"/>
                <label>Email</label>
                <span></span>
            </div>
            <div id="email_error" class="error">
                <p>This Email already exists</p>
                <hr>
            </div>
            <div class="styled-input w3ls-text">
                <@form.password path="password" title="UpperCase, LowerCase, Number/SpecialChar and min 8 Chars" required="required" pattern="(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$"/>

                <label>Password</label>
                <span></span>
            </div>

            <div class="styled-input w3ls-text">
                <@form.password title="UpperCase, LowerCase, Number/SpecialChar and min 8 Chars" pattern="(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$" path="matchingPassword" required="required"/>

                <label>Confirm Password</label>
                <span></span>
            </div>
            <div id="matching_error" class="error">
                <p>Match the password correct</p>
                <hr>
            </div>
            <div class="wthree-text">
                <p>
                    By signing up, you agree to the Terms of Service and Privacy Policy, including Cookie Use. Others
                    will be able to find you by searching for your email address or phone number when provided.
                </p>
            </div>
            <div class="agileits-bottom">
                <input id="submit" type="submit" value="Sign Up">
            </div>
        </@form.form>
    </div>
    <div class="w3agile-btm">
        <p>Already a member? <a href="/login">Log in</a></p>
    </div>
</div>
<script type="application/javascript">
    $(document).ready(function () {
        (function () {
            var $form = $('form#reg');
            var $errors = $form.find(".error");
            var $username_error = $form.find("#username_error");
            var $email_error = $form.find("#email_error");
            var $matching_error = $form.find("#matching_error");
            var $inputs = $form.find(':input');
            $inputs.on('keyup', function () {
                $errors.removeClass('show-error');
            });
            var $username = $('#username');
            var $email = $('#email');
            var $password = $('#password');
            var $matchingPassword = $('#matchingPassword ');
            $form.on('submit', function (event) {
                if ($form.attr("data-checked")) {
                    return true;
                }
                if (!($password.val() == $matchingPassword.val())) {
                    $matching_error.addClass("show-error");
                    setTimeout(function () {
                        $matching_error.addClass("trans-width");
                    }, 300);
                }
                else {
                    $.ajax({
                        url: "/register/check_user",
                        method: 'post',
                        data: {
                            username: $username.val(),
                            email: $email.val()
                        },
                        success: function (data) {
                            switch (data) {
                                case 'OK':
                                    $form.attr("data-checked", "1");
                                    $form.submit();
                                    break;
                                case 'EMAIL_EXISTS':
                                    $email_error.addClass("show-error");
                                    setTimeout(function () {
                                        $email_error.addClass("trans-width");
                                    }, 300);
                                    break;
                                case 'USERNAME_EXISTS':
                                    $username_error.addClass("show-error");
                                    setTimeout(function () {
                                        $username_error.addClass("trans-width");
                                    }, 300);
                            }
                        }
                    });
                }
                event.preventDefault();
            });
        })();
    })
    ;
</script>
</@body>
