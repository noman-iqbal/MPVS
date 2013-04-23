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
            <div class="box color_1">
                <div class="title">
                    <h2>Results</h2>  
                </div>
                <div class="content  row-fluid">
                    <c:forEach var="rs" items="${rsList}">
                        <div class="well result" id="${rs.id}">
                            ${rs.resultId.examId.title}
                            <span class="small">${rs.resultId.examId.sbId.name}</span>
                        </div>
                    </c:forEach> 

                </div>
            </div>
            <div class="box color_1">
                <div class="title">
                    <h2>Messages</h2>
                </div>
                <div class="content row-fluid ">
                    <c:forEach var="r" items="${rList}">
                        <div class="well message" id="${r.requestId.id}">
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
    $(document).ready(function() {
        $('.message').click(function(){
            $.ajax({
                type:'POST',url:'ajcall',
                data:{id:this.id,func:'getMessagePopUp'},
                dataType:'HTML'
            }).done(function(msg){
                $('#myModal').html(msg);
                $('#myModal').modal('show');
                
            });
        });
        $('.result').click(function(){
            $.ajax({
                type:'POST',url:'ajcall',
                data:{id:this.id,func:'getResultPopUp'},
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
            selectable: false,
            selectHelper: false,
            editable: false,
            droppable:false,
            disableResizing :false,
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
                //                $.ajax({
                //                    type:'POST',url:'ajcall',
                //                    data:{id:event.id,func:'getExamView'},
                //                    dataType:'HTML'
                //                }).done(function(msg){
                //                    $('#myModal').html(msg);
                //                    $('#myModal').modal('show');
                //                
                //                });
            },

            events:function(start,end,callback){
                $.getJSON('ajcall',{func:'getExams',from:start,to:end},function(data){
                    var events = [];
                    $(data).each(function() {
                        events.push({
                            id: this.id,
                            title: this.title,
                            start: new Date(this.start), 
                            end: new Date(this.end) // will be parsed
                        });
                    });
                    callback(events);
                });
            }
        });
        var data = [];
        var series = Math.floor(Math.random()*5)+1;
        data[0] = { label: "Pass1",data:42, color: "#cb4b4b" };
        data[1] = { label: "Fail1", data:27, color: "#4da74d"};
        data[2] = { label: "absent1", data:9, color: "#edc240"};
        $.plot($("#donut"), data,
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
                    
    });
            
            
            
            
            
 
    
          
</script>
