/**
 * js公用函数库
 * @since 2019-11-14
 */

/**
 * 日期转时间戳
 * @param date string 日期
 * @param type integer 0：10位时间戳 1：13位时间戳
 * @param zone integer 时区
 * @returns integer/long
 */
function dateToTimestamp(date, type = 1, zone = 0) {
	if (date.length == 8)
		date = date.substring(0,4)+"/"+date.substring(4,6)+"/"+date.substring(6,8);
	else {
		date = date.substring(0,19);
		date = date.replace(/-/g,'/');
	}
	var defOffset = new Date().getTimezoneOffset()*60;//时区差值
	var timestamp = new Date(date).getTime();
	if (isNaN(timestamp)) 
		return 0;
	//timestamp = timestamp + (defOffset+zone*3600)*1000;//如果需要时区
	return type == 1 ? timestamp : timestamp/1000;
}