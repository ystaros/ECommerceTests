package utils;

import org.testng.annotations.DataProvider;

public final class TestData {
    public static final String BASE_URL = "http://localhost:3000";

    public static final String HOME_END_POINT = "/";
    public static final String WOMEN_END_POINT = "/women";
    public static final String MEN_END_POINT = "/men";
    public static final String ACCESSORIES_END_POINT = "/accessories";
    public static final String ABOUT_END_POINT = "/#footer-about";

    public static final String WOMEN = "women";
    public static final String MEN = "men";
    public static final String ACCESSORIES = "accessories";
    public static final String ABOUT = "About";
    public static final String WOMEN_BANNER = "Women \n Apparels";
    public static final String MEN_BANNER = "men apparels";
    public static final String ACCESSORIES_BANNER = "Accessories";

    @DataProvider(name = "navigationTestData")
    public static Object[][] getNavigationTestData() {

        return new Object[][]{
                {"//nav[@class='navbar']//li/a[text()='" + MEN + "']", -1, BASE_URL + MEN_END_POINT},
                {"//nav[@class='navbar']//li/a[text()='" + WOMEN + "']", -1, BASE_URL + WOMEN_END_POINT},
                {"//nav[@class='navbar']//li/a[text()='" + ACCESSORIES + "']", -1, BASE_URL + ACCESSORIES_END_POINT},
                {"//nav[@class='navbar']//li/a[text()='" + ABOUT + "']", -1, BASE_URL + ABOUT_END_POINT},
                {"//section[@class='collection-container']/a/p", 0, BASE_URL + WOMEN_END_POINT},
                {"//section[@class='collection-container']/a/p", 1, BASE_URL + MEN_END_POINT},
                {"//section[@class='collection-container']/a/p", 2, BASE_URL + ACCESSORIES_END_POINT},
        };
    }
}