var Meotrics = {};
var host = "//dev.meotrics.com/";
function getCookie(name) {
	var value = "; " + document.cookie;
	var parts = value.split("; " + name + "=");
	if (parts.length == 2) return parts.pop().split(";").shift();
}
Meotrics.__visitorid = getCookie("meovisitorid");
Meotrics.getVisitorId = function () {
	return Meotrics.__visitorid;
};


Meotrics.set = function (data, done, fail) {
	$.post(host + 'set', data, function () {
		if (done !== undefined) done();
	}).fail(function () {
		if (fail !== undefined) fail()
	});
};

Meotrics.record = function (eventname, n1, n2, s1, s2, totalsec, done, fail) {
	$.post(host + 'record', {
		event: eventname,
		n1: n1,
		n2: n2,
		s1: s1,
		s2: s2,
		language: navigator.language,
		platform: navigator.platform,
		screenres: availWidth + "x" + availHeight,
		visitorid: Meotrics.getVisitorId(),
		appid: Meotrics.appid,
		referer: document.referrer,
		totalsec: totalsec,
		url: window.location.href
	}, function () {
		if (done !== undefined) done();
	}).fail(function () {
		if (fail !== undefined) fail();
	});
};


if (Meotrics.__visitorid === undefined) {
	$.post(host + 'new', function(){

	})
}