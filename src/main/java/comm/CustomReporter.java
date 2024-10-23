package comm;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CustomReporter implements IReporter, ITestListener {
	
	@Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("<html><head><title>TestNG Custom Report</title></head><body>");
        reportContent.append("<h1>TestNG Custom Report</h1>");

        for (ISuite suite : suites) {
            reportContent.append("<h2>").append(suite.getName()).append("</h2>");

            // ISuiteResult is the correct type to retrieve
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult suiteResult : suiteResults.values()) {
                ITestContext context = suiteResult.getTestContext();
                
                reportContent.append("<h3>Test: ").append(context.getName()).append("</");
                		reportContent.append("<h3>Test: ").append(context.getName()).append("</h3>");

                        // Passed tests
                        reportContent.append("<h4>Passed Tests</h4><ul>");
                        for (ITestResult result : context.getPassedTests().getAllResults()) {
                            reportContent.append("<li>").append(result.getName()).append("</li>");
                        }
                        reportContent.append("</ul>");

                        // Failed tests
                        reportContent.append("<h4>Failed Tests</h4><ul>");
                        for (ITestResult result : context.getFailedTests().getAllResults()) {
                            reportContent.append("<li>").append(result.getName()).append("</li>");
                        }
                        reportContent.append("</ul>");

                        // Skipped tests
                        reportContent.append("<h4>Skipped Tests</h4><ul>");
                        for (ITestResult result : context.getSkippedTests().getAllResults()) {
                            reportContent.append("<li>").append(result.getName()).append("</li>");
                        }
                        reportContent.append("</ul>");
                    }
                }

                reportContent.append("</body></html>");

                // Write to file
                try (FileWriter writer = new FileWriter(outputDirectory + "/custom-emailable-report.html")) {
                    writer.write(reportContent.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
	
	
	@Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Method: " + result.getMethod().getMethodName());
        System.out.println("Instance: " + result.getInstanceName());
    }

    // Called when a test succeeds
    @Override
    public void onTestSuccess(ITestResult result) {
        long timeInSeconds = (result.getEndMillis() - result.getStartMillis()) / 1000;
        System.out.println("Test Passed: " + result.getMethod().getMethodName());
        System.out.println("Time (seconds): " + timeInSeconds);
    }

    // Called when a test fails
    @Override
    public void onTestFailure(ITestResult result) {
        long timeInSeconds = (result.getEndMillis() - result.getStartMillis()) / 1000;
        System.out.println("Test Failed: " + result.getMethod().getMethodName());
        System.out.println("Exception: " + result.getThrowable().getMessage());
        System.out.println("Time (seconds): " + timeInSeconds);
    }

    // Other ITestListener methods can be overridden as needed

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite Finished: " + context.getName());
    }
	
	

}
