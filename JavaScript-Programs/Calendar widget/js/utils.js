var MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
var FULL_MONTHS = new Array("January", "February", "March", 
"April", "May", "June", "July", "August", "September", 
"October", "November", "December");
function onLoad(f) {
	if(onLoad.loaded)
	{
		window.setTimeout(f, 0);
	} else if (window.addEventListener)
	{	
		window.addEventListener("load", f, false);
	} else if(window.attachEvent)
	{
		document.attachEvent( "onreadystatechange", f );
		//window.attachEvent("onLoad",f);
	}
}
onLoad.loaded = false;

function bindContainerChildrenClick(containerClass, childrenTag, method) {
	
	if(typeof method !== "function") return;
	var container = document.getElementsByClassName(containerClass)[0];
	if(!container) return;
	container.onclick = function(e) {
		e.preventDefault();
		e = e || window.event;
		var target = e.target || e.srcElement;
		while(target.nodeName.toUpperCase() !== childrenTag.toUpperCase()) {
			if( target === this ) {
				target = null;
				break;
			} else {
				target = target.parentNode;
			}
		}

		if(target) {
			// do something;
			method(e);
		}
	};
}

function getFormatedDateString (date, format) {
	var result_;
	switch(format) {
		case 'MMM d':
			result_ = MONTHS[date.getMonth()].toUpperCase() + " " + date.getDate();
		break;
		case 'm d':
			result_ = FULL_MONTHS[date.getMonth()] + " " + date.getFullYear();
		break;
		default:
		break;
	}
	
	return result_;
}

function insertAfter(elem, refElem) {
    return refElem.parentNode.insertBefore(elem, refElem.nextSibling);
}



// "getElementsByClassName" не определен IE<9, 
 if( typeof document.getElementsByClassName != 'function' ) {
        HTMLDocument.prototype.getElementsByClassName = Element.prototype.getElementsByClassName = function (className) {
            if( !className )
                return [];
            var elements = this.getElementsByTagName('*');
            var list = [];
            var expr = new RegExp( '(^|\\b)' + className + '(\\b|$)' );
            for (var i = 0, length = elements.length; i < length; i++){
                if (expr.test(elements[i].className)) {
                    list.push(elements[i]);
				}
			}
            return list;
        };
    }
