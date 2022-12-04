const createError = require("http-errors");
const express = require("express");
const path = require("path");
const cookieParser = require("cookie-parser");
const logger = require("morgan");

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
const chromeOptions = new chromeDriver.Options();
const downloadPath = path.join("./public/downloads");

chromeOptions.addArguments("--headless");
chromeOptions.addArguments("--disable-gpu");
// chromeOptions.addArguments("--no-sandbox");
chromeOptions.setUserPreferences({
  "download.default_directory": downloadPath,
});

const chromeExample = async () => {
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
      until.elementLocated(By.className("filedrop")),
      10 * 1000
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
      10 * 1000
    );
    await driver
      .findElement(
        By.xpath(
          "//div[@class='filedrop filedrop-mvc fileplacement width-for-mobile']/input"
        )
      )
      .sendKeys(
        "/home/yeongori/workspace/Engineering-data-search-service/macro/public/images/testfile2.dwg"
      );

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
      10 * 1000
    );

    await driver.findElement(By.id("DownloadButton")).click();
    await driver.sleep(5 * 1000); // wait: Download 완료
    /* Download End */
  } catch (err) {
    console.log(err);
  }
  await driver.quit();
};

chromeExample();

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
