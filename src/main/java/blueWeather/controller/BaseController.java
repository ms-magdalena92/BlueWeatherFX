package blueWeather.controller;

public abstract class BaseController {

    private final String viewFxmlFileName;

    public BaseController(String viewFxmlFileName) {
        this.viewFxmlFileName = viewFxmlFileName;
    }

    public String getViewFxmlFileName() {
        return viewFxmlFileName;
    }
}
