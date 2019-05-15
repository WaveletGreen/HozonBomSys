$(document).ready(function () {
    //$('#calendar').eCalendar();
    $('#calendar').eCalendar({
    	weekDays: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
    	months: ['一月', '二月', '三月', '四月', '五月', '六月',
         '七月', '八月', '九月', '十月', '十一月', '十二月'],
	    events: [
	    	{title: 'Event Title 1', description: 'Description 1', datetime: new Date(2015, 3, 25, 15)},
	        {title: 'Event Title 2', description: 'Description 2', datetime: new Date(2015, 3, 30, 12)},
	        {title: 'Event Title 3', description: 'Description 3', datetime: new Date(2015, 3, 30, 17)},
	        {title: 'Event Title 4', description: 'Description 4', datetime: new Date(2015, 4, 1, 12)}
	    ]
});
});