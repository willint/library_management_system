const LOCAL_STORAGE_KEY_PREFIX = 'hnaAPP_local_storage';
const SESSION_STORAGE_KEY_PREFIX = "hnaAPP_session_storage";
export default {

    localFetch: function (key) {
        var value = window.JSON.parse(window.localStorage.getItem(LOCAL_STORAGE_KEY_PREFIX + key) || '[]')
        if (value == "" || value == null || value == undefined) {
            return "";
        }
        return value;
    },
    localSave: function (key, items) {

        window.localStorage.setItem(LOCAL_STORAGE_KEY_PREFIX + key, window.JSON.stringify(items))

    },
    localRemove: function (key) {
        window.localStorage.removeItem(LOCAL_STORAGE_KEY_PREFIX + key)
    },
    localClear: function () {
        window.localStorage.clear();
    },
    sessionFetch: function (key) {
        var value = window.JSON.parse(window.sessionStorage.getItem(SESSION_STORAGE_KEY_PREFIX + key) || '[]')
        if (value == "" || value == null || value == undefined) {
            return "";
        }
        return value;
    },
    sessionSave: function (key, items) {
        window.sessionStorage.setItem(SESSION_STORAGE_KEY_PREFIX + key, window.JSON.stringify(items))
    },
    sessionRemove: function (key) {
        window.sessionStorage.removeItem(SESSION_STORAGE_KEY_PREFIX + key)
    }
}