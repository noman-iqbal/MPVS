<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="row-fluid">
    <div class="span17">
        <div class="box color_13">
            <div class="title">
                <h4>
                    <span>title</span>
                </h4>
            </div>
            <div class="content">
                <form class="form-horizontal row-fluid"  action="" method="POST" id="addUserForm" enctype="multipart/form-data" autocomplete="off">
                    <input name="type" type="hidden" value="${typeSt}"/>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field">Subject Id</label>
                        <div class="controls span7">
                            <input type="text" id="normal-field uniId" name="id" class="row-fluid">
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field">Name</label>
                        <div class="controls span7">
                            <input type="text" id="normal-field email" class="row-fluid"name="name">
                        </div>
                    </div>
                    <div class="form-row control-group row-fluid">
                        <label class="control-label span2" for="normal-field">Credit hr </label>
                        <div class="controls span7">
                            <input type="number" id="normal-field email" class="row-fluid"name="cHr">
                        </div>
                    </div>

                    <div class="form-actions row-fluid">
                        <%
                            if (request.getAttribute("error") != null) {
                        %>
                        <div class="error form-row control-group row-fluid span7" >
                            <%=request.getAttribute("error")%>
                        </div>    
                        <%
                            }
                        %>   
                        <div class="span7 offset2">
                            <button type="submit" class="btn btn-primary">Save changes</button>
                            <button type="reset" class="btn btn-secondary">Cancel</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        $("#addUserForm").validate({
            rules: {
                id: {
                    required: true
                }
               
            
            },
            messages: {
                id:{
                    required:"Please provide an ID"
                }
                
            }
        });
        
        
        
        
    });
</script>