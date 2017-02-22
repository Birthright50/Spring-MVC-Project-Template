<#include "../templates/body.ftl">
<@body
title = "Registration"
styles=["<link href=\"/resources/ccs/style1.css\" rel=\"stylesheet\" type=\"text/css\"/>"]>

<div class="agilesign-form">
    <div class="agileits-top">

        <@form.form modelAttribute="user" id="reg" action="/registration" method="post">

            <div class="styled-input w3ls-text">
                <@form.input path="username" pattern=".{3,}" type="text" required="required"/>
                <label>User Name</label>
                <span></span>
            </div>
            <div class="styled-input w3ls-text">
                <@form.input path="email" type="text" required="required" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$" />
                <label>Email ID</label>
                <span></span>
            </div>
            <input type="hidden" id="hidden_elen" required="required"/>
            <div class="styled-input w3ls-text">
                <@form.password   path="password" required="required" pattern=".{6,}"/>
                <label>Password</label>
                <span></span>
            </div>
            <div class="styled-input w3ls-text">
                <@form.password pattern=".{6,}" path="matchingPassword" required="required"/>
                <label>Confirm Password</label>
                <span></span>
            </div>
            <div class="wthree-text">
                <p>
                    <input type="checkbox" id="brand">
                    <label for="brand">
                        <span></span>
                        I accept the terms of use
                    </label>
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
            var $form = $('#reg');
            var $username = $('#username');
            var $email = $('#email');
            var $checkbox = $('#brand');
            var $label = $('label[for="' + $checkbox.attr('id') + '"]');
            var $label_span = $label.children();
            var $password = $('#password');
            var $button = $('#submit');
            var $matchingPassword = $('#matchingPassword ');
            var $inputs = $form.find('input:not(:checkbox)');
            $inputs.on('keyup change', function () {
                var length = $(this).val().length;
                if (length != 0 || $(this).is(":focus")) {
                    $(this).siblings('label').css('top', '-1.5em', 'color', 'orange');
                }
                else {
                    if (length == 0) {
                        $(this).siblings('label').css('top', '0');
                    }
                }
            });

            $form.on('submit', function (event) {
                if ($form.attr("data-checked")) {
                    return true;
                }
                if (!$checkbox.is(":checked")) {
                    $label.css('color', 'red');
                    $label_span.css('border-color', 'red');
                } else {
                    if (!($password.val() == $matchingPassword.val())) {
                    }
                    else {
                        $.ajax({
                            url: "/registration/check_user",
                            method: 'post',
                            data: {
                                username: $username.val(),
                                email: $email.val()
                            },
                            success: function (data) {
                                if (data == "OK") {
                                    $form.attr("data-checked", "1");
                                    $form.submit();
                                }
                                else {
                                    alert(data);
                                }
                            }
                        });
                    }
                }
                event.preventDefault();
            });
        })();
    });

</script>
</@body>
