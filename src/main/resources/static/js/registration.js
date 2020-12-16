$(document).ready(function () {
    var token = $("meta[name=_csrf]").attr("content");
    var header = $("meta[name=_csrf_header]").attr("content");
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

        var patterns = new Map();
        patterns.set("login", /^[a-zA-Z0-9]{1,15}$/);
        patterns.set("email", /^[a-z0-9_.-]+@[a-z0-9-]+\.([a-z]{1,6}\.)?[a-z]{2,6}$/i);
        patterns.set("name", /^[a-zA-Zа-яА-Я]{1,20}$/);
        patterns.set("password", /^[a-zA-Z0-9]{9,40}$/);

        function checkPattern(string, pattern) {
            if ($.trim(string).search(pattern) === 0) {
                return true;
            } else {
                return false;
            }
        }


        var loginTimer;
        var emailTimer;
        $("input").on("input", function () {
            $(this).parent().children().removeClass("active");
            let inputText = $(this).val();
            let inputName = $(this).attr("name");
            let pattern;
            if (inputName === "firstName" || inputName === "lastName") {
                pattern = patterns.get("name");
            } else {
                pattern = patterns.get(inputName);
            }
            if (checkNull(inputText)) {
                errors.delete(inputName + "-pattern");
                errors.delete(inputName + "-exists");
                return;
            }
            if (checkPattern(inputText, pattern)) {
                errors.delete(inputName + "-pattern");
                switch (inputName) {
                    case "login":
                        clearTimeout(loginTimer);
                        loginTimer = setTimeout(checkUnique, 1000, inputText, inputName);
                        break;
                    case "email":
                        clearTimeout(emailTimer);
                        emailTimer = setTimeout(checkUnique, 1000, inputText, inputName);
                        break;
                }

            } else {
                $("#error-" + inputName + "-pattern").addClass("active");
                errors.add(inputName + "-pattern");
            }
        });

        function checkUnique(inputText, inputName) {
            $.ajax({
                url: "/registration/check/" + inputName,
                data: "fieldText=" + inputText,
                type: "POST",
                success: function (res) {
                    if (res) {
                        errors.delete(inputName + "-exists");
                    } else {
                        $("#error-" + inputName + "-exists").addClass("active");
                        errors.add(inputName + "-exists");
                    }
                }
            });
        }

        function checkInputsNull() {
            $(".active").removeClass("active");
            let flag = true;
            if (checkNull($("input[name=login]").val())) {
                $("#error-login-null").addClass("active");
                flag = false;
            }
            if (checkNull($("input[name=email]").val())) {
                $("#error-email-null").addClass("active");
                flag = false;
            }
            if (checkNull($("input[name=password]").val())) {
                $("#error-password-null").addClass("active");
                flag = false;
            }

            return flag;
        }

        $("form[name=userForm]").submit(function (e) {
            e.preventDefault();
            checkUnique($("input[name=login]").val(), "login");
            checkUnique($("input[name=email]").val(), "email");
            if (errors.size === 0 && checkInputsNull()) {
                $.ajax({
                    url: "/registration",
                    data: $(this).serialize(),
                    type: "POST",
                    async: false,
                    success: function (res) {
                        if (res.validated) {
                            $(location).attr('href', '/registration/confirm');
                        } else {
                            $.each(res.errorMessages, function (key, value) {
                            });
                        }
                    },
                    error: function (res) {
                            $(location).attr('href', '/error');
                    }
                });
            }
        });
    }
);