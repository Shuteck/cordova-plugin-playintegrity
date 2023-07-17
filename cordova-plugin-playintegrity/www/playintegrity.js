var exec = require('cordova/exec');

exports.checkIntegrity = function(nonce, success, error) {
    exec(success, error, "playIntegrity", "checkIntegrity", [nonce]);
};
