package com.softteco.toolset.sample;

import com.softteco.toolset.AbstractApplicationModule;
import com.softteco.toolset.restlet.AbstractRestletApplication;

/**
 *
 * @author serge
 */
public class ApplicationModule extends AbstractApplicationModule {

    @Override
    protected String getJpaUnitName() {
        return "sample-unit";
    }

    @Override
    protected Class<? extends AbstractRestletApplication> getRestletApplication() {
        return RestletApplication.class;
    }

//    public static final String CLIENT_ID_PROPERTY_NAME = "google.plus.client.id";
    @Override
    protected void configureApplication() {
//        final File propertyFile = new File("/opt/statics.properties");
//
//        final Properties properties = new Properties();
//        if (propertyFile.exists()) {
//            try {
//                properties.load(new FileInputStream(propertyFile));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            properties.setProperty(CLIENT_ID_PROPERTY_NAME, "227562485678-5n8jidgvv664guv3ng83n83dapirpq54.apps.googleusercontent.com");
//            properties.setProperty("google.plus.client.secret", "OxTAKdaa_wD44MjMgtVGA4kT");
//            new RuntimeException(propertyFile.getAbsolutePath() + " was not found. We'll use default values.").printStackTrace(System.out);
//        }
//        Names.bindProperties(binder(), properties);
//
//        filter("/api/*").through(LogFilter.class);
//
//        final Map<String, String> params = new HashMap<String, String>();
//        params.put("org.restlet.application", "com.softteco.statistics.rest.StatisticsApplication");
//        bind(StatisticServerServlet.class).in(Scopes.SINGLETON);
//        serve("/api/*").with(StatisticServerServlet.class, params);
//        serve("/importTimeRecords").with(TimeRecordImportServlet.class);
//        serve("/importPersons").with(PersonsImportServlet.class);
//        serve("/importSalaries").with(PersonsSalaryImportServlet.class);
//        serve("/report/forInvoice").with(ReportForInvoiceServlet.class);
    }
}
