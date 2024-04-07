package tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.LoggerUtils;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;  // - брать из документации или настроить автокод
import static utils.TestData.*;


public final class NavigationTest extends BaseTest {
    @Test(testName = "Test - landing on the Home Page")
    public void TestBaseUrlLanding() {
        getPage().navigate(BASE_URL);

        assertThat(getPage()).hasURL(BASE_URL + HOME_END_POINT);
    }

//-------- Menu Navigation -------------------------------------------------------
    @DataProvider(name = "menuNavigationTestData")
    private Object[][] menuNavigationTestData() {

        return new Object[][]{
                {MEN, BASE_URL + MEN_END_POINT},
                {WOMEN, BASE_URL + WOMEN_END_POINT},
                {ACCESSORIES, BASE_URL + ACCESSORIES_END_POINT},
                {ABOUT, BASE_URL + ABOUT_END_POINT}
        };
    }

    @Test(dataProvider = "menuNavigationTestData")
    public void testMenuNavigatesToCorrespondingPage(String menuName, String correspondingPageEndpoint) {
        if (getIsOnHomePage()) {

            getPage().locator("//nav[@class='navbar']//li/a[text()='" + menuName + "']").click();

            assertThat(getPage()).hasURL(correspondingPageEndpoint);
        } else {
            Assert.fail();
        }
    }
//-------- end Menu Navigation -------------------------------------------------------

//-------- Banner Navigation -------------------------------------------------------
    @DataProvider(name = "bannerNavigationTestData")
    private Object[][] getBannerNavigationTestData() {

        return new Object[][]{
                {WOMEN_BANNER, BASE_URL + WOMEN_END_POINT},
                {MEN_BANNER, BASE_URL + MEN_END_POINT},
                {ACCESSORIES, BASE_URL + ACCESSORIES_END_POINT},
        };
    }

    @Test(dataProvider = "bannerNavigationTestData")
    public void testBannerNavigatesToCorrespondingPage(String bannerName, String correspondingPageEndpoint) {
        if (getIsOnHomePage()) {
            getPage().locator("//section[@class='collection-container']/a/p[text()='" + bannerName + "']").click();

            assertThat(getPage()).hasURL(correspondingPageEndpoint);
        } else {
            Assert.fail();
        }
    }
//-------- end Banner Navigation -------------------------------------------------------

//-------- Banner Navigation with number/index -------------------------------------------------------
    @DataProvider(name = "bannerNumberNavigationTestData")
    private Object[][] getBannerNumberNavigationTestData() {

        return new Object[][]{
                {0, BASE_URL + WOMEN_END_POINT},
                {1, BASE_URL + MEN_END_POINT},
                {2, BASE_URL + ACCESSORIES_END_POINT},
        };
    }

    @Test(dataProvider = "bannerNumberNavigationTestData")
    public void testBannerNumberNavigatesToCorrespondingPage(int bannerNumber, String correspondingPageEndpoint) {
        if (getIsOnHomePage()) {

            assertThat(getPage().locator("//section[@class='collection-container']/a/p")).hasCount(3);

            getPage().locator("//section[@class='collection-container']/a/p").nth(bannerNumber).click();

                assertThat(getPage()).hasURL(correspondingPageEndpoint);
        } else {
            Assert.fail();
        }
    }
//-------- end Banner Navigation with index -------------------------------------------------------

//-------- Navigation on Home Page -------------------------------------------------------

//    Перенесли DataProvider в TestData.java
//    @DataProvider(name = "navigationTestData")
//    private Object[][] getNavigationTestData() {
//
//        return new Object[][]{
//                {"//nav[@class='navbar']//li/a[text()='" + MEN + "']", -1, BASE_URL + MEN_END_POINT},
//                {"//nav[@class='navbar']//li/a[text()='" + WOMEN + "']", -1, BASE_URL + WOMEN_END_POINT},
//                {"//nav[@class='navbar']//li/a[text()='" + ACCESSORIES + "']", -1, BASE_URL + ACCESSORIES_END_POINT},
//                {"//nav[@class='navbar']//li/a[text()='" + ABOUT + "']", -1, BASE_URL + ABOUT_END_POINT},
//                {"//section[@class='collection-container']/a/p", 0, BASE_URL + WOMEN_END_POINT},
//                {"//section[@class='collection-container']/a/p", 1, BASE_URL + MEN_END_POINT},
//                {"//section[@class='collection-container']/a/p", 2, BASE_URL + ACCESSORIES_END_POINT},
//        };
//    }

    @Test(dataProvider = "navigationTestData", dataProviderClass = TestData.class)
    public void testNavigationElementNavigatesToCorrespondingPage(String locator, int index, String correspondingPageEndpoint) {
        if (getIsOnHomePage()) {
            if (index == -1) {
                getPage().locator(locator).click();
            } else {
                assertThat(getPage().locator(locator)).hasCount(3);

                getPage().locator(locator).nth(index).click();
            }

            assertThat(getPage()).hasURL(correspondingPageEndpoint);
        } else {
            Assert.fail();
        }
    }
//-------- end Navigation on Home Page -------------------------------------------------------

//---------------------------------------------------------------
    @Test
    public void testWomenNavigatesToWomenPage() {
        if (getIsOnHomePage()) {

//            getPage().navigate(BASE_URL);   // убрали и перенесли в if (getIsOnHomePage())

            getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(WOMEN).setExact(true)).click();

            assertThat(getPage()).hasURL(BASE_URL + WOMEN_END_POINT);
        } else {
            Assert.fail();
        }
    }

    @Test
    public void testMenNavigatesToMenPage() { //структура: меню->страница
        getPage().navigate(BASE_URL);
//    page.getByText(MEN).click(); // показать все локаторые, которые он видит
        getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(TestData.MEN).setExact(true)).click();

        assertThat(getPage()).hasURL(BASE_URL + MEN_END_POINT);
    }

    @Test
    public void testAccessoriesNavigatesToAccessoriesPage() {
        getPage().navigate(BASE_URL);
//        getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(ACCESSORIES).setExact(true)).click();
        getPage().getByRole(AriaRole.NAVIGATION).getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName(TestData.ACCESSORIES)).click();

//        getPage().locator("#navbar").getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("accessories")).click();

        assertThat(getPage()).hasURL(BASE_URL + ACCESSORIES_END_POINT);
    }

    @Test
    public void testAboutNavigatesToAboutPage() {
        getPage().navigate(BASE_URL);
        getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(ABOUT).setExact(true)).click();

        assertThat(getPage()).hasURL(BASE_URL + ABOUT_END_POINT);
    }


}


