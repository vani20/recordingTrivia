package trivia;

import atu.testrecorder.ATUTestRecorder;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class recordTrivia {
    static AndroidDriver<MobileElement> driver;
    private static Wait<MobileDriver<MobileElement>> wait = null;

    private static AppiumServiceBuilder service;
    static DesiredCapabilities capabilities = new DesiredCapabilities();

    public String nameOfAVD = "emulator-5554";

    @Test
    public void test1() throws Exception {
        // Get current date and time to provide in recorded video name.
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);

        File appDir = new File("resources/");
        File app = new File(appDir, "prod_swoo.apk");

        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, "com.kryptolabs.android.speakerswire.ui.SplashActivity,com.kryptolabs.android.speakerswire.ui.*.*");


        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        String sessionId = driver.getSessionId().toString();

        WebDriverWait wait = new WebDriverWait(driver, 15);

        //recorder start
        ATUTestRecorder recorder = new ATUTestRecorder("AllRecordings","triviaRec" + strDate,true);
        recorder.start();
        System.out.println("recording started");
        Thread.sleep(8000);
        //driver.findElement(By.xpath("//android.widget.ImageButton[contains(@resource-id, 'dismiss')]")).click();
        //driver.findElement(By.id("skip")).click();

        String googleIcon = "//android.view.ViewGroup[@index=3]/android.widget.ImageView[contains(@resource-id, 'social_icon')]";
        String mailLink = "com.google.android.gms:id/account_profile_picture";
        String allowBtn = "//android.widget.Button[contains(@resource-id, 'permission_allow_button')]";
        String triviaTab = "//android.support.v7.app.ActionBar.Tab[4]/android.widget.ImageView";

        // trivia code
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(googleIcon)));
        driver.findElement(By.xpath(googleIcon)).tap(1, 1);
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(mailLink)));
        driver.findElement(By.id(mailLink)).click();
        Thread.sleep(7000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(allowBtn)));
        if(driver.findElement(By.xpath(allowBtn)).isDisplayed()) {
            driver.findElement(By.xpath(allowBtn)).click();
            Thread.sleep(7000);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(triviaTab)));
        driver.findElement(By.xpath(triviaTab)).tap(1,1);
        Thread.sleep(10000);
        //TimeUnit.MINUTES.sleep(30);

        driver.quit();
        recorder.stop();
        System.out.println("recording stopped");
    }
}


//Appium Server started

       /* AppiumServiceBuilder builder = new AppiumServiceBuilder();
          AppiumDriverLocalService service = null;
       boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(4723);
            serverSocket.close();
        } catch (IOException e) {
            //If control comes here, then it means that the port is in use
            isServerRunning = true;
        } finally {
            isServerRunning = false;
        }

        if(isServerRunning == false) {
            builder.withIPAddress("127.0.0.1");
            builder.usingPort(4723);
            service = AppiumDriverLocalService.buildService(builder);
            service.start();

            System.out.println(" Appium server started successfully ");
        }*/
