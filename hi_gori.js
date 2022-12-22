const decompress = require('decompress');
const fs = require('fs');

let test = "test.zip"

const fct = async () => {
    decompress(test, "./")
        .then((files)=>{
            console.log(files);
        })
        .catch((error)=>{
            console.log(error);
        });
}
fct();