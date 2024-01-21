package test;   // - самый низкий доступ - с него надо начинать
//protected - в пределах одного package
//public - видно в пределах всего проекта
// default - parent and children только в пределах одного package
// private - внутри класса

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

abstract class BaseTest {

        private final Playwright playwright = Playwright.create(); // создали и проинициализировали плэйрат
        private final Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false).setSlowMo(1500));   // создали и проинициализировали браузер
        private BrowserContext context; // создали newContext - "слепок" страницы
        private Page page;

        @BeforeSuite
//        package void checkIfPlaywrightCreatedAndBrowserLaunched() {
        void checkIfPlaywrightCreatedAndBrowserLaunched() {
                if (playwright != null){
                        System.out.println("Playwright created.");
                } else {
                        System.out.println("FATAL: Playwright is NOT created.");
                        System.exit(1); // выходим из системы с кодом ошибки 1
                }

                if (browser.isConnected()) {
                        System.out.println("Browser "+ browser.browserType().name() + " is connected.");
                } else {
                        System.out.println("FATAL: Browser is NOT connected.");
                        System.exit(1); // выходим из системы с кодом ошибки 1
                }
        }

        @BeforeMethod
        void createContextAndPage() {
                context = browser.newContext(); // создаем контекст (слепок страницы)
                System.out.println("Context created.");

                page = context.newPage(); // создаем новую страницу
                System.out.println("Page created.");
        }

        @AfterMethod
        void closeContext() {
                if (page != null){
                        page.close();
                        System.out.println("Page closed.");
                }
                if (context !=null) {
                        context.close();
                        System.out.println("Context closed.");
                }
                page.close();
                context.close();
        }

        @AfterSuite
        void closeBrowserAndPlaywright() {
                if (page != null){
                        page.close();
                        System.out.println("Browser closed.");
                }
                if (context !=null) {
                        context.close();
                        System.out.println("Playwright closed.");
                }
        }

        Page getPage() {
                return page;        // возвращается страница
        }
}

