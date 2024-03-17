package tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.LoggerUtils;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;  // - брать из документации или настроить автокод
import static utils.TestData.*;


public final class NavigationTest extends BaseTest {
    @Test (testName = "Test - landing on the Home Page")
    public void TestBaseUrlLanding() {
        getPage().navigate(BASE_URL);

        assertThat(getPage()).hasURL(BASE_URL + HOME_END_POINT);
    }

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


