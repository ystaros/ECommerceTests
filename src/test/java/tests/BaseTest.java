// Сделать data provider
// Cделаем кастомизированные репорты
// ДЗ перевести все sout в логи

package tests;   // - самый низкий доступ - с него надо начинать
//protected - в пределах одного package
//public - видно в пределах всего проекта
// default - parent and children только в пределах одного package
// private - внутри класса

import com.microsoft.playwright.*;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.LoggerUtils;
import utils.ReportUtils;
import utils.TestData;
import utils.runner.BrowserManager;
import utils.runner.ConfigProperties;

import java.lang.reflect.Method;

import static utils.TestData.BASE_URL;
import static utils.TestData.HOME_END_POINT;

public abstract class BaseTest {

        private final Playwright playwright = Playwright.create(); // создали и проинициализировали плэйрат

//        private final Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
//                .setHeadless(false).setSlowMo(1500));   // создали и проинициализировали браузер
//  вместо такой строки сделали следующую, (используя BrowserManager) чтобы унифицировать
//        private final Browser browser = BrowserManager.createBrowser(playwright, "chromium", false, 1500);
        private final Browser browser = BrowserManager.createBrowser(playwright, ConfigProperties.ENVIRONMENT_CHROMIUM);

        private BrowserContext context; // создали newContext - "слепок" страницы
        private Page page;   // создали страницу

        @BeforeSuite    // настройка на весь фрэймворк - самый высокий уровень
//      package void checkIfPlaywrightCreatedAndBrowserLaunched() {  // the same: "package void"  or "void"
        void checkIfPlaywrightCreatedAndBrowserLaunched() {
//                LoggerUtils.logInfo(ConfigProperties.properties.toString()); // распечатывает properties,
                // но они должны быть public в файле ConfigProperties:  public static Properties properties = initProperties();
                // вывод будет: BaseTestMethod - {browserName1=chromium, browserName2=firefox, isHeadless3=false, 1slowMo1=1500, 1slowMo3=1500, 1slowMo2=1500, isHeadless2=false, isHeadless1=false, browserName3=webkit}

                ReportUtils.logReportHeader();
                ReportUtils.logLine();

                if (playwright != null){
//                        System.out.println("Playwright created.");
//                        Reporter.log("-----------  Playwright created!!", true); // логи для TestNG
                        LoggerUtils.logInfo("Playwright created.");
                } else {
//                        System.out.println("FATAL: Playwright is NOT created.");
                        LoggerUtils.logFatal("FATAL: Playwright is NOT created.");
                        System.exit(1); // выходим из системы с кодом ошибки 1
                }

                if (browser.isConnected()) {
//                        System.out.println("Browser "+ browser.browserType().name() + " is connected.");
                        LoggerUtils.logInfo("Browser "+ browser.browserType().name() + " is connected.");
                } else {
//                        System.out.println("FATAL: Browser is NOT connected.");
                        LoggerUtils.logFatal("FATAL: Browser is NOT connected.");
                        System.exit(1); // выходим из системы с кодом ошибки 1
                }
        }

        @BeforeMethod   // настройка для тестов - самый низкий уровень
        void createContextAndPage(Method method) {

                ReportUtils.logLine();
                ReportUtils.logTestName(method);

                context = browser.newContext(); // создаем контекст (слепок страницы)
                LoggerUtils.logInfo("Context created.");

                page = context.newPage(); // создаем новую страницу
                LoggerUtils.logInfo("Page created.");

                LoggerUtils.logInfo("Start test.");

                getPage().navigate(BASE_URL);
                if (isOnHomePage()) {   // == true
                        LoggerUtils.logInfo("Base URL is opened and content is not empty.");
                } else {    // == false
                        LoggerUtils.logError("ERROR: Base URL is NOT opened OR content is EMPTY.");
                }
        }

        @AfterMethod
        void closeContext() {
                if (page != null){
                        page.close();
                        LoggerUtils.logInfo("Page closed.");
                }
                if (context !=null) {
                        context.close();
                        LoggerUtils.logInfo("Context closed.");
                }

                ReportUtils.logLine();
        }

        @AfterSuite
        void closeBrowserAndPlaywright() {
                if (browser != null){
                        browser.close();
                        LoggerUtils.logInfo("Browser closed.");
                }
                if (playwright !=null) {
                        playwright.close();
                        LoggerUtils.logInfo("Playwright closed.");
                }

                ReportUtils.logLine();
        }

        private boolean isOnHomePage() {
                getPage().waitForLoadState();  // ждем, чтобы контент подтянулся

                return getPage().url().equals(BASE_URL + HOME_END_POINT) && !page.content().isEmpty();    // эта строка заменила if-else написанный ниже
//
//                if (getPage().url().equals(BASE_URL) && !page.content().isEmpty()) {
//                        return true;
//                } else {
//                        return false;
//                }

        }

        Page getPage() {
                return page;        // возвращается страница
        }

        protected boolean getIsOnHomePage() {

                return isOnHomePage();
        }

}

