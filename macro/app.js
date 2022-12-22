const createError = require("http-errors");
const express = require("express");
const path = require("path");
const cookieParser = require("cookie-parser");
const logger = require("morgan");
const fs = require("fs");
const JSZip = require('jszip');

const indexRouter = require("./routes/index");
const usersRouter = require("./routes/users");

const app = express();

app.set("views", path.join(__dirname, "views"));
app.set("view engine", "jade");

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, "public")));

app.use("/", indexRouter);
app.use("/users", usersRouter);

app.use(function (req, res, next) {
    next(createError(404));
});

const { Builder, Browser, By, Key, until } = require("selenium-webdriver");
const chromeDriver = require("selenium-webdriver/chrome");
const { type } = require("os");
const chromeOptions = new chromeDriver.Options();
const downloadPath = path.join("./public/downloads");

chromeOptions.addArguments("--headless");
chromeOptions.addArguments("--disable-gpu");
chromeOptions.addArguments("--no-sandbox");
chromeOptions.setUserPreferences({
    "download.default_directory": downloadPath,
});

const crawling = async (filePath) => {
    if (filePath == undefined || filePath == "") return;
    const driver = await new Builder()
        .forBrowser(Browser.CHROME)
        .setChromeOptions(chromeOptions)
        .build();

    await driver.manage().window().maximize();

    try {
        /* Upload Start */
        await driver.get("https://products.aspose.app/cad/text-extractor/dwg");

        await driver.wait(
            // wait: file 업로드 element 렌더링
            until.elementLocated(
                By.className(
                    "filedrop filedrop-mvc fileplacement width-for-mobile"
                )
            ),
            5 * 1000
        );
        await driver.wait(
            // wait: file 업로드 element 활성화
            until.elementIsEnabled(
                driver.findElement(
                    By.xpath(
                        "//div[@class='filedrop filedrop-mvc fileplacement width-for-mobile']/input"
                    )
                )
            ),
            8 * 1000
        );
        await driver
            .findElement(
                By.xpath(
                    "//div[@class='filedrop filedrop-mvc fileplacement width-for-mobile']/input"
                )
            )
            .sendKeys(`${filePath}`);

        await driver.wait(
            // wait: 변환 버튼 활성화
            until.elementIsEnabled(driver.findElement(By.id("uploadButton"))),
            10 * 1000
        );
        await driver.findElement(By.id("uploadButton")).click();
        /* Upload End */

        /* Download Start */
        await driver.wait(
            //wait: Download 페이지로의 redirection
            until.elementIsVisible(driver.findElement(By.id("DownloadButton"))),
            100 * 1000
        );
        await driver.wait(
            // wait: Download 버튼 활성화
            until.elementIsEnabled(driver.findElement(By.id("DownloadButton"))),
            7 * 1000
        );

        await driver.findElement(By.id("DownloadButton")).click();
        await driver.sleep(3 * 1000); // wait: Download 완료
        /* Download End */
    } catch (err) {
        console.log(err);
    }
    await driver.quit();
};
let commonPath ="/home/icecoffee/Engineering-data-search-service/macro/public/upload";
let totalFilePath = "";
let unzipPath = "/home/icecoffee/Engineering-data-search-service/macro/public/downloads"
const getFilePath = (curPath) => {
    fs.readdir(curPath, (err, FileList) => {
        if (FileList == undefined) return;
        for (let i = 0; i < FileList.length; i++) {
            if (FileList[i].search(".dwg") == -1) {
                let newPath = path.join(curPath + "/" + FileList[i]);
                getFilePath(newPath);
            } else {
                let newPath = path.join(curPath + "/" + FileList[i]);
                totalFilePath = totalFilePath + "\n" + newPath;
            }
        }
    });
};
// const unZip = (filePath)=>{
//     fs.readFile( filePath, function(err, data){
//         if(!err){
//             for(let i=0;i<data.length;i++){
//                 if(data[i].search(".zip")!=-1){
//                     let zip = new JSZip();
//                     JSZip.loadAsync(data).then(function(zip){
//                         Object.keys(zip.files).forEach(function(filename){
//                             zip.files[filename].async('string').then(function(fileData){
//                                 console.log(fileData)
//                             })
//                         });
//                     });
//                 }
//             }
//         }
//     });
// };



process.setMaxListeners(0);
getFilePath(commonPath);
//unZip(unzipPath);
setTimeout(async () => {
    console.log("HIHI")
    console.log(totalFilePath);
    console.log("HI")
    const files = totalFilePath.split("\n");
    let cnt = 0;
    for (const f of files) {
         setTimeout(async ()=>{
             await console.log(f);
         },1000);
    }
    //files.forEach(async(element)=>{
    //    console.log(element)
    

    // const files = totalFilePath.split("\n");
    // let cnt = 0;
    // files.forEach(async (element) => {
    //     await crawling(element);
    //     cnt++;
    //     console.log(element);
    //     if (cnt == 9) {
    //         setTimeout(() => {}, 2000);
    //         cnt = 0;
    //     }
    //});
}, 5000);
// crawling();
// error handler
app.use(function (err, req, res, next) {
    // set locals, only providing error in development
    res.locals.message = err.message;
    res.locals.error = req.app.get("env") === "development" ? err : {};

    // render the error page
    res.status(err.status || 500);
    res.render("error");
});

module.exports = app;
