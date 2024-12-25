/**
 * ajax 사용 시 공통화를 위한 js
 */

function ajax(url, method, async, data, successCallback, errorCallback, completeCallback) {
    $.ajax({
        url: url,
        type: method,
        data: data,
        async: async !== undefined ? async : true,
        success: function (data) {
            if (successCallback && typeof successCallback === 'function') {
                successCallback(data);
            }
        },
        error: function (error) {
            if (errorCallback && typeof errorCallback === 'function') {
                errorCallback(error);
            }
        },
        complete : function(result){
            if (completeCallback && typeof completeCallback === "function") {
                completeCallback(result);
            }
        }
    })
}

function ajaxToJson(url, method, async, data, successCallback, errorCallback, completeCallback) {
    $.ajax({
        url: url,
        type: method,
        data: JSON.stringify(data),
        async: async !== undefined ? async : true,
        contentType : 'application/json; charset=UTF-8',
        success: function (data) {
            if (successCallback && typeof successCallback === 'function') {
                successCallback(data);
            }
        },
        error: function (error) {
            if (errorCallback && typeof errorCallback === 'function') {
                errorCallback(error);
            }
        },
        complete : function(result){
            if (completeCallback && typeof completeCallback === "function") {
                completeCallback(result);
            }
        }
    })
}

/**
 * Promise 를 return 하는 ajax
 * @param {String }url
 * @param {String} method
 * @param data
 * @returns {Promise<unknown>}
 */
function ajaxToJsonPromise(url, method, data) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            method: method,
            data: JSON.stringify(data),
            success: resolve,
            error: reject
        });
    });
}
