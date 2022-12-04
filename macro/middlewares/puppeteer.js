const puppeteer = require('puppeteer')
const path = require('path')
const downloadPath = path.resolve('public/images/010_자동요금징수설비 구성도.dwg');

const fileUploadAndDownload = async () => {
    const browser = await puppeteer.launch({headless: true}) // 브라우저 정의
    const page = await browser.newPage(); // 신규 페이지 open

    await page.goto('https://products.aspose.app/cad/text-extractor/dwg') // 페이지로 이동

    const [fileChooser] = await Promise.all([
        page.waitForFileChooser(),
        page.click('#UploadFileInput-6510804868252500')
    ])

    await fileChooser.accept(['public/images/010_자동요금징수설비 구성도.dwg'])

    await page.waitForSelector('#fileupload-1')
    await page.click('.convertbtn')

    // 변환까지 대기

    const page2 = await browser.newPage();
    await page2.goto('https://products.aspose.app/cad/text-extractor/dwg/navigation/result')

    await page.waitForSelector('.downloadbtn')
    await page.click('.downloadbtn')

    await browser.close();
}

module.exports = fileUploadAndDownload