function hexToBase64(input) {
    var sub0 = input.replace(/\r|\n/g, "");
    var sub1 = sub0.replace(/([\da-fA-F]{2}) ?/g, "0x$1 ");
    var sub2 = sub1.replace(/ +$/, "");
    var ready = sub2.split(" ");
    return btoa(ready.reduce(function (data, byte) {
        return data + String.fromCharCode(byte);
    }, ''));
}
