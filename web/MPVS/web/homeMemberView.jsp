<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<script language="javascript" type="text/javascript" src="js/plugins/full-calendar/fullcalendar.min.js"></script> 
<script language="javascript" type="text/javascript" src="js/plugins/flot/jquery.flot.pie.js"></script> 

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="main-content">
    <div class="row-fluid">
        <div class="span8">
            <div class="box color_1">
                <div class="title">
                    <h4>Exams</h4>  
                </div>
                <div class="content">
                    <div class="row">
                        <div class="content top ">
                            <div id='calendar'> 
                            </div>
                        </div>

                    </div>

                </div>
            </div> 
        </div>
        <div class="span4">
            <div class="box color_21">
                <div class="title">
                    <h2><span id="examTitle"></span> <small id="subjectTitle"></small></h2>  
                </div>
                <div class="content  row-fluid">
                    <div id="donut" class="graph" style="width:100%;height:260px;"></div>
                    <div class="row-fluid fluid">
                        <button class="btn btn-large span5" id="pre">Previous</button>
                        <button class="btn btn-large span5 offset2" id="next">Next</button>
                    </div>

                </div>
            </div>
            <div class="box color_1">
                <div class="title">
                    <h2>Messages</h2>
                </div>
                <div class="content row-fluid " id="messageContent" style="height: 225px;">
                    <c:forEach var="r" items="${rList}">
                        <div class="well" id="${r.requestId.id}">
                            ${r.requestId.subject}
                        </div>
                    </c:forEach>
                </div>


            </div>
        </div>
    </div>
</div>
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
<script type="text/javascript">
    /**** Specific JS for this page ****/
    var results;
    var rIndex=0;
    $(document).ready(function() {
        
        
        
        var content=$('#messageContent').html();
        if($.trim(content)=="")
        {
            $('#messageContent').html('No data found .');
        }
        
             
        var content=$('#examTitle').html();
        if($.trim(content)=="")
        {
            $('#examTitle').html('No Result found .');
        }
        
        
        
        
        
        
        
        
        
        $('#pre').click(function(){
            if(rIndex>0)
            {
                rIndex--;
                myDonut(results[rIndex]);
                    
            }
        });
        $('#next').click(function(){
            if(rIndex<results.length){
                rIndex++;
                myDonut(results[rIndex]);
    
            }
            
        });

        $.ajax({
            type:'POST',url:'ajcall',
            data:{func:'getResultStatus'},
            dataType:'JSON'
        }).done(function(msg){
            results=msg;
            myDonut(results[0]);
            
        });
        $('.well').click(function(){
            $.ajax({
                type:'POST',url:'ajcall',
                data:{id:this.id,func:'getMessagePopUp'},
                dataType:'HTML'
            }).done(function(msg){
                $('#myModal').html(msg);
                $('#myModal').modal('show');
                
            });
        });
        var calendar = $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            selectable: true,
            selectHelper: true,
            editable: true,
            droppable:true,
            disableResizing :true,
            eventDrop :function(event,dayDelta,minuteDelta,allDay,revertFunc){
                    
                $.ajax({
                    url:'ajcall',
                    type:'GET',
                    data:{func:"setExams",id:event.id,held:event.start}
                }).done(function(data){
                    console.log(event);
                });
            },
            eventClick:function( event, jsEvent, view ){
                $.ajax({
                    type:'POST',url:'ajcall',
                    data:{id:event.id,func:'getExamView'},
                    dataType:'HTML'
                }).done(function(msg){
                    $('#myModal').html(msg);
                    $('#myModal').modal('show');
                
                });
            },

            events:function(start,end,callback){
                $.getJSON('ajcall',{func:'getExams',from:start,to:end},function(data){
                    var events = [];
                    $(data).each(function() {
                        events.push({
                            id: this.id,
                            title: this.title,
                            start: new Date(this.start), 
                            end: new Date(this.end) 
                        });
                    });
                    callback(events);
                });
            }
        });
     
       
                    
    });
            
            
            
            
    function myDonut(mydata)
    {
        $('#examTitle').html(mydata.exam_name);
        $('#subjectTitle').html(mydata.subject_name);
        var status=[];
        var series = Math.floor(Math.random()*5)+1;
        status[0] = { label: "Pass",data: parseInt(mydata.pass), color: "#cb4b4b" };
        status[1] = { label: "Fail", data: parseInt(mydata.fail), color: "#4da74d"};
        status[2] = { label: "Absent", data: parseInt(mydata.absent), color: "#edc240"};


        $.plot($("#donut"), status,
        {
            series: {
                pie: { 
                    show: true,
                    innerRadius: 0.42,
                    highlight: {
                        opacity: 0.3
                    },
                    radius: 1,
                    stroke: {
                        color: '#fff',
                        width: 4
                    },
                    startAngle: 0,
                    combine: {
                        color: '#353535',
                        threshold: 0.05
                    },
                    label: {
                        show: true,
                        radius: 1,
                        formatter: function(label, series){
                            return '<div class="chart-label">'+label+'&nbsp;'+Math.round(series.percent)+'%</div>';
                        }
                    }
                },
                grow: { active: false}
            },
            legend:{show:false},
            grid: {
                hoverable: true,
                clickable: true
            }
        });
    }
 
    
          
</script>
