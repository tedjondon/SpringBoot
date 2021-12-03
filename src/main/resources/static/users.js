function printUserRoles(roles) {
    let printRoles = [];
    for (r in roles) {
        printRoles += roles[r].roleName.replace(/ROLE_/gi, " ") + " ";
    }
    return printRoles;
}

function setRolesID(someRoles) {
    let id
    if (someRoles.indexOf("USER") >= 0) {
        id = 2
    }
    if (someRoles.indexOf("ADMIN") >= 0) {
        id = 1
    }
    return id;
}

function rolesForSelection(roles) {
    let select = "";
    for (i in roles) {
        select +=
            "<option value=" + roles[i].roleName + ">" + roles[i].roleName.replace(/ROLE_/gi, "") + "</option>";
    }
    return select;
}


function allRolesForSelection() {
    return $.ajax(
        {
            url: "allRoles",
            method: "GET",
            dataType: "json"
        });
}


function selectRoles(idSelector) {
    let selectedRoles = [];
    $(idSelector).find(":selected").each(function () {
        setRolesID($(this).val());
        let currentRoleObject = {
            "id": setRolesID($(this).val()),
            "roleName": $(this).val()
        }
        selectedRoles.push(currentRoleObject);
    })
    return selectedRoles;
}

function showUserTable() {
    $.ajax(
        {
            url: "allUsers",
            method: "GET",
            dataType: "json"
        })
        .done(function (allUsers) {
            let allUsersTable = "";
            allUsers.forEach(function (user) {
                let roles = printUserRoles(user.roles);

                allUsersTable += `<tr> 
                <td>${user.id} </td> 
                <td>${user.firstName}</td>   
                <td>${user.lastName}</td>   
                <td>${user.age}</td>   
                <td>${user.email}</td>   
                 <td>${roles}</td>  
                <td>
                <button type="button" id="editShowModal" class="btn btn-info edit-btn"
                th:value="${user.id}" onclick= editShowModal("${user.id}")>
                
                Edit
                </button>
                </td>
                <td>
                <button type="button" class="btn btn-danger delete-btn"
                th:value="${user.id}" onclick = deleteShowModal("${user.id}")>
                Delete
                </button>
                </td>   
            </tr>`;
            })
            $("#allUsersTable").html(allUsersTable)
        })
        .fail(function () {
            alert("что то пошло не так");
        });
}

$(document).ready(function () {
        let promise = allRolesForSelection();

        showUserTable();
        promise.then(function (data) {
            $("#newUsersRoles").html(rolesForSelection(data));
        });
    }
)

function deleteShowModal(id) {
    $.ajax(
        {
            url: "get/user/" + id,
            method: "GET",
            dataType: "json"
        })
        .done(function (user) {
            $("#deleteId").val(user.id);
            $("#deleteFirstName").val(user.firstName);
            $("#deleteLastName").val(user.lastName);
            $("#deleteAge").val(user.age);
            $("#deleteEmail").val(user.email);
            $("#deleteUsersRoles").html(rolesForSelection(user.roles));
            $("#deleteModal").modal("show");
        })
        .fail(function () {
            alert("error");
        });
}

$("#deleteButton").click(function () {
        $.ajax(
            {
                url: "delete/" + $("#deleteId").val(),
                method: "Post",
            })
            .done(function () {
                showUserTable();
                $("#deleteModal").modal("hide");
            })
            .fail(function () {
                alert("что то пошло не так");
            });
    }
)

function editShowModal(id) {
    let promise = allRolesForSelection();
    $.ajax(
        {
            url: "get/user/" + id,
            method: "GET",
            dataType: "json"
        })
        .done(function (user) {
            $("#editId").val(user.id);
            $("#editFirstName").val(user.firstName);
            $("#editLastName").val(user.lastName);
            $("#editAge").val(user.age);
            $("#editEmail").val(user.email);
            $("#editOldPassword").val(user.password);
            promise.then(function (data) {
                $("#editUsersRoles").html(rolesForSelection(data));
            });
            $("#editModal").modal("show");
        })
        .fail(function () {
            alert("error");
        });
}


$("#editButton").click(function () {

        let password;
        let newPassword = $("#editNewPassword").val();

        if (newPassword) {
            password = newPassword;
        } else {
            password = $("#editOldPassword").val();
        }

        let user = {
            id: $("#editId").val(),
            firstName: $("#editFirstName").val(),
            lastName: $("#editLastName").val(),
            age: $("#editAge").val(),
            email: $("#editEmail").val(),
            pass: password,
            roles: selectRoles("#editUsersRoles")
        };

        $.ajax(
            {
                url: "edit",
                method: "POST",
                data: JSON.stringify(user),
                contentType: "application/json; charset=utf-8"
            })
            .done(function (response) {
                showUserTable()
                $("#editModal").modal("hide");
            })
            .fail(function (response) {
                alert("что то пошло не так");
            });
    }
)

$("#addNewUser-btn").click(function () {

    let user = {
        firstName: $("#addNewUserName").val(),
        lastName: $("#addNewUserLastName").val(),
        age: $("#addNewUserAge").val(),
        pass: $("#addNewUserPass").val(),
        email: $("#addNewUserEmail").val(),
        roles: selectRoles("#newUsersRoles")
    };
    $.ajax
    ({
        type: "POST",
        data: JSON.stringify(user),
        url: "save",
        contentType: "application/json; charset=utf-8"
    })
        .done(function () {
            showUserTable();
            alert("Новый пользователь по имени " + $("#addNewUserName").val() + " добавлен");
        })
        .fail(function () {
            alert("Проверьте введенные данные. Возможно, пользователь с таким именем существует");
        });
})


