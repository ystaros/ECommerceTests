package test;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;
import utils.TestData;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;  // - брать из документации или настроить автокод
import static utils.TestData.*;


public final class NavigationTest extends BaseTest {
    @Test
    public void TestBaseUrlLanding() {
        getPage().navigate(BASE_URL);

        assertThat(getPage()).hasURL(BASE_URL + HOME_END_POINT);
    }

    @Test
    public void testForHimNavigatesToForHimPage() { //структура: меню->страница
    getPage().navigate(BASE_URL);
//    page.getByText(MEN).click(); // показать все локаторые, которые он видит
    getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(TestData.MEN).setExact(true)).click();

    assertThat(getPage()).hasURL(BASE_URL + MEN_END_POINT);
    }

    @Test
    public void testWomenNavigatesToWomenPage() {
        getPage().navigate(BASE_URL);
        getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(WOMEN).setExact(true)).click();

        assertThat(getPage()).hasURL(BASE_URL + WOMEN_END_POINT);
    }
}


