<%-- 
    Document   : index
    Created on : Feb 21, 2013, 5:21:32 PM
    Author     : noman
--%>
<div id="login_page"> 
    <div id="login">
        <div id="login_container">
            <div class="row-fluid">
                <div class="span5 row-fluid">
                    <img id="img" style="width: 250px;height: 200px;" src="${empty cookie.img.value?"img/user-identity-icon.png":cookie.img.value}"/>
                </div>
                <div class="span7 row-fluid">
                    <div class="title">
                        <span class="name" >${cookie.name.value}</span>
                        <span class="subtitle" >${cookie.title.value}</span>
                    </div>
                    <form id="loginForm" class="form-search" action="" method="POST">
                        <input type="hidden" name="functionName" value="loginUser">
                        <div class="input-append row-fluid">
                            <input type="text" class="row-fluid search-query" placeholder="Name" value="${cookie.user_name.value}" name="userName" id="user_name">
                        </div>
                        <div class="input-append row-fluid">
                            <input type="password" class="row-fluid search-query" placeholder="Password"id="pass" name="pass">
                            <a id="loginButton" onclick="$('#loginForm').submit();" class="btn color_1"></a>
                        </div>
                    </form>
                    <button id="with_dif" class="btn btn-danger">With Diffrant User</button>
                    <span class="help-block text_color_1">${error}</span> 
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $('#with_dif').click(function(){
        
        $('.name').html('M.P.V.S');
        $('.subtitle').html('');
        $('#user_name').val('');
        $('#pass').val('');
        $('#img').attr('src','img/user-identity-icon.png');
        $(this).hide();
    });             
</script>