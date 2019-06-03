$(document).ready(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    var errors = new Set();

    function checkNull(string) {
        if ($.trim(string) === "") {
            return true;
        } else {
            return false;
        }
    }


    var patternLogin = /^[a-zA-Z0-9]{1,15}$/;
    var patternEmail = /^[a-z0-9_.-]+@[a-z0-9-]+\.([a-z]{1,6}\.)?[a-z]{2,6}$/i;
    var patternName = /^[a-zA-Zа-яА-Я]{1,20}$/;
    var patternPassword = /^[a-zA-Z0-9]{9,40}$/;

    function checkPattern(string, pattern) {
        if ($.trim(string).search(pattern) === 0) {
            return true;
        } else {
            return false;
        }
    }


//CHECK LOGIN
    var loginTimer;
    $('input[name=login]').on('input', function () {
        $(this).parent().children().removeClass("active");
        clearTimeout(loginTimer);
        let login = $(this).val();
        if (checkNull(login)) {
            errors.delete("login-pattern");
            errors.delete("login-exists");
            return;
        }
        if (checkPattern(login, patternLogin)) {
            errors.delete("login-pattern");
            loginTimer = setTimeout(checkLogin, 1000, login);
        } else {
            $('#error-login-pattern').addClass("active");
            errors.add("login-pattern");
        }

    });

    function checkLogin(login) {
        $.ajax({
            url: '/check/login',
            data: 'login=' + login,
            type: 'POST',
            success: function (res) {
                console.log(res);
                if (res) {
                    errors.delete("login-exists");
                } else {
                    $('#error-login-exists').addClass("active");
                    errors.add("login-exists");
                }
            }
        });

    }


//CHECK EMAIL
    var emailTimer;
    $('input[name=email]').on('input', function () {
        $(this).parent().children().removeClass("active");
        clearTimeout(emailTimer);
        let email = $(this).val();
        if (checkNull(email)) {
            errors.delete("email-pattern");
            errors.delete("email-exists");
            return;
        }
        if (checkPattern(email, patternEmail)) {
            errors.delete("email-pattern");
            emailTimer = setTimeout(checkEmail, 1000, email);
        } else {
            $('#error-email-pattern').addClass("active");
            errors.add("email-pattern");
        }

    });

    function checkEmail(email) {
        $.ajax({
            url: '/check/email',
            data: 'email=' + email,
            type: 'POST',
            success: function (res) {
                console.log(res);
                if (res) {
                    errors.delete("email-exists");
                } else {
                    $('#error-email-exists').addClass("active");
                    errors.add("email-exists");
                }
            }
        });

    }


//CHECK FIRSTNAME
    $('input[name=firstName]').on('input', function () {
        $(this).parent().children().removeClass("active");
        let firstName = $(this).val();
        if (checkNull(firstName)) {
            errors.delete("firstName-pattern");
            return;
        }
        if (checkPattern(firstName, patternName)) {
            errors.delete("firstName-pattern");
        } else {
            $('#error-firstName-pattern').addClass("active");
            errors.add("firstName-pattern");
        }
    });


//CHECK LASTNAME
    $('input[name=lastName]').on('input', function () {
        $(this).parent().children().removeClass("active");
        let lastName = $(this).val();
        if (checkNull(lastName)) {
            errors.delete("lastName-pattern");
            return;
        }
        if (checkPattern(lastName, patternName)) {
            errors.delete("lastName-pattern");
        } else {
            $('#error-lastName-pattern').addClass("active");
            errors.add("lastName-pattern");
        }
    });

//CHECK PASSWORD
    $('input[name=password]').on('input', function () {
        $(this).parent().children().removeClass("active");
        let password = $(this).val();
        if (checkNull(password)) {
            errors.delete("password-pattern");
            return;
        }
        if (checkPattern(password, patternPassword)) {
            errors.delete("password-pattern");
        } else {
            $('#error-password-pattern').addClass("active");
            errors.add("password-pattern");
        }
    });


    function checkInputsNull() {
        $('.active').removeClass("active");
        let flag = true;
        if (checkNull($('input[name=login]').val())) {
            $('#error-login-pattern').addClass("active");
            flag = false;
        }
        if (checkNull($('input[name=email]').val())) {
            $('#error-email-pattern').addClass("active");
            flag = false;
        }
        if (checkNull($('input[name=password]').val())) {
            $('#error-password-pattern').addClass("active");
            flag = false;
        }

        return flag;
    }

    $('form[name=userForm]').submit(function (e) {
        e.preventDefault();
        if (errors.size === 0 && checkInputsNull()) {
            $.ajax({
                url: '/registration',
                data: $(this).serialize(),
                type: 'POST',
                success: function (res) {

                    if (res.validated) {

                        alert("+");

                    } else {
                        $.each(res.errorMessages, function (key, value) {
                            //$('input[name=' + key + ']').notify(value, "error");
                        });
                    }
                }
            });
            alert("NICE");
        } else {
            alert("hasErrors");
        }
    });
});