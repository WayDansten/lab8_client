package utility.management;

import exceptions.ErrorInFunctionException;
import utility.requests.Request;
import utility.requests.RequestForData;
import utility.requests.RequestWithFlatCreation;

import java.util.ArrayList;

public class RequestManager {
    private static RequestManager instance;
    private RequestManager(){}
    public static RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }
    private final ArrayList<String> interactiveCommandKeys = new ArrayList<>();
    {
        interactiveCommandKeys.add("add");
        interactiveCommandKeys.add("update");
    }
    private final ArrayList<String> noninteractiveCommandKeys = new ArrayList<>();
    {
        noninteractiveCommandKeys.add("remove_by_id");
        noninteractiveCommandKeys.add("help");
        noninteractiveCommandKeys.add("clear");
        noninteractiveCommandKeys.add("show");
        noninteractiveCommandKeys.add("info");
        noninteractiveCommandKeys.add("remove_greater");
        noninteractiveCommandKeys.add("remove_lower");
        noninteractiveCommandKeys.add("history");
        noninteractiveCommandKeys.add("filter_contains_name");
        noninteractiveCommandKeys.add("count_greater_than_house");
        noninteractiveCommandKeys.add("execute_script");
    }
    public Request selectRequest(String... arguments) throws ErrorInFunctionException {
        if (interactiveCommandKeys.contains(arguments[0].toLowerCase())) {
            RequestWithFlatCreation newRequest = new RequestWithFlatCreation(arguments[0].toLowerCase(), arguments[1]);
            newRequest.addExtraArgument();
            return newRequest;
        } else if (noninteractiveCommandKeys.contains(arguments[0].toLowerCase())) {
            return new RequestForData(arguments[0].toLowerCase(), arguments[1]);
        } else {
            System.err.println("Команда не найдена!");
            return null;
        }
    }
}
