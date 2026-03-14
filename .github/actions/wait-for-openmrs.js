const { chromium } = require('playwright');

(async () => {
  const browser = await chromium.launch();
  const page = await browser.newPage();

  const url = "http://localhost/openmrs/spa";
  const timeout = 10 * 60 * 60 * 1000; // 10 часов
  const start = Date.now();

  console.log("Waiting for OpenMRS SPA at:", url);

  while (true) {
    try {
      const response = await page.goto(url, { waitUntil: "domcontentloaded", timeout: 30000 });

      console.log("HTTP status:", response.status());

      const html = await page.content();
      console.log("Page size:", html.length);

      const found = await page.locator("#username").count();

      if (found > 0) {
        console.log("✅ Found username field. OpenMRS is ready.");
        break;
      } else {
        console.log("❌ Username field not found yet.");
      }

    } catch (e) {
      console.log("Page not ready yet:", e.message);
    }

    if (Date.now() - start > timeout) {
      console.error("❌ Timeout waiting for OpenMRS");
      process.exit(1);
    }

    console.log("Retrying in 10 seconds...\n");
    await new Promise(r => setTimeout(r, 10000));
  }

  await browser.close();
})();
