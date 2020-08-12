<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/includes.jsp" %>
<meta charset='utf-8' />
<link href='<c:url value="/calendar/core/main.css" />' rel='stylesheet' />
<link href='<c:url value="/calendar/daygrid/main.css" />' rel='stylesheet' />
<script src='<c:url value="/calendar/core/main.min.js" />'></script>
<script src='<c:url value="/calendar/interaction/main.min.js" />'></script>
<script src='<c:url value="/calendar/daygrid/main.min.js" />'></script>
<script>
  var calendar;
  var loggedTime = [];

  document.addEventListener('DOMContentLoaded', function() {
    var Calendar = FullCalendar.Calendar;
    var Draggable = FullCalendarInteraction.Draggable

    var containerEl = document.getElementById('external-events-list');
	<c:if test="${enableSubmit}">
    new Draggable(containerEl, {
      itemSelector: '.fc-event',
      eventData: function(eventEl) {
        return {
          title: eventEl.innerText.trim() + " - " + document.getElementById("timeToLog").innerHTML
        }
      }
    });
	</c:if>

    var calendarEl = document.getElementById('calendar');
    calendar = new Calendar(calendarEl, {
      plugins: [ 'dayGrid' ],
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
      },
      editable: false,
      droppable: ${enableSubmit},
      eventLimit: true,
      businessHours: true,
      drop: function(arg) {
      },
      eventReceive: function(info) {
	    loggedTime.push(info.event);
		updateChangeHistory();
      },
	  events: ${eventsList}
    });
    calendar.render();

  });

</script>
<style>

  body {
    font-size: 14px;
  }

  #wrap {
    width: 1100px;
    margin: 0 auto;
  }

  #external-events {
    float: left;
    width: 150px;
    padding: 0 10px;
    border: 1px solid #ccc;
    background: #eee;
    text-align: left;
  }

  #external-events h4 {
    font-size: 16px;
    margin-top: 0;
    padding-top: 1em;
  }

  #external-events .fc-event {
    margin: 10px 0;
    cursor: pointer;
  }

  #external-events p {
    margin: 1.5em 0;
    font-size: 11px;
    color: #666;
  }

  #external-events p input {
    margin: 0;
    vertical-align: middle;
  }

  #calendar {
    float: right;
    width: 900px;
  }

ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
}
  li {
	font-size: 11px;
	text-decoration: none;
	display: block;
	width: 200px;
	padding-top: 5px
}

li:last-child {
	border: none;
}

</style>
</head>
<body>
    <%@ include file="/common/header.jsp" %>


		  <div id='wrap'>


		      <div id='external-events'>

			<c:if test="${enableSubmit}">
		      <input type="range" name="points" min="30" max="480" value="240" step="30" style="width:100%" onchange="updateTimeToLog(this.value)"/>

		      <p id="timeToLog" style="text-align:center;font: 20px bold Arial, sans-serif;">4h</p>

			    <form id="mySuperForm" method="post">
		          <input id="entries" name="entries" type="hidden" value="" />
		        </form>
		      <p>
				  <button onclick="commitChanges()" style="width:100%">Commit</button>
		      </p>
			</c:if>

		      <h4>Entries:</h4>

		      <div id='external-events-list'>

				<c:forEach var="type" items="${summaryListing}">
					<div class='fc-event'>
		            	${type}
					</div>
				</c:forEach>
		      </div>

			<c:if test="${enableSubmit}">
		      <p>
				<button onclick="undoChanges()" style="width:100%">Undo</button>
		      </p>
			</c:if>
		      <p id="changeHistory" style="text-align:left;font: 15px bold Arial, sans-serif;"></p>
		    </div>

		    <div id='calendar'></div>

		    <div style='clear:both'></div>

		  </div>


	<script>

	function myFunction2(item) {

	    var projectId = item.title.substring(0, item.title.indexOf("."));

	    var tempString = item.title.substring(item.title.indexOf("- ")+1);
	    var hours = parseInt(tempString.substring(0, tempString.indexOf("h")));
		tempString = tempString.substring(tempString.indexOf("h") + 1);
	    var minutes = 0;

		if (tempString.length > 0) {
	        minutes = parseInt(tempString.substring(0, tempString.indexOf("m")));
		}

		var timeSpent = hours * 60 + minutes;

		var date = new Date(item.start)
		var datestring = date.getFullYear() + "/" + ("0"+(date.getMonth()+1)).slice(-2) + "/" + ("0" + date.getDate()).slice(-2);

	    var abc = []

	    var theObject = {projectId:projectId, timeSpent:timeSpent, date:datestring};

		abc.push(theObject);
		abc.push(theObject);

		alert(JSON.stringify(abc));
	}
	function undoChanges(item) {
		if (loggedTime.length > 0) {
			var y = loggedTime.pop();
			y.remove();
			updateChangeHistory();
		}
	}

	function commitChanges(item) {
	    var events = loggedTime;
	    var output = []

	    for (index = 0; index < events.length; ++index) {
			var projectId = events[index].title.substring(0, events[index].title.indexOf("."));
			var hours = 0;
			var minutes = 0;

			var tempString = events[index].title.substring(events[index].title.lastIndexOf("- ")+1);
			if (tempString.indexOf("h") > 0) {
				hours = parseInt(tempString.substring(0, tempString.indexOf("h")));
				tempString = tempString.substring(tempString.indexOf("h") + 1);
			}

			if (tempString.length > 0) {
				minutes = parseInt(tempString.substring(0, tempString.indexOf("m")));
			}

			var timeSpent = hours * 60 + minutes;

			var date = new Date(events[index].start)
			var datestring = date.getFullYear() + "-" + ("0"+(date.getMonth()+1)).slice(-2) + "-" + ("0" + date.getDate()).slice(-2);

			var abc = []

			var theObject = {projectId:projectId, timeSpent:timeSpent, date:datestring};

			output.push(theObject);
	    }

	    document.getElementById("entries").value = JSON.stringify(output);
	    $("#mySuperForm").submit();
	}

	function updateChangeHistory() {
		text = "<ul>";
		for (i = 0; i < loggedTime.length; i++) {
		    var date = new Date(loggedTime[i].start)
			var datestring = date.getFullYear() + "/" + ("0"+(date.getMonth()+1)).slice(-2) + "/" + ("0" + date.getDate()).slice(-2);

			text += "<li><strong>" + datestring + "</strong><br>&nbsp;&nbsp;&nbsp;" + loggedTime[i].title + "</li>";
		}
		text += "</ul>";

		document.getElementById("changeHistory").innerHTML = text;
	}

	function updateTimeToLog(value) {
	  x = "";
	  hours = Math.floor(value/60);
	  minutes = value - hours * 60;

	  if (hours != 0) {
	      x = hours + "h";
	  }
	  if (minutes != 0) {
	      x += minutes + "m";
	  }

	  document.getElementById("timeToLog").innerHTML = x;
	}
	</script>

    <%@ include file="/common/footer.jsp" %>
</body>
</html>
