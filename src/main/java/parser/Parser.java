package parser;

import exceptions.NovaException;

public class Parser {


    public String[] parseBySpace(String action) throws NovaException {
        action = action.trim();
        String[] splitAction = action.split(" ", 2);
        if (splitAction.length == 1) {
            throw new NovaException("too little arguments or invalid command");
        }
        return splitAction;
    }

    public String[] splitBySlash(String action) {
        return action.split("/");
    }
}
