var page = require('webpage').create(), system = require('system'), content, output;

content = system.args[1];
output = system.args[2];
page.content = content;
page.paperSize = {
	format : "A4",
	orientation : 'portrait',
	margin : '1cm'
};
window.setTimeout(function() {
	page.render(output);
	phantom.exit();
}, 200);