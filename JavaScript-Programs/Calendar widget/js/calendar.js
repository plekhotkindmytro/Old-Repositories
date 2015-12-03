var CalendarWidget = (function (){
	/**
		types of data that can be displayed in calendar
	*/
	var dataTypeToShow = {
		DAY: 0,
		MONTH: 1,
		YEAR: 2
	};
	
	/**
		important data
	*/
	var statics = {
		inputElement: null,
		calendarHead: null,
		calendarBody: null,
		chosenDate: null,
		shownDataType: dataTypeToShow.DAY
	};
	
	/**
		generates html string for week days
	*/
	var weekDaysTemplate = (function() {
		var weekDays =	["Sun","Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
		var template_ = "<ul class='widget-calendar-body-week-days'>";
				var i;
				for(i=0;i<weekDays.length;i++)
				{
					template_ += "<li>"+weekDays[i]+"</li>";
				}
				
			template_ += "</ul>";
			return template_;
	})();
	
	
	/**
		returns html string for days in month
	*/
	var getDaysInMonthTemplate = function() {
		var days = (function () {
			var days_=[],
				firstDateOfMonth = new Date(statics.chosenDate),
				currMonth_ = statics.chosenDate.getMonth();
				
			firstDateOfMonth.setDate(1);
			
			var firstDayOfMonth = firstDateOfMonth.getDay(),
				dateIterator = new Date(+firstDateOfMonth - firstDayOfMonth*24*60*60*1000),
				iDay_;
			while(currMonth_ !== dateIterator.getMonth())
			{
				iDay_ = dateIterator.getDate();
				days_[days_.length] = {day:iDay_, isNative: false};
				dateIterator.setDate(iDay_+1);
			}
			while(currMonth_ === dateIterator.getMonth())
			{
				iDay_ = dateIterator.getDate();
				days_[days_.length] = {day:iDay_, isNative: true};
				dateIterator.setDate(iDay_+1);
			}
			while(dateIterator.getDay() !== 0)
			{
				iDay_ = dateIterator.getDate();
				days_[days_.length] ={day:iDay_, isNative: false};
				dateIterator.setDate(iDay_+1);
			}
			
			return days_;
		})();
		
		var template_ = "<ul class='widget-calendar-body-month-days'>",
			i, chosenDay = statics.chosenDate.getDate();
		
		
		for(i=0; i<days.length;i++)
		{
			template_ +="<li class='native-"
					+days[i].isNative
					+(days[i].day===chosenDay?" chosen":"")
					+"'>"+days[i].day+"</li>";
		}
		template_ +="</ul>";
			
		return template_;
	};
	
	/**
		returns html string for months
	*/
	var getMonthsTemplate = function() {
		var template_ = "<ul class='widget-calendar-body-months'>",
		i, chosenMonth = statics.chosenDate.getMonth();
		for(i=0; i< MONTHS.length; i++)
		{
			template_ +="<li"
					+(i===chosenMonth?" class=' chosen'":"")
					+">"+MONTHS[i]+"</li>";
		}
		template_ +="</ul>";
			
		return template_;
	};
	
	/**
		returns html string for years
	*/
	var getYearsTemplate = function() {
		var template_ = "<ul class='widget-calendar-body-years'>",
			currDate_ = new Date(),
			currYear_ = currDate_.getFullYear(),
			firstYear_ =  currYear_ - 10,
			lastYear_ = currYear_ + 10,
			chosenYear = statics.chosenDate.getFullYear(),
			i;
		for(i=firstYear_; i<lastYear_;i++)
		{
			template_ +="<li" 
					+(i===chosenYear?" class=' chosen'":"")	
					+">"+i+"</li>";
		}
		template_ +="</ul>";
		statics.calendarHead.innerHTML = firstYear_+"-"+(lastYear_-1);
		
		return template_;
	};
	
	/**
		writes days into calendar body
	*/
	var loadDays =function() {
		statics.calendarBody.innerHTML = weekDaysTemplate + getDaysInMonthTemplate();
		statics.shownDataType = dataTypeToShow.DAY;
		statics.calendarHead.innerHTML = getFormatedDateString(statics.chosenDate, 'm d');
		
		bindContainerChildrenClick("widget-calendar-body-month-days", 'li', function(e) {
			if(e.target.className === "native-false") return;
			
			statics.chosenDate.setDate(e.target.innerHTML);
			reSelectDate(e.target);
		});
	};
	
	/**
		writes months into calendar body
	*/
	var loadMonth = function() {
		statics.calendarBody.innerHTML = getMonthsTemplate();
		statics.shownDataType = dataTypeToShow.MONTH;
		statics.calendarHead.innerHTML = statics.chosenDate.getFullYear();	
		
		bindContainerChildrenClick("widget-calendar-body-months", 'li', function(e) {
			
			statics.chosenDate.setMonth(MONTHS.indexOf(e.target.innerHTML));
			reSelectDate(e.target);
		});
	};
	
	/**
		writes years into calendar body
	*/
	var loadYears = function() {
		statics.calendarBody.innerHTML = getYearsTemplate();
		statics.shownDataType = dataTypeToShow.YEAR;
		bindContainerChildrenClick("widget-calendar-body-years", 'li', function(e) {
			
			statics.chosenDate.setYear(e.target.innerHTML);
			reSelectDate(e.target);
		});
	};
	
	/**
		clears previous selected items and selects new one
	*/
	function reSelectDate(selected) {
		var li_ = statics.calendarBody.getElementsByTagName('li');
			for(var i =0;i<li_.length;i++)
			{
				li_[i].className = li_[i].className.replace(" chosen", ""); 
			}
		selected.className += " chosen";
		statics.inputElement.value =getFormatedDateString(statics.chosenDate, 'MMM d');
	}
	
	/**
		'prev' button 'onclick'
	*/
	var bindPrevBtnClick = function() {
		var prevBtn = document.getElementById("widget-calendar-nav-prev-btn");
		prevBtn.onclick = function(e) {
			e.preventDefault();
			switch(statics.shownDataType) {
				case dataTypeToShow.MONTH:
					loadDays();
					break;
				case dataTypeToShow.YEAR:
					loadMonth();
					break;
			}
		};
		
	};
	
	/**
		'next' button 'onclick'
	*/
	var bindNextBtnClick = function() {
		var nextBtn = document.getElementById("widget-calendar-nav-next-btn");
		nextBtn.onclick = function(e) {
			e.preventDefault();
			switch(statics.shownDataType) {
				case dataTypeToShow.DAY:
					loadMonth();
					break;
				case dataTypeToShow.MONTH:
					loadYears();
					break;
			}
		};
	};
	
	/**
		'today' button 'onclick'
	*/
	var bindTodayBtnClick = function() {
		var todayBtn = document.getElementById("widget-calendar-today-btn");
		todayBtn.onclick = function(e) {
			e.preventDefault();
			statics.chosenDate = new Date();
			statics.inputElement.value = getFormatedDateString(statics.chosenDate, 'MMM d');
			
			switch(statics.shownDataType) {			
				case dataTypeToShow.DAY:
					loadDays();
					break;
				case dataTypeToShow.MONTH:
					loadMonth();
					break;
				case dataTypeToShow.YEAR:
					loadYears();
					break;
			}
		};
	};
	
	
	var getCalendarInnerTemplate = function() {
		var template_ = '<div class="widget-calendar-head">'
					+'<div class="widget-calendar-nav-prev">'
						+'<a href="#" id="widget-calendar-nav-prev-btn">&#9668;</a>'
					+'</div>'
					+'<div class="widget-calendar-month-name">'
						+ getFormatedDateString(statics.chosenDate, 'm d')	
					+'</div>'
					+'<div class="widget-calendar-nav-next">'
						+'<a href="#" id="widget-calendar-nav-next-btn">&#9658;</a>'
					+'</div>'
				+'</div>'
				+'<div class="widget-calendar-body">'	
				+'</div>'
				+'<div style="clear:both;"></div>'
				+'<div id="widget-calendar-today">'
					+'<a href="#" id="widget-calendar-today-btn">Today</a>'
				+'</div>';
		return template_;
	};
	
	/**
		create calendar and insert it into dom
	*/
	var createCalendar = function(id) {
			statics.chosenDate = new Date();
			var calendarInput = statics.inputElement = document.getElementById(id),
				calendar_ = document.createElement("div");
			calendar_.className = "widget-calendar hidden";
			calendar_.style.top = parseInt(calendarInput.offsetTop) + parseInt(calendarInput.offsetHeight) + 'px';
			calendar_.style.left = parseInt(calendarInput.offsetLeft) + 'px';
			document.body.appendChild(calendar_);
			
			calendar_.innerHTML = getCalendarInnerTemplate();
			
		statics.calendarHead = document.getElementsByClassName("widget-calendar-month-name")[0];
		statics.calendarBody = document.getElementsByClassName("widget-calendar-body")[0];
		
	};
	
	/**
		add clickable img for calendar
	*/
	var instertImageIntoInput = function() {
		var calendarInput = statics.inputElement;
		var img = document.createElement("img");
			img.id="calendar-logo";
			img.src = "img/calendar-logo.png";
			img.width = "19";
			img.height = "14";
			img.style.top = parseInt(calendarInput.offsetTop)  + "px";
			img.style.left = parseInt(calendarInput.offsetLeft) + parseInt(calendarInput.offsetWidth) - img.width+ "px";
			insertAfter(img, calendarInput);
			
			img.onclick = function() {
				var widget = document.getElementsByClassName("widget-calendar")[0];
				if (/hidden/.test(widget.className)) {
					widget.className = widget.className.replace(" hidden", "");
				} else {
					widget.className += " hidden";
				}
				
			};
	};
	
	
	return {
		/**
			calendar entry point. Initializes calendar-widget
		*/
		init: function(id) {
			
			createCalendar(id);
			instertImageIntoInput();
			
			loadDays();
			
			bindPrevBtnClick();
			bindNextBtnClick();
			bindTodayBtnClick();
			
		}
	}
})();

