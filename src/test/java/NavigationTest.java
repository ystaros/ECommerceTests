import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;  // - брать из документации или настроить автокод

public class NavigationTest {

    Playwright playwright = Playwright.create(); // инициализация плэйрата
    Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setHeadless(false).setSlowMo(1500));   // инициализация браузера
    Page page = browser.newPage(); // создаем страницу

    final String baseUrl = "http://localhost:3000/";

    @Test
    public void TestBaseUrlLanding() {
        page.navigate(baseUrl);
        assertThat(page).hasURL(baseUrl);
    }

    @Test
    public void testForHimNavigatesToForHimPage() { //структура: меню->страница
    page.navigate(baseUrl);

    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Men")).click();
    assertThat(page).hasURL("http://localhost:3000/men");
    }

}


