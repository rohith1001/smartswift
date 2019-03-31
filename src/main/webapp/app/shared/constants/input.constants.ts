export const DATE_FORMAT = 'YYYY-MM-DD';
export const DATE_TIME_FORMAT = 'YYYY-MM-DDTHH:mm';
export const DATE_TIME_REGEX = '[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}';
export const DATE_FROM = function(){
    let today = new Date();
    today.setFullYear(today.getFullYear() - 10);
    return today.toISOString().substr(0, 16);
}();
export const DATE_TO = function(){
    let today = new Date();
    today.setFullYear(today.getFullYear() + 10);
    return today.toISOString().substr(0, 16);
}();
