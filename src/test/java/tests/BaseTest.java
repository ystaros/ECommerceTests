package tests;   // - самый низкий доступ - с него надо начинать
//protected - в пределах одного package
//public - видно в пределах всего проекта
// default - parent and children только в пределах одного package
// private - внутри класса

import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
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
    private Browser browser;

    private BrowserContext context; // создали newContext - "слепок" страницы
    private Page page;   // создали страницу

    @BeforeSuite
        // настройка на весь фрэймворк - самый высокий уровень
//      package void checkIfPlaywrightCreatedAndBrowserLaunched() {  // the same: "package void"  or "void"
//        void checkIfPlaywrightCreatedAndBrowserLaunched() {
    void checkIfPlaywrightCreatedAndBrowserLaunched() {
//                LoggerUtils.logInfo(ConfigProperties.properties.toString()); // распечатывает properties,
        // но они должны быть public в файле ConfigProperties:  public static Properties properties = initProperties();
        // вывод будет: BaseTestMethod - {browserName1=chromium, browserName2=firefox, isHeadless3=false, 1slowMo1=1500, 1slowMo3=1500, 1slowMo2=1500, isHeadless2=false, isHeadless1=false, browserName3=webkit}

        ReportUtils.logReportHeader();

        if (playwright != null) {
//                        Reporter.log("-----------  Playwright created!!", true); // логи для TestNG
            LoggerUtils.logInfo("Playwright created.");
        } else {
            LoggerUtils.logFatal("FATAL: Playwright is NOT created.");
            System.exit(1); // выходим из системы с кодом ошибки 1
        }

        LoggerUtils.logInfo(ReportUtils.printLine());
    }

    @BeforeClass
    void launchBrowser() {
        browser = BrowserManager.createBrowser(playwright, ConfigProperties.ENVIRONMENT_CHROMIUM);

        if (browser.isConnected()) {
            LoggerUtils.logInfo(ReportUtils.printLine());
            LoggerUtils.logInfo("Browser " + browser.browserType().name() + " is connected.\n");
            LoggerUtils.logInfo(ReportUtils.printLine());
        } else {
            LoggerUtils.logFatal("FATAL: Browser is NOT connected.");
            System.exit(1); // выходим из системы с кодом ошибки 1
        }
    }

//        @Parameters({"browserOption", "isHeadless", "slowMo"})
//        @BeforeClass
//        void launchBrowser(String browserOption, String isHeadless, String slowMo) {
//            browser = BrowserManager.createBrowser(playwright, browserOption, isHeadless, slowMo);
//
//                if (browser.isConnected()) {
////                        System.out.println("Browser "+ browser.browserType().name() + " is connected.");
//
//                        LoggerUtils.logInfo("Browser " + browser.browserType().name() + " is connected.\n");
//                } else {
////                        System.out.println("FATAL: Browser is NOT connected.");
//                        LoggerUtils.logFatal("FATAL: Browser is NOT connected.");
//                        System.exit(1); // выходим из системы с кодом ошибки 1
//                }
//        }

        @BeforeMethod
        // настройка для тестов - самый низкий уровень
    void createContextAndPage(Method method) {

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
    void closeContext(Method method, ITestResult result) {

        if (page != null) {
            page.close();
            LoggerUtils.logInfo("Page closed.");
        }
        if (context != null) {
            context.close();
            LoggerUtils.logInfo("Context closed.");
        }

//                LoggerUtils.logInfo(
//                        "\n\n" + ReportUtils.getTestName(method) + " ---- " + ReportUtils.getTestRunTime(result)
//                                + ReportUtils.getLine() + "\n"
//                );
        ReportUtils.logTestResult(method, result);

    }

    @AfterClass
    void closeBrowser() {
        if (browser != null && browser.isConnected()) {
            browser.close();
            if(!browser.isConnected()) {
                LoggerUtils.logInfo(ReportUtils.printLine());
                LoggerUtils.logInfo("Browser " + browser.browserType().name() + " is closed");
                LoggerUtils.logInfo(ReportUtils.printLine());
            }
        }
    }

    @AfterSuite
    void closeBrowserAndPlaywright() {
        if (playwright != null) {
            playwright.close();
            LoggerUtils.logInfo("Playwright closed.");
        }
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

