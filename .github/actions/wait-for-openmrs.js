// wait-for-openmrs.js
const { chromium } = require('playwright');

(async () => {
  const browser = await chromium.launch();
  const page = await browser.newPage();
  const url = process.argv[2];
  const timeout = parseInt(process.argv[3], 10);

  console.log(`Waiting for OpenMRS SPA at ${url}...`);

  await page.goto(url);

  // Ждём появления input[name="username"]
  await page.waitForSelector('input[name="username"]', { timeout });

  console.log("OpenMRS SPA is ready!");
  await browser.close();
})();